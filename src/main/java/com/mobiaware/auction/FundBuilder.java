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

package com.mobiaware.auction;

public class FundBuilder {
  private int _uid = -1;
  private int _auctionUid = -1;
  private int _userUid = -1;
  private double _bidPrice;
  private long _bidDate;

  public FundBuilder() {
    // empty
  }

  public FundBuilder(final Fund fund) {
    _uid = fund.getUid();
    _auctionUid = fund.getAuctionUid();
    _userUid = fund.getUserUid();
    _bidPrice = fund.getBidPrice();
    _bidDate = fund.getBidDate();
  }

  public FundBuilder uid(final int uid) {
    _uid = uid;
    return this;
  }

  public FundBuilder auctionUid(final int auctionUid) {
    _auctionUid = auctionUid;
    return this;
  }

  public FundBuilder userUid(final int userUid) {
    _userUid = userUid;
    return this;
  }

  public FundBuilder bidPrice(final double bidPrice) {
    _bidPrice = bidPrice;
    return this;
  }

  public FundBuilder bidDate(final long bidDate) {
    _bidDate = bidDate;
    return this;
  }

  public Fund build() {
    return new Fund(_uid, _auctionUid, _userUid, _bidPrice, _bidDate);
  }
}
