package Services;

import Domain.Game;
import Domain.Move;
import Domain.Player;

import java.util.List;

public interface IServices {
    void login(Player player, IObserver observer) throws GameException;
    Game startGame(Player player) throws GameException;
    void makeMove(Move move) throws GameException;
    void updateGame(Game game) throws GameException;
    List<Game> getRanking() throws GameException;
}
