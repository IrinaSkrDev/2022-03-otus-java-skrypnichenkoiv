package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.dao.DBServiceClient;
import ru.otus.dao.UserDao;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.*;

public class ClientServlet extends HttpServlet {
    private static final String CLIENT_PAGE_TEMPLATE = "client.html";
    private static final String TEMPLATE_ATTR_CLIENTS = "clients";
    private final DBServiceClient dbServiceClient;
    private final TemplateProcessor templateProcessor;


    public ClientServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        List<Client> clientsList =dbServiceClient.findAll();
        paramsMap.put(TEMPLATE_ATTR_CLIENTS,clientsList);
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENT_PAGE_TEMPLATE,  paramsMap));

    }

    private Client extractClientFromRequest(HttpServletRequest request) {
        HttpServletRequest request1 = request;
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        Client client = new Client(null, name, new Address(null, address),
                List.of(new Phone(null, phone)));
        return client;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Client client = dbServiceClient.saveClient(extractClientFromRequest(request));
        response.setContentType("application/json;charset=UTF-8");
        response.sendRedirect("/client");


    }
}
