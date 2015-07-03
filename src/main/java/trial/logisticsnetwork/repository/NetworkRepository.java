package trial.logisticsnetwork.repository;

import trial.logisticsnetwork.entity.Network;

public interface NetworkRepository {

    Network findByName(String name);

    void insert(Network network);
}
