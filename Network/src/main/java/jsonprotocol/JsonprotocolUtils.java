package jsonprotocol;

import Domain.Game;
import Domain.Move;
import Domain.Player;
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
}
