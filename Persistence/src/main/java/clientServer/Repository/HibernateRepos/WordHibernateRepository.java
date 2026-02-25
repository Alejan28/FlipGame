package clientServer.Repository.HibernateRepos;

import Domain.Configuration;
import Domain.Word;
import Utils.HibernateUtils;
import clientServer.Repository.Interfaces.WordRepoInterface;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WordHibernateRepository implements WordRepoInterface {


    @Override
    public void add(Word elem) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(elem));
    }

    @Override
    public void update(Integer integer, Word elem) {

    }

    @Override
    public Iterable<Word> findAll() {
        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Word ", Word.class).getResultList();
        }
    }

    @Override
    public List<Word> getALlWord() {
        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Word ", Word.class).getResultList();
        }
    }

    @Override
    public List<Word> generateRandomWord(Integer count) {
        List<Word> allWords=getALlWord();
        allWords.forEach(w -> System.out.println("Word id: " + w.getId() + ", text: " + w.getWord()));
        Collections.shuffle(allWords);            // Shuffle the list randomly
        return allWords.stream()
                .limit(count)              // Take the first 'count' words
                .collect(Collectors.toList());
    }
}
