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

package com.mobiaware.servlet.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public abstract class Action {
  private List<Error> _errors;

  public abstract void perform(final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException;

  @JsonProperty("errors")
  public List<Error> getErrors() {
    return _errors;
  }

  @JsonProperty("success")
  public boolean isSuccess() {
    return !hasErrors();
  }

  public void addError(final String code, final String message) {
    if (_errors == null) {
      _errors = new ArrayList<Error>();
    }
    _errors.add(new Error(code, message));
  }

  public boolean hasErrors() {
    return ((getErrors() != null) && (!getErrors().isEmpty()));
  }

  public void checkRequiredMin(final int value, final int minValue) {
    if (value < minValue) {
      addError(ErrorConstants.E_101, ErrorConstants.E_101_MSG);
    }
  }

  public void checkRequiredMin(final double value, final double minValue) {
    if (value < minValue) {
      addError(ErrorConstants.E_101, ErrorConstants.E_101_MSG);
    }
  }

  public void checkRequiredMax(final int value, final int maxValue) {
    if (value > maxValue) {
      addError(ErrorConstants.E_101, ErrorConstants.E_101_MSG);
    }
  }

  public void checkRequiredMax(final double value, final double maxValue) {
    if (value > maxValue) {
      addError(ErrorConstants.E_101, ErrorConstants.E_101_MSG);
    }
  }

  public void checkRequiredRange(final int value, final int minValue, final int maxValue) {
    if ((value > maxValue) || (value < minValue)) {
      addError(ErrorConstants.E_101, ErrorConstants.E_101_MSG);
    }
  }

  public void checkRequiredString(final String value) {
    if (StringUtils.isBlank(value)) {
      addError(ErrorConstants.E_101, ErrorConstants.E_101_MSG);
    }
  }

  public void checkRequiredString(final String value, final int maxLen) {
    if (StringUtils.isBlank(value) || (value.length() > maxLen)) {
      addError(ErrorConstants.E_101, ErrorConstants.E_101_MSG);
    }
  }
}
