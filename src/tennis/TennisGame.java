package tennis;

public class TennisGame {

    private Player player1;
    private Player player2;

    public TennisGame(String player1Name, String player2Name) {
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
    }

    public void wonPoint(String playerName) {
        player(playerName).wonPoint();
    }

    public String getScore() {
        if (player1.hasSameScoreThen(player2)) {
            return sameScore();
        } else if (playerOneLeadsAndHasMoreThanFourPoints()) {
            if (playerOneHasTwoMorePoints()) {
                return winFor(player1);
            } else {
                return advantageFor(player1);
            }
        } else if (playerTwoLeadsAndHasMoreThanFourPoints()) {
            if (playerTwoHasTwoMorePoints()) {
                return winFor(player2);
            } else {
                return advantageFor(player2);
            }
        } else {
            return score();
        }
    }
    
    private Player player(String name) {
        if (player1.name.equals(name)) {
            return player1;
        } else if (player2.name.equals(name)) {
            return player2;
        } else {
            throw unknownPlayer(name);
        }
    }

    private boolean playerTwoHasTwoMorePoints() {
        return player2.hasTwoMorePointsThen(player1);
    }

    private boolean playerOneHasTwoMorePoints() {
        return player1.hasTwoMorePointsThen(player2);
    }

    private boolean playerTwoLeadsAndHasMoreThanFourPoints() {
        return player2.hasFourPoints() && player2.hasMorePointsThen(player1);
    }

    private boolean playerOneLeadsAndHasMoreThanFourPoints() {
        return player1.hasFourPoints() && player1.hasMorePointsThen(player2);
    }

    private String score() {
        return player1.score.name() + "-" + player2.score.name();
    }

    private String advantageFor(Player player) {
        return "Advantage " + player.name;
    }

    private String winFor(Player player) {
        return "Win for " + player.name;
    }

    private String sameScore() {
        Score scoreAll = player1.score;
        if (scoreAll.isDeuce())
            return scoreAll.name();
        else
            return scoreAll.name() + "-All";
    }
    
    private IllegalArgumentException unknownPlayer(String name) {
        String msg = String.format("Player with name %s is unknown.", name);
        return new IllegalArgumentException(msg);
    }
}

class Player {

    final String name;

    Score score = Score.zero();

    public Player(String name) {
        this.name = name;
    }

    public boolean hasTwoMorePointsThen(Player player2) {
        return score.value >= player2.score.value + 2;
    }

    public boolean hasMorePointsThen(Player player2) {
        return score.value > player2.score.value;
    }

    public void wonPoint() {
        this.score = score.plusOne();
    }

    public boolean hasSameScoreThen(Player player) {
        return score.equals(player.score);
    }

    public boolean hasFourPoints() {
        return score.value >= 4;
    }

}

class Score {

    final int value;

    public Score(int scoreValue) {
        this.value = scoreValue;
    }

    public Score plusOne() {
        return new Score(value + 1);
    }

    public String name() {
        String name;
        switch (value) {
        case 0:
            name = "Love";
            break;
        case 1:
            name = "Fifteen";
            break;
        case 2:
            name = "Thirty";
            break;
        case 3:
            name = "Forty";
            break;
        default:
            name = "Deuce";
        }
        return name;
    }

    public boolean isDeuce() {
        return value == 4;
    }

    public static Score zero() {
        return new Score(0);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + value;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Score other = (Score) obj;
        if (value != other.value)
            return false;
        return true;
    }

}
