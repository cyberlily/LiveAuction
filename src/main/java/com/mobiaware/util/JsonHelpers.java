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

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;

public class JsonHelpers {
  private static ObjectMapper _mapper = new ObjectMapper();

  private JsonHelpers() {
    // static only
  }

  public static <T> Object fromJson(final String json, final Class<T> clazz) throws JsonMappingException,
      JsonParseException, IOException {
    return _mapper.readValue(json, clazz);
  }

  public static <T> Object fromJson(final FileReader fr, final Class<T> clazz) throws JsonParseException, IOException {
    return _mapper.readValue(fr, clazz);
  }

  public static String toJson(final Object pojo, final boolean pretty) throws JsonMappingException,
      JsonGenerationException, IOException {
    if (pretty) {
      return _mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pojo);
    }
    return _mapper.writeValueAsString(pojo);
  }

  public static void toJson(final Object pojo, final FileWriter writer, final boolean pretty)
      throws JsonMappingException, JsonGenerationException, IOException {
    Preconditions.checkNotNull(pojo);
    Preconditions.checkNotNull(writer);

    if (pretty) {
      _mapper.writerWithDefaultPrettyPrinter().writeValue(writer, pojo);
    }
    _mapper.writeValue(writer, pojo);
  }
}
