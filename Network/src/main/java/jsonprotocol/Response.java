package jsonprotocol;


import dtos.GameDTO;
import dtos.WordGameDTO;

import java.io.Serializable;

public class Response implements Serializable {
    private ResponseType type;
    private String errorMessage;
    private GameDTO game;
    private GameDTO[] games;
    private WordGameDTO[] wordGames;
    private WordGameDTO wordGame;
    public Response(){

    }
    public void setType(ResponseType type){
        this.type = type;
    }
    public ResponseType getType(){
        return type;
    }
    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage(){
        return errorMessage;
    }
    public void setGame(GameDTO game){
        this.game = game;
    }
    public GameDTO getGame(){
        return game;
    }
    public void setGames(GameDTO[] games){
        this.games = games;
    }
    public GameDTO[] getGames(){
        return games;
    }
    public void setWordGame(WordGameDTO wordGame){
        this.wordGame = wordGame;
    }
    public WordGameDTO getWordGame(){
        return wordGame;
    }
    public void setWordGames(WordGameDTO[] wordGames){
        this.wordGames = wordGames;
    }
    public WordGameDTO[] getWordGames(){
        return wordGames;
    }


    @Override
    public String toString() {
        return "Response{" +
                "type=" + type +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
