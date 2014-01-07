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

package com.mobiaware.auction.event;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.mobiaware.auction.data.DataServiceHealthCheck;

@WebListener
public class MetricsContextListener implements ServletContextListener {
  private transient final MetricRegistry _metricRegistry = new MetricRegistry();
  private transient final HealthCheckRegistry _healthCheckRegistry = new HealthCheckRegistry();

  @Override
  public void contextInitialized(final ServletContextEvent event) {
    _healthCheckRegistry.register("dataservice", new DataServiceHealthCheck());

    ServletContext context = event.getServletContext();

    context.setAttribute(MetricsServlet.METRICS_REGISTRY, _metricRegistry);
    context.setAttribute(HealthCheckServlet.HEALTH_CHECK_REGISTRY, _healthCheckRegistry);
  }

  @Override
  public void contextDestroyed(final ServletContextEvent event) {
    // nothing
  }
}
