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

public class DeviceBuilder {
  private int _uid = -1;
  private int _userUid = -1;
  private String _deviceId;
  private String _deviceType;

  public DeviceBuilder() {
    // empty
  }

  public DeviceBuilder(final Device device) {
    _uid = device.getUid();
    _userUid = device.getUserUid();
    _deviceId = device.getDeviceId();
    _deviceType = device.getDeviceType();
  }

  public DeviceBuilder uid(final int uid) {
    _uid = uid;
    return this;
  }

  public DeviceBuilder userUid(final int userUid) {
    _userUid = userUid;
    return this;
  }

  public DeviceBuilder deviceId(final String deviceId) {
    _deviceId = deviceId;
    return this;
  }

  public DeviceBuilder deviceType(final String deviceType) {
    _deviceType = deviceType;
    return this;
  }

  public Device build() {
    return new Device(_uid, _userUid, _deviceId, _deviceType);
  }
}
