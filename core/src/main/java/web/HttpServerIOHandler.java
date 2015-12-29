package web;

import util.Charsets;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormParserFactory;


/**
 * @author ChNan
 */
public class HttpServerIOHandler implements HttpHandler {
    private HttpServerHandler handler;

    public HttpServerIOHandler(HttpServerHandler handler) {
        this.handler = handler;
        FormParserFactory.Builder formParserFactoryBuilder = FormParserFactory.builder();
        formParserFactoryBuilder.setDefaultCharset(Charsets.UTF_8.name());
    }

    @Override
    public void handleRequest(final HttpServerExchange exchange) throws Exception {
        exchange.dispatch(handler);
    }
}
