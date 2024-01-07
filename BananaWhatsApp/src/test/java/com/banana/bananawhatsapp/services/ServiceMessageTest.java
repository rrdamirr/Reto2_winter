package com.banana.bananawhatsapp.services;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.MessageException;
import com.banana.bananawhatsapp.models.Message;
import com.banana.bananawhatsapp.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ServiceMessageTest {

    @Autowired
    IServiceMessage service;

    @Test
    void dadoRemitenteYDestinatarioYTextoValido_cuandoEnviarMensaje_entoncesMensajeValido() {
        User userRemitente = new User(15, null, null, null, true);
        User userDestinatario = new User(18, null, null, null, true);
        Message message = service.enviarMensaje(userRemitente, userDestinatario, "Hola de nuevo Curso");

        assertThat(message.getId(), greaterThan(0));
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValido_cuandoEnviarMensaje_entoncesExcepcion() {

        assertThrows(MessageException.class, () -> {
            User userRemitente = new User(8, null, null, null, true);
            User userDestinatario = new User(9, null, null, null, true);
            service.enviarMensaje(userRemitente, userDestinatario, null);
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoMostrarChatConUsuario_entoncesListaMensajes() {

        User userRemitente = new User();
        userRemitente.setId(1);

        User userDestinatario = new User();
        userDestinatario.setId(2);

        List<Message> messageList = service.mostrarChatConUsuario(userRemitente, userDestinatario);

        System.out.println("Mensaje Recibido: " + messageList);

        assertThat(messageList.size(), greaterThan(0));

    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoMostrarChatConUsuario_entoncesExcepcion() {
        assertThrows(Exception.class, () -> {
            User userRemitente = new User();
            userRemitente.setId(11);

            User userDestinatario = new User();
            userDestinatario.setId(45);

            List<Message> messageList = service.mostrarChatConUsuario(userRemitente,userDestinatario);

        });
    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoBorrarChatConUsuario_entoncesOK() {

        User userRemitente = new User();
        userRemitente.setId(11);

        User userDestinatario = new User();
        userDestinatario.setId(12);

        boolean deleteMessage = service.borrarChatConUsuario(userRemitente, userDestinatario);

        assertThat(deleteMessage, is(true));
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoBorrarChatConUsuario_entoncesExcepcion() {
        assertThrows(Exception.class, () -> {
            User userRemitente = new User();
            userRemitente.setId(23);

            User userDestinatario = new User();
            userDestinatario.setId(16);

            boolean deleteMessage = service.borrarChatConUsuario(userRemitente, userDestinatario);

        });
    }
}