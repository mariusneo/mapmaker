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

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * ZipUtil.java
 * <p/>
 * Utility class dependent on commons-compress and commons-io to
 * handle my zip compression needs.
 *
 * @author Jason Ferguson
 */
public class ZipUtil {

    public static String tmpFile;

    static {
        tmpFile = System.getProperty("java.io.tmpdir");
    }

    /**
     * Decompress a ZIP file into the temp directory, then return alist of File objects for further
     * processing. User is responsible for cleaning up the files later
     *
     * @param zipFile File object representing the zipped file to be decompressed
     * @return a List<File> containing File objects for each file that was in the zip archive
     * @throws FileNotFoundException zip archive file was not found
     * @throws ArchiveException      something went wrong creating
     *                               the archive input stream
     * @throws IOException
     */
    public static List<File> decompress(File zipFile) throws
            FileNotFoundException,
            ArchiveException,
            IOException {

        List<File> archiveContents = new ArrayList<File>();

        // create the input stream for the file, then the input stream for the actual zip file
        final InputStream is = new FileInputStream(zipFile);
        ArchiveInputStream ais = new
                ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.ZIP,
                is);

        // cycle through the entries in the zip archive and write them to the system temp dir
        ZipArchiveEntry entry = (ZipArchiveEntry) ais.getNextEntry();
        while (entry != null) {
            File outputFile = new File(tmpFile, entry.getName());   // don't do this anonymously, need it for the list
            OutputStream os = new FileOutputStream(outputFile);

            IOUtils.copy(ais, os);  // copy from the archiveinputstream to the output stream
            os.close();     // close the output stream

            archiveContents.add(outputFile);

            entry = (ZipArchiveEntry) ais.getNextEntry();
        }

        ais.close();
        is.close();

        return archiveContents;
    }

    /**
     * Decompress a zip file from a remote URL.
     *
     * @param url       remote URL to import the file from
     * @return          a List<File> containing File objects for each file that was in the zip archive
     * @throws FileNotFoundException    zip archive file was not found
     * @throws ArchiveException         something went wrong creating
     *                                  the archive input stream
     * @throws IOException
     */
    public static List<File> decompress(URL url) throws
            FileNotFoundException,
            ArchiveException,
            IOException {

        List<File> archiveContents = new ArrayList<File>();
        final InputStream is = new BufferedInputStream(url.openStream(), 1024);
        ArchiveInputStream ais = new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.ZIP, is);

                // cycle through the entries in the zip archive and write them to the system temp dir
        ZipArchiveEntry entry = (ZipArchiveEntry) ais.getNextEntry();
        while (entry != null) {
            File outputFile = new File(tmpFile, entry.getName());   // don't do this anonymously, need it for the list
            OutputStream os = new FileOutputStream(outputFile);

            IOUtils.copy(ais, os);  // copy from the archiveinputstream to the output stream
            os.close();     // close the output stream

            archiveContents.add(outputFile);

            entry = (ZipArchiveEntry) ais.getNextEntry();
        }

        ais.close();
        is.close();

        return archiveContents;
    }

    /**
     * Compress a List of File objects into a single ZIP file. User is responsible for deleting the files which
     * have been compressed
     *
     * @param filename filename for the generated zip file
     * @param fileList List<File> containing the File objects to add to the archive
     * @return a File object representing the zip archive file
     * @throws FileNotFoundException unable to find the file when creating the OutputStream
     * @throws ArchiveException      something went wrong creating
     *                               the output archive
     * @throws IOException
     */
    public static File compress(String filename, List<File> fileList)
            throws FileNotFoundException,

            ArchiveException,

            IOException {

        // Create the File object that will later be returned
        File zipfile = new File(tmpFile, filename);

        // create the output stream and the archiveoutput stream (as a zip file)
        final OutputStream os = new FileOutputStream(zipfile);
        ArchiveOutputStream aos = new
                ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP,
                os);

        // cycle throught the files in the list
        for (File file : fileList) {
            // add an anonymous ZipArchiveEntry to the archiveoutputstream, then copy the streams
            aos.putArchiveEntry(new ZipArchiveEntry(file.getName()));
            IOUtils.copy(new FileInputStream(file), aos);
            aos.closeArchiveEntry();

        }

        // cleanup. Everybody do their part.
        aos.close();
        os.close();

        return zipfile;
    }

}