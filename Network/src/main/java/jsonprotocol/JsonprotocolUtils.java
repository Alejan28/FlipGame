package jsonprotocol;

import Domain.*;
import dtos.DTOUtils;

import java.util.List;

public class JsonprotocolUtils {
    public static Response createOkResponse(){
        Response resp=new Response();
        resp.setType(ResponseType.OK);
        return resp;
    }
    public static Response createErrorResponse(String errorMessage){
        Response resp=new Response();
        resp.setType(ResponseType.ERROR);
        resp.setErrorMessage(errorMessage);
        return resp;
    }
    public static Request createLoginRequest(Player player){
        Request req=new Request();
        req.setType(RequestType.LOGIN);
        req.setPlayer(DTOUtils.getDTO(player));
        return req;
    }
    public static Request createNewGameRequest( Player player){
        Request req=new Request();
        req.setType(RequestType.START_GAME);
        req.setPlayer(DTOUtils.getDTO(player));
        return req;
    }
    public static Response createNewGameResponse(Game game){
        Response resp=new Response();
        resp.setType(ResponseType.GAME_STARTED);
        resp.setGame(DTOUtils.getDTO(game));
        return resp;
    }
    public static Request createMakeAMoveRequest(Move move){
        Request req=new Request();
        req.setType(RequestType.MAKE_MOVE);
        req.setMove(DTOUtils.getDTO(move));
        return req;
    }
    public static Request createUpdateGameRequest(Game game){
        Request req=new Request();
        req.setType(RequestType.UPDATE_GAME);
        req.setGame(DTOUtils.getDTO(game));
        return req;
    }
    public static Request getRanking(){
        Request req=new Request();
        req.setType(RequestType.GET_RANKING);
        return req;
    }
    public static Response createRankingResponse(List<Game> games){
        Response resp=new Response();
        resp.setType(ResponseType.RETURN_RANKING);
        resp.setGames(DTOUtils.getDTO(games));
        return resp;
    }
    public static Response createGameWon(Game game){
        Response resp=new Response();
        resp.setType(ResponseType.GAME_WON);
        resp.setGame(DTOUtils.getDTO(game));
        return resp;
    }
    public static Response createWordGameWon(WordGame wordGame){
        Response resp=new Response();
        resp.setType(ResponseType.WORD_GAME_FINISHED);
        resp.setWordGame(DTOUtils.getDTO(wordGame));
        return resp;
    }
    public static Request createStartWordGameRequest(Player game){
        Request req=new Request();
        req.setType(RequestType.START_WORD_GAME);
        req.setPlayer(DTOUtils.getDTO(game));
        return req;
    }
    public static Response createWordGameStartedResponse(WordGame game){
        Response resp=new Response();
        resp.setType(ResponseType.WORD_GAME_STARTED);
        resp.setWordGame(DTOUtils.getDTO(game));
        return resp;
    }
    public static Request createMakeChoiceDTO(Choice choice){
        Request req=new Request();
        req.setType(RequestType.SEND_CHOICE);
        req.setChoice(DTOUtils.getDTO(choice));
        return req;
    }
    public static Request createUpdateWordGameRequest(WordGame game){
        Request req=new Request();
        req.setType(RequestType.UPDATE_WORD_GAME);
        req.setWordGame(DTOUtils.getDTO(game));
        return req;
    }
    public static Request getWordGameRanking(){
        Request req=new Request();
        req.setType(RequestType.GET_RANKING_WG);
        return req;
    }
    public static Response createWordGameRanking(List<WordGame> games){
        Response resp=new Response();
        resp.setType(ResponseType.RETURN_RANKING);
        resp.setWordGames(DTOUtils.getWordGameDTO(games));
        return resp;
    }

}
