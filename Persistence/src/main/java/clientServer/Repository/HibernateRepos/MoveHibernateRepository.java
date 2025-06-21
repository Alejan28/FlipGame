package clientServer.Repository.HibernateRepos;

import Domain.Game;
import Domain.Move;
import Utils.JdbcUtils;
import clientServer.Repository.Interfaces.MoveRepoInterface;
import Utils.HibernateUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Component;


import java.sql.Connection;
import java.util.List;
import java.util.Properties;

@Component
public class MoveHibernateRepository implements MoveRepoInterface {
    @Override
    public void add(Move elem) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(elem));
    }

    @Override
    public void update(Integer integer, Move elem) {

    }

    @Override
    public Iterable<Move> findAll() {
        return null;
    }

    @Override
    public List<Move> getMovesForLostGames(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Move where game.status = :status and game.player= :idP", Move.class
                    )
                    .setParameter("status", Game.gameStatus.LOST)
                    .setParameter("idP", id)
                    .getResultList();
        }
    }
}
