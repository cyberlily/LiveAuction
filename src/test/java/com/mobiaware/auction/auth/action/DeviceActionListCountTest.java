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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import com.mobiaware.auction.data.DataService;

public class DeviceActionListCountTest {
  @Test
  public void testShouldSucceed() throws IOException {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getSession()).thenReturn(session);

    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    Mockito.when(response.getWriter()).thenReturn(pw);

    DataService data = Mockito.mock(DataService.class);
    Mockito.when(data.getDeviceCount()).thenReturn(100);

    DeviceActionListCount action = Mockito.spy(new DeviceActionListCount());
    Mockito.when(action.getDataService()).thenReturn(data);

    action.perform(request, response);
  }
}
