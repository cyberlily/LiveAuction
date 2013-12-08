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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobiaware.servlet.HttpConstants;

@WebServlet(name="ImporterServlet", urlPatterns="/import")
public class ImporterServlet extends HttpServlet {
  private static final long serialVersionUID = -5191381416329093341L;

  private static final int THRESHOLD_SIZE = 1024 * 1024 * 1; // 1MB
  private static final int MAX_FILE_SIZE = 1024 * 1024 * 1; // 1MB
  private static final int REQUEST_SIZE = 1024 * 1024 * 1; // 1MB

  private static final String NAME = ImporterServlet.class.getSimpleName();
  private static final Logger LOG = LoggerFactory.getLogger(NAME);

  @Override
  protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException {
    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    if (!isMultipart) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }

    DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
    fileItemFactory.setSizeThreshold(THRESHOLD_SIZE);
    fileItemFactory.setRepository(FileUtils.getTempDirectory());

    ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
    servletFileUpload.setFileSizeMax(MAX_FILE_SIZE);
    servletFileUpload.setSizeMax(REQUEST_SIZE);

    File paramFile = null;
    Map<String, String> paramMap = new HashMap<String, String>();

    try {
      FileItemIterator fileItemIterator = servletFileUpload.getItemIterator(request);

      while (fileItemIterator.hasNext()) {
        FileItemStream fileItem = fileItemIterator.next();

        if (fileItem.isFormField()) {
          paramMap.put(fileItem.getFieldName(), Streams.asString(fileItem.openStream()));
        } else {
          paramFile = File.createTempFile("liim_", null);

          InputStream is = new BufferedInputStream(fileItem.openStream());
          FileUtils.copyInputStreamToFile(is, paramFile);
        }
      }
    } catch (IOException e) {
      LOG.error("!EXCEPTION!", e);
    } catch (FileUploadException e) {
      LOG.error("!EXCEPTION!", e);
    }

    if ((paramFile == null) || (!paramFile.exists())) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }

    int pauctionuid = NumberUtils.toInt(paramMap.get(HttpConstants.PARAM_AUCTIONUID), -1);
    if (pauctionuid < 0) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }

    // Non required parameters
    String paction = paramMap.get(HttpConstants.PARAM_ACTION);

    Importer importer = new GreaterGivingImporter(); // only one for now

    FileInputStream is = new FileInputStream(paramFile);
    if (StringUtils.equalsIgnoreCase(paction, HttpConstants.ACTION_ITEMIMPORT)) {
      importer.importItems(pauctionuid, is);
    } else {
      importer.importUsers(pauctionuid, is);
    }

    LOG.info("Imported items [{}] [{}]", paramFile.getName(), importer);

    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(importer.toString());
  }
}
