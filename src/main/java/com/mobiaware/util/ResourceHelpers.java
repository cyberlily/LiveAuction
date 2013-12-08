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
import java.nio.charset.Charset;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class ResourceHelpers {
  private ResourceHelpers() {
    // static only
  }

  public static String fixture(final String filename) throws IOException {
    return fixture(filename, Charsets.UTF_8);
  }

  private static String fixture(final String filename, final Charset charset) throws IOException {
    return Resources.toString(Resources.getResource(filename), charset).trim();
  }
}
