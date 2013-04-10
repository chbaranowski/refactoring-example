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

    private Player player(String name) {
        if (player1.name.equals(name)) {
            return player1;
        } else if (player2.name.equals(name)) {
            return player2;
        } else {
            throw unknownPlayer(name);
        }
    }

    private IllegalArgumentException unknownPlayer(String name) {
        String msg = String.format("Player with name %s is unknown.", name);
        return new IllegalArgumentException(msg);
    }

    public String getScore() {
        String score = "";
        Score tempScore = Score.zero();
        if (player1.score.equals(player2.score)) {
            score = getAllScore(player1.score);
        } else if (player1.score.value >= 4 || player2.score.value >= 4) {
            int minusResult = player1.score.minus(player2.score).value;
            if (minusResult == 1)
                score = "Advantage player1";
            else if (minusResult == -1)
                score = "Advantage player2";
            else if (minusResult >= 2)
                score = "Win for player1";
            else
                score = "Win for player2";
        } else {
            for (int i = 1; i < 3; i++) {
                if (i == 1)
                    tempScore = player1.score;
                else {
                    score += "-";
                    tempScore = player2.score;
                }
                switch (tempScore.value) {
                case 0:
                    score += "Love";
                    break;
                case 1:
                    score += "Fifteen";
                    break;
                case 2:
                    score += "Thirty";
                    break;
                default:
                    score += "Forty";
                    break;
                }
            }
        }
        return score;
    }

    private String getAllScore(Score scoreAllValue) {
        String score;
        switch (scoreAllValue.value) {
        case 0:
            score = "Love-All";
            break;
        case 1:
            score = "Fifteen-All";
            break;
        case 2:
            score = "Thirty-All";
            break;
        case 3:
            score = "Forty-All";
            break;
        default:
            score = "Deuce";
            break;
        }
        return score;
    }
}

class Player {

    final String name;

    Score score = Score.zero();

    public Player(String name) {
        this.name = name;
    }

    public void wonPoint() {
        this.score = score.plusOne();
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

    public Score minus(Score score) {
        return new Score(value - score.value);
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
