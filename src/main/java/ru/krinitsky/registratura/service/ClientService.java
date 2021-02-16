package ru.krinitsky.registratura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.krinitsky.registratura.domain.Client;
import ru.krinitsky.registratura.reposytory.ClientRepository;

@Service
public class ClientService {

    private final ClientRepository clientRepository;


    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    //Добавляем клиента в базу данных
    public void addClient(Client client){
        clientRepository.save(client);
    }

    //Удаляем клиента из базы данных
    public void deleteClient(Client client){
        clientRepository.delete(client);
    }



}
