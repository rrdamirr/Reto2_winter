package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.persistence.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class RepositoryConfig {

    @Value("${db_url}")
    String connect;

    @Bean
    public DBConnector createDBConnector() {
        return new DBConnector();
    }

    @Bean
    //@Profile("development")
    public IUserRepository createIUsuarioRepository() {
        UserRepository repo = new UserRepository();
        System.out.println("connect: " + connect);
        repo.setDb_url(connect);
        return repo;
    }

    @Bean
    //@Profile("development")
    public IMessageRepository createIMensajeRepository() {
        MessageRepository repo = new MessageRepository();
        System.out.println("connect: " + connect);
        repo.setDb_url(connect);
        return repo;
    }

}
