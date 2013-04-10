package tennis;

public class TennisGame {

    private Score score1 = Score.zero();
    private Score score2 = Score.zero();
    private String player1Name;
    private String player2Name;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (player1Name.equals(playerName))
            score1 = score1.plusOne();
        else if (player2Name.equals(playerName))
            score2 = score2.plusOne();
        else
            throw new IllegalArgumentException("Player with name " + playerName
                    + " is unknown.");
    }

    public String getScore() {
        String score = "";
        Score tempScore = Score.zero();
        if (score1.equals(score2)) {
            score = getAllScore(score1);
        } else if (score1.value >= 4 || score2.value >= 4) {
            int minusResult = score1.minus(score2).value;
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
                    tempScore = score1;
                else {
                    score += "-";
                    tempScore = score2;
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

class Score {
    
    final int value;

    public Score(int scoreValue) {
        this.value = scoreValue;
    }
    
    public Score plusOne(){
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
