import entities.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;

public class BBBServerStart {
    public static void main(String[] args) throws SQLException {
//        new BlahBlahBoxServer();
        System.out.println("BBBServerStart");

        /**
         * Создаем фабрикуСессий подключений к БД один раз на все приложение.
         * Требует закрытия после окончания работы с приложением.
        */
        SessionFactory sessionFactory = new Configuration()
                .configure("/configuration/hibernate.cfg.xml")
                .buildSessionFactory();

        /**
         * Создаем пустой объект-сессию подключения к БД
         * с которым будем работать каждый раз при обращении к БД
         */
        Session session = null;

        /**
         * Открываем сессию подключения к БД
         */
        session = sessionFactory.getCurrentSession();

        /**
         * Начинаем транзакцию
         */
        session.beginTransaction();

        /**
         * получаем объект из БД и записываем его в новый объект Java
         */
        Customer customer = session.get(Customer.class, 1L);

        /**
         * заканчиваем транзакцию и автоматически закрываем сессию
         * для открытия новой сесии выполни:
         * session = sessionFactory.getCurrentSession();
         * session.beginTransaction();
         */
        session.getTransaction().commit();

        System.out.println(customer);

        System.out.println("BBBServerStop");

    }
}
