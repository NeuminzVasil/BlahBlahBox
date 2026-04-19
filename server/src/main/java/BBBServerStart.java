import entities.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.util.List;


//@PropertySource("classpath:ignoreMe/values.properties")
public class BBBServerStart {
    public static void main(String[] args) throws SQLException
    {
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
         * пример использования NamedQuery
         */
        Customer customer2 = (Customer) session.getNamedQuery("customerWithAuthority")
                                                            .setParameter("id", 1L)
                                                            .getSingleResult();

        List<Customer> customers = session.getNamedQuery("allCustomers")
                                                        .getResultList();


        /**
         * работаем с объектом
         */
        System.out.println(customer);
        System.out.println(customer.getAuthorities());


        System.out.println("customer2:");
        System.out.println(customer2);

        System.out.println("customers:");
        System.out.println(customers);

        /**
         * заканчиваем транзакцию и автоматически закрываем сессию
         * для открытия новой сесии выполни:
         * session = sessionFactory.getCurrentSession();
         * session.beginTransaction();
         */
        session.getTransaction().commit();


        /**
         * Закрываем фабрикуСессий
         */
        sessionFactory.close();
        if (session != null) session.close();

        System.out.println("BBBServerStop");

    }
}
