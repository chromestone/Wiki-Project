package wikiProject;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public final class WGConfig {

   private WGConfig() {
   }

   public static ArrayList<String> getConfig(String fileName) throws Exception {
      try {
         ArrayList<String> config = new ArrayList<String>();
         BufferedReader configReader = new BufferedReader(new FileReader(fileName));
         String line;
         while ((line = configReader.readLine()) != null) {
            //finds the value which is after the equal sign
            config.add(line.substring(line.indexOf("\t")+1));
         }
         configReader.close();
         return config;
      }
      catch (Exception e) {
         System.err.println("Error reading wiki-getter configuration file.");
         throw e;
      }
   }
}
