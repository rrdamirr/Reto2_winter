package com.banana.bananawhatsapp.controlers;

import com.banana.bananawhatsapp.config.SpringConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ControlerMessageTest {


    @Autowired
    ControlerMessages controlerMessage;

    @Test
    void dadoRemitenteYDestinatarioYTextoValidos_cuandoEnviarMensaje_entoncesOK() {

        boolean messageOK = controlerMessage.enviarMensaje(11, 20, "Mensaje del controler automatico");

        assertTrue(messageOK);
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValidos_cuandoEnviarMensaje_entoncesExcepcion() {

        assertThrows(Exception.class, () -> {
            boolean messageOK = controlerMessage.enviarMensaje(13, 11, "¡Hola Mundo!");
        });

    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoMostrarChat_entoncesOK() {

        boolean messageOK = controlerMessage.mostrarChat(11,12);

        assertTrue(messageOK);

    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoMostrarChat_entoncesExcepcion() {

        assertThrows(Exception.class, () -> {
            boolean messageOK = controlerMessage.mostrarChat(46, 11);
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoEliminarChatConUsuario_entoncesOK() {

        boolean deleteMessage = controlerMessage.eliminarChatConUsuario(15, 16);

        assertThat(deleteMessage, is(true));
    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoEliminarChatConUsuario_entoncesExcepcion() {

        assertThrows(Exception.class, () -> {

            boolean deleteMessage = controlerMessage.eliminarChatConUsuario(13, 16);

        });

    }

}