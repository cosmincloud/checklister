package cloud.cosmin.checklister.discovery;

public class ServiceDiscovery {
    public static Service getService(String name) {
        var host = System.getenv(name + "_HOST");
        var port = System.getenv(name + "_TCP_8080");

        if(host == null) {
            host = "localhost";
        }

        if(port == null) {
            port = "8080";
        }

        return new Service(host, Integer.valueOf(port));
    }
}
