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
public class Watch {
  private final int _uid;
  private final int _itemUid;
  private final int _userUid;

  @JsonCreator
  public Watch(@JsonProperty("uid") final int uid, @JsonProperty("itemUid") final int itemUid,
      @JsonProperty("userUid") final int userUid) {

    _uid = uid;
    _itemUid = itemUid;
    _userUid = userUid;
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

  public static WatchBuilder newBuilder() {
    return new WatchBuilder();
  }

  public WatchBuilder toBuilder() {
    return new WatchBuilder(this);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getUid(), getItemUid(), getUserUid());
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    Watch other = (Watch) obj;

    return Objects.equal(getUid(), other.getUid())
        && Objects.equal(getItemUid(), other.getItemUid())
        && Objects.equal(getUserUid(), other.getUserUid());
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).toString();
  }
}
