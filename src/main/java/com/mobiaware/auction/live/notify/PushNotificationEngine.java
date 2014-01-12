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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.eventbus.Subscribe;
import com.mobiaware.servlet.PropertyManager;
import com.relayrides.pushy.apns.ApnsEnvironment;
import com.relayrides.pushy.apns.PushManager;
import com.relayrides.pushy.apns.RejectedNotificationListener;
import com.relayrides.pushy.apns.RejectedNotificationReason;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;

public class PushNotificationEngine implements NotificationEngine {
  private static final String NAME = PushNotificationEngine.class.getSimpleName();
  private static final Logger LOG = LoggerFactory.getLogger(NAME);

  private static volatile PushNotificationEngine instance = new PushNotificationEngine();

  private PushManager<SimpleApnsPushNotification> _pushManager;

  public class PushManagerRejectedNotificationListener
      implements
        RejectedNotificationListener<SimpleApnsPushNotification> {
    public void handleRejectedNotification(SimpleApnsPushNotification notification, RejectedNotificationReason reason) {
      LOG.error("%s was rejected with rejection reason %s\n", notification, reason);
    }
  }

  private PushNotificationEngine() {
    PropertyManager pm = new PropertyManager(System.getProperty("PARAM1"));

    String keystore = pm.getString("apns.keystore");
    String password = pm.getString("apns.password");
    boolean isprod = Boolean.parseBoolean(pm.getString("apns.production"));

    InputStream is = null;

    try {
      is = new FileInputStream(keystore);

      KeyStore keyStore = KeyStore.getInstance("PKCS12");
      keyStore.load(is, password.toCharArray());

      ApnsEnvironment env =
          isprod ? ApnsEnvironment.getProductionEnvironment() : ApnsEnvironment.getSandboxEnvironment();

      _pushManager = new PushManager<SimpleApnsPushNotification>(env, keyStore, password.toCharArray());
      _pushManager.registerRejectedNotificationListener(new PushManagerRejectedNotificationListener());
    } catch (NoSuchAlgorithmException e) {
      LOG.error(Throwables.getStackTraceAsString(e));
    } catch (CertificateException e) {
      LOG.error(Throwables.getStackTraceAsString(e));
    } catch (IOException e) {
      LOG.error(Throwables.getStackTraceAsString(e));
    } catch (KeyStoreException e) {
      LOG.error(Throwables.getStackTraceAsString(e));
    } finally {
      IOUtils.closeQuietly(is);
    }
  }

  public static PushNotificationEngine getInstance() {
    return instance;
  }

  public PushManager<SimpleApnsPushNotification> getPushManager() {
    return _pushManager;
  }

  @Override
  public void start() {
    Preconditions.checkNotNull(getPushManager());

    getPushManager().start();

    if (LOG.isDebugEnabled()) {
      LOG.debug("Apple notifications (APNS) started.");
    }
  }

  @Override
  public void stop() {
    Preconditions.checkNotNull(getPushManager());

    try {
      getPushManager().shutdown();
    } catch (InterruptedException e) {
      LOG.error(Throwables.getStackTraceAsString(e));
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("Apple notifications (APNS) stopped.");
    }
  }

  @Subscribe
  public void listen(final PushNotification notification) {
    sendMessage(notification);
  }

  private void sendMessage(final PushNotification notification) {
    Preconditions.checkNotNull(notification);

    if (LOG.isDebugEnabled()) {
      LOG.debug("Sending Apple notifications (APNS) [" + notification.getPayload() + "]");
    }

    getPushManager().enqueueAllNotifications(notification.getNotifications());
  }
}
