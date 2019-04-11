package buptspirit.spm.rest;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("webapi")
public class SpmResourceConfig extends ResourceConfig {
    public SpmResourceConfig() {
        register(MultiPartFeature.class);
        packages(true, "buptspirit.spm.rest");
    }
}
