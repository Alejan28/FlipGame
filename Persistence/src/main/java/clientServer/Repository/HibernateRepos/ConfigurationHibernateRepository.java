package clientServer.Repository.HibernateRepos;

import Domain.Configuration;
import clientServer.Repository.Interfaces.ConfigurationRepoInterface;
import Utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.StreamSupport;
@Repository
public class ConfigurationHibernateRepository implements ConfigurationRepoInterface {
    @Override
    public Configuration getRandomConfiguration() {
        Iterable<Configuration> allConfigs=findAll();
        List<Configuration> configs= StreamSupport.stream(allConfigs.spliterator(),false).toList();
        int size=configs.size();
        Random rand=new Random();
        int index=rand.nextInt(size);
        return configs.get(index);
    }

    @Override
    public void add(Configuration elem) {
        final SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        if (elem.getId() == null) {
            elem.setId(Math.abs(UUID.randomUUID().hashCode()));
        }
        Session session=sessionFactory.openSession();
        Transaction tx=session.beginTransaction();
        session.persist(elem);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Integer integer, Configuration elem) {

    }

    @Override
    public Iterable<Configuration> findAll() {
        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Configuration ", Configuration.class).getResultList();
        }
    }
}
