package tennis;

import java.util.*;

public class TennisGame {

    private PlayerRegistry registry = new PlayerRegistry();
    
    public TennisGame(String firstPlayer, String secondPlayer) {
        registry.register(firstPlayer);
        registry.register(secondPlayer);
    }

    public void wonPoint(String playerName) {
        Player player = findPlayerWith(playerName);
        player.wonPoint();
    }

    public String getScore() {
        return getScore(registry.getFirstPlayer(), registry.getSecondPlayer());
    }

    private String getScore(Player firstPlayer, Player secondPlayer) {
        if (firstPlayer.hasSameScoreThen(secondPlayer)) {
            return sameScore();
        } else if (firstPlayer.hasFourOrMoreAndHasMorePointsThen(secondPlayer)) {
            if (firstPlayer.hasTwoMorePointsThen(secondPlayer)) {
                return winFor(firstPlayer);
            } else {
                return advantageFor(firstPlayer);
            }
        } else if (secondPlayer.hasFourOrMoreAndHasMorePointsThen(firstPlayer)) {
            if (secondPlayer.hasTwoMorePointsThen(firstPlayer)) {
                return winFor(secondPlayer);
            } else {
                return advantageFor(secondPlayer);
            }
        } else {
            return score(firstPlayer, secondPlayer);
        }
    }
    
    private Player findPlayerWith(String name) {
        Player player = registry.findByName(name);
        if (player != null) {
            return player;
        } else {
            throw unknownPlayer(name);
        }
    }
   
    private String score(Player player1, Player player2) {
        return String.format("%s-%s", player1.getScore(), player2.getScore());
    }

    private String advantageFor(Player player) {
        return String.format("Advantage %s", player);
    }

    private String winFor(Player player) {
        return String.format("Win for %s", player);
    }

    private String sameScore() {
        Score score = registry.getFirstPlayer().getScore();
        if (score.isDeuce())
            return String.valueOf(score);
        else
            return String.format("%s-All", score);
    }
    
    private IllegalArgumentException unknownPlayer(String name) {
        String msg = String.format("Player with name %s is unknown.", name);
        return new IllegalArgumentException(msg);
    }
}

class PlayerRegistry {
    
    private Map<String, Player> players = new HashMap<>();
    
    void register(String name) {
        players.put(name, new Player(name));
    }
    
    Player getFirstPlayer(){
        Iterator<Player> iterator = players.values().iterator();
        return iterator.next();
    }
    
    Player getSecondPlayer(){
        Iterator<Player> iterator = players.values().iterator();
        iterator.next();
        return iterator.next();
    }
    
    Player findByName(String name) {
        return players.get(name);
    }
}

class Player extends ValueObject<String>{

    private Score score = Score.zero();

    Player(String name) {
        super(name);
    }

    boolean hasTwoMorePointsThen(Player otherPlayer) {
        return getScore().isGreaterThan(otherPlayer.getScore().plus(1));
    }

    boolean hasMorePointsThen(Player otherPlayer) {
        return getScore().isGreaterThan(otherPlayer.getScore());
    }
    
    boolean hasFourOrMoreAndHasMorePointsThen(Player otherPlayer) {
        return hasMorePointsThen(otherPlayer) && hasFourOrMorePoints();
    }

    void wonPoint() {
       this.score = score.plus(1);
    }

    boolean hasSameScoreThen(Player player) {
        return getScore().equals(player.getScore());
    }

    boolean hasFourOrMorePoints() {
        return getScore().value >= 4;
    }

    Score getScore() {
        return score;
    }
}

class Score extends ValueObject<Integer> {

    Score(Integer scoreValue) {
        super(scoreValue);
    }
    
    boolean isGreaterThan(Score otherScore) {
        return value > otherScore.value;
    }

    Score plus(Integer scoreValue){
        return new Score(this.value + scoreValue);
    }
    
    boolean isDeuce() {
        return value == 4;
    }

    static Score zero() {
        return new Score(0);
    }

    @Override
    public String toString() {
        String name = "unknown";
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
        case 4:
            name = "Deuce";
            break;
        }
        return name;
    }

}

class ValueObject<VALUEOBJECT_TYPE> {
    
    protected final VALUEOBJECT_TYPE value;
    
    public ValueObject(VALUEOBJECT_TYPE value) {
        if(value == null) {
            throw new NullPointerException();
        }
        this.value = value;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ValueObject<?> other = (ValueObject<?>) obj;
        return Objects.equals(value, other.value);
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
    
}
