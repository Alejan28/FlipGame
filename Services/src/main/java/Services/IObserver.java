package Services;

import Domain.Game;
import Domain.WordGame;

public interface IObserver {
    void gameWon(Game game) throws GameException;
    void wordGame(WordGame wordGame) throws GameException;
}
