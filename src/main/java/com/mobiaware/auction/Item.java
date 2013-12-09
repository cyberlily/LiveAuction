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
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (!(obj instanceof Item)) {
      return false;
    }

    Item other = (Item) obj;

    if (getUid() != other.getUid()) {
      return false;
    }

    if (getAuctionUid() != other.getAuctionUid()) {
      return false;
    }

    if (getItemNumber() == null) {
      if (other.getItemNumber() != null) {
        return false;
      }
    } else if (!getItemNumber().equals(other.getItemNumber())) {
      return false;
    }

    if (getName() == null) {
      if (other.getName() != null) {
        return false;
      }
    } else if (!getName().equals(other.getName())) {
      return false;
    }

    if (getDescription() == null) {
      if (other.getDescription() != null) {
        return false;
      }
    } else if (!getDescription().equals(other.getDescription())) {
      return false;
    }

    if (getCategory() == null) {
      if (other.getCategory() != null) {
        return false;
      }
    } else if (!getCategory().equals(other.getCategory())) {
      return false;
    }

    if (getSeller() == null) {
      if (other.getSeller() != null) {
        return false;
      }
    } else if (!getSeller().equals(other.getSeller())) {
      return false;
    }

    if (Double.doubleToLongBits(getValPrice()) != Double.doubleToLongBits(other.getValPrice())) {
      return false;
    }

    if (Double.doubleToLongBits(getMinPrice()) != Double.doubleToLongBits(other.getMinPrice())) {
      return false;
    }

    if (Double.doubleToLongBits(getCurPrice()) != Double.doubleToLongBits(other.getCurPrice())) {
      return false;
    }

    if (Double.doubleToLongBits(getIncPrice()) != Double.doubleToLongBits(other.getIncPrice())) {
      return false;
    }

    if (getWinner() == null) {
      if (other.getWinner() != null) {
        return false;
      }
    } else if (!getWinner().equals(other.getWinner())) {
      return false;
    }

    if (getBidCount() != other.getBidCount()) {
      return false;
    }

    if (getWatchCount() != other.getWatchCount()) {
      return false;
    }

    if (getUrl() == null) {
      if (other.getUrl() != null) {
        return false;
      }
    } else if (!getUrl().equals(other.getUrl())) {
      return false;
    }

    if (getMulti() != other.getMulti()) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).toString();
  }
}
