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

package com.mobiaware.auction.live.action;

import static com.mobiaware.util.HttpRequestHelpers.getStringParameter;
import static com.mobiaware.util.HttpResponseHelpers.outputJsonReponse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.mobiaware.auction.Bid;
import com.mobiaware.auction.Item;
import com.mobiaware.auction.User;
import com.mobiaware.auction.data.DataService;
import com.mobiaware.auction.data.MySqlDataServiceImpl;
import com.mobiaware.servlet.HttpConstants;
import com.mobiaware.servlet.action.Action;
import com.mobiaware.servlet.action.ErrorConstants;

public class ItemActionMyBidItemList extends Action {
  private final transient DataService _dataService = new MySqlDataServiceImpl();

  @JsonProperty("items")
  private List<Item> _items;

  @JsonProperty("total")
  private int _total;

  @JsonProperty("user")
  private User _user;

  @JsonIgnore
  public DataService getDataService() {
    return _dataService;
  }

  @Override
  public void perform(final HttpServletRequest request, final HttpServletResponse response) {
    String pbiddernumber = getStringParameter(request, HttpConstants.PARAM_BIDDERNUMBER, null);
    String ppassword = getStringParameter(request, HttpConstants.PARAM_PASSWORD, null);

    checkRequiredString(pbiddernumber, 32);
    checkRequiredString(ppassword, 128);

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

    _items = Lists.newArrayList();

    List<Bid> bids = getDataService().getBidsByUser2(_user.getUid());
    for (Bid bid : bids) {
      Item item = Item.newBuilder().uid(bid.getItemUid()).build();
      _items.add(item);
    }

    _total = _items.size();

    outputJsonReponse(response, this);
  }
}
