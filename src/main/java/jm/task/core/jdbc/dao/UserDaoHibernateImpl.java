package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Session session;
    public UserDaoHibernateImpl() {
        session = Util.getSessionFactory().openSession();
    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String usersTable = "CREATE TABLE IF NOT EXISTS users" +
                "(id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(20) NOT NULL ," +
                "lastName VARCHAR(20)NOT NULL," +
                "age TINYINT)";
        try {
            transaction = session.beginTransaction();
            session.createNativeQuery(usersTable,User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String dropUsers = "DROP TABLE IF EXISTS users";
        try{
            transaction = session.beginTransaction();
            session.createNativeQuery(dropUsers,User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name,lastName,age);
        try {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        User user = session.get(User.class,id);
        try {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> getAllUsers() {
        try {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }

    }
}
