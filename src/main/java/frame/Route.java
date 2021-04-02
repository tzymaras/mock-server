package frame;

public class Route {
    private final String method;
    private final String url;
    private final String statusCode;
    private final String body;

    public Route(String method, String url, String statusCode, String body) {
        this.method = method;
        this.url = url;
        this.statusCode = statusCode;
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Route{" +
            "url='" + url + '\'' +
            ", statusCode='" + statusCode + '\'' +
            ", body='" + body + '\'' +
            '}';
    }
}
