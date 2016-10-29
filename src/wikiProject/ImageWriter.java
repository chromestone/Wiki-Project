package wikiProject;

import java.net.*;
import java.io.*;

public final class ImageWriter {

   private ImageWriter() {
   }

   public static void write(URL url, String fileName) throws Exception {
      if (hasValidExtension(fileName)) {
         System.out.println(fileName.substring(fileName.lastIndexOf(".")+1));
         try {
            URLConnection urlCon = url.openConnection();
            InputStream inputStream = urlCon.getInputStream();
            writeBinary(inputStream, fileName);
            inputStream.close();
         }
         catch (Exception e) {
            System.err.println("Error writing file via stream from URL.");
            throw e;
         }
      }
   }

   private static void writeBinary(InputStream inputStream, String fileName) throws Exception{
      try {
         OutputStream outputStream = new FileOutputStream(new File(fileName));
         int read = 0;
         byte[] bytes = new byte[1024];
         while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
         }
         outputStream.close();
      }
      catch(Exception e) {
         System.err.println("Error writing binary file.");
         throw e;
      }
   }

   private enum Ext {
      jpg, jpeg, jpe, tif, tiff, gif, bmp, dib, png;
   }

   public static boolean hasValidExtension(String fileName) {
      boolean valid = false;
      try {
         Ext ext = Ext.valueOf(fileName.substring(fileName.lastIndexOf(".")+1));
         switch (ext) {
         case jpg: valid = true; break;
         case png: valid = true; break;
         case jpeg: valid = true; break;
         case gif: valid = true; break;
         case jpe: valid = true; break;
         case tif: valid = true; break;
         case tiff: valid = true; break;
         case bmp: valid = true; break;
         case dib: valid = true; break;
         default : break;
         }
      }
      catch (Exception e) {
         valid = false;
      }
      return valid;
   }
}
