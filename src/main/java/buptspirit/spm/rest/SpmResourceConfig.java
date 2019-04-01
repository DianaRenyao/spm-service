package buptspirit.spm.rest;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("webapi")
public class SpmResourceConfig extends ResourceConfig {
    public SpmResourceConfig() {
        packages(false, "buptspirit.spm.rest.resource");
        packages(false, "buptspirit.spm.rest.filter");
        packages(false, "buptspirit.spm.rest.exception");
    }
}
