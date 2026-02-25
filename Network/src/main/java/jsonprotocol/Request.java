package jsonprotocol;

import dtos.*;

public class Request {
    private RequestType type;
    private PlayerDTO player;
    private GameDTO game;
    private MoveDTO move;
    private WordGameDTO wordGame;
    private ChoiceDTO choice;
    public Request(){

    }
    public RequestType getType() {
        return type;
    }
    public void setType(RequestType type) {
        this.type = type;
    }
    public PlayerDTO getPlayer() {
        return player;
    }
    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }
    public GameDTO getGame() {
        return game;
    }
    public void setGame(GameDTO game) {
        this.game = game;
    }
    public MoveDTO getMove() {
        return move;
    }
    public void setMove(MoveDTO move) {
        this.move = move;
    }
    public WordGameDTO getWordGame() {
        return wordGame;
    }
    public void setWordGame(WordGameDTO wordGame) {
        this.wordGame = wordGame;
    }
    public ChoiceDTO getChoice() {
        return choice;
    }
    public void setChoice(ChoiceDTO choice) {
        this.choice = choice;
    }


    @Override
    public String toString() {
        return "Request{" +
                "type=" + type +
                '}';
    }
}
