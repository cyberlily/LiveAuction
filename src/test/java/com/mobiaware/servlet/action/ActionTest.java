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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ActionTest {
  @Test
  public void testErrors() {
    Action action = Mockito.mock(Action.class, Mockito.CALLS_REAL_METHODS);

    action.checkRequiredMin(1, 0);
    action.checkRequiredMin(-1, 0);
    Assert.assertTrue(action.getErrors().size() == 1);

    action.checkRequiredMin(1.0, 0.0);
    action.checkRequiredMin(-1.0, 0.0);
    Assert.assertTrue(action.getErrors().size() == 2);

    action.checkRequiredMax(-1, 0);
    action.checkRequiredMax(1, 0);
    Assert.assertTrue(action.getErrors().size() == 3);

    action.checkRequiredMax(-1.0, 0.0);
    action.checkRequiredMax(1.0, 0.0);
    Assert.assertTrue(action.getErrors().size() == 4);

    action.checkRequiredRange(0, -1, 1);
    action.checkRequiredRange(-2, -1, 1);
    action.checkRequiredRange(2, -1, 1);
    Assert.assertTrue(action.getErrors().size() == 6);

    action.checkRequiredString("ok");
    action.checkRequiredString(null);
    action.checkRequiredString("");
    Assert.assertTrue(action.getErrors().size() == 8);

    action.checkRequiredString("ok", 3);
    action.checkRequiredString(null, 3);
    action.checkRequiredString("", 3);
    action.checkRequiredString("oops", 3);
    Assert.assertTrue(action.getErrors().size() == 11);
  }
}
