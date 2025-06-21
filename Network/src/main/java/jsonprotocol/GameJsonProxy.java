package jsonprotocol;

import Domain.Game;
import Domain.Move;
import Domain.Player;
import Services.GameException;
import Services.IObserver;
import Services.IServices;
import com.google.gson.Gson;
import dtos.DTOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameJsonProxy implements IServices {
    private String host;
    private int port;

    private IObserver client;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    private static Logger logger = LogManager.getLogger(GameJsonProxy.class);

    public GameJsonProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
        logger.debug("INIT");
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }

    }

    private void sendRequest(Request request)throws GameException {
        String reqLine=gsonFormatter.toJson(request);
        try {
            output.println(reqLine);
            output.flush();
        } catch (Exception e) {
            throw new GameException("Error sending object "+e);
        }

    }

    private Response readResponse() throws GameException {
       Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
        return response;
    }
    private void initializeConnection() throws GameException {
        try {
            logger.debug("trying to initialize connection");
            gsonFormatter=new Gson();
            connection=new Socket(host,port);
            output=new PrintWriter(connection.getOutputStream());
            output.flush();
            input=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            finished=false;
            startReader();
        } catch (IOException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(Response response){
        if (response.getType()== ResponseType.GAME_WON){
           Game game=DTOUtils.getFromDTO(response.getGame());
            logger.debug("Game Won");
            try{
                client.gameWon(game);
            }catch(GameException e){
                logger.error(e);
                logger.error(e.getStackTrace());
            }
        }
    }

    private boolean isUpdate(Response response){
       return response.getType()==ResponseType.GAME_WON;
    }

    @Override
    public void login(Player player, IObserver observer) throws GameException {
        initializeConnection();
        Request req= JsonprotocolUtils.createLoginRequest(player);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
            this.client=observer;
            return;
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new GameException(err);
        }
    }

    @Override
    public Game startGame(Player player) throws GameException {
        Request req= JsonprotocolUtils.createNewGameRequest(player);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new GameException(err);
        }
        return DTOUtils.getFromDTO(response.getGame());
    }

    @Override
    public void makeMove(Move move) throws GameException {
        Request req= JsonprotocolUtils.createMakeAMoveRequest(move);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
            return;
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new GameException(err);
        }
    }

    @Override
    public void updateGame(Game game) throws GameException {
        Request req= JsonprotocolUtils.createUpdateGameRequest(game);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
            return;
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new GameException(err);
        }
    }

    @Override
    public List<Game> getRanking() throws GameException {
        Request req= JsonprotocolUtils.getRanking();
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new GameException(err);
        }
        return DTOUtils.getGamesFromDTO(response.getGames());
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    String responseLine=input.readLine();
                    if (responseLine == null) {
                        logger.warn("No data received or server closed connection.");
                        break;  // Exit the loop when the server closes the connection or no data
                    }
                    logger.debug("response received {}",responseLine);
                    Response response=gsonFormatter.fromJson(responseLine, Response.class);
                    logger.debug(response.toString());
                    if (isUpdate(response)){
                        logger.debug("update response");
                        logger.debug("entering handle update");
                        handleUpdate(response);
                        logger.debug("exiting handle update");
                    }else{

                        try {
                            logger.debug("putting response");
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            logger.error(e);
                            logger.error(e.getStackTrace());
                        }
                    }
                } catch (IOException e) {
                    logger.error("Reading error "+e);
                }
            }
        }
    }
}
