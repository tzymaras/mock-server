package frame;

import java.util.List;

public class Route {
    private final String         body;
    private final List<String[]> headers;
    private       String         method;
    private       String         url;
    private       String         statusCode;

    public Route(String method, String url, String statusCode, String body, List<String[]> headers) {
        this.method     = method;
        this.url        = url;
        this.statusCode = statusCode;
        this.body       = body;
        this.headers    = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<String[]> getHeaders() {
        return headers;
    }

    @Override
    public String toString() {
        return "Route{" +
            "method='" + method + '\'' +
            ", url='" + url + '\'' +
            ", statusCode='" + statusCode + '\'' +
            ", body='" + body + '\'' +
            ", headers=" + headers +
            '}';
    }
}
