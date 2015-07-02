package trial.logisticsnetwork.entity;

import javax.persistence.*;

/**
 * Represents a possible
 */
@Entity
@Table(name = "network_path")
public class Path {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_node", nullable = false)
    private String source;

    @Column(name = "target_node", nullable = false)
    private String target;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "distance", nullable = false)
    private double distance;

    @ManyToOne
    @JoinColumn(name = "network_id", referencedColumnName = "id", nullable = false)
    private Network network;

    @Transient
    private String current;

    public Path() {

    }

    public Path(String source){
        this.source = source;
        this.current = source;
        this.description = source;
    }

    public Path(String source, String target, double distance, String description, String current, Network network) {
        this.source = source;
        this.target = target;
        this.distance = distance;
        this.description = description;
        this.current = current;
        this.network = network;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Path path = (Path) o;

        if (Double.compare(path.distance, distance) != 0) return false;
        if (!source.equals(path.source)) return false;
        return target.equals(path.target);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = source.hashCode();
        result = 31 * result + target.hashCode();
        temp = Double.doubleToLongBits(distance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Path{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", description='" + description + '\'' +
                ", distance=" + distance +
                '}';
    }
}
