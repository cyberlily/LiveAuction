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

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import com.mobiaware.servlet.PropertyManager;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;
import com.notnoop.exceptions.InvalidSSLConfig;

public class AppleNotificationEngine implements NotificationEngine {
  private static final String NAME = AppleNotificationEngine.class.getSimpleName();
  private static final Logger LOG = LoggerFactory.getLogger(NAME);

  private static volatile AppleNotificationEngine instance = new AppleNotificationEngine();

  private final ApnsService _service;

  private AppleNotificationEngine() {
    PropertyManager pm = new PropertyManager(System.getProperty("PARAM1"));

    String keystore = pm.getString("apns.keystore");
    String password = pm.getString("apns.password");
    boolean isprod = Boolean.parseBoolean(pm.getString("apns.production"));
    int maxconn = pm.getInt("apns.maxconn");

    InputStream is = null;
    ApnsServiceBuilder builder = APNS.newService();

    try {
      is = AppleNotificationEngine.class.getClassLoader().getResourceAsStream(keystore);

      builder.withCert(is, password).asPool(maxconn);

      if (isprod) {
        builder.withProductionDestination();
      } else {
        builder.withSandboxDestination();
      }
    } catch (InvalidSSLConfig e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      IOUtils.closeQuietly(is);
    }

    _service = builder.build();
  }

  public static AppleNotificationEngine getInstance() {
    return instance;
  }

  public ApnsService getApnsService() {
    return _service;
  }

  @Override
  public void start() {
    Preconditions.checkNotNull(getApnsService());

    getApnsService().start();

    if (LOG.isDebugEnabled()) {
      LOG.debug("Apple notifications (APNS) started.");
    }
  }

  @Override
  public void stop() {
    Preconditions.checkNotNull(getApnsService());

    getApnsService().stop();

    if (LOG.isDebugEnabled()) {
      LOG.debug("Apple notifications (APNS) stopped.");
    }
  }

  @Subscribe
  public void listen(final AppleNotification notification) {
    sendMessage(notification);
  }

  private void sendMessage(final AppleNotification notification) {
    Preconditions.checkNotNull(notification);

    if (LOG.isDebugEnabled()) {
      LOG.debug("Sending Apple notifications (APNS) [" + notification.getPayload() + "]");
    }

    synchronized (this) {
      getApnsService().push(notification.getDeviceTokens(), notification.getPayload());
    }
  }
}
