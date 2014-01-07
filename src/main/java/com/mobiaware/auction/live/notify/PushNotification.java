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

package com.mobiaware.auction.live.notify;

import java.util.ArrayList;
import java.util.List;

import com.mobiaware.auction.Device;
import com.relayrides.pushy.apns.util.ApnsPayloadBuilder;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import com.relayrides.pushy.apns.util.TokenUtil;

public class PushNotification {
  private final List<SimpleApnsPushNotification> _notifications = new ArrayList<SimpleApnsPushNotification>();
  private final String _payload;

  public PushNotification(final List<Device> devices, final String message) {
    ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
    payloadBuilder.setAlertBody(message);

    _payload = payloadBuilder.buildWithDefaultMaximumLength();

    for (Device device : devices) {
      byte[] token = TokenUtil.tokenStringToByteArray(device.getDeviceId());
      _notifications.add(new SimpleApnsPushNotification(token, getPayload()));
    }
  }

  public List<SimpleApnsPushNotification> getNotifications() {
    return _notifications;
  }

  public String getPayload() {
    return _payload;
  }
}
