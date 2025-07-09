package trivia;

import java.util.*;

public class Game implements IGame {

   private final List<Player> players = new ArrayList<>();
   private final Map<Category, Queue<String>> questions = new EnumMap<>(Category.class);

   private int currentPlayer = 0;
   private boolean isGettingOutOfPenaltyBox;

   public Game() {
      for (Category category : Category.values()) {
         questions.put(category, new LinkedList<>());
      }

      for (int i = 0; i < 50; i++) {
         questions.get(Category.POP).add("Pop Question " + i);
         questions.get(Category.SCIENCE).add("Science Question " + i);
         questions.get(Category.SPORTS).add("Sports Question " + i);
         questions.get(Category.ROCK).add("Rock Question " + i);
      }
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
            isGettingOutOfPenaltyBox = true;
            System.out.println(player.getName() + " is getting out of the penalty box");

            movePlayer(player, roll);
            printCategoryAndAsk();
         } else {
            isGettingOutOfPenaltyBox = false;
            System.out.println(player.getName() + " is not getting out of the penalty box");
         }
      } else {
         movePlayer(player, roll);
         printCategoryAndAsk();
      }
   }

   private void movePlayer(Player player, int roll) {
      int newPlace = player.getPlace() + roll;
      if (newPlace > 12) newPlace -= 12;
      player.setPlace(newPlace);

      System.out.println(player.getName() + "'s new location is " + newPlace);
   }

   private void printCategoryAndAsk() {
      Category category = currentCategory();
      System.out.println("The category is " + formatCategory(category));
      askQuestion(category);
   }

   private void askQuestion(Category category) {
      System.out.println(questions.get(category).remove());
   }

   private Category currentCategory() {
      int place = players.get(currentPlayer).getPlace() - 1;
      return switch (place % 12) {
         case 0, 4, 8 -> Category.POP;
         case 1, 5, 9 -> Category.SCIENCE;
         case 2, 6, 10 -> Category.SPORTS;
         default -> Category.ROCK;
      };
   }

   private String formatCategory(Category category) {
      return switch (category) {
         case POP -> "Pop";
         case SCIENCE -> "Science";
         case SPORTS -> "Sports";
         case ROCK -> "Rock";
      };
   }

   public boolean handleCorrectAnswer() {
      Player player = players.get(currentPlayer);

      if (player.isInPenaltyBox()) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            player.setPurse(player.getPurse() + 1);
            System.out.println(player.getName() + " now has " + player.getPurse() + " Gold Coins.");

            boolean winner = didPlayerWin();
            nextPlayer();
            return winner;
         } else {
            nextPlayer();
            return true;
         }

      } else {
         System.out.println("Answer was corrent!!!!");
         player.setPurse(player.getPurse() + 1);
         System.out.println(player.getName() + " now has " + player.getPurse() + " Gold Coins.");

         boolean winner = didPlayerWin();
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

   private boolean didPlayerWin() {
      return !(players.get(currentPlayer).getPurse() == 6);
   }
}
