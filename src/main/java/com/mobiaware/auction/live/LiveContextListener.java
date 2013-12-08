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
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.google.common.eventbus.EventBus;
import com.mobiaware.auction.live.notify.AppleNotificationEngine;
import com.mobiaware.auction.live.notify.NotificationEngine;
import com.mobiaware.auction.live.notify.WebEventNotificationEngine;

@WebListener
public class LiveContextListener implements ServletContextListener {
  private transient final AppleNotificationEngine _appleEngine = new AppleNotificationEngine();
  private transient final WebEventNotificationEngine _webEngine = new WebEventNotificationEngine();

  private transient final EventBus _eventBus = new EventBus();

  @Override
  public void contextInitialized(final ServletContextEvent event) {
    _appleEngine.start();
    _webEngine.start();

    _eventBus.register(_appleEngine);
    _eventBus.register(_webEngine);

    ServletContext context = event.getServletContext();
    context.setAttribute(AppleNotificationEngine.NOTIFICATION_REGISTRY, _appleEngine);
    context.setAttribute(WebEventNotificationEngine.NOTIFICATION_REGISTRY, _webEngine);
    context.setAttribute(NotificationEngine.EVENTBUS_REGISTRY, _eventBus);
  }

  @Override
  public void contextDestroyed(final ServletContextEvent event) {
    _appleEngine.stop();
    _webEngine.stop();
  }
}
