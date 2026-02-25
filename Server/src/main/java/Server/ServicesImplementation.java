package Server;

import Domain.*;
import clientServer.Repository.Interfaces.*;
import Services.GameException;
import Services.IObserver;
import Services.IServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.StreamSupport;

public class ServicesImplementation implements IServices {
    private PlayerRepoInterface playerRepo;
    private GameRepoInterface gameRepo;
    private ConfigurationRepoInterface configurationRepo;
    private MoveRepoInterface moveRepo;
    private WordRepoInterface wordRepo;
    private WordGameConfigRepoInterface wordGameConfigRepo;
    private WordGameRepoInterface wordGameRepo;
    private ChoiceRepoInterface choiceRepo;
    private Map<String, IObserver> loggedPlayers;
    private static Logger logger = LogManager.getLogger(ServicesImplementation.class);
    public ServicesImplementation(PlayerRepoInterface playerRepo,GameRepoInterface gameRepo,ConfigurationRepoInterface configurationRepo,MoveRepoInterface moveRepo,WordRepoInterface wordRepo,WordGameConfigRepoInterface wordGameConfigRepo,WordGameRepoInterface wordGameRepo,ChoiceRepoInterface choiceRepo) {
        this.playerRepo = playerRepo;
        this.gameRepo = gameRepo;
        this.configurationRepo = configurationRepo;
        this.moveRepo = moveRepo;
        this.wordRepo = wordRepo;
        this.wordGameConfigRepo = wordGameConfigRepo;
        this.wordGameRepo = wordGameRepo;
        this.choiceRepo = choiceRepo;
        loggedPlayers=new ConcurrentHashMap<>();
    }
    public void setWordRepo(WordRepoInterface wordRepo) {
        this.wordRepo = wordRepo;
    }
    public void setWordGameConfigRepo(WordGameConfigRepoInterface wordGameConfigRepo) {
        this.wordGameConfigRepo = wordGameConfigRepo;
    }
    public void setWordGameRepo(WordGameRepoInterface wordGameRepo) {
        this.wordGameRepo = wordGameRepo;
    }
    @Override
    public synchronized void login(Player player, IObserver observer) throws GameException {
        Player playerR=playerRepo.findByNickname(player.getNickname());
        if (playerR!=null){
            if(loggedPlayers.get(playerR.getNickname())!=null)
                throw new GameException("Player already logged in.");
            player.setNume(playerR.getNume());
            player.setPrenume(playerR.getPrenume());
            loggedPlayers.put(playerR.getNickname(), observer);
        }else
            throw new GameException("Authentication failed.");
    }
    private Configuration getConfiguration(){
        return configurationRepo.getRandomConfiguration();
    }

    @Override
    public  synchronized Game startGame(Player player) throws GameException {
        Game game= new Game();
        Player playerR=playerRepo.findByNickname(player.getNickname());
        logger.debug(playerR.getId());
        game.setPlayer(playerR);
        game.setConfig(getConfiguration());
        game.setStatus(Game.gameStatus.STARTED);
        game.setStartTime(LocalDateTime.now());
        game.setDuration(Duration.ZERO);
        game.setTrials(0);
        game= gameRepo.createNewGame(game);
        return game;
    }

    @Override
    public synchronized void makeMove(Move move) throws GameException {
        move.setId(null);
        moveRepo.add(move);
    }

    @Override
    public synchronized void updateGame(Game game) throws GameException {
        if(game.getStatus()==Game.gameStatus.WON){
            for(IObserver observer:loggedPlayers.values()){
                observer.gameWon(game);
            }
        }
        gameRepo.update(game.getId(),game);
    }

    @Override
    public synchronized List<Game> getRanking() throws GameException {
        Iterable<Game> games= gameRepo.findAll();
        List<Game> ranking=new ArrayList<>();
        ranking= StreamSupport.stream(games.spliterator(),false).toList();
        return ranking;
    }
    private synchronized List<Word> getWords(){
        return wordRepo.generateRandomWord(6);
    }
    private synchronized WordGameConfig getWordGameConfig(){
        WordGameConfig wgc= new WordGameConfig();
        wgc.setWords(getWords());
        return wgc;
    }

    @Override
    public synchronized WordGame startWordGame(Player player) throws GameException {
        WordGameConfig wgc= getWordGameConfig();
        WordGame wg= new WordGame();
        Player playerR=playerRepo.findByNickname(player.getNickname());
        wg.setPlayer(playerR);
        wg.setConfig(wgc);
        wg.setStatus(Game.gameStatus.STARTED);
        wg.setStartTime(LocalDateTime.now());
        wg.setDuration(Duration.ZERO);
        wg.setTurns(0);
        WordGame finalwg=wordGameRepo.createNewGame(wg);
        return finalwg;
    }

    @Override
    public synchronized void addChoice(Choice choice) throws GameException {
        choiceRepo.add(choice);
    }

    @Override
    public synchronized void updateWordGame(WordGame wordGame) throws GameException {
        for(IObserver observer:loggedPlayers.values()){
                observer.wordGame(wordGame);
        }
        wordGameRepo.update(wordGame.getId(),wordGame);
    }

    @Override
    public synchronized List<WordGame> getRankingWordGame() throws GameException {
        Iterable<WordGame> games= wordGameRepo.findAll();
        List<WordGame> ranking=new ArrayList<>();
        ranking= StreamSupport.stream(games.spliterator(),false).toList();
        return ranking;
    }
}
