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

package com.mobiaware.auction.auth;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;

import com.mobiaware.servlet.HttpConstants;
import com.mobiaware.servlet.action.InvalidAction;

public class AuthenticationActionFactoryTest {
  @Test
  public void testShouldSucceed() {
    HttpServletRequest request = mock(HttpServletRequest.class);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(HttpConstants.ACTION_SIGNIN);
    Assert.assertFalse(AuthenticationActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(HttpConstants.ACTION_SIGNOUT);
    Assert.assertFalse(AuthenticationActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(
        HttpConstants.ACTION_REGISTERDEVICE);
    Assert.assertFalse(AuthenticationActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(
        HttpConstants.ACTION_DEVICELIST);
    Assert.assertFalse(AuthenticationActionFactory.getAction(request) instanceof InvalidAction);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn(
        HttpConstants.ACTION_DEVICELISTCNT);
    Assert.assertFalse(AuthenticationActionFactory.getAction(request) instanceof InvalidAction);
  }

  @Test
  public void testShouldFail() {
    HttpServletRequest request = mock(HttpServletRequest.class);

    when(request.getParameter(HttpConstants.PARAM_ACTION)).thenReturn("abcdef");
    Assert.assertTrue(AuthenticationActionFactory.getAction(request) instanceof InvalidAction);
  }
}
