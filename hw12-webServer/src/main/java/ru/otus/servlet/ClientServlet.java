package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.dao.DBServiceClient;
import ru.otus.dao.UserDao;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ClientServlet extends HttpServlet {
    private static final String USERS_PAGE_TEMPLATE = "client.html";
    private static final String TEMPLATE_ATTR_RANDOM_CLIENTS = "clients";
    private final DBServiceClient dbServiceClient;
    private final TemplateProcessor templateProcessor;

    public ClientServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();

        Optional.of(dbServiceClient.findAll()).ifPresent(clients->clients.forEach(client -> paramsMap.put(TEMPLATE_ATTR_RANDOM_CLIENTS,client)));


        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }
}
