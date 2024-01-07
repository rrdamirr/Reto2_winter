package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.controlers.ControlerMessages;
import com.banana.bananawhatsapp.controlers.ControlerUsers;
import com.banana.bananawhatsapp.services.IServiceMessage;
import com.banana.bananawhatsapp.services.IServiceUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class ControllersConfig {

    @Autowired
    IServiceUsers servUsers;

    @Bean
    //@Profile("development")
    public ControlerUsers createControllerUser() {
        ControlerUsers controllerUser = new ControlerUsers();
        controllerUser.setServicioUsuarios(servUsers);
        return controllerUser;
    }

    @Autowired
    IServiceMessage servMensa;

    @Bean
    //@Profile("development")
    public ControlerMessages createControllerMessage() {
        ControlerMessages controllerMessage = new ControlerMessages();
        controllerMessage.setServicioMensajeria(servMensa);
        return controllerMessage;
    }
}
