package cloud.cosmin.checklister.discovery;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class ServiceDiscovery {
    private static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private static int getServicePort(String serviceName, int internalPort) throws IOException {
        var tree = mapper.readTree(new File("docker-compose.yml"));

        var ports = tree.get("services").get("checklister").get("ports");

        for(int i = 0; i < ports.size(); i++) {
            var port = ports.get(i).asText();
            if(port.contains(":")) {
                var portParts = port.split(":");
                if(Integer.parseInt(portParts[1]) == internalPort) {
                    return Integer.parseInt(portParts[0]);
                }
            }
        }
        return -1;
    }

    public static Service getService(String name) throws IOException {
        var host = System.getenv(name + "_HOST");
        var port = System.getenv(name + "_TCP_8080");

        if(host == null) {
            host = "localhost";
        }

        if(port == null) {
            return new Service(host, getServicePort("checklister", 8080));
        }

        return new Service(host, Integer.valueOf(port));
    }
}
