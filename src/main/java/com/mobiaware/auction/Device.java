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
public class Device {
  private final int _uid;
  private final int _userUid;
  private final String _deviceId;
  private final String _deviceType;

  @JsonCreator
  public Device(@JsonProperty("uid") final int uid, @JsonProperty("userUid") final int userUid,
      @JsonProperty("deviceId") final String deviceId,
      @JsonProperty("deviceType") final String deviceType) {

    _uid = uid;
    _userUid = userUid;
    _deviceId = deviceId;
    _deviceType = deviceType;
  }

  @JsonProperty("uid")
  public int getUid() {
    return _uid;
  }

  @JsonProperty("userUid")
  public int getUserUid() {
    return _userUid;
  }

  @JsonProperty("deviceId")
  public String getDeviceId() {
    return _deviceId;
  }

  @JsonProperty("deviceType")
  public String getDeviceType() {
    return _deviceType;
  }

  public static DeviceBuilder newBuilder() {
    return new DeviceBuilder();
  }

  public DeviceBuilder toBuilder() {
    return new DeviceBuilder(this);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getUid(), getUserUid(), getDeviceId(), getDeviceType());
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (!(obj instanceof Device)) {
      return false;
    }

    Device other = (Device) obj;

    if (getUid() != other.getUid()) {
      return false;
    }

    if (getUserUid() != other.getUserUid()) {
      return false;
    }

    if (getDeviceId() == null) {
      if (other.getDeviceId() != null) {
        return false;
      }
    } else if (!getDeviceId().equals(other.getDeviceId())) {
      return false;
    }

    if (getDeviceType() == null) {
      if (other.getDeviceType() != null) {
        return false;
      }
    } else if (!getDeviceType().equals(other.getDeviceType())) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).toString();
  }
}
