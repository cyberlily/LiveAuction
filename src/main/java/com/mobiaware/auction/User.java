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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User {
  private final int _uid;
  private final int _auctionUid;
  private final String _bidderNumber;
  private final String _firstName;
  private final String _lastName;

  private String _sessionId; // should not serialize
  private String _passwordHash; // should not serialize

  @JsonCreator
  public User(@JsonProperty("uid") final int uid, @JsonProperty("auctionuid") final int auctionUid,
      @JsonProperty("biddernumber") final String bidderNumber,
      @JsonProperty("firstname") final String firstName,
      @JsonProperty("lastname") final String lastName) {

    _uid = uid;
    _auctionUid = auctionUid;
    _bidderNumber = bidderNumber;
    _firstName = firstName;
    _lastName = lastName;
  }

  @JsonProperty("uid")
  public int getUid() {
    return _uid;
  }

  @JsonProperty("auctionuid")
  public int getAuctionUid() {
    return _auctionUid;
  }

  @JsonProperty("biddernumber")
  public String getBidderNumber() {
    return _bidderNumber;
  }

  @JsonProperty("firstname")
  public String getFirstName() {
    return _firstName;
  }

  @JsonProperty("lastname")
  public String getLastName() {
    return _lastName;
  }

  @JsonProperty("sessionid")
  public String getSessionId() {
    return _sessionId;
  }

  public void setSessionId(final String sessionId) {
    _sessionId = sessionId;
  }

  @JsonIgnore
  public String getPasswordHash() {
    return _passwordHash;
  }

  public void setPasswordHash(final String passwordHash) {
    _passwordHash = passwordHash;
  }

  public static UserBuilder newBuilder() {
    return new UserBuilder();
  }

  public UserBuilder toBuilder() {
    return new UserBuilder(this);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getUid(), getAuctionUid(), getBidderNumber(), getFirstName(),
        getLastName());
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (!(obj instanceof User)) {
      return false;
    }

    User other = (User) obj;

    if (getUid() != other.getUid()) {
      return false;
    }

    if (getAuctionUid() != other.getAuctionUid()) {
      return false;
    }

    if (getBidderNumber() == null) {
      if (other.getBidderNumber() != null) {
        return false;
      }
    } else if (!getBidderNumber().equals(other.getBidderNumber())) {
      return false;
    }

    if (getFirstName() == null) {
      if (other.getFirstName() != null) {
        return false;
      }
    } else if (!getFirstName().equals(other.getFirstName())) {
      return false;
    }

    if (getLastName() == null) {
      if (other.getLastName() != null) {
        return false;
      }
    } else if (!getLastName().equals(other.getLastName())) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).toString();
  }
}
