package ru.otus.crm.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@RestController
public class ClientRestController {

    private final DBServiceClient clientService;

    public ClientRestController(DBServiceClient clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/api/client/{id}")
    public Client getClientById(@PathVariable(name = "id") long id) {
        if (!clientService.getClient(id).isPresent()) {
            // throw new HttpClientErrorException("not fouund",HttpStatus.NOT_FOUND,null,null,null,null);
            return new Client();
        }

        return clientService.getClient(id).get();
    }

    @GetMapping("/api/clients")
    public List<Client> getAllClient() {
        return clientService.findAll();
    }

    @PostMapping("/api/client/new")
    public Client saveClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }


}
