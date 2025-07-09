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
      Player player = getCurrentPlayer();

      System.out.println(player.getName() + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (player.isInPenaltyBox()) {
         if (player.shouldExitPenaltyBox(roll)) {
            player.move(roll);
            describePlayerLocationAndAsk(player);
         }
      } else {
         player.move(roll);
         describePlayerLocationAndAsk(player);
      }
   }

   private void describePlayerLocationAndAsk(Player player) {
      System.out.println(player.getName() + "'s new location is " + player.getPlace());
      Category category = Category.getCategoryForPlace(player.getPlace());
      System.out.println("The category is " + category);
      System.out.println(questions.get(category).remove());
   }

   public boolean handleCorrectAnswer() {
      Player player = getCurrentPlayer();

      if (player.isInPenaltyBox()) {
         if (player.isGettingOutOfPenaltyBox()) {
            return processCorrectAnswer(player);
         } else {
            nextPlayer();
            return true;
         }
      } else {
         return processCorrectAnswer(player);
      }
   }

   private boolean processCorrectAnswer(Player player) {
      System.out.println("Answer was correct!!!!");
      player.addCoin();
      System.out.println(player.getFormattedCoinCount());

      boolean winner = !isGameWon(player);
      nextPlayer();
      return winner;
   }

   public boolean wrongAnswer() {
      Player player = getCurrentPlayer();

      System.out.println("Question was incorrectly answered");
      System.out.println(player.getName() + " was sent to the penalty box");
      player.setInPenaltyBox(true);

      nextPlayer();
      return true;
   }

   private void nextPlayer() {
      currentPlayer = (currentPlayer + 1) % players.size();
   }

   private Player getCurrentPlayer() {
      return players.get(currentPlayer);
   }

   private boolean isGameWon(Player player) {
      return player.getPurse() == GameConstants.WINNING_COINS;
   }
}
