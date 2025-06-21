package clientServer.Repository.Interfaces;

import Domain.Configuration;

public interface ConfigurationRepoInterface extends IRepository<Integer, Configuration> {
    Configuration getRandomConfiguration();
}
