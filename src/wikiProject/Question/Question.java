package wikiProject.Question;

public abstract class Question {

   private String text;
   private String question;
   private String answer;

   public Question(String text) {
      this.text = text;
   }
   
   public String getText() {
      return text;
   }

   public abstract boolean generateQA();
   
   protected void setQuestion(String question) {
      this.question = question;
   }

   public String getQuestion() {
      return question;
   }

   protected void setAnswer(String answer) {
      this.answer = answer;
   }

   public String getAnswer() {
      return answer;
   }
}
