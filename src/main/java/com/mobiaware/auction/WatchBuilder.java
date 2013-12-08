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

public class WatchBuilder {
  private int _uid = -1;
  private int _itemUid = -1;
  private int _userUid = -1;

  public WatchBuilder() {
    // empty
  }

  public WatchBuilder(final Watch watch) {
    _uid = watch.getUid();
    _itemUid = watch.getItemUid();
    _userUid = watch.getUserUid();
  }

  public WatchBuilder uid(final int uid) {
    _uid = uid;
    return this;
  }

  public WatchBuilder itemUid(final int itemUid) {
    _itemUid = itemUid;
    return this;
  }

  public WatchBuilder userUid(final int userUid) {
    _userUid = userUid;
    return this;
  }

  public Watch build() {
    return new Watch(_uid, _itemUid, _userUid);
  }
}
