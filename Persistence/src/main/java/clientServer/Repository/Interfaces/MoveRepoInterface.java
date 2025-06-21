package clientServer.Repository.Interfaces;

import Domain.Move;

import java.util.List;

public interface MoveRepoInterface extends IRepository<Integer, Move>{
    List<Move> getMovesForLostGames(Integer id);
}
