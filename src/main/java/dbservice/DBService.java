package dbservice;

import accounts.UserProfile;
import org.hibernate.*;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class DBService {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "validate";

    private static SessionFactory sessionFactory;

    @SuppressWarnings("UnusedDeclaration")
    private static void getPostgresqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserProfile.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/usersJava");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "Xfq8ybR*");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        sessionFactory = createSessionFactory(configuration);
    }

    private static void checkSession(){
        if(sessionFactory == null){
            getPostgresqlConfiguration();
        }
    }

    public static UserProfile getUserByLogin(String login) {
        checkSession();
        try {

            Session session = sessionFactory.openSession();
            UsersDAO dao = new UsersDAO(session);
            return dao.get(login);
        }
        catch (HibernateException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void addUser(UserProfile user)  {
        checkSession();
        try{
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            dao.insertUser(user);
            transaction.commit();
            session.close();
        }
        catch (HibernateException e){
            e.printStackTrace();
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
