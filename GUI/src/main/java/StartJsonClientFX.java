
import Services.IServices;
import gui.GameController;
import gui.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jsonprotocol.GameJsonProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class StartJsonClientFX extends Application {
    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    private static Logger logger = LogManager.getLogger(StartJsonClientFX.class);

    public void start(Stage primaryStage) throws Exception {
        logger.debug("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartJsonClientFX.class.getResourceAsStream("/client.properties"));
            logger.info("Client properties set {} ",clientProps);
            clientProps.list(System.out);
        } catch (IOException e) {
            logger.error("Cannot find chatclient.properties " + e);
            logger.debug("Looking for client.properties in folder {}",(new File(".")).getAbsolutePath());
            return;
        }
        String serverIP = clientProps.getProperty("game.server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("game.server.port"));
        } catch (NumberFormatException ex) {
            logger.error("Wrong port number " + ex.getMessage());
            logger.debug("Using default port: " + defaultChatPort);
        }
        logger.info("Using server IP " + serverIP);
        logger.info("Using server port " + serverPort);

        //IMcServices server = new McProtoProxy(serverIP, serverPort);
        IServices server= new GameJsonProxy(serverIP, serverPort);
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("login-view.fxml"));
        Parent root=loader.load();


        LogInController ctrl =
                loader.<LogInController>getController();
        ctrl.setServices(server);

        FXMLLoader cloader = new FXMLLoader(
                getClass().getClassLoader().getResource("game-view.fxml"));
        Parent croot=cloader.load();


        GameController gameCtrl =
                cloader.<GameController>getController();
        ctrl.setGameController(gameCtrl);
        ctrl.setParent(croot);
        primaryStage.setTitle("Joc Animale");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


}
