package utils;


import Services.IServices;
import jsonprotocol.GameJsonWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Socket;

public class JsonConcurrentServer extends AbsConcurrentServer{
    private IServices chatServer;
    private static Logger logger = LogManager.getLogger(JsonConcurrentServer.class);
    public JsonConcurrentServer(int port, IServices chatServer) {
        super(port);
        this.chatServer = chatServer;
        logger.info("Chat-McJsonConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        GameJsonWorker worker=new GameJsonWorker(chatServer, client);

        Thread tw=new Thread(worker);
        return tw;
    }
}
