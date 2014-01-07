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

package com.mobiaware.auction.data;

import java.util.List;

import com.mobiaware.auction.Auction;
import com.mobiaware.auction.Bid;
import com.mobiaware.auction.Category;
import com.mobiaware.auction.Device;
import com.mobiaware.auction.Item;
import com.mobiaware.auction.User;
import com.mobiaware.auction.Watch;

public interface DataService {
  public boolean healthCheck();

  public String signin(int userUid);

  public void signout(int userUid);

  public List<Device> getDevices(final int start, final int length, final String sort, final String dir);

  public Integer getDeviceCount();

  public Integer registerDevice(int userUid, String deviceId, String deviceType);

  public List<Device> getDevicesForUser(int userUid);

  public Auction getAuction(int uid);

  public List<Auction> getAuctions(final int start, final int length, final String sort, final String dir);

  public Integer getAuctionCount();

  public Integer editAuction(Auction auction);

  public Integer deleteAuction(int uid);

  public Item getItem(int uid);

  public List<Item> getItems(int auctionUid, int categoryuid, int start, int length, final String sort, final String dir);

  public Integer getItemCount(int auctionUid, int categoryuid);

  public List<Item> getItemUpdates(final int auctionUid);

  public Integer editItem(Item item);

  public Integer deleteItem(int uid);

  public Bid getMaxBidForItem(int itemUid);

  public Category getCategory(int uid);

  public List<Category> getCategories(final int auctionUid, final int start, final int length, final String sort,
      final String dir);

  public Integer getCategoryCount(int auctionUid);

  public Integer editCategory(Category category);

  public Integer deleteCategory(int uid);

  public User getUser(int uid);

  public User getUserByBidderNumber(String biddernumber);

  public List<User> getUsers(final int auctionUid, final int start, final int length, final String sort,
      final String dir);

  public Integer getUserCount(int auctionUid);

  public Integer editUser(User user);

  public Integer deleteUser(int uid);

  public List<Bid> getBidsByItem(int itemUid);

  public List<Bid> getBidsByUser(int userUid);

  public List<Bid> getBidsByUser2(int userUid);

  public Item addBid(final Bid bid);

  public Integer deleteBid(int uid);

  public List<Watch> getWatchesByItem(int itemUid);

  public List<Watch> getWatchesByUser(int userUid);

  public Item addWatch(final Watch watch);

  public Integer deleteWatch(int uid);

  public double fundACause(int auctionUid, int userUid, double fundPrice);
}
