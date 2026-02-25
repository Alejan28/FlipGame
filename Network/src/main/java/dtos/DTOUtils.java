package dtos;

import Domain.*;

import java.util.ArrayList;
import java.util.List;

public class DTOUtils {
    public static PlayerDTO getDTO(Player player){
        PlayerDTO playerDTO = new PlayerDTO();
        int id=0;
        try{
            id=player.getId();
        }catch(Exception e){
            id=0;
        }
        String nickname=player.getNickname();
        String nume=player.getNume();
        String prenume=player.getPrenume();
        playerDTO.setId(id);
        playerDTO.setNickname(nickname);
        playerDTO.setNume(nume);
        playerDTO.setPrenume(prenume);
        return playerDTO;
    }
    public static Player getPlayerDTO(PlayerDTO playerDTO){
        Player player=new Player();
        player.setId(playerDTO.getId());
        player.setNickname(playerDTO.getNickname());
        player.setNume(playerDTO.getNume());
        player.setPrenume(playerDTO.getPrenume());
        return player;
    }
    public static ConfigurationDTO getDTO(Configuration config){
        ConfigurationDTO configDTO=new ConfigurationDTO();
        int id=0;
        try{
            id=config.getId();
        }catch(Exception e){
            id=0;
        }
        Integer row=config.getRow();
        Integer column=config.getColumn();
        String animal=config.getAnimal();
        String image=config.getImage();
        configDTO.setId(id);
        configDTO.setRow(row);
        configDTO.setColumn(column);
        configDTO.setAnimal(animal);
        configDTO.setImage(image);
        return configDTO;
    }
    public static Configuration getConfigurationDTO(ConfigurationDTO configDTO){
        Configuration configuration=new Configuration();
        configuration.setId(configDTO.getId());
        configuration.setRow(configDTO.getRow());
        configuration.setColumn(configDTO.getColumn());
        configuration.setAnimal(configDTO.getAnimal());
        configuration.setImage(configDTO.getImage());
        return configuration;
    }
    public static GameDTO getDTO(Game game){
        int id=0;
        try{
            id=game.getId();
        }catch(Exception e){
            id=0;
        }
        PlayerDTO playerDTO=getDTO(game.getPlayer());
        ConfigurationDTO configDTO=null;
        try{
            configDTO=getDTO(game.getConfig());
        }catch(Exception e){
            configDTO=null;
        }
        Game.gameStatus status=game.getStatus();
        GameDTO gameDTO=new GameDTO();
        gameDTO.setId(id);
        gameDTO.setPlayer(playerDTO);
        gameDTO.setConfiguration(configDTO);
        gameDTO.setStatus(status);
        gameDTO.setDuration(game.getDuration());
        gameDTO.setTrials(game.getTrials());
        gameDTO.setStartTime(game.getStartTime());
        return gameDTO;
    }
    public static Game getFromDTO(GameDTO gameDto){
        Game game=new Game();
        game.setId(gameDto.getId());
        Player player=getPlayerDTO(gameDto.getPlayer());
        Configuration configuration=getConfigurationDTO(gameDto.getConfiguration());
        game.setPlayer(player);
        game.setConfig(configuration);
        game.setTrials(gameDto.getTrials());
        game.setStatus(gameDto.getStatus());
        game.setDuration(gameDto.getDuration());
        game.setStartTime(gameDto.getStartTime());
        return game;
    }
    public static MoveDTO getDTO(Move move){
        MoveDTO moveDTO=new MoveDTO();
        int id=0;
        try{
            id=move.getId();
        }catch(Exception e){
            id=0;
        }
        GameDTO gameDTO=getDTO(move.getGame());
        moveDTO.setId(id);
        moveDTO.setGame(gameDTO);
        moveDTO.setX(move.getX());
        moveDTO.setY(move.getY());
        return moveDTO;
    }
    public static GameDTO[] getDTO(List<Game> games){
        GameDTO[] dtos=new GameDTO[games.size()];
        for(int i=0;i<games.size();i++){
            dtos[i]=getDTO(games.get(i));
        }
        return dtos;
    }
    public static List<Game> getGamesFromDTO(GameDTO[] dtos){
        List<Game> games=new ArrayList<>();
        for(int i=0;i<dtos.length;i++){
            Game game=getFromDTO(dtos[i]);
            games.add(game);
        }
        return games;
    }
    public static WordGameDTO[] getWordGameDTO(List<WordGame> games){
        WordGameDTO[] dtos=new WordGameDTO[games.size()];
        for(int i=0;i<games.size();i++){
            dtos[i]=getDTO(games.get(i));
        }
        return dtos;
    }
    public static List<WordGame> getFromDTO(WordGameDTO[] dtos){
        List<WordGame> games=new ArrayList<>();
        for(int i=0;i<dtos.length;i++){
            WordGame game=getFromDTO(dtos[i]);
            games.add(game);
        }
        return games;
    }
    public static Move getFromDTO(MoveDTO moveDTO){
        Move move=new Move();
        Integer id=0;
        try{
            id=move.getId();
        }catch(Exception e){
            id=0;
        }
        move.setId(id);
        move.setGame(getFromDTO(moveDTO.getGame()));
        move.setX(moveDTO.getX());
        move.setY(moveDTO.getY());
        return move;
    }
    public static WordDTO getDTO(Word word){
        WordDTO wordDTO=new WordDTO();
        Integer id=0;
        try{
            id=word.getId();
        }catch(Exception e){
            id=0;
        }
        wordDTO.setId(id);
        wordDTO.setWord(word.getWord());
        return wordDTO;
    }
    public static Word getFromDTO(WordDTO wordDTO){
        Word word=new Word();
        word.setId(wordDTO.getId());
        word.setWord(wordDTO.getWord());
        return word;
    }
    public static WordGameConfigurationDTO getDTO(WordGameConfig config){
        WordGameConfigurationDTO wordGameConfigurationDTO=new WordGameConfigurationDTO();
        Integer id=0;
        try{
            id=config.getId();
        }catch(Exception e){
            id=0;
        }
        wordGameConfigurationDTO.setId(id);
        List<WordDTO> words=new ArrayList<>();
        for(Word w:config.getWords()){
            WordDTO wordDTO=getDTO(w);
            words.add(wordDTO);
        }
        wordGameConfigurationDTO.setWords(words);
        return wordGameConfigurationDTO;
    }
    public static WordGameConfig getFromDTO(WordGameConfigurationDTO wordGameConfigurationDTO){
        WordGameConfig wordGameConfig=new WordGameConfig();
        wordGameConfig.setId(wordGameConfigurationDTO.getId());
        List<Word> words=new ArrayList<>();
        for(WordDTO w:wordGameConfigurationDTO.getWords()){
            Word word=getFromDTO(w);
            words.add(word);
        }
        wordGameConfig.setWords(words);
        return wordGameConfig;
    }
    public static WordGameDTO getDTO(WordGame game){
        WordGameDTO wordGameDTO=new WordGameDTO();
        Integer id=0;
        try{
            id=game.getId();
        }catch(Exception e){
            id=0;
        }
        wordGameDTO.setId(id);
        wordGameDTO.setDuration(game.getDuration());
        wordGameDTO.setPlayer(getDTO(game.getPlayer()));
        wordGameDTO.setConfiguration(getDTO(game.getConfig()));
        wordGameDTO.setStatus(game.getStatus());
        wordGameDTO.setStartTime(game.getStartTime());
        wordGameDTO.setTurns(game.getTurns());
        return wordGameDTO;
    }
    public static WordGame getFromDTO(WordGameDTO wordGameDTO){
        WordGame wordGame=new WordGame();
        wordGame.setId(wordGameDTO.getId());
        wordGame.setDuration(wordGameDTO.getDuration());
        wordGame.setPlayer(getPlayerDTO(wordGameDTO.getPlayer()));
        wordGame.setConfig(getFromDTO(wordGameDTO.getConfiguration()));
        wordGame.setStatus(wordGameDTO.getStatus());
        wordGame.setStartTime(wordGameDTO.getStartTime());
        wordGame.setTurns(wordGameDTO.getTurns());
        return wordGame;
    }
    public static ChoiceDTO getDTO(Choice choice){
        ChoiceDTO choiceDTO=new ChoiceDTO();
        Integer id=0;
        try{
            id=choice.getId();
        }catch(Exception e){
            id=0;
        }
        choiceDTO.setId(id);
        choiceDTO.setWordGame(getDTO(choice.getWordGame()));
        choiceDTO.setFirst_word(choice.getFirst_word());
        choiceDTO.setSecond_word(choice.getSecond_word());
        return choiceDTO;
    }
    public static Choice getFromDTO(ChoiceDTO choiceDTO){
        Choice choice=new Choice();
        choice.setId(choiceDTO.getId());
        choice.setWordGame(getFromDTO(choiceDTO.getWordGame()));
        choice.setFirst_word(choiceDTO.getFirst_word());
        choice.setSecond_word(choiceDTO.getSecond_word());
        return choice;
    }
}
