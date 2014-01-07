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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Item {
  private final int _uid;
  private final int _auctionUid;
  private final String _itemNumber;
  private final String _name;
  private final String _description;
  private final String _category;
  private final String _seller;
  private final double _valPrice;
  private final double _minPrice;
  private final double _incPrice;
  private final double _curPrice;
  private final String _winner;
  private final int _bidCount;
  private final int _watchCount;
  private final String _url;
  private final boolean _multi;

  @JsonCreator
  public Item(@JsonProperty("uid") final int uid, @JsonProperty("auctionUid") final int auctionUid,
      @JsonProperty("itemNumber") final String itemNumber, @JsonProperty("name") final String name,
      @JsonProperty("description") final String description,
      @JsonProperty("category") final String category, @JsonProperty("seller") final String seller,
      @JsonProperty("valPrice") final double valPrice,
      @JsonProperty("minPrice") final double minPrice,
      @JsonProperty("incPrice") final double incPrice,
      @JsonProperty("curPrice") final double curPrice, @JsonProperty("winner") final String winner,
      @JsonProperty("bidCount") final int bidCount,
      @JsonProperty("watchCount") final int watchCount, @JsonProperty("url") final String url,
      @JsonProperty("multi") final boolean multi) {

    _uid = uid;
    _auctionUid = auctionUid;
    _itemNumber = itemNumber;
    _name = name;
    _description = description;
    _category = category;
    _seller = seller;
    _valPrice = valPrice;
    _minPrice = minPrice;
    _incPrice = incPrice;
    _curPrice = curPrice;
    _winner = winner;
    _bidCount = bidCount;
    _watchCount = watchCount;
    _url = url;
    _multi = multi;
  }


  @JsonProperty("uid")
  public int getUid() {
    return _uid;
  }

  @JsonProperty("auctionUid")
  public int getAuctionUid() {
    return _auctionUid;
  }

  @JsonProperty("itemNumber")
  public String getItemNumber() {
    return _itemNumber;
  }

  @JsonProperty("name")
  public String getName() {
    return _name;
  }

  @JsonProperty("description")
  public String getDescription() {
    return _description;
  }

  @JsonProperty("category")
  public String getCategory() {
    return _category;
  }

  @JsonProperty("seller")
  public String getSeller() {
    return _seller;
  }

  @JsonProperty("valPrice")
  public double getValPrice() {
    return _valPrice;
  }

  @JsonProperty("minPrice")
  public double getMinPrice() {
    return _minPrice;
  }

  @JsonProperty("incPrice")
  public double getIncPrice() {
    return _incPrice;
  }

  @JsonProperty("curPrice")
  public double getCurPrice() {
    return _curPrice;
  }

  @JsonProperty("winner")
  public String getWinner() {
    return _winner;
  }

  @JsonProperty("bidCount")
  public int getBidCount() {
    return _bidCount;
  }

  @JsonProperty("watchCount")
  public int getWatchCount() {
    return _watchCount;
  }

  @JsonProperty("url")
  public String getUrl() {
    return _url;
  }

  @JsonProperty("multi")
  public boolean getMulti() {
    return _multi;
  }

  public static ItemBuilder newBuilder() {
    return new ItemBuilder();
  }

  public ItemBuilder toBuilder() {
    return new ItemBuilder(this);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getUid(), getAuctionUid(), getItemNumber(), getName(),
        getDescription(), getCategory(), getSeller(), getValPrice(), getMinPrice(), getIncPrice(),
        getCurPrice(), getWinner(), getBidCount(), getWatchCount(), getUrl(), getMulti());
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    Item other = (Item) obj;

    return Objects.equal(getUid(), other.getUid())
        && Objects.equal(getAuctionUid(), other.getAuctionUid())
        && Objects.equal(getItemNumber(), other.getItemNumber())
        && Objects.equal(getName(), other.getName())
        && Objects.equal(getDescription(), other.getDescription())
        && Objects.equal(getCategory(), other.getCategory())
        && Objects.equal(getSeller(), other.getSeller())
        && Objects.equal(getValPrice(), other.getValPrice())
        && Objects.equal(getMinPrice(), other.getMinPrice())
        && Objects.equal(getCurPrice(), other.getCurPrice())
        && Objects.equal(getIncPrice(), other.getIncPrice())
        && Objects.equal(getWinner(), other.getWinner())
        && Objects.equal(getBidCount(), other.getBidCount())
        && Objects.equal(getWatchCount(), other.getWatchCount())
        && Objects.equal(getUrl(), other.getUrl()) && Objects.equal(getMulti(), other.getMulti());
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(getClass()).add("uid", getUid())
        .add("itemnumber", getItemNumber()).add("name", getName()).toString();
  }
}
