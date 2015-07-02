package trial.logisticsnetwork.request;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

public class PathRequest {

    @PathParam("network-name")
    private String networkName;

    @PathParam("source")
    private String source;

    @PathParam("target")
    private String target;

    @QueryParam("autonomy")
    @DefaultValue("0")
    private Double autonomy;

    @QueryParam("liter-price")
    @DefaultValue("0")
    private Double literPrice;

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
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

    public Double getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(Double autonomy) {
        this.autonomy = autonomy;
    }

    public Double getLiterPrice() {
        return literPrice;
    }

    public void setLiterPrice(Double literPrice) {
        this.literPrice = literPrice;
    }
}
