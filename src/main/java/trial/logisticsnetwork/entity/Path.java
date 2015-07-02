package trial.logisticsnetwork.entity;

/**
 * Created by mateus on 7/1/15.
 */
public class Path {

    private String source;

    private String target;

    private String pathDescription;

    private double distance;

    private String current;

    /*public Path() {
    }*/

    public Path(String source){
        this.source = source;
        this.current = source;
        this.pathDescription = source;
    }

    public Path(String source, String target, double distance, String pathDescription, String current) {
        this.source = source;
        this.target = target;
        this.distance = distance;
        this.pathDescription = pathDescription;
        this.current = current;
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
                ", pathDescription='" + pathDescription + '\'' +
                ", distance=" + distance +
                '}';
    }
}
