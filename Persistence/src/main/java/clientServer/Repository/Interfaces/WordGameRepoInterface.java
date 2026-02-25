package clientServer.Repository.Interfaces;

import Domain.WordGame;

public interface WordGameRepoInterface extends IRepository<Integer, WordGame> {
    WordGame createNewGame(WordGame game);
}
