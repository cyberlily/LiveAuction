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

package com.mobiaware.auction.event.action;

import static com.mobiaware.util.HttpRequestHelpers.getDoubleParameter;
import static com.mobiaware.util.HttpRequestHelpers.getIntParameter;
import static com.mobiaware.util.HttpRequestHelpers.getStringParameter;
import static com.mobiaware.util.HttpResponseHelpers.outputJsonReponse;

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

public class ItemActionEdit extends Action {
  private final transient DataService _dataService = new MySqlDataServiceImpl();

  @JsonProperty("uid")
  private int _uid = -1;

  @JsonIgnore
  public DataService getDataService() {
    return _dataService;
  }

  @Override
  public void perform(final HttpServletRequest request, final HttpServletResponse response) {
    int pauctionuid = getIntParameter(request, HttpConstants.PARAM_AUCTIONUID, -1);
    String pitemnumber = getStringParameter(request, HttpConstants.PARAM_ITEMNUMBER, null);
    String pname = getStringParameter(request, HttpConstants.PARAM_NAME, null);
    String pcategory = getStringParameter(request, HttpConstants.PARAM_CATEGORY, null);
    String pdescription = getStringParameter(request, HttpConstants.PARAM_DESCRIPTION, null);
    String pseller = getStringParameter(request, HttpConstants.PARAM_SELLER, null);
    double pvalprice = getDoubleParameter(request, HttpConstants.PARAM_VALPRICE, 0);
    double pminprice = getDoubleParameter(request, HttpConstants.PARAM_MINPRICE, 0);
    double pincprice = getDoubleParameter(request, HttpConstants.PARAM_INCPRICE, 0);

    checkRequiredMin(pauctionuid, 0);
    checkRequiredString(pitemnumber, 32);
    checkRequiredString(pname, 128);
    checkRequiredString(pcategory, 128);
    checkRequiredString(pcategory, 4096);
    checkRequiredString(pcategory, 128);
    checkRequiredMin(pvalprice, 0);
    checkRequiredMin(pminprice, 0);
    checkRequiredMin(pincprice, 0);

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

    Item item =
        Item.newBuilder().auctionUid(auction.getUid()).itemNumber(pitemnumber).name(pname)
            .category(pcategory).description(pdescription).seller(pseller).valPrice(pvalprice)
            .minPrice(pminprice).incPrice(pincprice).build();

    _uid = getDataService().editItem(item);

    outputJsonReponse(response, this);
  }
}
