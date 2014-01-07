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

package com.mobiaware.auction.live;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobiaware.auction.live.notify.WebNotificationEngine;

@ServerEndpoint("/notify")
public class NotificationServlet {
  private static final String NAME = NotificationServlet.class.getSimpleName();
  private static final Logger LOG = LoggerFactory.getLogger(NAME);

  private Session _webSocketSession;

  public Session getWebSocketSession() {
    return _webSocketSession;
  }

  @OnOpen
  public void onOpen(final Session session, final EndpointConfig config) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Websocket open. + [" + session.getId() + "]");
    }

    _webSocketSession = session;

    WebNotificationEngine.getInstance().join(this);
  }

  @OnClose
  public void onClose(final Session session, final CloseReason closeReason) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Websocket close. + [" + session.getId() + "]");
    }

    WebNotificationEngine.getInstance().leave(this);
  }
}
