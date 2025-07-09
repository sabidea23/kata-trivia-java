package trivia;

import java.util.*;

public class Game implements IGame {

   private final List<Player> players = new ArrayList<>();
   private final Map<Category, Queue<String>> questions;
   private int currentPlayer = 0;


   public Game() {
      this.questions = QuestionFactory.createDefaultQuestions(GameConstants.DEFAULT_QUESTION_COUNT);
   }

   public void add(String playerName) {
      Player player = new Player(playerName);
      players.add(player);

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
   }

   public void roll(int roll) {
      Player player = players.get(currentPlayer);

      System.out.println(player.getName() + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (player.isInPenaltyBox()) {
         if (roll % 2 != 0) {
            player.setGettingOutOfPenaltyBox(true);
            System.out.println(player.getName() + " is getting out of the penalty box");

            player.move(roll);
            System.out.println(player.getName() + "'s new location is " + player.getPlace());
            printCategoryAndAsk();
         } else {
            player.setGettingOutOfPenaltyBox(false);
            System.out.println(player.getName() + " is not getting out of the penalty box");
         }
      } else {
         player.move(roll);
         System.out.println(player.getName() + "'s new location is " + player.getPlace());
         printCategoryAndAsk();
      }
   }

   private void printCategoryAndAsk() {
      Category category = currentCategory();
      System.out.println("The category is " + category);
      askQuestion(category);
   }

   private void askQuestion(Category category) {
      System.out.println(questions.get(category).remove());
   }

   private Category currentCategory() {
      return Category.getCategoryForPlace(players.get(currentPlayer).getPlace());
   }

   public boolean handleCorrectAnswer() {
      Player player = players.get(currentPlayer);

      if (player.isInPenaltyBox()) {
         if (player.isGettingOutOfPenaltyBox()) {
            System.out.println("Answer was correct!!!!");
            player.addCoin();
            System.out.println(player.getFormattedCoinCount());

            boolean winner = !isGameWon(player);
            nextPlayer();
            return winner;
         } else {
            nextPlayer();
            return true;
         }

      } else {
         System.out.println("Answer was corrent!!!!");
         player.addCoin();
         System.out.println(player.getFormattedCoinCount());

         boolean winner = !isGameWon(player);
         nextPlayer();
         return winner;
      }
   }

   public boolean wrongAnswer() {
      Player player = players.get(currentPlayer);

      System.out.println("Question was incorrectly answered");
      System.out.println(player.getName() + " was sent to the penalty box");
      player.setInPenaltyBox(true);

      nextPlayer();
      return true;
   }

   private void nextPlayer() {
      currentPlayer = (currentPlayer + 1) % players.size();
   }

   private boolean isGameWon(Player player) {
      return player.getPurse() == GameConstants.WINNING_COINS;
   }
}
