package trial.logisticsnetwork.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a network with all it's edges.
 */
@Entity
@Table(name = "network")
public class Network {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "network", cascade = CascadeType.PERSIST)
    private List<Edge> edges;

    @Column(name = "processed_mark", nullable = false)
    private boolean processed;

    public Network() {
        name = "empty network";
        edges = Collections.emptyList();
    }

    public Network(String name, List<Edge> edges) {
        this.name = name;

        if (edges == null) {
            this.edges = Collections.emptyList();
        } else {
            this.edges = new ArrayList<>(edges.size());
            for (Edge edge : edges) {
                edge.setNetwork(this);
                this.edges.add(edge);
            }

        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<Edge> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    public void add(Edge edge){
        edge.setNetwork(this);
        edges.add(edge);
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", size=" + edges.size() +
                '}';
    }
}
