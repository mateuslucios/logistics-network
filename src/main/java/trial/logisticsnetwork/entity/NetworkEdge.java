package trial.logisticsnetwork.entity;

/**
 * Represents an edge or path connecting two nodes in the network.
 *
 * Nodes are represented as strings (source and target)
 */
public class NetworkEdge {
    private String source;

    private String target;

    private Double distance;

    public NetworkEdge(){
        distance = 0.0;
    }

    public NetworkEdge(String source, String target, Double distance) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NetworkEdge that = (NetworkEdge) o;

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
        return "NetworkEdge{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", distance=" + distance +
                '}';
    }
}
