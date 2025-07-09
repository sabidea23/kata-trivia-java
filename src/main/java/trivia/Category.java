package trivia;

public enum Category {
    POP, SCIENCE, SPORTS, ROCK;

    @Override
    public String toString() {
        return switch (this) {
            case POP -> "Pop";
            case SCIENCE -> "Science";
            case SPORTS -> "Sports";
            case ROCK -> "Rock";
        };
    }

    public static Category fromPlace(int place) {
        int pos = (place - 1) % 12;
        return switch (pos) {
            case 0, 4, 8 -> POP;
            case 1, 5, 9 -> SCIENCE;
            case 2, 6, 10 -> SPORTS;
            default -> ROCK;
        };
    }
}
