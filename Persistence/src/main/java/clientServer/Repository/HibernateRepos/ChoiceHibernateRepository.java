package clientServer.Repository.HibernateRepos;

import Domain.Choice;
import Utils.HibernateUtils;
import clientServer.Repository.Interfaces.ChoiceRepoInterface;

public class ChoiceHibernateRepository implements ChoiceRepoInterface {
    @Override
    public void add(Choice elem) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(elem));
    }

    @Override
    public void update(Integer integer, Choice elem) {

    }

    @Override
    public Iterable<Choice> findAll() {
        return null;
    }
}
