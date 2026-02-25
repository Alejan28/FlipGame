package Services;

import Domain.*;

import java.util.List;

public interface IServices {
    void login(Player player, IObserver observer) throws GameException;
    Game startGame(Player player) throws GameException;
    void makeMove(Move move) throws GameException;
    void updateGame(Game game) throws GameException;
    List<Game> getRanking() throws GameException;
    WordGame startWordGame(Player player) throws GameException;
    void addChoice(Choice choice) throws GameException;
    void updateWordGame(WordGame wordGame) throws GameException;
    List<WordGame> getRankingWordGame() throws GameException;
}
