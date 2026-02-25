package clientServer.Repository.HibernateRepos;

import Domain.Game;
import Domain.WordGame;
import Utils.HibernateUtils;
import clientServer.Repository.Interfaces.WordGameRepoInterface;
import org.hibernate.Session;

public class WordGameHibernateRepository implements WordGameRepoInterface {
    @Override
    public void add(WordGame elem) {

    }

    @Override
    public void update(Integer integer, WordGame elem) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            WordGame existingGame = session.get(WordGame.class, integer);
            if (existingGame != null) {
                existingGame.setStatus(elem.getStatus());
                existingGame.setPlayer(elem.getPlayer());
                existingGame.setConfig(elem.getConfig());
                existingGame.setStartTime(elem.getStartTime());
                existingGame.setDuration(elem.getDuration());
                existingGame.setTurns(elem.getTurns());

                session.merge(existingGame);
            } else {
                throw new IllegalArgumentException("Game with ID " + integer + " not found.");
            }
        });
    }

    @Override
    public Iterable<WordGame> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from WordGame where status = :status order by duration,turns", WordGame.class
                    )
                    .setParameter("status", Game.gameStatus.WON)
                    .getResultList();
        }
    }

    @Override
    public WordGame createNewGame(WordGame game) {
        return HibernateUtils.getSessionFactory().fromTransaction(session -> session.merge(game));

    }
}
