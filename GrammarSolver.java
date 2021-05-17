// Austin Faux
// CSE: 143, TA Ken Aragon
// 5/9/19

// Generates a madlib of Strings that correspond to a list of nonterminal rules 
// the user can select. The user can also determine how many times they want to 
// generate a madlib. Each non-terminal has rules it must follow. 


import java.util.*;

public class GrammarSolver {
   private SortedMap<String, List<String>> rules;
   
   // Pre: the list of grammar must not be empty (throw IllegalArgumentException if not)
   // Post: connects nonterminals to its corresponding rules. 
   public GrammarSolver(List<String> grammar) {
      if (grammar.isEmpty()) {
         throw new IllegalArgumentException();
      }
      rules = new TreeMap<String, List<String>>();
      for (String word: grammar) {
         // construct map 
         String[] parts = word.split("[|]|::=");
         for (int i = 1; i < parts.length; i++) {
            if (!rules.containsKey(parts[0])) {
               rules.put(parts[0], new ArrayList<String>());    
            } 
            rules.get(parts[0]).add(parts[i].trim());
         }
      }
   }
   
   // Post: returns true if the grammar contains the corresponding sent in symbol. 
   //       false otherwise.
   public boolean grammarContains(String symbol) {
      if (rules.containsKey(symbol)) {
         return true;
      } else {
         return false;
      }
   }
   
   // Pre: The number of iterations must be greater than 0 and the grammar
   //       must contain the given symbol (throw IllegalArgumentException if not)
   // Post: Returns an array of strings which contents correspond to the passed in symbols 
   //       grammar rules. The size of the array will be the number of iterations the 
   //       user chooses to pass in. 
   public String[] generate(String symbol, int times) {
      if (times < 0 || !rules.containsKey(symbol)) {
         throw new IllegalArgumentException();
      }
      String[] result = new String[times];
      for (int i = 0; i < times; i++) {
         result[i] = output(symbol);
      }
      return result;
   }
 
   // Returns a String that acts as a list of available nonterminal symbols. 
   public String getSymbols() {     
      return rules.keySet().toString();
   }
      
   // Post: breaks down each rule until it finds a terminal grammar symbol 
   //       builds a string from it's rules. 
   private String output(String symbol) {
      Random r = new Random();
      String result = "";
      int num = r.nextInt(rules.get(symbol).size());
      String[] temp = rules.get(symbol).get(num).split("[ \t]+");
      for (int i = 0; i < temp.length; i++) {
         if (!rules.containsKey(temp[i])) {   
           result += temp[i];
         } else {
            if (i == 0) {
               result += output(temp[i]);
            } else {
               result += " " + output(temp[i]);
            }
         }
      }
      return result;
   }
}