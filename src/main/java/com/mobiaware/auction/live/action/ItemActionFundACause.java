package com.mobiaware.auction.live.action;

import static com.mobiaware.util.HttpRequestHelpers.getDoubleParameter;
import static com.mobiaware.util.HttpRequestHelpers.getIntParameter;
import static com.mobiaware.util.HttpRequestHelpers.getStringParameter;
import static com.mobiaware.util.HttpResponseHelpers.outputJsonReponse;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.EventBus;
import com.mobiaware.auction.Auction;
import com.mobiaware.auction.User;
import com.mobiaware.auction.data.DataService;
import com.mobiaware.auction.data.MySqlDataServiceImpl;
import com.mobiaware.auction.live.notify.NotificationEngine;
import com.mobiaware.auction.live.notify.WebNotification;
import com.mobiaware.servlet.HttpConstants;
import com.mobiaware.servlet.action.Action;
import com.mobiaware.servlet.action.ErrorConstants;

public class ItemActionFundACause extends Action {
  private static final String NAME = ItemActionFundACause.class.getSimpleName();
  private static final Logger LOG = LoggerFactory.getLogger(NAME);

  private final transient DataService _dataService = new MySqlDataServiceImpl();

  @JsonProperty("sum")
  private double _sum;

  @JsonProperty("user")
  private User _user;

  @JsonIgnore
  public DataService getDataService() {
    return _dataService;
  }

  @Override
  public void perform(final HttpServletRequest request, final HttpServletResponse response) {
    ServletContext context = request.getServletContext();

    MetricRegistry metricRegistry =
        (MetricRegistry) context.getAttribute(MetricsServlet.METRICS_REGISTRY);
    metricRegistry.meter("com.mobiaware.auction.fundacause").mark();

    String pbiddernumber = getStringParameter(request, HttpConstants.PARAM_BIDDERNUMBER, null);
    String ppassword = getStringParameter(request, HttpConstants.PARAM_PASSWORD, null);
    int pauctionuid = getIntParameter(request, HttpConstants.PARAM_AUCTIONUID, -1);
    double pprice = getDoubleParameter(request, HttpConstants.PARAM_PRICE, 0);

    checkRequiredString(pbiddernumber, 32);
    checkRequiredString(ppassword, 128);
    checkRequiredMin(pauctionuid, 0);
    checkRequiredMin(pprice, 0);

    if (hasErrors()) {
      outputJsonReponse(response, this);
      return;
    }

    _user = getDataService().getUserByBidderNumber(pbiddernumber);

    if ((_user != null) && (!BCrypt.checkpw(ppassword, _user.getPasswordHash()))) {
      _user = null;
    }

    if (_user == null) {
      addError(ErrorConstants.E_404, ErrorConstants.E_404_MSG);
      outputJsonReponse(response, this);
      return;
    }

    Auction auction = getDataService().getAuction(pauctionuid);
    if (auction == null) {
      addError(ErrorConstants.E_401, ErrorConstants.E_401_MSG);
      outputJsonReponse(response, this);
      return;
    }

    // if (!auction.isActive(new Date())) {
    // addError(ErrorConstants.E_402, ErrorConstants.E_402_MSG);
    // outputJsonReponse(response, this);
    // return;
    // }

    _sum = getDataService().fundACause(pauctionuid, _user.getUid(), pprice);

    String jsonData = null;

    ObjectMapper mapper = new ObjectMapper();
    try {
      jsonData = mapper.writeValueAsString(this);
    } catch (JsonGenerationException e) {
      LOG.error("!EXCEPTION!", e);
    } catch (JsonMappingException e) {
      LOG.error("!EXCEPTION!", e);
    } catch (IOException e) {
      LOG.error("!EXCEPTION!", e);
    }

    // Signal Real-Time Channel
    EventBus eventBus = (EventBus) context.getAttribute(NotificationEngine.EVENTBUS_REGISTRY);
    eventBus.post(new WebNotification("liveauction-fund", jsonData));

    outputJsonReponse(response, jsonData);
  }
}
