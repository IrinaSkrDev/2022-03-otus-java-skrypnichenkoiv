package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.dao.DbServiceClientImpl;
import ru.otus.dao.InMemoryUserDao;
import ru.otus.dao.UserDao;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.server.UsersWebServer;
import ru.otus.server.UsersWebServerWithBasicSecurity;
import ru.otus.services.InMemoryLoginServiceImpl;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithBasicSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";

    public static void main(String[] args) throws Exception {
        UserDao userDao = new InMemoryUserDao();
        var cfg = new Configuration();

        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        cfg.setProperty("hibernate.connection.driver_class", "org.h2.Driver");

        cfg.setProperty("hibernate.connection.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        cfg.setProperty("hibernate.connection.username", "sa");
        cfg.setProperty("hibernate.connection.password", "");

        cfg.setProperty("hibernate.show_sql", "true");
        cfg.setProperty("hibernate.format_sql", "false");
        cfg.setProperty("hibernate.generate_statistics", "true");

        cfg.setProperty("hibernate.hbm2ddl.auto", "create");
        cfg.setProperty("hibernate.enable_lazy_load_no_trans", "false");
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties()).build();


        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(Phone.class);
        metadataSources.addAnnotatedClass(Address.class);
        metadataSources.addAnnotatedClass(Client.class);
        Metadata metadata = metadataSources.getMetadataBuilder().build();
        var sessionFactory = metadata.getSessionFactoryBuilder().build();


        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
///
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
        LoginService loginService = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);
        //LoginService loginService = new InMemoryLoginServiceImpl(userDao);

        UsersWebServer usersWebServer = new UsersWebServerWithBasicSecurity(WEB_SERVER_PORT,
                loginService, dbServiceClient, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
