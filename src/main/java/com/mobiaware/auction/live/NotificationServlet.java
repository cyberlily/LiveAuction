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

package com.mobiaware.auction.live;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobiaware.auction.live.notify.WebEventNotificationEngine;

public class NotificationServlet extends Endpoint {
  private static final String NAME = NotificationServlet.class.getSimpleName();
  private static final Logger LOG = LoggerFactory.getLogger(NAME);

  private Session _webSocketSession;
  private HttpSession _httpSession;

  public Session getWebSocketSession() {
    return _webSocketSession;
  }

  public HttpSession getHttpSession() {
    return _httpSession;
  }

  @Override
  public void onOpen(final Session session, final EndpointConfig config) {
    _webSocketSession = session;
    _httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

    ServletContext context = _httpSession.getServletContext();
    WebEventNotificationEngine engine =
        (WebEventNotificationEngine) context
            .getAttribute(WebEventNotificationEngine.NOTIFICATION_REGISTRY);
    engine.join(this);
  }

  @Override
  public void onClose(final Session session, final CloseReason closeReason) {
    ServletContext context = _httpSession.getServletContext();
    WebEventNotificationEngine engine =
        (WebEventNotificationEngine) context
            .getAttribute(WebEventNotificationEngine.NOTIFICATION_REGISTRY);
    engine.leave(this);
  }
}
