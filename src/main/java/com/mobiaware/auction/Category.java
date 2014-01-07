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
public class Category {
  private final int _uid;
  private final int _auctionUid;
  private final String _name;

  @JsonCreator
  public Category(@JsonProperty("uid") final int uid,
      @JsonProperty("auctionUid") final int auctionUid, @JsonProperty("name") final String name) {

    _uid = uid;
    _auctionUid = auctionUid;
    _name = name;
  }

  @JsonProperty("uid")
  public int getUid() {
    return _uid;
  }

  @JsonProperty("auctionUid")
  public int getAuctionUid() {
    return _auctionUid;
  }

  @JsonProperty("name")
  public String getName() {
    return _name;
  }

  public static CategoryBuilder newBuilder() {
    return new CategoryBuilder();
  }

  public CategoryBuilder toBuilder() {
    return new CategoryBuilder(this);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getUid(), getAuctionUid(), getName());
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    Category other = (Category) obj;

    return Objects.equal(getUid(), other.getUid())
        && Objects.equal(getAuctionUid(), other.getAuctionUid())
        && Objects.equal(getName(), other.getName());
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(getClass()).add("uid", getUid()).add("name", getName())
        .toString();
  }
}
