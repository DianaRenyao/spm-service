package buptspirit.spm.rest;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("webapi")
public class SpmResourceConfig extends ResourceConfig {
    public SpmResourceConfig() {
        // use CDI instead
        register(MultiPartFeature.class);
        packages(false, "buptspirit.spm.rest.filter");
        packages(false, "buptspirit.spm.rest.mapper");
        packages(false, "buptspirit.spm.rest.resource");
    }
}
