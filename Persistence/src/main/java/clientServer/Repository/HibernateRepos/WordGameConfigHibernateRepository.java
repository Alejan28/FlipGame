package clientServer.Repository.HibernateRepos;

import Domain.WordGame;
import Domain.WordGameConfig;
import Utils.HibernateUtils;
import clientServer.Repository.Interfaces.WordGameConfigRepoInterface;

public class WordGameConfigHibernateRepository implements WordGameConfigRepoInterface {
    @Override
    public void add(WordGameConfig elem) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.merge(elem));
    }

    @Override
    public void update(Integer integer, WordGameConfig elem) {

    }

    @Override
    public Iterable<WordGameConfig> findAll() {
        return null;
    }


    @Override
    public WordGameConfig getWordGameConfig(WordGameConfig wordGameConfig) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.merge(wordGameConfig));
        return wordGameConfig;
    }
}
