/*
 * Copyright (c) 2010 mobiaware.com.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.mobiaware.auction.event.action;

import static com.mobiaware.util.HttpRequestHelpers.getIntParameter;
import static com.mobiaware.util.HttpRequestHelpers.getStringParameter;
import static com.mobiaware.util.HttpResponseHelpers.outputJsonReponse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobiaware.auction.Auction;
import com.mobiaware.auction.Item;
import com.mobiaware.auction.data.DataService;
import com.mobiaware.auction.data.MySqlDataServiceImpl;
import com.mobiaware.servlet.HttpConstants;
import com.mobiaware.servlet.action.Action;
import com.mobiaware.servlet.action.ErrorConstants;

public class ItemActionList extends Action {
  private final transient DataService _dataService = new MySqlDataServiceImpl();

  @JsonProperty("items")
  private List<Item> _items;

  @JsonProperty("total")
  private int _total;

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

    Auction auction = getDataService().getAuction(pauctionuid);
    if (auction == null) {
      addError(ErrorConstants.E_102, ErrorConstants.E_102_MSG);
      outputJsonReponse(response, this);
      return;
    }

    // Non required parameters
    int pcategoryuid = getIntParameter(request, HttpConstants.PARAM_CATEGORYUID, -1);
    int pstart = getIntParameter(request, HttpConstants.PARAM_START, 0);
    int plength = getIntParameter(request, HttpConstants.PARAM_LENGTH, 500);
    String psort = getStringParameter(request, HttpConstants.PARAM_SORT, null);
    String pdir = getStringParameter(request, HttpConstants.PARAM_DIR, null);

    _items = getDataService().getItems(pauctionuid, pcategoryuid, pstart, plength, psort, pdir);
    _total = getDataService().getItemCount(pauctionuid, pcategoryuid);

    outputJsonReponse(request, response, this);
  }
}
