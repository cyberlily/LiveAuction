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
  public Bid(@JsonProperty("uid") final int uid, @JsonProperty("itemUid") final int itemUid,
      @JsonProperty("userUid") final int userUid, @JsonProperty("bidPrice") final double bidPrice,
      @JsonProperty("bidDate") final long bidDate) {

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

  @JsonProperty("itemUid")
  public int getItemUid() {
    return _itemUid;
  }

  @JsonProperty("userUid")
  public int getUserUid() {
    return _userUid;
  }

  @JsonProperty("bidPrice")
  public double getBidPrice() {
    return _bidPrice;
  }

  @JsonProperty("bidDate")
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
    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    Bid other = (Bid) obj;

    return Objects.equal(getUid(), other.getUid())
        && Objects.equal(getItemUid(), other.getItemUid())
        && Objects.equal(getUserUid(), other.getUserUid())
        && Objects.equal(getBidPrice(), other.getBidPrice())
        && Objects.equal(getBidDate(), other.getBidDate());
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(getClass()).add("uid", getUid()).add("itemuid", getItemUid())
        .add("useruid", getUserUid()).add("bidprice", getBidPrice()).add("biddate", getBidDate())
        .toString();
  }
}
