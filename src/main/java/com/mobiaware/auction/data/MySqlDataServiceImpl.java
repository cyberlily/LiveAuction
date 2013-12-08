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

package com.mobiaware.auction.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.mobiaware.auction.Auction;
import com.mobiaware.auction.AuctionBuilder;
import com.mobiaware.auction.Bid;
import com.mobiaware.auction.BidBuilder;
import com.mobiaware.auction.Category;
import com.mobiaware.auction.CategoryBuilder;
import com.mobiaware.auction.Device;
import com.mobiaware.auction.DeviceBuilder;
import com.mobiaware.auction.Item;
import com.mobiaware.auction.ItemBuilder;
import com.mobiaware.auction.User;
import com.mobiaware.auction.UserBuilder;
import com.mobiaware.auction.Watch;
import com.mobiaware.auction.WatchBuilder;
import com.mobiaware.database.PoolConnection;

public class MySqlDataServiceImpl implements DataService {
  private static final String NAME = MySqlDataServiceImpl.class.getSimpleName();
  private static final Logger LOG = LoggerFactory.getLogger(NAME);

  @Override
  public boolean healthCheck() {
    boolean healthy = true;

    Connection conn = null;
    CallableStatement stmt = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("select now()");

      stmt.execute();
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);

      healthy = false;
    } finally {
      PoolConnection.returnConnection(conn, stmt);
    }

    return healthy;
  }

  @Override
  public String signin(final int userUid) {
    Connection conn = null;
    CallableStatement stmt = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_SIGNIN (?,?)}");
      stmt.setInt(1, userUid);
      stmt.registerOutParameter(2, Types.VARCHAR);

      stmt.execute();

      return stmt.getString(2);
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt);
    }

    return null;
  }

  @Override
  public void signout(final int userUid) {
    Connection conn = null;
    CallableStatement stmt = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_SIGNOUT (?)}");
      stmt.setInt(1, userUid);

      stmt.execute();
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt);
    }
  }

  @Override
  public Integer registerDevice(final int userUid, final String deviceId, final String deviceType) {
    Integer uid = -1;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_REGISTERDEVICE (?,?,?,?)}");
      stmt.registerOutParameter(1, Types.INTEGER);
      stmt.setInt(2, userUid);
      stmt.setString(3, deviceId);
      stmt.setString(4, deviceType);
      stmt.execute();

      uid = stmt.getInt(1);
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("DEVICE [method:{} result:{}]", new Object[] {"edit", uid});
    }

    return uid;
  }

  @Override
  public List<Device> getDevices(final int start, final int length, final String sort,
      final String dir) {
    List<Device> objs = new ArrayList<Device>();

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETDEVICES (?,?,?,?)}");
      stmt.setInt(1, start);
      stmt.setInt(2, length);
      stmt.setString(3, sort);
      stmt.setString(4, dir);

      rs = stmt.executeQuery();

      while (rs.next()) {
        DeviceBuilder builder =
            Device.newBuilder().uid(rs.getInt("UID")).userUid(rs.getInt("USERUID"))
                .deviceId(rs.getString("DEVICEID")).deviceType(rs.getString("DEVICETYPE"));

        objs.add(builder.build());
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("DEVICE [method:{} result:{}]", new Object[] {"get", objs.size()});
    }

    return ImmutableList.copyOf(objs);
  }

  @Override
  public Integer getDeviceCount() {
    Integer count = 0;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETDEVICECOUNT ()}");

      rs = stmt.executeQuery();

      if (rs.next()) {
        count = rs.getInt(1);
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("DEVICE [method:{} result:{}]", new Object[] {"count", count});
    }

    return count;
  }

  @Override
  public List<Device> getDevicesForUser(final int userUid) {
    List<Device> objs = new ArrayList<Device>();

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETDEVICESFORUSER (?)}");
      stmt.setInt(1, userUid);

      rs = stmt.executeQuery();

      while (rs.next()) {
        DeviceBuilder builder =
            Device.newBuilder().uid(rs.getInt("UID")).userUid(rs.getInt("USERUID"))
                .deviceId(rs.getString("DEVICEID")).deviceType(rs.getString("DEVICETYPE"));

        objs.add(builder.build());
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("DEVICE [method:{} result:{}]", new Object[] {"get", objs.size()});
    }

    return ImmutableList.copyOf(objs);
  }

  @Override
  public Auction getAuction(final int uid) {
    Auction obj = null;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETAUCTION (?)}");
      stmt.setInt(1, uid);

      rs = stmt.executeQuery();

      if (rs.next()) {
        AuctionBuilder builder =
            Auction.newBuilder().uid(rs.getInt("UID")).name(rs.getString("NAME"));

        Date startdate = rs.getDate("STARTDATE");
        if (startdate != null) {
          builder.startDate(startdate.getTime());
        }

        Date enddate = rs.getDate("ENDDATE");
        if (enddate != null) {
          builder.endDate(enddate.getTime());
        }

        obj = builder.build();
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("AUCTION [method:{} result:{}]", new Object[] {"get",
          obj != null ? obj.toString() : "[error]"});
    }

    return obj;
  }

  @Override
  public List<Auction> getAuctions(final int start, final int length, final String sort,
      final String dir) {
    List<Auction> objs = new ArrayList<Auction>();

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETAUCTIONS (?,?,?,?)}");
      stmt.setInt(1, start);
      stmt.setInt(2, length);
      stmt.setString(3, sort);
      stmt.setString(4, dir);

      rs = stmt.executeQuery();

      while (rs.next()) {
        AuctionBuilder builder =
            Auction.newBuilder().uid(rs.getInt("UID")).name(rs.getString("NAME"));

        Date startdate = rs.getDate("STARTDATE");
        if (startdate != null) {
          builder.startDate(startdate.getTime());
        }

        Date enddate = rs.getDate("ENDDATE");
        if (enddate != null) {
          builder.endDate(enddate.getTime());
        }

        objs.add(builder.build());
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("AUCTION [method:{} result:{}]", new Object[] {"get", objs.size()});
    }

    return ImmutableList.copyOf(objs);
  }

  @Override
  public Integer getAuctionCount() {
    Integer count = 0;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETAUCTIONCOUNT ()}");

      rs = stmt.executeQuery();

      if (rs.next()) {
        count = rs.getInt(1);
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("AUCTION [method:{} result:{}]", new Object[] {"count", count});
    }

    return count;
  }

  @Override
  public Integer editAuction(final Auction auction) {
    Integer uid = -1;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_EDITAUCTION (?,?,?,?)}");
      stmt.registerOutParameter(1, Types.INTEGER);
      stmt.setString(2, auction.getName());
      stmt.setDate(3, new Date(auction.getStartDate()));
      stmt.setDate(4, new Date(auction.getEndDate()));
      stmt.execute();

      uid = stmt.getInt(1);
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("AUCTION [method:{} result:{}]", new Object[] {"edit", uid});
    }

    return uid;
  }

  @Override
  public Integer deleteAuction(final int uid) {
    Integer result = -1;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_DELETEAUCTION (?)}");
      stmt.setInt(1, uid);

      rs = stmt.executeQuery();

      if (rs.next()) {
        result = rs.getInt(1);
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("AUCTION [method:{} result:{}]", new Object[] {"delete", result});
    }

    return result;
  }

  @Override
  public Item getItem(final int uid) {
    Item obj = null;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETITEM (?)}");
      stmt.setInt(1, uid);

      rs = stmt.executeQuery();

      if (rs.next()) {
        ItemBuilder builder =
            Item.newBuilder().uid(rs.getInt("UID")).auctionUid(rs.getInt("AUCTIONUID"))
                .itemNumber(rs.getString("ITEMNUMBER")).name(rs.getString("NAME"))
                .description(rs.getString("DESCRIPTION")).category(rs.getString("CATEGORY"))
                .seller(rs.getString("SELLER")).valPrice(rs.getDouble("VALPRICE"))
                .minPrice(rs.getDouble("MINPRICE")).incPrice(rs.getDouble("INCPRICE"))
                .curPrice(rs.getDouble("CURPRICE")).winner(rs.getString("WINNER"))
                .bidCount(rs.getInt("BIDCOUNT")).watchCount(rs.getInt("WATCHCOUNT"))
                .url(rs.getString("URL")).multi(rs.getBoolean("MULTI"));

        obj = builder.build();
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("ITEM [method:{} result:{}]", new Object[] {"get",
          obj != null ? obj.toString() : "[error]"});
    }

    return obj;
  }

  @Override
  public List<Item> getItems(final int auctionUid, final int categoryuid, final int start,
      final int length, final String sort, final String dir) {
    List<Item> objs = new ArrayList<Item>();

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETITEMS (?,?,?,?,?,?)}");
      stmt.setInt(1, auctionUid);
      stmt.setInt(2, categoryuid);
      stmt.setInt(3, start);
      stmt.setInt(4, length);
      stmt.setString(5, sort);
      stmt.setString(6, dir);

      rs = stmt.executeQuery();

      while (rs.next()) {
        ItemBuilder builder =
            Item.newBuilder().uid(rs.getInt("UID")).auctionUid(rs.getInt("AUCTIONUID"))
                .itemNumber(rs.getString("ITEMNUMBER")).name(rs.getString("NAME"))
                .description(rs.getString("DESCRIPTION")).category(rs.getString("CATEGORY"))
                .seller(rs.getString("SELLER")).valPrice(rs.getDouble("VALPRICE"))
                .minPrice(rs.getDouble("MINPRICE")).incPrice(rs.getDouble("INCPRICE"))
                .curPrice(rs.getDouble("CURPRICE")).winner(rs.getString("WINNER"))
                .bidCount(rs.getInt("BIDCOUNT")).watchCount(rs.getInt("WATCHCOUNT"))
                .url(rs.getString("URL")).multi(rs.getBoolean("MULTI"));

        objs.add(builder.build());
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("ITEM [method:{} result:{}]", new Object[] {"get", objs.size()});
    }

    return ImmutableList.copyOf(objs);
  }

  @Override
  public List<Item> getItemUpdates(final int auctionUid) {
    List<Item> objs = new ArrayList<Item>();

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETITEMUPDATES (?)}");
      stmt.setInt(1, auctionUid);

      rs = stmt.executeQuery();

      while (rs.next()) {
        ItemBuilder builder =
            Item.newBuilder().uid(rs.getInt("UID")).auctionUid(rs.getInt("AUCTIONUID"))
                .itemNumber(rs.getString("ITEMNUMBER")).curPrice(rs.getDouble("CURPRICE"))
                .winner(rs.getString("WINNER")).bidCount(rs.getInt("BIDCOUNT"))
                .watchCount(rs.getInt("WATCHCOUNT"));

        objs.add(builder.build());
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("ITEM [method:{} result:{}]", new Object[] {"get", objs.size()});
    }

    return ImmutableList.copyOf(objs);
  }

  @Override
  public Integer getItemCount(final int auctionUid, final int categoryuid) {
    Integer count = 0;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETITEMCOUNT (?,?)}");
      stmt.setInt(1, auctionUid);
      stmt.setInt(2, categoryuid);

      rs = stmt.executeQuery();

      if (rs.next()) {
        count = rs.getInt(1);
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("ITEM [method:{} result:{}]", new Object[] {"count", count});
    }

    return count;
  }

  @Override
  public Integer editItem(final Item item) {
    int uid = -1;

    Connection conn = null;
    CallableStatement stmt = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_EDITITEM (?,?,?,?,?,?,?,?,?,?)}");
      stmt.registerOutParameter(1, Types.INTEGER);
      stmt.setInt(2, item.getAuctionUid());
      stmt.setString(3, item.getItemNumber());
      stmt.setString(4, item.getName());
      stmt.setString(5, item.getDescription());
      stmt.setString(6, item.getCategory());
      stmt.setString(7, item.getSeller());
      stmt.setDouble(8, item.getValPrice());
      stmt.setDouble(9, item.getMinPrice());
      stmt.setDouble(10, item.getIncPrice());

      stmt.execute();

      uid = stmt.getInt(1);
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);

      uid = -1;
    } finally {
      PoolConnection.returnConnection(conn, stmt);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("ITEM [method:{} result:{}]", new Object[] {"edit", uid});
    }

    return uid;
  }

  @Override
  public Integer deleteItem(final int uid) {
    Integer result = -1;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_DELETEITEM (?)}");
      stmt.setInt(1, uid);

      rs = stmt.executeQuery();

      if (rs.next()) {
        result = rs.getInt(1);
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("ITEM [method:{} result:{}]", new Object[] {"delet", result});
    }

    return result;
  }

  @Override
  public Category getCategory(final int uid) {
    Category obj = null;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETCATEGORY (?)}");
      stmt.setInt(1, uid);

      rs = stmt.executeQuery();

      while (rs.next()) {
        CategoryBuilder builder =
            Category.newBuilder().uid(rs.getInt("UID")).auctionUid(rs.getInt("AUCTIONUID"))
                .name(rs.getString("NAME"));

        obj = builder.build();
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("CATEGORY [method:{} result:{}]", new Object[] {"get",
          obj != null ? obj.toString() : "[error]"});
    }

    return obj;
  }

  @Override
  public List<Category> getCategories(final int auctionUid, final int start, final int length,
      final String sort, final String dir) {
    List<Category> objs = new ArrayList<Category>();

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETCATEGORIES (?,?,?,?,?)}");
      stmt.setInt(1, auctionUid);
      stmt.setInt(2, start);
      stmt.setInt(3, length);
      stmt.setString(4, sort);
      stmt.setString(5, dir);

      rs = stmt.executeQuery();

      while (rs.next()) {
        CategoryBuilder builder =
            Category.newBuilder().uid(rs.getInt("UID")).auctionUid(rs.getInt("AUCTIONUID"))
                .name(rs.getString("NAME"));

        objs.add(builder.build());
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("CATEGORY [method:{} result:{}]", new Object[] {"get", objs.size()});
    }

    return ImmutableList.copyOf(objs);
  }

  @Override
  public Integer getCategoryCount(final int auctionUid) {
    Integer count = 0;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETCATEGORYCOUNT (?)}");
      stmt.setInt(1, auctionUid);

      rs = stmt.executeQuery();

      if (rs.next()) {
        count = rs.getInt(1);
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("CATEGORY [method:{} result:{}]", new Object[] {"count", count});
    }

    return count;
  }

  @Override
  public Integer editCategory(final Category category) {
    Integer uid = -1;

    Connection conn = null;
    CallableStatement stmt = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_EDITCATEGORY (?,?,?)}");
      stmt.registerOutParameter(1, Types.INTEGER);
      stmt.setInt(2, category.getAuctionUid());
      stmt.setString(3, category.getName());

      stmt.execute();

      uid = stmt.getInt(1);
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);

      uid = -1;
    } finally {
      PoolConnection.returnConnection(conn, stmt);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("CATEGORY [method:{} result:{}]", new Object[] {"edit", uid});
    }

    return uid;
  }

  @Override
  public Integer deleteCategory(final int uid) {
    Integer result = -1;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_DELETECATEGORY (?)}");
      stmt.setInt(1, uid);

      rs = stmt.executeQuery();

      if (rs.next()) {
        result = rs.getInt(1);
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("CATEGORY [method:{} result:{}]", new Object[] {"delete", result});
    }

    return result;
  }

  @Override
  public User getUser(final int uid) {
    User obj = null;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETUSER (?)}");
      stmt.setInt(1, uid);

      rs = stmt.executeQuery();

      if (rs.next()) {
        UserBuilder builder =
            User.newBuilder().uid(rs.getInt("UID")).auctionUid(rs.getInt("AUCTIONUID"))
                .bidderNumber(rs.getString("BIDDERNUMBER")).firstName(rs.getString("FIRSTNAME"))
                .lastName(rs.getString("LASTNAME"));

        obj = builder.build();
        obj.setPasswordHash(rs.getString("PASSWORDHASH"));
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("USER [method:{} result:{}]", new Object[] {"get",
          obj != null ? obj.toString() : "[error]"});
    }

    return obj;
  }

  @Override
  public User getUserByBidderNumber(final String biddernumber) {
    User obj = null;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETUSERBYBIDDERNUMBER (?)}");
      stmt.setString(1, biddernumber);

      rs = stmt.executeQuery();

      if (rs.next()) {
        UserBuilder builder =
            User.newBuilder().uid(rs.getInt("UID")).auctionUid(rs.getInt("AUCTIONUID"))
                .bidderNumber(rs.getString("BIDDERNUMBER")).firstName(rs.getString("FIRSTNAME"))
                .lastName(rs.getString("LASTNAME"));

        obj = builder.build();
        obj.setPasswordHash(rs.getString("PASSWORDHASH"));
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("USER [method:{} result:{}]", new Object[] {"get",
          obj != null ? obj.toString() : "[error]"});
    }

    return obj;
  }

  @Override
  public List<User> getUsers(final int auctionUid, final int start, final int length,
      final String sort, final String dir) {
    List<User> objs = new ArrayList<User>();

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETUSERS (?,?,?,?,?)}");
      stmt.setInt(1, auctionUid);
      stmt.setInt(2, start);
      stmt.setInt(3, length);
      stmt.setString(4, sort);
      stmt.setString(5, dir);

      rs = stmt.executeQuery();

      while (rs.next()) {
        UserBuilder builder =
            User.newBuilder().uid(rs.getInt("UID")).auctionUid(rs.getInt("AUCTIONUID"))
                .bidderNumber(rs.getString("BIDDERNUMBER")).firstName(rs.getString("FIRSTNAME"))
                .lastName(rs.getString("LASTNAME"));

        User obj = builder.build();
        obj.setPasswordHash(rs.getString("PASSWORDHASH"));

        objs.add(obj);
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("USER [method:{} result:{}]", new Object[] {"get", objs.size()});
    }

    return ImmutableList.copyOf(objs);
  }

  @Override
  public Integer getUserCount(final int auctionUid) {
    Integer count = 0;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETUSERCOUNT (?)}");
      stmt.setInt(1, auctionUid);

      rs = stmt.executeQuery();

      if (rs.next()) {
        count = rs.getInt(1);
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("USER [method:{} result:{}]", new Object[] {"count", count});
    }

    return count;
  }

  @Override
  public Integer editUser(final User user) {
    int uid = -1;

    Connection conn = null;
    CallableStatement stmt = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_EDITUSER (?,?,?,?,?,?)}");
      stmt.registerOutParameter(1, Types.INTEGER);
      stmt.setInt(2, user.getAuctionUid());
      stmt.setString(3, user.getBidderNumber());
      stmt.setString(4, user.getFirstName());
      stmt.setString(5, user.getLastName());
      // Use lastname as the default password
      stmt.setString(6, BCrypt.hashpw(user.getLastName(), BCrypt.gensalt()));
      stmt.execute();

      uid = stmt.getInt(1);
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
      uid = -1;
    } finally {
      PoolConnection.returnConnection(conn, stmt);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("USER [method:{} result:{}]", new Object[] {"add", uid});
    }

    return uid;
  }

  @Override
  public Integer deleteUser(final int uid) {
    Integer result = -1;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_DELETEUSER (?)}");
      stmt.setInt(1, uid);

      rs = stmt.executeQuery();

      if (rs.next()) {
        result = rs.getInt(1);
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("USER [method:{} result:{}]", new Object[] {"delete", result});
    }

    return result;
  }

  @Override
  public List<Bid> getBidsByItem(final int itemUid) {
    List<Bid> objs = new ArrayList<Bid>();

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETBIDSBYITEM (?)}");
      stmt.setInt(1, itemUid);

      rs = stmt.executeQuery();

      while (rs.next()) {
        BidBuilder builder =
            Bid.newBuilder().uid(rs.getInt("UID")).itemUid(rs.getInt("ITEMUID"))
                .userUid(rs.getInt("USERUID")).bidPrice(rs.getDouble("BIDPRICE"))
                .bidDate(rs.getDate("BIDDATE").getTime());

        objs.add(builder.build());
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("BID [method:{} result:{}]", new Object[] {"get", objs.size()});
    }

    return ImmutableList.copyOf(objs);
  }

  @Override
  public List<Bid> getBidsByUser(final int userUid) {
    List<Bid> objs = new ArrayList<Bid>();

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETBIDSBYUSER (?)}");
      stmt.setInt(1, userUid);

      rs = stmt.executeQuery();

      while (rs.next()) {
        BidBuilder builder =
            Bid.newBuilder().uid(rs.getInt("UID")).itemUid(rs.getInt("ITEMUID"))
                .userUid(rs.getInt("USERUID")).bidPrice(rs.getDouble("BIDPRICE"))
                .bidDate(rs.getDate("BIDDATE").getTime());

        objs.add(builder.build());
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("BID [method:{} result:{}]", new Object[] {"get", objs.size()});
    }

    return ImmutableList.copyOf(objs);
  }

  @Override
  public List<Bid> getBidsByUser2(final int userUid) {
    List<Bid> objs = new ArrayList<Bid>();

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETBIDSBYUSER2 (?)}");
      stmt.setInt(1, userUid);

      rs = stmt.executeQuery();

      while (rs.next()) {
        BidBuilder builder =
            Bid.newBuilder().uid(rs.getInt("UID")).itemUid(rs.getInt("ITEMUID"))
                .userUid(rs.getInt("USERUID")).bidPrice(rs.getDouble("BIDPRICE"))
                .bidDate(rs.getDate("BIDDATE").getTime());

        objs.add(builder.build());
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("BID [method:{} result:{}]", new Object[] {"get", objs.size()});
    }

    return ImmutableList.copyOf(objs);
  }

  @Override
  public Item addBid(final Bid bid) {
    Item obj = null;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_EDITBID (?,?,?,?)}");
      stmt.registerOutParameter(1, Types.INTEGER);
      stmt.setInt(2, bid.getItemUid());
      stmt.setInt(3, bid.getUserUid());
      stmt.setDouble(4, bid.getBidPrice());

      stmt.execute();
      stmt.close(); // close statement to prevent leak

      stmt = conn.prepareCall("{call SP_GETITEM (?)}");
      stmt.setInt(1, bid.getItemUid());

      rs = stmt.executeQuery();

      if (rs.next()) {
        ItemBuilder builder =
            Item.newBuilder().uid(rs.getInt("UID")).auctionUid(rs.getInt("AUCTIONUID"))
                .itemNumber(rs.getString("ITEMNUMBER")).curPrice(rs.getDouble("CURPRICE"))
                .winner(rs.getString("WINNER")).bidCount(rs.getInt("BIDCOUNT"))
                .watchCount(rs.getInt("WATCHCOUNT"));

        obj = builder.build();
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("BID [method:{} result:{}]", new Object[] {"add",
          obj != null ? obj.toString() : "[error]"});
    }

    return obj;
  }

  @Override
  public Integer deleteBid(final int uid) {
    Integer result = -1;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_DELETEBID (?)}");
      stmt.setInt(1, uid);

      rs = stmt.executeQuery();

      if (rs.next()) {
        result = rs.getInt(1);
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("BID [method:{} result:{}]", new Object[] {"delete", result});
    }

    return result;
  }

  @Override
  public List<Watch> getWatchesByItem(final int itemUid) {
    List<Watch> objs = new ArrayList<Watch>();

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETWATCHESBYITEM (?)}");
      stmt.setInt(1, itemUid);

      rs = stmt.executeQuery();

      while (rs.next()) {
        WatchBuilder builder =
            Watch.newBuilder().uid(rs.getInt("UID")).userUid(rs.getInt("USERUID"))
                .itemUid(rs.getInt("ITEMUID"));

        objs.add(builder.build());
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("WATCH [method:{} result:{}]", new Object[] {"get", objs.size()});
    }

    return ImmutableList.copyOf(objs);
  }

  @Override
  public List<Watch> getWatchesByUser(final int userUid) {
    List<Watch> objs = new ArrayList<Watch>();

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETWATCHESBYUSER (?)}");
      stmt.setInt(1, userUid);

      rs = stmt.executeQuery();

      while (rs.next()) {
        WatchBuilder builder =
            Watch.newBuilder().uid(rs.getInt("UID")).userUid(rs.getInt("USERUID"))
                .itemUid(rs.getInt("ITEMUID"));

        objs.add(builder.build());
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("WATCH [method:{} result:{}]", new Object[] {"get", objs.size()});
    }

    return ImmutableList.copyOf(objs);
  }

  @Override
  public Item addWatch(final Watch watch) {
    Item obj = null;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_EDITWATCH (?,?,?)}");
      stmt.registerOutParameter(1, Types.INTEGER);
      stmt.setInt(2, watch.getItemUid());
      stmt.setInt(3, watch.getUserUid());

      stmt.execute();
      stmt.close(); // close statement to prevent leak

      stmt = conn.prepareCall("{call SP_GETITEM (?)}");
      stmt.setInt(1, watch.getItemUid());

      rs = stmt.executeQuery();

      if (rs.next()) {
        ItemBuilder builder =
            Item.newBuilder().uid(rs.getInt("UID")).auctionUid(rs.getInt("AUCTIONUID"))
                .itemNumber(rs.getString("ITEMNUMBER")).curPrice(rs.getDouble("CURPRICE"))
                .winner(rs.getString("WINNER")).bidCount(rs.getInt("BIDCOUNT"))
                .watchCount(rs.getInt("WATCHCOUNT"));

        obj = builder.build();
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("WATCH [method:{} result:{}]", new Object[] {"add",
          obj != null ? obj.toString() : "[error]"});
    }

    return obj;
  }

  @Override
  public Integer deleteWatch(final int uid) {
    Integer result = -1;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_DELETEWATCH (?)}");
      stmt.setInt(1, uid);

      rs = stmt.executeQuery();

      if (rs.next()) {
        result = rs.getInt(1);
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("WATCH [method:{} result:{}]", new Object[] {"delete", result});
    }

    return result;
  }

  @Override
  public Bid getMaxBidForItem(final int itemUid) {
    Bid obj = null;

    Connection conn = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_GETMAXBIDBYITEM (?)}");
      stmt.setInt(1, itemUid);

      rs = stmt.executeQuery();

      if (rs.next()) {
        BidBuilder builder =
            Bid.newBuilder().uid(rs.getInt("UID")).itemUid(rs.getInt("ITEMUID"))
                .userUid(rs.getInt("USERUID")).bidPrice(rs.getDouble("BIDPRICE"))
                .bidDate(rs.getDate("BIDDATE").getTime());

        obj = builder.build();
      }
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);
    } finally {
      PoolConnection.returnConnection(conn, stmt, rs);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("BID [method:{} result:{}]", new Object[] {"get",
          obj != null ? obj.toString() : "[error]"});
    }

    return obj;
  }

  @Override
  public double fundACause(final int auctionUid, final int userUid, final double fundPrice) {
    double sum = 0;

    Connection conn = null;
    CallableStatement stmt = null;

    try {
      conn = PoolConnection.borrowConnection();

      stmt = conn.prepareCall("{call SP_EDITFUND (?,?,?,?)}");
      stmt.registerOutParameter(1, Types.DOUBLE);
      stmt.setInt(2, auctionUid);
      stmt.setInt(3, userUid);
      stmt.setDouble(4, fundPrice);

      stmt.execute();

      sum = stmt.getDouble(1);
    } catch (SQLException e) {
      LOG.error("!EXCEPTION!", e);

      sum = 0;
    } finally {
      PoolConnection.returnConnection(conn, stmt);
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("FUND [method:{} result:{}]", new Object[] {"edit", sum});
    }

    return sum;
  }
}
