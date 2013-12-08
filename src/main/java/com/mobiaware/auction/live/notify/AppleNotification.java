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

package com.mobiaware.auction.live.notify;

import java.util.ArrayList;
import java.util.List;

import com.mobiaware.auction.Device;
import com.notnoop.apns.APNS;
import com.notnoop.apns.PayloadBuilder;

public class AppleNotification {
  private final List<String> _deviceTokens = new ArrayList<String>();;
  private final String _payload;

  public AppleNotification(final List<Device> devices, final String message) {
    PayloadBuilder payloadBuilder = APNS.newPayload().alertBody(message).sound("default");

    if (payloadBuilder.isTooLong()) {
      payloadBuilder = payloadBuilder.shrinkBody();
    }
    _payload = payloadBuilder.copy().build();

    for (Device device : devices) {
      _deviceTokens.add(device.getDeviceId());
    }
  }

  public List<String> getDeviceTokens() {
    return _deviceTokens;
  }

  public String getPayload() {
    return _payload;
  }
}
