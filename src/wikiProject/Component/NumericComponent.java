package wikiProject.Component;

public class NumericComponent extends StringComponent {

   public NumericComponent(String baseString) {

      super(baseString);
      if (!NumericComponent.isValidDateNumber(baseString)) {
         throw new IllegalArgumentException();
      }
   }

   public int getLength() {
      return getBaseString().length();
   }

   public static boolean isValidDateNumber(String dateNumString) {
      if (dateNumString == null) {
         return false;
      }

      int length = dateNumString.length();

      if (length == 0) {
         return false;
      }

      if (dateNumString.charAt(0) == '-') {
         return false;
      }

      for (int i = 0; i < length; i++) {
         char c = dateNumString.charAt(i);
         if (c <= '/' || c >= ':') {
            return false;
         }
      }

      return true;
   }
}
