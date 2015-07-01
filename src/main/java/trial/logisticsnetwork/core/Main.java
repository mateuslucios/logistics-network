package trial.logisticsnetwork.core;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

import java.net.BindException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Date;

public class Main {
    public static String WS_WEBAPP_CONTEXT_NAME = "trial-ws";
    public static String WS_ENDPOINT_NAME = "api";

    public static String WS_ACCEPTOR_HOST = "localhost";
    public static int WS_ACCEPTOR_PORT = 8080;

    public static void main(String[] args) {
        Thread.currentThread().setName("main-" + Thread.currentThread().getId());

        try {
            URI uri = URI.create(String.format("http://%s:%d", WS_ACCEPTOR_HOST, WS_ACCEPTOR_PORT));

            HttpServer webContainer = GrizzlyHttpServerFactory.createHttpServer(uri);

            WebappContext context = new WebappContext("WebappContext", "/" + WS_WEBAPP_CONTEXT_NAME);

            context.addListener("org.springframework.web.context.ContextLoaderListener");

            context.addContextInitParameter("contextConfigLocation", "classpath:application-context.xml");

            ServletRegistration registration = context.addServlet("ServletContainer", ServletContainer.class);

            registration.setInitParameter(ServerProperties.PROVIDER_PACKAGES,
                    "trial.logisticsnetwork.service");
            registration.setInitParameter(ServerProperties.PROVIDER_CLASSNAMES,
                    "com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider " +
                            "com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider " +
                            "org.glassfish.jersey.media.multipart.MultiPartFeature");

            registration.setInitParameter("javax.ws.rs.Application", "trial.logisticsnetwork.core.AppResourceConfig");

            registration.addMapping(String.format("/%s/*", WS_ENDPOINT_NAME));

            registration.setLoadOnStartup(1);

            context.deploy(webContainer);

            webContainer.start();

            System.out.println(String.format("Serving in the address [http://%s:%d]",
                    WS_ACCEPTOR_HOST, WS_ACCEPTOR_PORT));

            System.out.println(String.format("*** Service was set up in %s ***", new Date()));

            /* Sets shutdown hook and iterates'il exit */
            setShutdownHook(webContainer);

            while (true) {
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            System.err.println("Erro inesperado; servico sera abortado...");
            e.printStackTrace(System.err);
        } catch (UnknownHostException e) {
            System.err.println(String.format(
                    "*** ATENCAO *** Endereco [%s] nao pode ser resolvido; servico sera abortado...", e.getMessage()));
            e.printStackTrace(System.err);
        } catch (Exception e) {
            if (e.getCause() instanceof BindException) {
                System.err.println(String.format(
                        "*** ATENCAO *** Endereco [%s:%s] ja esta sendo usado; servico sera abortado...",
                        WS_ACCEPTOR_HOST, WS_ACCEPTOR_PORT));
            } else {
                System.err.println("Erro inesperado; servico sera abortado...");
                e.printStackTrace(System.err);
            }
        } finally {

        }

        System.exit(0);
    }

    private static void setShutdownHook(final HttpServer webContainer) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            public void run() {
                Thread.currentThread().setName("main-shutdown-hook-" + Thread.currentThread().getId());

                try {
                    webContainer.stop();

                } catch (Exception e) {
                    System.err.println(String.format("*** Unable to stop web container at %s ***", new Date()));
                    e.printStackTrace(System.err);
                }
            }
        }));
    }
}