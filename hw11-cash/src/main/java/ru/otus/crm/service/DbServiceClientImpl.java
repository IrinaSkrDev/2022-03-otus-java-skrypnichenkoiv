package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cache.MyCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.crm.model.Client;
import ru.otus.core.sessionmanager.TransactionManager;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final MyCache<Long, Client> cashClient;
    private final ru.otus.cache.HwListener<Long, Client> listener = new ru.otus.cache.HwListener<Long, Client>() {
        @Override
        public void notify(Long key, Client client, String action) {
            log.info("key:{}, value:{}, action: {}", key, client, action);
        }
    };

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.cashClient = new MyCache<Long, Client>();
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();

            if (client.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                cashClient.put(clientCloned.getId(), clientCloned);
                cashClient.addListener(listener);
                return clientCloned;
            }
            clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", clientCloned);
            cashClient.remove(clientCloned.getId());
            cashClient.put(clientCloned.getId(), clientCloned);
            cashClient.addListener(listener);
            return clientCloned;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        Client client = cashClient.get(id);
        cashClient.removeListener(listener);
        if (client != null) return Optional.of(client);
        return transactionManager.doInReadOnlyTransaction(session -> {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            var clientOptional = clientDataTemplate.findById(session, id);
            if (clientOptional.isPresent()) cashClient.put(clientOptional.get().getId(), clientOptional.get());
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }
}
