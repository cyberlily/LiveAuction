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

package com.mobiaware.servlet;

public interface HttpConstants {
  public static final String API_APPKEY = "dia";
  public static final String API_SIGNATURE = "lwe";
  public static final String API_TIMESTAMP = "wtj";

  public static final String[] API_KEYWORDS = {API_APPKEY, API_TIMESTAMP};
  public static final String[] API_SEPRATORS = {"@", "$"};

  public static final String PARAM_ACTION = "qxh";
  public static final String PARAM_AUCTIONUID = "uxa";
  public static final String PARAM_BIDDERNUMBER = "ode";
  public static final String PARAM_CATEGORYUID = "zfc";
  public static final String PARAM_DEVICEID = "roc";
  public static final String PARAM_DEVICETYPE = "kos";
  public static final String PARAM_END = "aib";
  public static final String PARAM_FIRSTNAME = "vxh";
  public static final String PARAM_ITEMNUMBER = "nje";
  public static final String PARAM_ITEMUID = "wqg";
  public static final String PARAM_LASTNAME = "gzp";
  public static final String PARAM_LENGTH = "mfu";
  public static final String PARAM_NAME = "cng";
  public static final String PARAM_PASSWORD = "mvk";
  public static final String PARAM_PRICE = "sfe";
  public static final String PARAM_START = "ymd";
  public static final String PARAM_SESSIONID = "jnf";
  public static final String PARAM_USERUID = "oev";
  public static final String PARAM_DESCRIPTION = "zip";
  public static final String PARAM_CATEGORY = "few";
  public static final String PARAM_SELLER = "eye";
  public static final String PARAM_VALPRICE = "iea";
  public static final String PARAM_MINPRICE = "bju";
  public static final String PARAM_INCPRICE = "nyv";
  public static final String PARAM_CALLBACK = "callback";
  public static final String PARAM_SORT = "fgd";
  public static final String PARAM_DIR = "fed";

  public static final String ACTION_AUCTIONLIST = "ycs";
  public static final String ACTION_AUCTIONLISTCNT = "gkh";
  public static final String ACTION_AUCTIONEDIT = "gxk";
  public static final String ACTION_AUCTIONDELETE = "sbt";

  public static final String ACTION_CATEGORYLIST = "gje";
  public static final String ACTION_CATEGORYLISTCNT = "ogc";
  public static final String ACTION_CATEGORYEDIT = "zmt";
  public static final String ACTION_CATEGORYDELETE = "fxr";

  public static final String ACTION_ITEMLIST = "bex";
  public static final String ACTION_ITEMLISTCNT = "pgp";
  public static final String ACTION_ITEMEDIT = "vku";
  public static final String ACTION_ITEMDELETE = "orb";
  public static final String ACTION_ITEMUPDATES = "mmm";
  public static final String ACTION_ITEMIMPORT = "uty";
  public static final String ACTION_MYWATCHITEMLIST = "rfr";
  public static final String ACTION_MYBIDITEMLIST = "jgj";

  public static final String ACTION_USERLIST = "kyl";
  public static final String ACTION_USERLISTCNT = "dit";
  public static final String ACTION_USEREDIT = "nhe";
  public static final String ACTION_USERDELETE = "ake";
  public static final String ACTION_USERIMPORT = "hgy";

  public static final String ACTION_BID = "say";
  public static final String ACTION_WATCH = "ros";
  public static final String ACTION_FUNDACAUSE = "gth";

  public static final String ACTION_SIGNIN = "iuy";
  public static final String ACTION_SIGNOUT = "tfg";

  public static final String ACTION_GENERATEQRCODE = "iij";

  public static final String ACTION_DEVICELIST = "nwl";
  public static final String ACTION_DEVICELISTCNT = "gqu";
  public static final String ACTION_REGISTERDEVICE = "olk";
  public static final String ACTION_DEVICEMESSAGE = "vdf";
}
