package clientServer.Repository.Interfaces;

import Domain.Game;
import Utils.LostGameDTO;

import java.util.List;

public interface GameRepoInterface extends IRepository<Integer, Game>{
    Game createNewGame(Game newGame);
    List<LostGameDTO> getLostGamesForPlayer(Integer id);
}
