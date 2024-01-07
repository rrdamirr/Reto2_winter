package com.banana.bananawhatsapp.persistence;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.models.Message;
import com.banana.bananawhatsapp.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class MessageRepositoryTest {

    @Autowired
    IMessageRepository repo;

    @Test
    void testBeans() {
        assertThat(repo, notNullValue());
    }

    @Test
    void dadoUnMensajeValido_cuandoCrear_entoncesMensajeValido() throws SQLException {

        User userRemitente = new User(12, null, null, null, true);
        User userDestinatario = new User(14, null, null, null, true);
        Message message = new Message(null, userRemitente, userDestinatario, "Mensaje de Bienvenida", LocalDate.now());

        repo.crear(message);

        System.out.println(message);

        assertThat(message.getId(), greaterThan(0));
    }

    @Test
    void dadoUnMensajeNOValido_cuandoCrear_entoncesExcepcion() {
        assertThrows(Exception.class, () -> {
            User userRemitente = new User(9, null, null, null, true);
            User userDestinatario = new User(11, null, null, null, true);
            Message message = new Message(null, userRemitente, userDestinatario, "¡Hola Mund@!", LocalDate.now());
            repo.crear(message);
        });

    }

    @Test
    void dadoUnUsuarioValido_cuandoObtener_entoncesListaMensajes() throws SQLException {
        User userTest = new User();
        userTest.setId(11);

        List<Message> messageList = repo.obtener(userTest);

        System.out.println("Mensaje recibido de vuelta: " + messageList);

        assertThat(messageList.size(), greaterThan(0));

    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtener_entoncesExcepcion() {
        assertThrows(Exception.class, () -> {
            User userTest = new User();
            userTest.setId(26);

            List<Message> messageList = repo.obtener(userTest);
        });


    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrarTodos_entoncesOK() throws SQLException {
        User userRemitente = new User();
        userRemitente.setId(17);

        User userDestinatario = new User();
        userDestinatario.setId(18);

        boolean deleteMessage = repo.borrarTodos(userRemitente, userDestinatario);

        assertThat(deleteMessage, is(true));


    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarTodos_entoncesExcepcion() throws SQLException {
        assertThrows(Exception.class, () -> {
            User userRemitente = new User();
            userRemitente.setId(21);

            User userDestinatario = new User();
            userDestinatario.setId(23);

            boolean deleteMessage = repo.borrarTodos(userRemitente, userDestinatario);

        });

    }

}