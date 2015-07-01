package trial.logisticsnetwork.entity;

import java.util.Collections;
import java.util.List;

/**
 * Represents a network with all it's edges.
 */
public class Network {
    private String name;

    private List<NetworkEdge> edges;

    public Network() {
        name = "empty network";
        edges = Collections.emptyList();
    }

    public Network(String name, List<NetworkEdge> edges) {
        this.name = name;

        if (edges == null)
            this.edges = Collections.emptyList();
        else
            this.edges = edges;
    }

    public String getName() {
        return name;
    }

    public List<NetworkEdge> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    public void add(NetworkEdge edge){
        edges.add(edge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Network network = (Network) o;

        return name.equals(network.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Network{" +
                "name='" + name + '\'' +
                "size='" + edges.size() + '\'' +
                '}';
    }
}
