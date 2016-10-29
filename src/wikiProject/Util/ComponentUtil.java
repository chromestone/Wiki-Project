package wikiProject.Util;

import java.util.*;

import wikiProject.Component.*;

public class ComponentUtil {

   public static ArrayList<StringComponent> convertToList(String str) {
      ArrayList<StringComponent> componentList = new ArrayList<StringComponent>();
      String[] componentStrings = str.split(" ");
      for (String componentString : componentStrings) {
         if (NumericComponent.isValidDateNumber(componentString)) {
            componentList.add(new NumericComponent(componentString));
         }
         else if (LetterComponent.isOnlyLetter(componentString)) {
            componentList.add(new LetterComponent(componentString));
         }
         else {
            componentList.add(new UnknownComponent(componentString));
         }
      }
      return componentList;
   }

   public static String convertToString(ArrayList<StringComponent> componentList) {
      String context = "";

      Iterator<StringComponent> it = componentList.iterator();

      while (it.hasNext()) {
         context += it.next().getBaseString() + " ";
      }

      return context.substring(0, context.length()-1);
   }
}
