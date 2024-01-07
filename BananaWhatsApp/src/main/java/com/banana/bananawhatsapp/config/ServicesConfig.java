package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.persistence.IMessageRepository;
import com.banana.bananawhatsapp.persistence.IUserRepository;
import com.banana.bananawhatsapp.services.IServiceMessage;
import com.banana.bananawhatsapp.services.IServiceUsers;
import com.banana.bananawhatsapp.services.ServiceMessage;
import com.banana.bananawhatsapp.services.ServiceUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {

    @Autowired
    IUserRepository repoUser;

    @Bean
    public IServiceUsers getServiceUser() {
        ServiceUsers servUsu = new ServiceUsers();
        servUsu.setRepoUsuario(repoUser);
        return servUsu;
    }
    @Autowired
    IMessageRepository repoMessage;

    @Bean
    public IServiceMessage getServiceMessage() {
        ServiceMessage servMensa = new ServiceMessage();
        servMensa.setRepoMensajeria(repoMessage);
        servMensa.setRepoUsuario(repoUser);
        return servMensa;
    }
}
