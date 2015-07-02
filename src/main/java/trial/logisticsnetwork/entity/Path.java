package trial.logisticsnetwork.entity;

/**
 * Created by mateus on 7/1/15.
 */
public class Path {
    private String source;

    private String target;

    private String pathDescription;

    private Double distance;

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

    public String getPathDescription() {
        return pathDescription;
    }

    public void setPathDescription(String pathDescription) {
        this.pathDescription = pathDescription;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Path{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", pathDescription='" + pathDescription + '\'' +
                ", distance=" + distance +
                '}';
    }
}
