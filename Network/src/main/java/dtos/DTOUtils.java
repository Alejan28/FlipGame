package dtos;

import Domain.Configuration;
import Domain.Game;
import Domain.Move;
import Domain.Player;

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
}
