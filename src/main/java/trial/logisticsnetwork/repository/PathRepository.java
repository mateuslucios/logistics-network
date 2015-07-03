package trial.logisticsnetwork.repository;

import trial.logisticsnetwork.entity.Network;
import trial.logisticsnetwork.entity.Path;

import java.util.List;

public interface PathRepository {

    List<Path> find(Network network, String source, String target);

}
