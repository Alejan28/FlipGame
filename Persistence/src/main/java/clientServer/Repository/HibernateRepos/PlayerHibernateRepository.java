package clientServer.Repository.HibernateRepos;

import Domain.Player;
import clientServer.Repository.Interfaces.PlayerRepoInterface;
import Utils.HibernateUtils;
import org.hibernate.Session;

public class PlayerHibernateRepository implements PlayerRepoInterface {
    @Override
    public Player findByNickname(String nickname) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Player where nickname=:playerNickname ", Player.class)
                    .setParameter("playerNickname", nickname)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public void add(Player elem) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(elem));
    }

    @Override
    public void update(Integer integer, Player elem) {

    }

    @Override
    public Iterable<Player> findAll() {
        return null;
    }
}
