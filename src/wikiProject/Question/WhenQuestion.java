package wikiProject.Question;

import java.util.*;
import java.util.regex.*;

import wikiProject.Util.ComponentUtil;

public class WhenQuestion extends Question {

   private ArrayList<String> validMonths;

   public WhenQuestion(String text) {
      super(text);
      validMonths = new ArrayList<String>();
      setValidMonths();
   }

   private void setValidMonths() {
      validMonths.add("January"); validMonths.add("Jan"); validMonths.add("Jan.");
      validMonths.add("February"); validMonths.add("Feb"); validMonths.add("Feb.");
      validMonths.add("March"); validMonths.add("Mar"); validMonths.add("Mar.");
      validMonths.add("April"); validMonths.add("Apr"); validMonths.add("Apr.");
      validMonths.add("May");
      validMonths.add("June"); validMonths.add("Jun"); validMonths.add("Jun.");
      validMonths.add("July"); validMonths.add("Jul"); validMonths.add("Jul.");
      validMonths.add("August"); validMonths.add("Aug"); validMonths.add("Aug.");
      validMonths.add("September"); validMonths.add("Sept"); validMonths.add("Sept.");
      validMonths.add("October"); validMonths.add("Oct"); validMonths.add("Oct.");
      validMonths.add("November"); validMonths.add("Nov"); validMonths.add("Nov.");
      validMonths.add("December"); validMonths.add("Dec"); validMonths.add("Dec.");
   }

   @Override
   public boolean generateQA() {

      ComponentUtil.convertToList(getText());
      return false;
   }

   /* When Questions KeyWords
    * Month, Year, AM, PM, Numbers, dates, days, century
    * Formats:
    * KeyWord + in + year(answer)
    * KeyWord + on + Month, Day, Year
    * (possible, but incorrect) an + date(answer) + keyword
    * a + date(answer) + s + keyword
    */
   /*@Override
   public boolean generateQA() {
      if (word_In_Year()) {}
      else if (month_Year()) {}
      else {
         return false;
      }
      else if (a_Date_Word()) {
         return true;
      }
      //p = Pattern.compile(".*\\s(.*).* on \\d.*");
      //m = p.matcher(text);
      return true;
   }
   */

   /* KeyWord + in + year(answer) */
   private boolean word_In_Year() {
      try {
         //finds: anything to whitespace char to anything to whitespace char to in (year)
         Pattern p = Pattern.compile(".*\\s(.*)\\sin \\d\\d\\d\\d");
         Matcher m = p.matcher(getText());
         if (m.find()) {
            Pattern p2 = Pattern.compile("in \\d\\d\\d\\d");
            Matcher m2 = p2.matcher(getText());
            if (m2.find()) {
               setQuestion("When was this " + m.group(1) + "?");
               setAnswer(m2.group().substring(3));
               return true;
            }
         }
      }
      catch (Exception e) {}
      return false;
   }

   // Work in Progess
   // the string pattern is : "on+space+*+space+digit+,+space+digit" 
   // for example: on May 22, 1999
   // 
   private boolean word_On_Month_Day_Year() {
      try {
      Pattern p = Pattern.compile("on .*, \\d\\d\\d\\d");
      Matcher m = p.matcher(getText());
         if (m.find()) {
            Pattern p2 = Pattern.compile("on \\d\\d\\d\\d");
            Matcher m2 = p2.matcher(getText());
            if (m2.find()) {
               setQuestion("When was this " + m.group(1) + "?");
               setAnswer(m2.group().substring(3));
               return true;
            }
         }
      }
      catch (Exception e) {}
      return false;
   }

   //example: a 1920 painting by someone
   //Work In Progress
   private boolean a_Date_Word() {
      Pattern p = Pattern.compile(".*a \\d.*\\s(.*).*");
      Matcher m = p.matcher(getText());
      if (m.find()) {
         Pattern p2 = Pattern.compile("a\\s(\\d.*)\\s");
         Matcher m2 = p2.matcher(getText());
         if (m2.find()) {
            setQuestion("When was this " + m.group(1) + "?");
            setAnswer(m2.group(1));
            return true;
         }
      }
      return false;
   }

   /*
    * Note: Keyword placement(answer) is currently unknown
    */
   private boolean month_Year() {
      try {
         Pattern p = Pattern.compile(".*\\s(.*)\\s\\d\\d\\d\\d");
         Matcher m = p.matcher(getText());
         if (m.find()) {
            String month = m.group(1);
            if (validMonths.contains(month)) {
               setQuestion("");
               setAnswer(month + m.group().substring(m.group().length()-5));
               return true;
            }
         }
      }
      catch (Exception e) {}
      return false;
   }

  /*
   private enum Month {
      January, February, March, April, May, June, July, August, September, October, November, December;
   }

   private enum Day {
      Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;
   }
   */
}
