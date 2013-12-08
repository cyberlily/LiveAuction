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

import static com.mobiaware.util.HttpRequestHelpers.getIntParameter;
import static com.mobiaware.util.HttpRequestHelpers.getStringParameter;
import static com.mobiaware.util.HttpResponseHelpers.outputJsonReponse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobiaware.auction.Device;
import com.mobiaware.auction.data.DataService;
import com.mobiaware.auction.data.MySqlDataServiceImpl;
import com.mobiaware.servlet.HttpConstants;
import com.mobiaware.servlet.action.Action;

public class DeviceActionList extends Action {
  private transient final DataService _dataService = new MySqlDataServiceImpl();

  private List<Device> _devices;
  private int _total;

  @JsonIgnore
  public DataService getDataService() {
    return _dataService;
  }

  @JsonProperty("devices")
  public List<Device> getDevices() {
    return _devices;
  }

  @JsonProperty("total")
  public int getTotal() {
    return _total;
  }

  @Override
  public void perform(final HttpServletRequest request, final HttpServletResponse response) {
    // Non required parameters
    int pstart = getIntParameter(request, HttpConstants.PARAM_START, 0);
    int plength = getIntParameter(request, HttpConstants.PARAM_LENGTH, 500);
    String psort = getStringParameter(request, HttpConstants.PARAM_SORT, null);
    String pdir = getStringParameter(request, HttpConstants.PARAM_DIR, null);

    _devices = getDataService().getDevices(pstart, plength, psort, pdir);
    _total = getDataService().getDeviceCount();

    outputJsonReponse(request, response, this);
  }
}
