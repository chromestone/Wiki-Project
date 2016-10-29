package wikiProject;

import java.util.*;

import wikiProject.Question.*;

public class QuestionBuilder {

   public QuestionBuilder() {
   }

   public ArrayList<Question> generateQuestions(String text) {
      ArrayList<Question> questions = new ArrayList<Question>();
      WhenQuestion whenQ = new WhenQuestion(text);
      if (whenQ.generateQA()) {
      }
      return questions; 
   }

   public static void main(String[] args) {
      QuestionBuilder qB = new QuestionBuilder();
      qB.generateQuestions("I am a student in 1997");
      //qB.generateQuestions("Double bassist Reggie Workman, tenor saxophone player Pharoah Sanders, and drummer Idris Muhammad performing in 1978");
      //qB.generateQuestions("a 1987 painting");
      //qB.generateQuestions("Someone was born on January 3, 1945");
      //qB.generateQuestions("Someone was born on January 1945");
   }
}
