package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.PersistenceException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory factory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE `user_table` " +
                    "(`ID` INT NOT NULL AUTO_INCREMENT," +
                    "`name` VARCHAR(45) NULL," +
                    "`last_name` VARCHAR(45) NULL," +
                    "`age` INT(3) NULL," +
                    "PRIMARY KEY (`ID`))").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица успешно создана");
        } catch (PersistenceException e) {
            System.out.println("Таблица с таким именем уже существует");
            session.getTransaction().rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE `user_table`").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица успешно удалена");
        } catch (PersistenceException e) {
            System.out.println("Удаляемой таблицы не существует");
            session.getTransaction().rollback();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("Ползователь: " + user + " успешно добавлен в таблицу");
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            System.out.println("Не удалось вставить данные в таблицу");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            System.out.println("Не удалось удалить user с id " + id);
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            List<User> list = session.createQuery("from User ", User.class).getResultList();
            session.getTransaction().commit();
            return list;
        } catch (RuntimeException e) {
            System.out.println("Не удалось получить данные");
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createQuery("delete User ").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Пользователи успешно удалены из таблицы");
        } catch (RuntimeException e) {
            System.out.println("Не удалось удалить всех user из таблицы");
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
}
