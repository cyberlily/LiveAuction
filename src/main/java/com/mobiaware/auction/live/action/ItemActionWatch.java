package com.mobiaware.auction.live.action;

import static com.mobiaware.util.HttpRequestHelpers.getIntParameter;
import static com.mobiaware.util.HttpRequestHelpers.getStringParameter;
import static com.mobiaware.util.HttpResponseHelpers.outputJsonReponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobiaware.auction.Auction;
import com.mobiaware.auction.Item;
import com.mobiaware.auction.User;
import com.mobiaware.auction.Watch;
import com.mobiaware.auction.data.DataService;
import com.mobiaware.auction.data.MySqlDataServiceImpl;
import com.mobiaware.servlet.HttpConstants;
import com.mobiaware.servlet.action.Action;
import com.mobiaware.servlet.action.ErrorConstants;

public class ItemActionWatch extends Action {

  private final transient DataService _dataService = new MySqlDataServiceImpl();

  @JsonProperty("item")
  private Item _item;

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
    metricRegistry.meter("com.mobiaware.auction.watch").mark();

    String pbiddernumber = getStringParameter(request, HttpConstants.PARAM_BIDDERNUMBER, null);
    String ppassword = getStringParameter(request, HttpConstants.PARAM_PASSWORD, null);
    int pitemuid = getIntParameter(request, HttpConstants.PARAM_ITEMUID, -1);

    checkRequiredString(pbiddernumber, 32);
    checkRequiredString(ppassword, 128);
    checkRequiredMin(pitemuid, 0);

    if (hasErrors()) {
      outputJsonReponse(response, this);
      return;
    }

    _user = getDataService().getUserByBidderNumber(pbiddernumber);

    if ((_user != null) && (!BCrypt.checkpw(ppassword, _user.getPasswordHash()))) {
      _user = null;
    }

    if (_user == null) {
      addError(ErrorConstants.E_305, ErrorConstants.E_305_MSG);
      outputJsonReponse(response, this);
      return;
    }

    Item item = getDataService().getItem(pitemuid);
    if (item == null) {
      addError(ErrorConstants.E_301, ErrorConstants.E_301_MSG);
      outputJsonReponse(response, this);
      return;
    }

    Auction auction = getDataService().getAuction(item.getAuctionUid());
    if (auction == null) {
      addError(ErrorConstants.E_302, ErrorConstants.E_302_MSG);
      outputJsonReponse(response, this);
      return;
    }

    if (!auction.isActive()) {
      addError(ErrorConstants.E_303, ErrorConstants.E_303_MSG);
      outputJsonReponse(response, this);
      return;
    }

    Watch watch = Watch.newBuilder().userUid(_user.getUid()).itemUid(pitemuid).build();

    _item = getDataService().addWatch(watch);

    if (_item == null) {
      addError(ErrorConstants.E_304, ErrorConstants.E_304_MSG);
      outputJsonReponse(response, this);
      return;
    }

    outputJsonReponse(response, this);
  }
}
