package ru.otus.demo;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.AddressDataSet;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.PhoneDataSet;
import ru.otus.crm.service.DbServiceClientImpl;

import java.util.ArrayList;

public class Homework {
    private static final Logger log = LoggerFactory.getLogger(Homework.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration,
                Client.class,
                AddressDataSet.class,
                PhoneDataSet.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
///
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
// first client
        var clientFirstAddress = new AddressDataSet();
        var clientFirst = new Client("First Client");
        clientFirstAddress.setStreet("First Client's address", clientFirst);
        var clientFirstPhones = new ArrayList<PhoneDataSet>();
        for (int i = 1; i <= 5; i++) {
            clientFirstPhones.add(new PhoneDataSet("number" + i, clientFirst));
        }

        clientFirst.setAddress(clientFirstAddress);
        clientFirst.setPhones(clientFirstPhones);

        dbServiceClient.saveClient(clientFirst);

// second client
        var clientSecondAddress = new AddressDataSet();
        var clientSecond = new Client("Second Client");
        clientSecondAddress.setStreet("Second Client's address", clientSecond);
        var clientSecondPhones = new ArrayList<PhoneDataSet>();
        for (int i = 11; i <= 15; i++) {
            clientSecondPhones.add(new PhoneDataSet("number" + i, clientSecond));
        }
        clientSecond.setAddress(clientSecondAddress);
        clientSecond.setPhones(clientSecondPhones);

        dbServiceClient.saveClient(clientSecond);
// second client update
        var clientSecondSelected = dbServiceClient.getClient((clientSecond.getId()))
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        log.info("clientSecondSelected: {}", clientSecondSelected);

        dbServiceClient.saveClient(new Client(clientSecondSelected.getId(),
                "Second Client Updated",
                clientSecondSelected.getAddress(),
                clientSecondSelected.getPhones()));
        var clientUpdated = dbServiceClient.getClient(clientSecondSelected.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecondSelected.getId()));
        log.info("clientSecondUpdated: {}", clientUpdated);

        log.info("All clients");
        dbServiceClient.findAll().forEach(client -> log.info("clients: {}", client));
    }
}
