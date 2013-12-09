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
public class Fund {
  private final int _uid;
  private final int _auctionUid;
  private final int _userUid;
  private final double _bidPrice;
  private final long _bidDate;

  @JsonCreator
  public Fund(@JsonProperty("uid") final int uid, @JsonProperty("auctionUid") final int auctionUid,
      @JsonProperty("userUid") final int userUid, @JsonProperty("bidPrice") final double bidPrice,
      @JsonProperty("bidDate") final long bidDate) {

    _uid = uid;
    _auctionUid = auctionUid;
    _userUid = userUid;
    _bidPrice = bidPrice;
    _bidDate = bidDate;
  }

  @JsonProperty("uid")
  public int getUid() {
    return _uid;
  }

  @JsonProperty("auctionUid")
  public int getAuctionUid() {
    return _auctionUid;
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

  public static FundBuilder newBuilder() {
    return new FundBuilder();
  }

  public FundBuilder toBuilder() {
    return new FundBuilder(this);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getUid(), getAuctionUid(), getUserUid(), getBidPrice(), getBidDate());
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    Fund other = (Fund) obj;

    return Objects.equal(getUid(), other.getUid())
        && Objects.equal(getAuctionUid(), other.getAuctionUid())
        && Objects.equal(getUserUid(), other.getUserUid())
        && Objects.equal(getBidPrice(), other.getBidPrice())
        && Objects.equal(getBidDate(), other.getBidDate());
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).toString();
  }
}
