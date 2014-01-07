/*
 * Copyright (c) 2010 mobiaware.com.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.mobiaware.auction;

public class CategoryBuilder {
  private int _uid = -1;
  private int _auctionUid = -1;
  private String _name;

  public CategoryBuilder() {
    // empty
  }

  public CategoryBuilder(final Category category) {
    _uid = category.getUid();
    _auctionUid = category.getAuctionUid();
    _name = category.getName();
  }

  public CategoryBuilder uid(final int uid) {
    _uid = uid;
    return this;
  }

  public CategoryBuilder auctionUid(final int auctionUid) {
    _auctionUid = auctionUid;
    return this;
  }

  public CategoryBuilder name(final String name) {
    _name = name;
    return this;
  }

  public Category build() {
    return new Category(_uid, _auctionUid, _name);
  }
}
