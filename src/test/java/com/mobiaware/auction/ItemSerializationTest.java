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

package com.mobiaware.auction;

import static com.mobiaware.util.JsonHelpers.fromJson;
import static com.mobiaware.util.JsonHelpers.toJson;
import static com.mobiaware.util.ResourceHelpers.fixture;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class ItemSerializationTest {
  public static final String FIXTURE = "item.json";

  public static final Item ITEM = Item.newBuilder().uid(1).auctionUid(1).itemNumber("100")
      .name("Fred's Bowling Ball").description("The official ball used by Fred Flinstone.")
      .category("Sports").seller("Wilma Flinstone").valPrice(1000.00).minPrice(500.00)
      .incPrice(10.00).curPrice(900.00).winner("Barney Rubble").bidCount(5).watchCount(100)
      .url("http://www.flinstone.com/ball.png").multi(false).build();

  @Test
  public void testSerializeToJson() throws IOException {
    Assert.assertEquals(fixture(FIXTURE), toJson(ITEM, false));
  }

  @Test
  public void testLoadFromJson() throws IOException {
    Assert.assertEquals(ITEM, fromJson(fixture(FIXTURE), Item.class));
  }
}
