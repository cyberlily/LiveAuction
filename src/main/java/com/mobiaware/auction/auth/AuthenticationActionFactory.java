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

package com.mobiaware.auction.auth;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;
import com.mobiaware.auction.auth.action.DeviceActionList;
import com.mobiaware.auction.auth.action.DeviceActionListCount;
import com.mobiaware.auction.auth.action.DeviceActionRegister;
import com.mobiaware.auction.auth.action.SignInAction;
import com.mobiaware.servlet.HttpConstants;
import com.mobiaware.servlet.action.Action;
import com.mobiaware.servlet.action.InvalidAction;

public class AuthenticationActionFactory {
  private AuthenticationActionFactory() {
    // static only
  }

  public static Action getAction(final HttpServletRequest request) {
    Preconditions.checkNotNull(request);

    String paction = request.getParameter(HttpConstants.PARAM_ACTION);

    if (StringUtils.isBlank(paction)) {
      return (new InvalidAction());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_SIGNIN)) {
      return (new SignInAction());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_SIGNOUT)) {
      return (new SignInAction());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_REGISTERDEVICE)) {
      return (new DeviceActionRegister());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_DEVICELIST)) {
      return (new DeviceActionList());
    } else if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_DEVICELISTCNT)) {
      return (new DeviceActionListCount());
    }

    return (new InvalidAction());
  }
}
