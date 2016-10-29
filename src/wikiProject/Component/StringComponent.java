package wikiProject.Component;

public abstract class StringComponent {

   private final String baseString;

   public StringComponent(String baseString) {

      if (baseString.contains(" ")) {
         throw new IllegalArgumentException();
      }
      this.baseString = baseString;
   }

   public String getBaseString() {
      return baseString;
   }
}
