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
public class Bid {
  private final int _uid;
  private final int _itemUid;
  private final int _userUid;
  private final double _bidPrice;
  private final long _bidDate;

  @JsonCreator
  public Bid(@JsonProperty("uid") final int uid, @JsonProperty("itemuid") final int itemUid,
      @JsonProperty("useruid") final int userUid, @JsonProperty("bidprice") final double bidPrice,
      @JsonProperty("biddate") final long bidDate) {

    _uid = uid;
    _itemUid = itemUid;
    _userUid = userUid;
    _bidPrice = bidPrice;
    _bidDate = bidDate;
  }

  @JsonProperty("uid")
  public int getUid() {
    return _uid;
  }

  @JsonProperty("itemuid")
  public int getItemUid() {
    return _itemUid;
  }

  @JsonProperty("useruid")
  public int getUserUid() {
    return _userUid;
  }

  @JsonProperty("bidprice")
  public double getBidPrice() {
    return _bidPrice;
  }

  @JsonProperty("biddate")
  public long getBidDate() {
    return _bidDate;
  }

  public static BidBuilder newBuilder() {
    return new BidBuilder();
  }

  public BidBuilder toBuilder() {
    return new BidBuilder(this);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getUid(), getItemUid(), getUserUid(), getBidPrice(), getBidDate());
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (!(obj instanceof Bid)) {
      return false;
    }

    Bid other = (Bid) obj;

    if (getUid() != other.getUid()) {
      return false;
    }

    if (getItemUid() != other.getItemUid()) {
      return false;
    }

    if (getUserUid() != other.getUserUid()) {
      return false;
    }

    if (Double.doubleToLongBits(getBidPrice()) != Double.doubleToLongBits(other.getBidPrice())) {
      return false;
    }

    if (getBidDate() != other.getBidDate()) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).toString();
  }
}
