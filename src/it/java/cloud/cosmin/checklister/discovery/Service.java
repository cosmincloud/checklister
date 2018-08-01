package cloud.cosmin.checklister.discovery;

import static java.lang.String.format;

public class Service {
    public final String host;
    public final int port;
    public final String http;

    public Service(String host, int port) {
        this.host = host;
        this.port = port;
        this.http = format("http://%s:%d", host, port);
    }
}
