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

package com.mobiaware.auction.importer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiaware.auction.Item;
import com.mobiaware.auction.User;
import com.mobiaware.auction.data.DataService;
import com.mobiaware.auction.data.MySqlDataServiceImpl;

public abstract class Importer {
  private static final String NAME = Importer.class.getSimpleName();
  private static final Logger LOG = LoggerFactory.getLogger(NAME);

  private transient final DataService _dataService = new MySqlDataServiceImpl();

  private int _totalCount;
  private int _importedCount;
  private boolean _success = true;

  @JsonIgnore
  public DataService getDataService() {
    return _dataService;
  }

  @JsonProperty("totalcount")
  public int getTotalCount() {
    return _totalCount;
  }

  @JsonProperty("importedcount")
  public int getImportedCount() {
    return _importedCount;
  }

  @JsonProperty("success")
  public boolean getSuccess() {
    return _success;
  }

  protected abstract List<Item> importItems(Reader reader);

  protected abstract List<User> importUsers(Reader reader);

  public void importItems(final int auctionuid, final InputStream is) {
    List<Item> items = importItems(new InputStreamReader(is));
    _totalCount = items.size();

    for (Item item : items) {
      int itemUid = addItem(auctionuid, item);
      if (itemUid < 0) {
        continue;
      }

      _importedCount++;
    }

    _success = (_totalCount == _importedCount);
  }

  public void importUsers(final int auctionuid, final InputStream is) {
    List<User> users = importUsers(new InputStreamReader(is));
    _totalCount = users.size();

    for (User user : users) {
      int itemUid = addUser(auctionuid, user);
      if (itemUid < 0) {
        continue;
      }

      _importedCount++;
    }

    _success = (_totalCount == _importedCount);
  }

  private int addItem(final int auctionuid, final Item item) {
    return getDataService().editItem(item.toBuilder().auctionUid(auctionuid).build());
  }

  private int addUser(final int auctionuid, final User user) {
    User user2 = user.toBuilder().auctionUid(auctionuid).build();
    return getDataService().editUser(user2);
  }

  @Override
  public String toString() {
    ObjectMapper mapper = new ObjectMapper();

    try {
      return mapper.writeValueAsString(this);
    } catch (JsonGenerationException e) {
      LOG.error("!EXCEPTION!", e);
    } catch (JsonMappingException e) {
      LOG.error("!EXCEPTION!", e);
    } catch (IOException e) {
      LOG.error("!EXCEPTION!", e);
    }

    return "";
  }
}
