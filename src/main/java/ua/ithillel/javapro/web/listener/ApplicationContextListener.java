package ua.ithillel.javapro.web.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import lombok.extern.slf4j.Slf4j;
import ua.ithillel.javapro.web.OrderServlet;

@Slf4j
public class ApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        log.info("Application context initialized");
        context.setAttribute("appStarted", true);
        context.addServlet("orderServlet", new OrderServlet()).addMapping("/order/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Application context destroyed");
    }
}

