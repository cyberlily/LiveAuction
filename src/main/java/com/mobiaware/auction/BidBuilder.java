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

package com.mobiaware.auction;

public class BidBuilder {
  private int _uid = -1;
  private int _itemUid = -1;
  private int _userUid = -1;
  private double _bidPrice;
  private long _bidDate;

  public BidBuilder() {
    // empty
  }

  public BidBuilder(final Bid bid) {
    _uid = bid.getUid();
    _itemUid = bid.getItemUid();
    _userUid = bid.getUserUid();
    _bidPrice = bid.getBidPrice();
    _bidDate = bid.getBidDate();
  }

  public BidBuilder uid(final int uid) {
    _uid = uid;
    return this;
  }

  public BidBuilder itemUid(final int itemUid) {
    _itemUid = itemUid;
    return this;
  }

  public BidBuilder userUid(final int userUid) {
    _userUid = userUid;
    return this;
  }

  public BidBuilder bidPrice(final double bidPrice) {
    _bidPrice = bidPrice;
    return this;
  }

  public BidBuilder bidDate(final long bidDate) {
    _bidDate = bidDate;
    return this;
  }

  public Bid build() {
    return new Bid(_uid, _itemUid, _userUid, _bidPrice, _bidDate);
  }
}
