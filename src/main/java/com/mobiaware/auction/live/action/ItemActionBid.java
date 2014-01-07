/*
 * Copyright (c) 2010 mobiaware.com.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.mobiaware.auction.live.action;

import static com.mobiaware.util.HttpRequestHelpers.getDoubleParameter;
import static com.mobiaware.util.HttpRequestHelpers.getIntParameter;
import static com.mobiaware.util.HttpRequestHelpers.getStringParameter;
import static com.mobiaware.util.HttpResponseHelpers.outputJsonReponse;

import java.io.IOException;
import java.util.List;

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
import com.mobiaware.auction.Bid;
import com.mobiaware.auction.Device;
import com.mobiaware.auction.Item;
import com.mobiaware.auction.User;
import com.mobiaware.auction.data.DataService;
import com.mobiaware.auction.data.MySqlDataServiceImpl;
import com.mobiaware.auction.live.notify.NotificationEngine;
import com.mobiaware.auction.live.notify.PushNotification;
import com.mobiaware.auction.live.notify.WebNotification;
import com.mobiaware.servlet.HttpConstants;
import com.mobiaware.servlet.action.Action;
import com.mobiaware.servlet.action.ErrorConstants;

public class ItemActionBid extends Action {
  private static final String NAME = ItemActionBid.class.getSimpleName();
  private static final Logger LOG = LoggerFactory.getLogger(NAME);

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
    metricRegistry.meter("com.mobiaware.auction.bid").mark();

    String pbiddernumber = getStringParameter(request, HttpConstants.PARAM_BIDDERNUMBER, null);
    String ppassword = getStringParameter(request, HttpConstants.PARAM_PASSWORD, null);
    int pitemuid = getIntParameter(request, HttpConstants.PARAM_ITEMUID, -1);
    double pprice = getDoubleParameter(request, HttpConstants.PARAM_PRICE, 0);

    checkRequiredString(pbiddernumber, 32);
    checkRequiredString(ppassword, 128);
    checkRequiredMin(pitemuid, 0);
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
      addError(ErrorConstants.E_205, ErrorConstants.E_205_MSG);
      outputJsonReponse(response, this);
      return;
    }

    Item item = getDataService().getItem(pitemuid);
    if (item == null) {
      addError(ErrorConstants.E_201, ErrorConstants.E_201_MSG);
      outputJsonReponse(response, this);
      return;
    }

    Auction auction = getDataService().getAuction(item.getAuctionUid());
    if (auction == null) {
      addError(ErrorConstants.E_202, ErrorConstants.E_202_MSG);
      outputJsonReponse(response, this);
      return;
    }

    if (!auction.isActive()) {
      addError(ErrorConstants.E_203, ErrorConstants.E_203_MSG);
      outputJsonReponse(response, this);
      return;
    }

    Bid maxbid = getDataService().getMaxBidForItem(item.getUid());

    if (pprice < (maxbid.getBidPrice() + item.getIncPrice())) {
      addError(ErrorConstants.E_206, ErrorConstants.E_206_MSG);
      outputJsonReponse(response, this);
      return;    
    }
    
    Bid bid = Bid.newBuilder().itemUid(pitemuid).userUid(_user.getUid()).bidPrice(pprice).build();

    _item = getDataService().addBid(bid); // record bid (ALL bids get recorded)
    if (_item == null) {
      addError(ErrorConstants.E_204, ErrorConstants.E_204_MSG);
      outputJsonReponse(response, this);
      return;
    }

    if ((maxbid == null)
        || (bid.getBidPrice() > maxbid.getBidPrice())
        || ((bid.getBidPrice() == maxbid.getBidPrice()) && (bid.getUserUid() == maxbid.getUserUid()))) {
      // nobids or highest bid or high bidder made same bid (updates to last time)
      _item = _item.toBuilder().winner(_user.getBidderNumber()).build();
    }

    String jsonData = null;

    ObjectMapper mapper = new ObjectMapper();
    try {
      jsonData = mapper.writeValueAsString(this);
    } catch (JsonGenerationException e) {
      LOG.error("!EXCEPTION!", e);

      jsonData = null;
    } catch (JsonMappingException e) {
      LOG.error("!EXCEPTION!", e);

      jsonData = null;
    } catch (IOException e) {
      LOG.error("!EXCEPTION!", e);

      jsonData = null;
    }

    // Signal Real-Time Channel
    EventBus eventBus = (EventBus) context.getAttribute(NotificationEngine.EVENTBUS_REGISTRY);
    eventBus.post(new WebNotification("liveauction-item", jsonData));

    // Signal notification
    if ((maxbid != null) && (bid.getBidPrice() > maxbid.getBidPrice())
        && (bid.getUserUid() != maxbid.getUserUid())) {
      List<Device> devices = getDataService().getDevicesForUser(maxbid.getUserUid());
      String alertMessage = "You have been outbid on Item# " + item.getItemNumber() + ".";
      eventBus.post(new PushNotification(devices, alertMessage));
    }

    outputJsonReponse(response, jsonData);
  }
}
