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

package com.mobiaware.auction.live;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;
import com.mobiaware.auction.live.action.AuctionActionStart;
import com.mobiaware.auction.live.action.AuctionActionStop;
import com.mobiaware.auction.live.action.DeviceActionSendMessage;
import com.mobiaware.auction.live.action.ItemActionBid;
import com.mobiaware.auction.live.action.ItemActionFundACause;
import com.mobiaware.auction.live.action.ItemActionMyBidItemList;
import com.mobiaware.auction.live.action.ItemActionMyWatchItemList;
import com.mobiaware.auction.live.action.ItemActionUpdates;
import com.mobiaware.auction.live.action.ItemActionWatch;
import com.mobiaware.servlet.HttpConstants;
import com.mobiaware.servlet.action.Action;
import com.mobiaware.servlet.action.InvalidAction;

public class LiveActionFactory {
  private LiveActionFactory() {
    // static only
  }

  public static Action getAction(final HttpServletRequest request) {
    Preconditions.checkNotNull(request);

    String paction = request.getParameter(HttpConstants.PARAM_ACTION);

    if (StringUtils.isBlank(paction)) {
      return (new InvalidAction());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_MYWATCHITEMLIST)) {
      return (new ItemActionMyWatchItemList());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_MYBIDITEMLIST)) {
      return (new ItemActionMyBidItemList());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_ITEMUPDATES)) {
      return (new ItemActionUpdates());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_BID)) {
      return (new ItemActionBid());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_WATCH)) {
      return (new ItemActionWatch());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_FUNDACAUSE)) {
      return (new ItemActionFundACause());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_DEVICEMESSAGE)) {
      return (new DeviceActionSendMessage());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_AUCTIONSTART)) {
      return (new AuctionActionStart());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_AUCTIONSTOP)) {
      return (new AuctionActionStop());
    }

    return (new InvalidAction());
  }
}
