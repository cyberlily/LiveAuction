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

package com.mobiaware.auction.event.action;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.ImmutableList;
import com.mobiaware.auction.AuctionSerializationTest;
import com.mobiaware.auction.auth.action.DeviceActionList;
import com.mobiaware.auction.data.DataService;
import com.mobiaware.servlet.HttpConstants;

public class AuctionActionListTest {
  @Test
  public void testShouldSucceed() throws JsonMappingException, JsonParseException, IOException {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getParameter(HttpConstants.PARAM_START)).thenReturn("0");
    when(request.getParameter(HttpConstants.PARAM_LENGTH)).thenReturn("5");
    when(request.getParameter(HttpConstants.PARAM_SORT)).thenReturn(null);
    when(request.getParameter(HttpConstants.PARAM_DIR)).thenReturn(null);

    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getSession()).thenReturn(session);

    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    Mockito.when(response.getWriter()).thenReturn(pw);

    DataService data = Mockito.mock(DataService.class);
    when(data.getAuctionCount()).thenReturn(1);
    when(data.getAuctions(0, 5, null, null)).thenReturn(
        ImmutableList.of(AuctionSerializationTest.AUCTION));

    DeviceActionList action = Mockito.spy(new DeviceActionList());
    Mockito.when(action.getDataService()).thenReturn(data);

    action.perform(request, response);

    verify(request, atLeast(1)).getParameter(HttpConstants.PARAM_START);
    verify(request, atLeast(1)).getParameter(HttpConstants.PARAM_LENGTH);
    verify(request, atLeast(1)).getParameter(HttpConstants.PARAM_SORT);
    verify(request, atLeast(1)).getParameter(HttpConstants.PARAM_DIR);
  }
}
