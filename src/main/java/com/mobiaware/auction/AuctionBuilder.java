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

public class AuctionBuilder {
  private int _uid = -1;
  private String _name;
  private long _startDate;
  private long _endDate;

  public AuctionBuilder() {
    // empty
  }

  public AuctionBuilder(final Auction auction) {
    _uid = auction.getUid();
    _name = auction.getName();
    _startDate = auction.getStartDate();
    _endDate = auction.getEndDate();
  }

  public AuctionBuilder uid(final int uid) {
    _uid = uid;
    return this;
  }

  public AuctionBuilder name(final String name) {
    _name = name;
    return this;
  }

  public AuctionBuilder startDate(final long startDate) {
    _startDate = startDate;
    return this;
  }

  public AuctionBuilder endDate(final long endDate) {
    _endDate = endDate;
    return this;
  }

  public Auction build() {
    return new Auction(_uid, _name, _startDate, _endDate);
  }
}
