package wikiProject;

import org.w3c.dom.*;
import javax.xml.xpath.*;

public class ImageHandler {

   Node _imgNode;
   String _imgURL;

   public ImageHandler(Node imgNode) {
      _imgNode = imgNode;
      _imgURL = "";
   }

   public String getImageName() {
      Node nameNode = _imgNode.getAttributes().getNamedItem("alt");
      if (nameNode != null) {
         String imgName = nameNode.getNodeValue();
         if (imgName.length() > 0 && !imgName.contains("Search") && !imgName.contains("Wiki") && isValid(imgName)) {
            return imgName;
         }
      }
      return "";
   }

   public String getSrcImageName() {
      if (_imgURL.length() > 0) {
         String imgName = _imgURL.substring(_imgURL.lastIndexOf("/")+1);
         if (isValid(imgName) && imgName.contains(".")) {
            if (ImageWriter.hasValidExtension(imgName)) {
               return imgName;
            }
         }
      }
      return "";
   }

   public String getImageURL() {
      Node srcNode = _imgNode.getAttributes().getNamedItem("src");
      if (srcNode != null) {
         _imgURL = srcNode.getNodeValue();
         if (isValid(_imgURL) && _imgURL.contains("upload")) {
            if (!_imgURL.contains("http:")) {
               return "http:" + _imgURL;
            }
            return _imgURL;
         }
      }
      return "";
   }

   private boolean isValid(String text) {
      if (!text.contains("svg") && !text.contains("logo")) {
         return true;
      }
      return false;
   }

   public String getImageDescription() {
      try {
         XPathFactory xPathfactory = XPathFactory.newInstance();
         XPath xpath = xPathfactory.newXPath();
         XPathExpression xpExpr = xpath.compile("../../*[2]");
         Node descrNode = (Node)xpExpr.evaluate(_imgNode, XPathConstants.NODE);
         if (descrNode != null) {
            String imgDescr = descrNode.getTextContent();
            if (imgDescr != null) {
               return imgDescr;
            }
         }
      }
      catch (Exception e) {
         e.printStackTrace();
         System.err.println("    <End>");
      }
      return "";
   }
}
