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
        if (player1.hasSameScoreThen(player2)) {
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
            score = player1.score.name() + "-" + player2.score.name();
        }
        return score;
    }

    private String getAllScore(Score scoreAll) {
        if(scoreAll.isDeuce())
            return scoreAll.name();
        else
            return scoreAll.name() + "-All";
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
    
    public boolean hasSameScoreThen(Player player){
        return score.equals(player.score);
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
    
    public boolean isDeuce(){
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
