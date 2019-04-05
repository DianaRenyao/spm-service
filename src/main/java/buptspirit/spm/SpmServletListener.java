package buptspirit.spm;

import buptspirit.spm.persistence.PersistenceSingleton;
import org.apache.logging.log4j.Logger;
import org.jboss.weld.servlet.api.ServletListener;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;

public class SpmServletListener implements ServletListener {

    @Inject
    private Logger logger;

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        PersistenceSingleton.instance.destroy();
        logger.info("context destroyed");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("context initialized");
    }
}
