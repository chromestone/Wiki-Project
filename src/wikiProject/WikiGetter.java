package wikiProject;

import java.util.*;
import java.net.*;
import java.io.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

public class WikiGetter {

	private HashMap<String, String> imgPkg;

	private final String imgListFileNm = "ImagesWritten.txt";
	private final String configFileNm = "WGConfiguration.txt";

	public WikiGetter() {
		imgPkg = new HashMap<String, String>();
	}

	// load image list from the file into a hashmap in memory 
	private void loadImagesWrittenFile() throws Exception {
		try {
			BufferedReader imgLogReader = new BufferedReader(new FileReader(imgListFileNm));
			String line;
			while ((line = imgLogReader.readLine()) != null) {
				imgPkg.put(line.substring(0, line.indexOf("\t")), line.substring(line.indexOf("\t")+1));
			}
			imgLogReader.close();
		}
		catch (Exception e) {
			System.err.println("Error loading" +  imgListFileNm );
			throw e;
		}
	}

	// write image list from hashmap into a file for persistence
	private void saveImagesWrittenFile() throws Exception {
		try {
			Set<Map.Entry<String, String>> set = imgPkg.entrySet();
			Iterator<Map.Entry<String, String>> it = set.iterator();

			BufferedWriter imgLogger = new BufferedWriter(new FileWriter(imgListFileNm));
			while(it.hasNext()) {
				Map.Entry<String, String> map = (Map.Entry<String, String>)it.next();
				imgLogger.write(map.getKey().toString() + "\t" + map.getValue().toString() + "\n");
			}
			//close the BufferredReader when done
			imgLogger.close();
		}
		catch (Exception e) {
			System.err.println("Error logging images written.");
			throw e;
		}
	}

	// parse image node, retrieve the image and save to local file
	private void handleImageNode(Node imgNode, String directory) throws Exception {
		try {
			ImageHandler imgHandler = new ImageHandler(imgNode);

			String imgURL = imgHandler.getImageURL();
			if (imgURL.length() > 0) {

				String imgDescr = imgHandler.getImageDescription();
				if (imgDescr.length() > 0) {

					String imgName = imgHandler.getSrcImageName();
					if (imgName.length() == 0) {

						imgName = imgHandler.getImageName();
						if (imgName.length() == 0) {
							return;
						}
					}
					//saves image
					ImageWriter.write(new URL(imgURL), directory + imgName);
					//keeps record of written images
					if (imgPkg.containsKey(imgName)) {
						imgPkg.put(imgName, imgDescr);
					}
				}
			}
		}
		catch (Exception e) {
			System.err.println("Error handling image node.");
			throw e;
		}
	}

	public void run() throws Exception {
		try {
			//gets configuration
			ArrayList<String> config = WGConfig.getConfig(configFileNm);
			String wikiTopic = config.get(0);
			int maxSize;
			try {
				maxSize = Integer.parseInt(config.get(1));
			}
			catch (Exception e) {
				maxSize = 100;
			}
			String directory = config.get(2);

			loadImagesWrittenFile();

			//opens a stream to internet(wikipedia.org)
			URL url = new URL("http://en.wikipedia.org/wiki/" + wikiTopic);
			URLConnection urlCon = url.openConnection();
			InputStream wikiStream = urlCon.getInputStream();

			//gets xml
			BufferedReader wikiReader = new BufferedReader(new InputStreamReader(wikiStream));
			String xmlString = "";
			String line;
			while ((line = wikiReader.readLine()) != null) {
				xmlString = xmlString.concat(line);
			}
			wikiReader.close();

			//creates xml document
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document xmlDoc = dBuilder.parse(new InputSource(new StringReader(xmlString)));
			NodeList imgList = xmlDoc.getElementsByTagName("img");
			// loop for each image node in the list, save image to local file
			for (int i = 0; i < imgList.getLength(); i++) {

				Node imgNode = imgList.item(i);
				if (imgNode.getNodeType() == Node.ELEMENT_NODE) {
					try {
						handleImageNode(imgNode, directory);
					}
					catch (Exception e) {
						e.printStackTrace();
						System.err.println("    <End>");
					}
					if (imgPkg.size() >= maxSize) {
						break;
					}
				}
			}
			saveImagesWrittenFile();
		}
		catch (Exception e) {
			System.err.println("Error running WikiGetter.");
			throw e;
		}
	}

	/*private void Test() throws Exception {
      try {
         ImageWriter img = new ImageWriter();
      }
      catch (Exception e) {
         throw e;
      }
   }
	 */

	public static void main(String[] args) {
		WikiGetter wikiGtr = new WikiGetter();
		try {
			wikiGtr.run();
			//wikiGtr.Test();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("    <END>");
		}
	}
}
