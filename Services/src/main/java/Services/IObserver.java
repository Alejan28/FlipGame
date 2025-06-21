package Services;

import Domain.Game;

public interface IObserver {
    void gameWon(Game game) throws GameException;
}
