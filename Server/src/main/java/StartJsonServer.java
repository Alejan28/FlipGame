import Domain.Configuration;
import clientServer.Repository.HibernateRepos.*;
import clientServer.Repository.Interfaces.*;
import Server.ServicesImplementation;
import Services.IServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.AbstractServer;
import utils.JsonConcurrentServer;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class StartJsonServer {
    private static int defaultPort=55555;
    private static Logger logger = LogManager.getLogger(StartJsonServer.class);
    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartJsonServer.class.getResourceAsStream("/server.properties"));
            logger.info("Server properties set. {} ", serverProps);
            //serverProps.list(System.out);
        } catch (IOException e) {
            logger.error("Cannot find server.properties "+e);
            logger.debug("Looking for file in "+(new File(".")).getAbsolutePath());
            return;
        }
        PlayerRepoInterface playerRepo=new PlayerHibernateRepository();
        ConfigurationRepoInterface configurationRepo=new ConfigurationHibernateRepository();
        GameRepoInterface gameRepo=new GameHibernateRepository();
        MoveRepoInterface moveRepo=new MoveHibernateRepository();
        WordGameRepoInterface wordGameRepo=new WordGameHibernateRepository();
        WordGameConfigRepoInterface wordGameConfigRepo=new WordGameConfigHibernateRepository();
        WordRepoInterface wordRepo=new WordHibernateRepository();
        ChoiceRepoInterface choiceRepo=new ChoiceHibernateRepository();
        Configuration newConfiguration=new Configuration();
        newConfiguration.setAnimal("Elefant");
        newConfiguration.setColumn(2);
        newConfiguration.setRow(1);
        newConfiguration.setImage("imagine");
        //configurationRepo.add(newConfiguration);
        IServices gameServer=new ServicesImplementation(playerRepo,gameRepo,configurationRepo,moveRepo,wordRepo,wordGameConfigRepo,wordGameRepo,choiceRepo);

        Properties props= serverProps;
        int McServerPort=defaultPort;
        try {
            McServerPort = Integer.parseInt(serverProps.getProperty("game.server.port"));
        }catch (NumberFormatException nef){
            logger.error("Wrong  Port Number"+nef.getMessage());
            logger.debug("Using default port "+defaultPort);
        }
        logger.debug("Starting server on port: "+McServerPort);
        AbstractServer server = null;
        try {
            server = new JsonConcurrentServer(McServerPort, gameServer);
            if (server != null) {
                server.start();
            } else {
                logger.error("Server failed to initialize.");
            }
        } catch (Exception e) {
            logger.error("Error starting server: " + e.getMessage());
        }
    }
}
