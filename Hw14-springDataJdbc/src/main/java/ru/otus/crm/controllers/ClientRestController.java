package ru.otus.crm.controllers;

import org.springframework.web.bind.annotation.*;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;

import java.util.List;


@RestController
public class ClientRestController {

    private final DBServiceClient clientService;

    public ClientRestController(DBServiceClient clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/api/client/{id}")
    public Client getClientById(@PathVariable(name = "id") long id) {
        return clientService.getClient(id).get();
    }

    @GetMapping("/api/clients")
    public List<Client> getAllClient() {
        return clientService.findAll();
    }

    @PostMapping("/api/client")
    public Client saveClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }


}
