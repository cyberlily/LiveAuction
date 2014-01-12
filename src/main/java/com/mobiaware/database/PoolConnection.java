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

package com.mobiaware.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.mobiaware.servlet.PropertyManager;

public class PoolConnection {
  private static final String NAME = PoolConnection.class.getSimpleName();
  private static final Logger LOG = LoggerFactory.getLogger(NAME);

  private static BoneCP POOL;

  public static void configureConnPool() {
    try {
      PropertyManager pm = new PropertyManager(System.getProperty("PARAM1"));

      String host = pm.getString("database.host");
      String port = pm.getString("database.port");
      String schema = pm.getString("database.schema");
      String user = pm.getString("database.user");
      String password = pm.getString("database.password");
      int maxconn = pm.getInt("database.maxconn");
      int minconn = pm.getInt("database.minconn");

      Class.forName("com.mysql.jdbc.Driver"); // also you need the MySQL driver

      BoneCPConfig config = new BoneCPConfig();
      config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + schema);
      config.setUsername(user);
      config.setPassword(password);
      config.setMaxConnectionsPerPartition(maxconn);
      config.setMinConnectionsPerPartition(minconn);
      config.setPartitionCount(1);

      POOL = new BoneCP(config); // setup the connection pool
      setConnectionPool(POOL);

      if (LOG.isDebugEnabled()) {
        LOG.debug("Connection pooling is configured. [{}]", POOL.getTotalCreatedConnections());
      }
    } catch (ClassNotFoundException e) {
      LOG.error(Throwables.getStackTraceAsString(e));
    } catch (SQLException e) {
      LOG.error(Throwables.getStackTraceAsString(e));
    }
  }

  public static void shutdownConnPool() {
    BoneCP connectionPool = getConnectionPool();

    // Called only once when the application stops.
    if (connectionPool != null) {
      connectionPool.shutdown();

      if (LOG.isDebugEnabled()) {
        LOG.debug("Connection pooling is shutdown.");
      }
    }
  }

  public static Connection borrowConnection() {
    Connection conn = null;

    BoneCP pool = getConnectionPool();

    try {
      // Get a thread-safe connection from the BoneCP connection pool;
      // synchronization of the method will be done inside BoneCP.
      conn = pool.getConnection();
    } catch (SQLException e) {
      LOG.error(Throwables.getStackTraceAsString(e));
    }

    if (LOG.isDebugEnabled()) {
      LOG.debug("Stats [total: {} leased: {} free: {}]",
          new Object[] {pool.getTotalCreatedConnections(), pool.getTotalLeased(), pool.getTotalFree()});
    }

    return conn;
  }

  public static void returnConnection(final Connection conn) {
    try {
      if (conn != null) {
        // Release the connection. The name is tricky but connection
        // is not closed; it is released and will stay in the pool.
        conn.close();
      }
    } catch (SQLException e) {
      LOG.error(Throwables.getStackTraceAsString(e));
    }
  }

  public static void returnConnection(final Connection conn, final CallableStatement stmt) {
    returnConnection(conn, stmt, null);
  }

  public static void returnConnection(final Connection conn, final CallableStatement stmt, final ResultSet rs) {
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (SQLException e) {
      LOG.error(Throwables.getStackTraceAsString(e));
    }

    try {
      if (stmt != null) {
        stmt.close();
      }
    } catch (SQLException e) {
      LOG.error(Throwables.getStackTraceAsString(e));
    }

    if (conn != null) {
      returnConnection(conn);
    }
  }


  public static BoneCP getConnectionPool() {
    return POOL;
  }

  public static void setConnectionPool(final BoneCP connectionPool) {
    POOL = connectionPool;
  }
}
