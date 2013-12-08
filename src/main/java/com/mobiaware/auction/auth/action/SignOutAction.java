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

package com.mobiaware.auction.auth.action;

import static com.mobiaware.util.HttpRequestHelpers.getStringParameter;
import static com.mobiaware.util.HttpResponseHelpers.outputJsonReponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobiaware.auction.User;
import com.mobiaware.auction.data.DataService;
import com.mobiaware.auction.data.MySqlDataServiceImpl;
import com.mobiaware.servlet.HttpConstants;
import com.mobiaware.servlet.action.Action;
import com.mobiaware.servlet.action.ErrorConstants;

public class SignOutAction extends Action {
  private transient final DataService _dataService = new MySqlDataServiceImpl();

  private User _user;

  @JsonIgnore
  public DataService getDataService() {
    return _dataService;
  }

  @JsonProperty("user")
  public User getUser() {
    return _user;
  }

  @Override
  public void perform(final HttpServletRequest request, final HttpServletResponse response) {
    ServletContext context = request.getServletContext();

    MetricRegistry metricRegistry =
        (MetricRegistry) context.getAttribute(MetricsServlet.METRICS_REGISTRY);
    metricRegistry.meter("com.mobiaware.auction.signout").mark();

    String pbiddernumber = getStringParameter(request, HttpConstants.PARAM_BIDDERNUMBER, null);

    checkRequiredString(pbiddernumber, 32);

    if (hasErrors()) {
      outputJsonReponse(response, this);
      return;
    }

    _user = getDataService().getUserByBidderNumber(pbiddernumber);

    if (_user == null) {
      addError(ErrorConstants.E_102, ErrorConstants.E_102_MSG);
    } else {
      // nothing
    }

    if (hasErrors()) {
      outputJsonReponse(response, this);
      return;
    }

    outputJsonReponse(response, _user);
  }
}
