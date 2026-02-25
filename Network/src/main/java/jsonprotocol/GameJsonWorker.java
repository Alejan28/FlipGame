package jsonprotocol;

import Domain.*;
import Services.GameException;
import Services.IObserver;
import Services.IServices;
import com.google.gson.Gson;
import dtos.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class GameJsonWorker implements Runnable, IObserver {
    private IServices server;
    private Socket connection;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private volatile boolean connected;

    private static Logger logger = LogManager.getLogger(GameJsonWorker.class);

    public GameJsonWorker(IServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        gsonFormatter=new Gson();
        try{
            output=new PrintWriter(connection.getOutputStream());
            input=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            connected=true;
        } catch (IOException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }

    public void run() {
        while(connected){
            try {
                String requestLine=input.readLine();
                logger.info(requestLine);
                Request request=gsonFormatter.fromJson(requestLine, Request.class);
                Response response=handleRequest(request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                logger.error(e);
                logger.error(e.getStackTrace());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e);
                logger.error(e.getStackTrace());
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            logger.error("Error "+e);
        }
    }

    private static Response okResponse=JsonprotocolUtils.createOkResponse();
    private Response handleRequest(Request request){
       Response response=null;
        if (request.getType()== RequestType.LOGIN){
            logger.debug("Login request ...{}"+request.getPlayer());
            PlayerDTO pdto=request.getPlayer();
            Player player= DTOUtils.getPlayerDTO(pdto);
            try {
                server.login(player,this );
                return okResponse;
            } catch (GameException e) {
                connected=false;
                return JsonprotocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType()== RequestType.START_GAME){
            logger.debug("Start game request by  ...{}"+request.getPlayer());
            PlayerDTO pdto=request.getPlayer();
            Player player= DTOUtils.getPlayerDTO(pdto);
            try {
                Game game=server.startGame(player);
                return JsonprotocolUtils.createNewGameResponse(game);
            } catch (GameException e) {
                connected=false;
                return JsonprotocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType()== RequestType.START_WORD_GAME){
            logger.debug("Start game request by  ...{}"+request.getPlayer());
            PlayerDTO pdto=request.getPlayer();
            Player player= DTOUtils.getPlayerDTO(pdto);
            try {
                WordGame game=server.startWordGame(player);
                return JsonprotocolUtils.createWordGameStartedResponse(game);
            } catch (GameException e) {
                connected=false;
                return JsonprotocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType()== RequestType.MAKE_MOVE){
            logger.debug("make a move  ...{}"+request.getMove());
            MoveDTO mdto=request.getMove();
            Move move=DTOUtils.getFromDTO(mdto);
            try {
                server.makeMove(move);
                return okResponse;
            } catch (GameException e) {
                connected=false;
                return JsonprotocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType()== RequestType.SEND_CHOICE){
            logger.debug("make a choice  ...{}"+request.getChoice());
            ChoiceDTO choiceDTO=request.getChoice();
            Choice choice= DTOUtils.getFromDTO(choiceDTO);
            try {
                server.addChoice(choice);
                return okResponse;
            } catch (GameException e) {
                connected=false;
                return JsonprotocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType()== RequestType.UPDATE_GAME){
            logger.debug("update a agme  ...{}"+request.getGame());
            GameDTO gdto=request.getGame();
            Game game=DTOUtils.getFromDTO(gdto);
            try {
                server.updateGame(game);
                return okResponse;
            } catch (GameException e) {
                connected=false;
                return JsonprotocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType()== RequestType.UPDATE_WORD_GAME){
            logger.debug("update a agme  ...{}"+request.getWordGame());
            WordGameDTO wdto=request.getWordGame();
            WordGame game=DTOUtils.getFromDTO(wdto);
            try {
                server.updateWordGame(game);
                return okResponse;
            } catch (GameException e) {
                connected=false;
                return JsonprotocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType()== RequestType.GET_RANKING){
            logger.debug("get ranking");
            try {
                List<Game> games=server.getRanking();
                return JsonprotocolUtils.createRankingResponse(games);
            } catch (GameException e) {
                connected=false;
                return JsonprotocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType()== RequestType.GET_RANKING_WG){
            logger.debug("get ranking");
            try {
                List<WordGame> games=server.getRankingWordGame();
                return JsonprotocolUtils.createWordGameRanking(games);
            } catch (GameException e) {
                connected=false;
                return JsonprotocolUtils.createErrorResponse(e.getMessage());
            }
        }
       return response;
    }

    private void sendResponse(Response response) throws IOException{
        String responseLine=gsonFormatter.toJson(response);
        logger.debug("sending response "+responseLine);
        synchronized (output) {
            output.println(responseLine);
            output.flush();
        }
    }

    @Override
    public void gameWon(Game game) throws GameException {
        Response resp= JsonprotocolUtils.createGameWon(game);
        logger.debug("Game won "+game.getPlayer().getNickname());
        try {
            sendResponse(resp);
        } catch (IOException e) {
            throw new GameException("Adding error: "+e);
        }
    }

    @Override
    public void wordGame(WordGame wordGame) throws GameException {
        Response resp= JsonprotocolUtils.createWordGameWon(wordGame);
        logger.debug("Game won "+wordGame.getPlayer().getNickname());
        try {
            sendResponse(resp);
        } catch (IOException e) {
            throw new GameException("Adding error: "+e);
        }
    }
}
