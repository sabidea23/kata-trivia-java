package trivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game implements IGame {

   ArrayList<Player> players = new ArrayList<>();

   LinkedList<String> popQuestions = new LinkedList<>();
   LinkedList<String> scienceQuestions = new LinkedList<>();
   LinkedList<String> sportsQuestions = new LinkedList<>();
   LinkedList<String> rockQuestions = new LinkedList<>();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public Game() {
      for (int i = 0; i < 50; i++) {
         popQuestions.addLast("Pop Question " + i);
         scienceQuestions.addLast("Science Question " + i);
         sportsQuestions.addLast("Sports Question " + i);
         rockQuestions.addLast("Rock Question " + i);
      }
   }


   public void add(String playerName) {
      Player player = new Player(playerName);
      player.setPlace(1);
      player.setPurse(0);
      player.setInPenaltyBox(false);
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

            System.out.println(player.getName() + "'s new location is " + player.getPlace());
            System.out.println("The category is " + currentCategory());
            askQuestion();
         } else {
            System.out.println(player.getName() + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {
         movePlayer(player, roll);

         System.out.println(player.getName() + "'s new location is " + player.getPlace());
         System.out.println("The category is " + currentCategory());
         askQuestion();
      }
   }

   private void movePlayer(Player player, int roll) {
      int newPlace = player.getPlace() + roll;

      if (newPlace > 12)
         newPlace -= 12;

      player.setPlace(newPlace);
   }

   private void askQuestion() {
      switch (currentCategory()) {
         case "Pop" -> System.out.println(popQuestions.removeFirst());
         case "Science" -> System.out.println(scienceQuestions.removeFirst());
         case "Sports" -> System.out.println(sportsQuestions.removeFirst());
         case "Rock" -> System.out.println(rockQuestions.removeFirst());
      }
   }

   private String currentCategory() {
      int place = players.get(currentPlayer).getPlace() - 1;
      return switch (place) {
         case 0, 4, 8 -> "Pop";
         case 1, 5, 9 -> "Science";
         case 2, 6, 10 -> "Sports";
         default -> "Rock";
      };
   }

   public boolean handleCorrectAnswer() {
      Player player = players.get(currentPlayer);

      if (player.isInPenaltyBox()) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            player.setPurse(player.getPurse() + 1);
            System.out.println(player.getName()
                    + " now has "
                    + player.getPurse()
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size())
               currentPlayer = 0;

            return winner;
         } else {
            currentPlayer++;
            if (currentPlayer == players.size())
               currentPlayer = 0;
            return true;
         }

      } else {
         System.out.println("Answer was corrent!!!!");
         player.setPurse(player.getPurse() + 1);
         System.out.println(player.getName()
                 + " now has "
                 + player.getPurse()
                 + " Gold Coins.");

         boolean winner = didPlayerWin();
         currentPlayer++;
         if (currentPlayer == players.size()) currentPlayer = 0;

         return winner;
      }
   }

   public boolean wrongAnswer() {
      Player player = players.get(currentPlayer);

      System.out.println("Question was incorrectly answered");
      System.out.println(player.getName() + " was sent to the penalty box");
      player.setInPenaltyBox(true);

      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
      return true;
   }

   private boolean didPlayerWin() {
      return !(players.get(currentPlayer).getPurse() == 6);
   }
}
