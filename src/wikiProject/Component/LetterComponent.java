package wikiProject.Component;

public class LetterComponent extends StringComponent {

   public LetterComponent(String baseString) {

      super(baseString);
      if (!LetterComponent.isOnlyLetter(baseString)) {
         throw new IllegalArgumentException();
      }
   }

   public static boolean isOnlyLetter(String onlyLetterString) {
      for (int i = 0; i < onlyLetterString.length(); i++) {
         if (!Character.isLetter(onlyLetterString.charAt(i))) {
            return false;
         }
      }
         
      return true;
   }
}
