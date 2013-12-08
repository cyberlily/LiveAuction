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

public class ItemBuilder {
  private int _uid = -1;
  private int _auctionUid = -1;
  private String _itemNumber;
  private String _name;
  private String _description;
  private String _category;
  private String _seller;
  private double _valPrice;
  private double _minPrice;
  private double _incPrice;
  private double _curPrice;
  private String _winner;
  private int _bidCount;
  private int _watchCount;
  private String _url;
  private boolean _multi;

  public ItemBuilder() {
    // empty
  }

  public ItemBuilder(final Item item) {
    _uid = item.getUid();
    _auctionUid = item.getAuctionUid();
    _itemNumber = item.getItemNumber();
    _name = item.getName();
    _description = item.getDescription();
    _category = item.getCategory();
    _seller = item.getSeller();
    _valPrice = item.getValPrice();
    _minPrice = item.getMinPrice();
    _incPrice = item.getIncPrice();
    _curPrice = item.getCurPrice();
    _winner = item.getWinner();
    _bidCount = item.getBidCount();
    _watchCount = item.getWatchCount();
    _url = item.getUrl();
    _multi = item.getMulti();
  }

  public ItemBuilder uid(final int uid) {
    _uid = uid;
    return this;
  }

  public ItemBuilder auctionUid(final int auctionUid) {
    _auctionUid = auctionUid;
    return this;
  }

  public ItemBuilder itemNumber(final String itemNumber) {
    _itemNumber = itemNumber;
    return this;
  }

  public ItemBuilder name(final String name) {
    _name = name;
    return this;
  }

  public ItemBuilder description(final String description) {
    _description = description;
    return this;
  }

  public ItemBuilder category(final String category) {
    _category = category;
    return this;
  }

  public ItemBuilder seller(final String seller) {
    _seller = seller;
    return this;
  }

  public ItemBuilder valPrice(final double valPrice) {
    _valPrice = valPrice;
    return this;
  }

  public ItemBuilder minPrice(final double minPrice) {
    _minPrice = minPrice;
    return this;
  }

  public ItemBuilder incPrice(final double incPrice) {
    _incPrice = incPrice;
    return this;
  }

  public ItemBuilder curPrice(final double curPrice) {
    _curPrice = curPrice;
    return this;
  }

  public ItemBuilder winner(final String winner) {
    _winner = winner;
    return this;
  }

  public ItemBuilder bidCount(final int bidCount) {
    _bidCount = bidCount;
    return this;
  }

  public ItemBuilder watchCount(final int watchCount) {
    _watchCount = watchCount;
    return this;
  }

  public ItemBuilder url(final String url) {
    _url = url;
    return this;
  }

  public ItemBuilder multi(final boolean multi) {
    _multi = multi;
    return this;
  }

  public Item build() {
    return new Item(_uid, _auctionUid, _itemNumber, _name, _description, _category, _seller,
        _valPrice, _minPrice, _incPrice, _curPrice, _winner, _bidCount, _watchCount, _url, _multi);
  }
}
