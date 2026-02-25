package Utils;

import Domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if ((sessionFactory==null)||(sessionFactory.isClosed()))
            sessionFactory=createNewSessionFactory();
        return sessionFactory;
    }
    private static  SessionFactory createNewSessionFactory(){
        sessionFactory = new Configuration()
                .addAnnotatedClass(Player.class)
                .addAnnotatedClass(Domain.Configuration.class)
                .addAnnotatedClass(Game.class)
                .addAnnotatedClass(Move.class)
                .addAnnotatedClass(Word.class)
                .addAnnotatedClass(WordGame.class)
                .addAnnotatedClass(WordGameConfig.class)
                .addAnnotatedClass(Choice.class)
                .buildSessionFactory();
        return sessionFactory;
    }

    public static  void closeSessionFactory(){
        if (sessionFactory!=null)
            sessionFactory.close();
    }
}
