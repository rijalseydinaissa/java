package org.odc.Factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.odc.Repository.ClientRepository;
import org.odc.Service.ClientServiceImpl;
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.HashMap;

public class ServiceFactory {
    private static ServiceFactory instance;
    private Map<String, String> serviceTypes;
    private Map<String, String> repositoryTypes;
    private EntityManagerFactory emf;
    private EntityManager em;
    private boolean useDatabase;
    private Map<String, Object> repositoryInstances = new HashMap<>();

    private ServiceFactory() {
        loadConfiguration();
        if (useDatabase) {
            this.emf = Persistence.createEntityManagerFactory("default");
            this.em = emf.createEntityManager();
        }
    }

    public static ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    private void loadConfiguration() {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.yaml")) {
            Map<String, Object> config = yaml.load(inputStream);
            this.serviceTypes = (Map<String, String>) config.get("Service");

            String storageType = (String) ((Map<String, Object>) config.get("storage")).get("type");
            this.useDatabase = "Db".equals(storageType);
            Map<String, Object> repoConfig = (Map<String, Object>) config.get("Repository");
            this.repositoryTypes = (Map<String, String>) repoConfig.get(storageType);

            if (this.repositoryTypes == null) {
                throw new RuntimeException("Invalid storage type in configuration: " + storageType);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public <T> T getService(String serviceName) {
        try {
            String serviceClass = serviceTypes.get(serviceName);
            if (serviceClass == null) {
                throw new RuntimeException("Service type not found for: " + serviceName);
            }

            Class<?> clazz = Class.forName(serviceClass);
            Constructor<?>[] constructors = clazz.getConstructors();

            if (constructors.length == 0) {
                throw new RuntimeException("No public constructor found for " + serviceClass);
            }

            Constructor<?> constructor = constructors[0];
            Class<?>[] paramTypes = constructor.getParameterTypes();
            Object[] params = new Object[paramTypes.length];

            for (int i = 0; i < paramTypes.length; i++) {
                String repositoryName = paramTypes[i].getSimpleName();
                params[i] = getRepository(repositoryName);
            }

            return (T) constructor.newInstance(params);
        } catch (Exception e) {
            throw new RuntimeException("Error creating service instance for " + serviceName, e);
        }
    }

    private Object createRepositoryInstance(String repositoryName) {
        try {
            String repositoryClass = repositoryTypes.get(repositoryName);
            if (repositoryClass == null) {
                throw new RuntimeException("Repository type not found for: " + repositoryName);
            }
            Class<?> clazz = Class.forName(repositoryClass);
            if (useDatabase) {
                return clazz.getDeclaredConstructor(EntityManager.class).newInstance(em);
            } else {
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating repository instance for " + repositoryName, e);
        }
    }

    public <T> T getRepository(String repositoryName) {
        if (!repositoryInstances.containsKey(repositoryName)) {
            repositoryInstances.put(repositoryName, createRepositoryInstance(repositoryName));
        }
        return (T) repositoryInstances.get(repositoryName);
    }

    // Méthodes getXXXService spécifiques si nécessaire
    public Object getClientService() {
        ClientRepository clientRepository = getRepository("ClientRepository");
        return new ClientServiceImpl(clientRepository);
    }

    public Object getArticleService() {
        return getService("ArticleService");
    }

    public Object getDetteService() {
        return getService("DetteService");
    }

    public Object getPaiementService() {
        return getService("PaiementService");
    }

    public Object getCompteService() {
        return getService("CompteService");
    }
    public void close() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

}