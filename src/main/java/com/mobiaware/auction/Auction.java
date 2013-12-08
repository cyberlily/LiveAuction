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

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Auction {
  private final int _uid;
  private final String _name;
  private final DateTime _startDate;
  private final DateTime _endDate;

  @JsonCreator
  public Auction(@JsonProperty("uid") final int uid, @JsonProperty("name") final String name,
      @JsonProperty("startdate") final long startDate, @JsonProperty("enddate") final long endDate) {
    _uid = uid;
    _name = name;
    _startDate = new DateTime(startDate);
    _endDate = new DateTime(endDate);
  }

  @JsonProperty("uid")
  public int getUid() {
    return _uid;
  }

  @JsonProperty("name")
  public String getName() {
    return _name;
  }

  @JsonProperty("startdate")
  public long getStartDate() {
    return _startDate.getMillis();
  }

  @JsonProperty("enddate")
  public long getEndDate() {
    return _endDate.getMillis();
  }

  @JsonProperty("active")
  public boolean isActive() {
    return new Interval(_startDate, _endDate).contains(new DateTime());
  }

  public static AuctionBuilder newBuilder() {
    return new AuctionBuilder();
  }

  public AuctionBuilder toBuilder() {
    return new AuctionBuilder(this);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getUid(), getName(), getStartDate(), getEndDate());
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (!(obj instanceof Auction)) {
      return false;
    }

    Auction other = (Auction) obj;

    if (getUid() != other.getUid()) {
      return false;
    }

    if (getName() == null) {
      if (other.getName() != null) {
        return false;
      }
    } else if (!getName().equals(other.getName())) {
      return false;
    }

    if (getStartDate() != other.getStartDate()) {
      return false;
    }

    if (getEndDate() != other.getEndDate()) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).toString();
  }
}
