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

package com.mobiaware.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.OverrideCombiner;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

public class PropertyManager {
  private static final String NAME = PropertyManager.class.getSimpleName();
  private static final Logger LOG = LoggerFactory.getLogger(NAME);

  private final CombinedConfiguration _config;

  public PropertyManager(final String path) {
    _config = new CombinedConfiguration(new OverrideCombiner());

    loadProperties(path);
  }

  private void loadProperties(final String path) {
    InputStream in = null;

    // service properties
    if (LOG.isDebugEnabled()) {
      LOG.debug("Reading service configuration. [{}]", path);
    }

    if ((path != null) && (StringUtils.isNotBlank(path))) {
      try {
        in = new FileInputStream(path);

        XMLConfiguration config = new XMLConfiguration();
        config.load(in);

        _config.addConfiguration(config);
      } catch (ConfigurationException e) {
        LOG.error(Throwables.getStackTraceAsString(e));
      } catch (FileNotFoundException e) {
        LOG.error(Throwables.getStackTraceAsString(e));
      } finally {
        IOUtils.closeQuietly(in);
      }
    }

    // default properties
    if (LOG.isDebugEnabled()) {
      LOG.debug("Reading default properties.");
    }

    try {
      in = getClass().getClassLoader().getResourceAsStream("liveauction.xml");

      XMLConfiguration config = new XMLConfiguration();
      config.load(in);

      _config.addConfiguration(config);
    } catch (ConfigurationException e) {
      LOG.error(Throwables.getStackTraceAsString(e));
    } finally {
      IOUtils.closeQuietly(in);
    }
  }

  public Object getProperty(final String key) {
    if ((_config != null) && (StringUtils.isNotBlank(key))) {
      return _config.getProperty(key);
    }
    return null;
  }

  public String getString(final String key) {
    if ((_config != null) && (StringUtils.isNotBlank(key))) {
      return _config.getString(key);
    }
    return "";
  }

  public int getInt(final String key) {
    if ((_config != null) && (StringUtils.isNotBlank(key))) {
      return _config.getInt(key);
    }
    return 0;
  }

  public List<String> getStrings(final String key) {
    if ((_config != null) && (StringUtils.isNotBlank(key))) {
      List<Object> objs = _config.getList(key);
      List<String> strings = Lists.newArrayList();
      for (Object obj : objs) {
        if ((obj instanceof String) && (StringUtils.isNotBlank((String) obj))) {
          strings.add((String) obj);
        }
      }
      return strings;
    }
    return Collections.emptyList();
  }

  public int getPropertyCount(final String key) {
    if ((_config != null) && (StringUtils.isNotBlank(key))) {
      return _config.getMaxIndex(key);
    }
    return 0;
  }
}
