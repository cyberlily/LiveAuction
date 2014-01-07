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

import static com.mobiaware.util.HttpRequestHelpers.getDoubleParameter;
import static com.mobiaware.util.HttpRequestHelpers.getHeaderOrParameter;
import static com.mobiaware.util.HttpRequestHelpers.getIntParameter;
import static com.mobiaware.util.HttpRequestHelpers.getStringParameter;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class HttpRequestHelpersTest {
  @Test
  public void testGetStringParameter() {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getParameter("param")).thenReturn("yes");

    Assert.assertEquals(getStringParameter(request, "param", "no"), "yes");
    Assert.assertEquals(getStringParameter(request, "paramnotfound", "no"), "no");
  }

  @Test
  public void testGetIntParameter() {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getParameter("param")).thenReturn("1");

    Assert.assertEquals(getIntParameter(request, "param", 0), 1);
    Assert.assertEquals(getIntParameter(request, "paramnotfound", 0), 0);
  }

  @Test
  public void testGetDoubleParameter() {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getParameter("param")).thenReturn("1.0");

    Assert.assertEquals(getDoubleParameter(request, "param", 0.0), 1.0, 0.0);
    Assert.assertEquals(getDoubleParameter(request, "paramnotfound", 0.0), 0.0, 0.0);
  }

  @Test
  public void testGetHeaderOrParameter() {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getHeader("header")).thenReturn("thing1");
    when(request.getParameter("param")).thenReturn("thing2");

    Assert.assertEquals(getHeaderOrParameter(request, "header"), "thing1");
    Assert.assertEquals(getHeaderOrParameter(request, "param"), "thing2");
    Assert.assertEquals(getHeaderOrParameter(request, "headerorparamnotfound"), null);
  }
}
