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

package com.mobiaware.auction.importer;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import com.mobiaware.auction.Item;
import com.mobiaware.auction.User;

public class GreaterGivingImporter extends Importer {

  @Override
  protected List<Item> importItems(final Reader reader) {
    Map<String, String> columnmap = new HashMap<String, String>();
    columnmap.put("Package Number", "itemNumber");
    columnmap.put("Name", "name");
    columnmap.put("Description", "description");
    columnmap.put("Section", "category");
    columnmap.put("Donors", "seller");
    columnmap.put("Value", "valPrice");
    columnmap.put("Minimum Bid", "minPrice");
    columnmap.put("Minimum Raise", "incPrice");

    HeaderColumnNameTranslateMappingStrategy<Item> strategy = new HeaderColumnNameTranslateMappingStrategy<Item>();
    strategy.setType(Item.class);
    strategy.setColumnMapping(columnmap);

    CSVReader cvsreader =
        new CSVReader(reader, CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER,
            CSVParser.DEFAULT_ESCAPE_CHARACTER, 0, false, false);

    CsvToBean<Item> csv = new CsvToBean<Item>();
    return csv.parse(strategy, cvsreader);
  }

  @Override
  protected List<User> importUsers(final Reader reader) {
    Map<String, String> columnmap = new HashMap<String, String>();
    columnmap.put("Bidder Number", "bidderNumber");
    columnmap.put("First", "firstName");
    columnmap.put("Last", "lastName");

    HeaderColumnNameTranslateMappingStrategy<User> strategy = new HeaderColumnNameTranslateMappingStrategy<User>();
    strategy.setType(User.class);
    strategy.setColumnMapping(columnmap);

    CSVReader cvsreader =
        new CSVReader(reader, CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER,
            CSVParser.DEFAULT_ESCAPE_CHARACTER, 0, false, false);

    CsvToBean<User> csv = new CsvToBean<User>();
    return csv.parse(strategy, cvsreader);
  }
}
