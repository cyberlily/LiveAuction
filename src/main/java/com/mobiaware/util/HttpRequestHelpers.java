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

package com.mobiaware.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.google.common.base.Preconditions;

public class HttpRequestHelpers {
  private HttpRequestHelpers() {
    // static only
  }

  public static String getStringParameter(final HttpServletRequest request, final String name, final String defaultValue) {
    Preconditions.checkNotNull(request);
    Preconditions.checkNotNull(name);

    String result = request.getParameter(name);
    if (result == null) {
      result = defaultValue;
    }
    return result;
  }

  public static int getIntParameter(final HttpServletRequest request, final String name, final int defaultValue) {
    return NumberUtils.toInt(request.getParameter(name), defaultValue);
  }

  public static double getDoubleParameter(final HttpServletRequest request, final String name, final double defaultValue) {
    return NumberUtils.toDouble(request.getParameter(name), defaultValue);
  }

  public static String getHeaderOrParameter(final HttpServletRequest request, final String name) {
    Preconditions.checkNotNull(request);
    Preconditions.checkNotNull(name);

    String value = request.getHeader(name);
    if (StringUtils.isBlank(value)) {
      value = request.getParameter(name);
    }
    return value;
  }
}
