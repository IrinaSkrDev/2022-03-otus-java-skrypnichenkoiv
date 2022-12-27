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
import ru.otus.model.User;

import java.io.IOException;
import java.util.List;


public class ClientApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final DBServiceClient dbServiceClient;
    private final Gson gson;

    public ClientApiServlet(DBServiceClient dbServiceClient, Gson gson) {
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Client client = dbServiceClient.saveClient(extractClientFromRequest(request));

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(client));
    }

    private Client extractClientFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        Client client = new Client(null, "Vasya", new Address(null, "AnyStreet"),
                List.of(new Phone(null, "13-555-22"), new Phone(null, "14-666-333"))); //(path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);
        return client;
    }

}
