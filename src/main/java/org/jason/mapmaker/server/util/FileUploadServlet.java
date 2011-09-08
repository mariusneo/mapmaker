/**
 * Copyright 2011 Jason Ferguson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jason.mapmaker.server.util;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.List;

/**
 * FileUploadServlet.java
 *
 *  Copyright 2011 Jason Ferguson
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * Servlet to do alot of the heavy lifting for processing an uploaded ShapeFile
 *
 * @author Jason Ferguson
 */
public class FileUploadServlet extends HttpServlet {

    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        String tmpFile = System.getProperty("java.io.tmpdir");

        // upload the ZIP file
        try {
            List<FileItem> fileItemList = (List<FileItem>) upload.parseRequest(request);

            for (FileItem item : fileItemList) {
                if (!item.isFormField()) {
                    String fieldName = item.getFieldName();
                    String fileName = item.getName();
                    String contentType = item.getContentType();
                    boolean isInMemory = item.isInMemory();
                    long sizeInBytes = item.getSize();

                    File file = new File(tmpFile, fileName);
                    // use commons-compress to process the zipfile
                    ZipFile zipFile = new ZipFile(file);
                    Enumeration zipFiles = zipFile.getEntries();
                    while (zipFiles.hasMoreElements()) {
                        ZipArchiveEntry entry = (ZipArchiveEntry) zipFiles.nextElement();
                        String shapeFileName;

                        if (!entry.isDirectory()) {
                            String entryFileName = entry.getName();
                            if (entryFileName.indexOf(".shp") > -1) {
                                shapeFileName = entryFileName;
                            }

                            BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
                            int currentByte;
                            byte[] data = new byte[2048];

                            FileOutputStream fos = new FileOutputStream(file);
                            BufferedOutputStream bos = new BufferedOutputStream(fos, 2048);
                            while((currentByte = bis.read(data, 0, 2048)) !=  -1) {
                                bos.write(data, 0, currentByte);
                            }

                            bos.flush();
                            bos.close();
                            bis.close();
                        }
                    }

                    item.write(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        // process the files inside the zip file

        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException();
    }
}
