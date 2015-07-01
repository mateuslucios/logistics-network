package trial.logisticsnetwork.core;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;


public class AppResourceConfig extends ResourceConfig {

    public AppResourceConfig(){
        register(RequestContextFilter.class);
        register(MultiPartFeature.class);
    }

}
