package trial.logisticsnetwork.entity;

import javax.persistence.*;

/**
 * Represents an edge or path connecting two nodes in the network.
 *
 * Nodes are represented as strings (source and target)
 */
@Entity
@Table(name = "network_edge")
public class Edge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "source_node", nullable = false)
    private String source;

    @Column(name = "target_node", nullable = false)
    private String target;

    @Column(name = "distance", nullable = false)
    private Double distance;

    @ManyToOne
    @JoinColumn(name = "network_id", referencedColumnName = "id", nullable = false)
    private Network network;

    public Edge(){
        distance = 0.0;
    }

    public Edge(String source, String target, Double distance) {
        this.source = source;
        this.target = target;
        this.distance = distance == null ? 0.0 : distance;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge that = (Edge) o;

        if (!source.equals(that.source)) return false;
        return target.equals(that.target);

    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + target.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", distance=" + distance +
                '}';
    }
}
