package Utils;

import java.util.List;

public class LostGameDTO {
    private Integer gameId;
    private Integer numberOfTries;
    private List<String> guessedPositions;
    private String actualPosition;
    public LostGameDTO() {

    }
    public Integer getGameId() {
        return gameId;
    }
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }
    public Integer getNumberOfTries() {
        return numberOfTries;
    }
    public void setNumberOfTries(Integer numberOfTries) {
        this.numberOfTries = numberOfTries;
    }
    public List<String> getGuessedPositions() {
        return guessedPositions;
    }
    public void setGuessedPositions(List<String> guessedPositions) {
        this.guessedPositions = guessedPositions;
    }
    public String getActualPosition() {
        return actualPosition;
    }
    public void setActualPosition(String actualPosition) {
        this.actualPosition = actualPosition;
    }

    // Constructors, getters, setters
}
