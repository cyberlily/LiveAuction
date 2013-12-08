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

public class UserBuilder {
  private int _uid = -1;
  private int _auctionUid = -1;
  private String _bidderNumber;
  private String _firstName;
  private String _lastName;

  public UserBuilder() {
    // empty
  }

  public UserBuilder(final User user) {
    _uid = user.getUid();
    _auctionUid = user.getAuctionUid();
    _bidderNumber = user.getBidderNumber();
    _firstName = user.getFirstName();
    _lastName = user.getLastName();
  }

  public UserBuilder uid(final int uid) {
    _uid = uid;
    return this;
  }

  public UserBuilder auctionUid(final int auctionUid) {
    _auctionUid = auctionUid;
    return this;
  }

  public UserBuilder bidderNumber(final String bidderNumber) {
    _bidderNumber = bidderNumber;
    return this;
  }

  public UserBuilder firstName(final String firstName) {
    _firstName = firstName;
    return this;
  }

  public UserBuilder lastName(final String lastName) {
    _lastName = lastName;
    return this;
  }

  public User build() {
    return new User(_uid, _auctionUid, _bidderNumber, _firstName, _lastName);
  }
}
