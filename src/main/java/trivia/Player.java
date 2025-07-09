package trivia;

public class Player {
    private final String name;
    private int place;
    private int purse;
    private boolean inPenaltyBox;
    private boolean gettingOutOfPenaltyBox;

    public Player(String name) {
        this.name = name;
        this.place = 1;
        this.purse = 0;
        this.inPenaltyBox = false;
    }

    public String getName() {
        return name;
    }

    public int getPlace() {
        return place;
    }

    public int getPurse() {
        return purse;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }

    public boolean isGettingOutOfPenaltyBox() {
        return gettingOutOfPenaltyBox;
    }

    public void setGettingOutOfPenaltyBox(boolean value) {
        this.gettingOutOfPenaltyBox = value;
    }

    public void addCoin() {
        this.purse++;
    }

    public String getFormattedCoinCount() {
        return name + " now has " + purse + " Gold Coins.";
    }

    public void move(int roll) {
        int newPlace = place + roll;
        if (newPlace > GameConstants.MAX_BOARD_POSITION) {
            newPlace -= GameConstants.MAX_BOARD_POSITION;
        }
        this.place = newPlace;
    }

    public boolean shouldExitPenaltyBox(int roll) {
        boolean shouldExit = roll % 2 != 0;
        setGettingOutOfPenaltyBox(shouldExit);
        System.out.println(name + (shouldExit ?
                " is getting out of the penalty box" : " is not getting out of the penalty box"));
        return shouldExit;
    }
}
