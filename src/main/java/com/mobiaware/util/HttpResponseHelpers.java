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

package com.mobiaware.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiaware.servlet.HttpConstants;

public class HttpResponseHelpers {
  private static final String CONTENT_TYPE = "application/json";
  private static final String CHARACTER_ENCODING = "UTF-8";

  private HttpResponseHelpers() {
    // static only
  }

  public static void outputJsonReponse(final HttpServletResponse response, final Object obj) {
    outputJsonReponse(null, response, obj);
  }

  public static void outputJsonReponse(final HttpServletRequest request,
      final HttpServletResponse response, final Object obj) {
    String jsonPCallback = null;
    if (request != null) {
      jsonPCallback = request.getParameter(HttpConstants.PARAM_CALLBACK);
    }

    PrintWriter writer = null;

    try {
      response.setStatus(HttpServletResponse.SC_OK);

      response.setContentType(CONTENT_TYPE);
      response.setCharacterEncoding(CHARACTER_ENCODING);

      writer = response.getWriter();
      if (StringUtils.isNotBlank(jsonPCallback)) {
        writer.write(jsonPCallback + "(");
      }

      if (obj instanceof String) {
        writer.write(obj.toString());
      } else {
        ObjectMapper mapper = new ObjectMapper();
        writer.write(mapper.writeValueAsString(obj));
      }

      if (StringUtils.isNotBlank(jsonPCallback)) {
        writer.write(")");
      }
      writer.flush();
    } catch (IOException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } finally {
      IOUtils.closeQuietly(writer);
    }
  }
}
