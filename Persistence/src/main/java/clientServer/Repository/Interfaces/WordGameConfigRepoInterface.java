package clientServer.Repository.Interfaces;

import Domain.WordGame;
import Domain.WordGameConfig;

public interface WordGameConfigRepoInterface extends IRepository<Integer, WordGameConfig> {
    WordGameConfig getWordGameConfig(WordGameConfig wordGameConfig);
}
