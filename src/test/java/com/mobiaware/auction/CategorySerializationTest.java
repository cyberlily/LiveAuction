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

package com.mobiaware.auction;

import static com.mobiaware.util.JsonHelpers.fromJson;
import static com.mobiaware.util.JsonHelpers.toJson;
import static com.mobiaware.util.ResourceHelpers.fixture;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class CategorySerializationTest {
  public static final String FIXTURE = "category.json";

  public static final Category CATEGORY = Category.newBuilder().uid(1).auctionUid(1).name("Sports").build();

  @Test
  public void testSerializeToJson() throws IOException {
    Assert.assertEquals(fixture(FIXTURE), toJson(CATEGORY, false));
  }

  @Test
  public void testLoadFromJson() throws IOException {
    Assert.assertEquals(CATEGORY, fromJson(fixture(FIXTURE), Category.class));
  }
}
