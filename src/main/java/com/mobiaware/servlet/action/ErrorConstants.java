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

public interface ErrorConstants {
  public static final String E_100 = "100";
  public static final String E_100_MSG = "Not implemented.";

  public static final String E_101 = "101";
  public static final String E_101_MSG = "Invalid parameter.";

  public static final String E_102 = "102";
  public static final String E_102_MSG = "System error.";

  public static final String E_201 = "201";
  public static final String E_201_MSG = "Bid not accepted. The item is not valid.";

  public static final String E_202 = "202";
  public static final String E_202_MSG = "Bid not accepted. The auction is not valid.";

  public static final String E_203 = "203";
  public static final String E_203_MSG = "Bid not accepted. The auction has ended.";

  public static final String E_204 = "204";
  public static final String E_204_MSG = "Bid not accepted. Unable to record the entry.";

  public static final String E_205 = "205";
  public static final String E_205_MSG = "Bid not accepted. User is not valid.";

  public static final String E_206 = "206";
  public static final String E_206_MSG = "Bid not accepted. Increment is not valid.";

  public static final String E_301 = "301";
  public static final String E_301_MSG = "Watch not added. The item is not valid.";

  public static final String E_302 = "302";
  public static final String E_302_MSG = "Watch not added. The auction is not valid.";

  public static final String E_303 = "303";
  public static final String E_303_MSG = "Watch not added. The auction has ended.";

  public static final String E_304 = "304";
  public static final String E_304_MSG = "Watch not added. Unable to record entry.";

  public static final String E_305 = "305";
  public static final String E_305_MSG = "Watch not accepted. User is not valid.";

  public static final String E_401 = "401";
  public static final String E_401_MSG = "Fund not added. The auction is not valid.";

  public static final String E_402 = "402";
  public static final String E_402_MSG = "Fund not added. The auction has ended.";

  public static final String E_403 = "403";
  public static final String E_403_MSG = "Fund not added. Unable to record entry.";

  public static final String E_404 = "404";
  public static final String E_404_MSG = "Fund not accepted. User is not valid.";
}
