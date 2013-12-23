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

import static com.mobiaware.util.HttpRequestHelpers.getIntParameter;
import static com.mobiaware.util.HttpResponseHelpers.outputJsonReponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobiaware.auction.Auction;
import com.mobiaware.auction.data.DataService;
import com.mobiaware.auction.data.MySqlDataServiceImpl;
import com.mobiaware.servlet.HttpConstants;
import com.mobiaware.servlet.action.Action;

public class AuctionActionStop extends Action {
  private final transient DataService _dataService = new MySqlDataServiceImpl();

  @JsonProperty("auction")
  private Auction _auction;

  @JsonIgnore
  public DataService getDataService() {
    return _dataService;
  }

  @Override
  public void perform(final HttpServletRequest request, final HttpServletResponse response) {
    int pauctionuid = getIntParameter(request, HttpConstants.PARAM_AUCTIONUID, -1);

    checkRequiredMin(pauctionuid, 0);

    if (hasErrors()) {
      outputJsonReponse(response, this);
      return;
    }

    DateTime today = new DateTime();
    DateTime yesterday = today.minusDays(1);
    
    Auction auction = getDataService().getAuction(pauctionuid);
    auction = auction.toBuilder().endDate(yesterday.getMillis()).build();
    
    int uid = getDataService().editAuction(auction);
    _auction = getDataService().getAuction(uid);

    outputJsonReponse(request, response, this);
  }
}
