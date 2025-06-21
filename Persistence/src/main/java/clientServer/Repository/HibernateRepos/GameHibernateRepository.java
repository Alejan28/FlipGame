package clientServer.Repository.HibernateRepos;

import Domain.Game;
import Utils.LostGameDTO;
import clientServer.Repository.Interfaces.GameRepoInterface;
import Utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class GameHibernateRepository implements GameRepoInterface {
    @Override
    public void add(Game elem) {

    }
    public Game createNewGame(Game elem) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(elem));
        return elem;
    }

    @Override
    public List<LostGameDTO> getLostGamesForPlayer(Integer id) {
        final SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            List<Game> lostGames = session.createQuery(
                            "from Game g where g.player.id = :id and g.status = :status", Game.class)
                    .setParameter("id", id)
                    .setParameter("status", Game.gameStatus.LOST)
                    .getResultList();

            List<LostGameDTO> result = new ArrayList<>();
            for (Game g : lostGames) {
                List<Object[]> results = session.createQuery(
                                "select m.x, m.y from Move m where m.game = :game", Object[].class)
                        .setParameter("game", g)
                        .getResultList();

                List<String> guessedPositions = new ArrayList<>();
                for (Object[] row : results) {
                    Integer x = (Integer) row[0];
                    Integer y = (Integer) row[1];
                    guessedPositions.add("(" + x + "," + y + ")");
                }

                LostGameDTO dto = new LostGameDTO();
                dto.setGameId(g.getId());
                dto.setNumberOfTries(guessedPositions.size());
                dto.setGuessedPositions(guessedPositions);
                dto.setActualPosition(g.getConfig().getColumn().toString()+" "+ g.getConfig().getRow().toString());

                result.add(dto);
            }

            return result;
        }
    }

    @Override
    public void update(Integer integer, Game elem) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Game existingGame = session.get(Game.class, integer);
            if (existingGame != null) {
               existingGame.setStatus(elem.getStatus());
               existingGame.setPlayer(elem.getPlayer());
               existingGame.setConfig(elem.getConfig());
               existingGame.setStartTime(elem.getStartTime());
               existingGame.setDuration(elem.getDuration());
               existingGame.setTrials(elem.getTrials());

                session.merge(existingGame);
            } else {
                throw new IllegalArgumentException("Game with ID " + integer + " not found.");
            }
        });
    }

    @Override
    public Iterable<Game> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Game where status = :status order by duration", Game.class
                    )
                    .setParameter("status", Game.gameStatus.WON)
                    .getResultList();
        }
    }
}
