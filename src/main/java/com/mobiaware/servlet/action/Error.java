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

package com.mobiaware.servlet.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Error {
  private final String _code;
  private final String _message;

  public Error(@JsonProperty("code") final String code, @JsonProperty("message") final String message) {
    _code = code;
    _message = message;
  }

  @JsonProperty("code")
  public String getCode() {
    return _code;
  }

  @JsonProperty("message")
  public String getMessage() {
    return _message;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).toString();
  }
}
