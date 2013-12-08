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

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import com.mobiaware.auction.live.NotificationServlet;

public class WebEventNotificationEngine implements NotificationEngine {
  private static final String NAME = WebEventNotificationEngine.class.getSimpleName();
  private static final Logger LOG = LoggerFactory.getLogger(NAME);

  public static final String NOTIFICATION_REGISTRY = NAME + ".registry";

  private final Set<NotificationServlet> _connections =
      new CopyOnWriteArraySet<NotificationServlet>();

  public WebEventNotificationEngine() {
    // empty
  }

  @Override
  public void start() {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Web event notifications started.");
    }
  }

  @Override
  public void stop() {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Web event notifications stopped.");
    }
  }

  public void join(final NotificationServlet connection) {
    _connections.add(connection);
  }

  public void leave(final NotificationServlet connection) {
    _connections.remove(connection);
  }

  @Subscribe
  public void listen(final WebNotification notification) {
    sendMessage(notification);
  }

  private void sendMessage(final WebNotification notification) {
    Preconditions.checkNotNull(notification);

    if (LOG.isDebugEnabled()) {
      LOG.debug("Sending web notification. [" + notification.getChannel() + "]");
    }

    for (NotificationServlet connection : _connections) {
      try {
        connection.getWebSocketSession().getBasicRemote().sendText(notification.getData());
      } catch (IOException e) {
        LOG.error("!EXCEPTION!", e);

        _connections.remove(connection);

        try {
          connection.getWebSocketSession().close();
        } catch (IOException e1) {
          // ignore
        }
      }
    }
  }
}
