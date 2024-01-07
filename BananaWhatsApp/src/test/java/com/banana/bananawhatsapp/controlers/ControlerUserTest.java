package com.banana.bananawhatsapp.controlers;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UserException;
import com.banana.bananawhatsapp.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ControlerUserTest {


    @Autowired
    ControlerUsers controlerUser;

    @Test
    void dadoUsuarioValido_cuandoAlta_entoncesUsuarioValido() {
        User user = new User(null, "Damir R.S.", "Drs@drs.com", LocalDate.now(), true);

        controlerUser.alta(user);

        System.out.println(user);

        assertThat(user.getId(), greaterThan(0));

    }

    @Test
    void dadoUsuarioNOValido_cuandoAlta_entoncesExcepcion() {
        assertThrows(UserException.class, () -> {
            User user = new User(null, "Nuevo Cliente 25", "drs.com", LocalDate.now(), true);
            controlerUser.alta(user);
        });
    }

    @Test
    void dadoUsuarioValido_cuandoActualizar_entoncesUsuarioValido() {
        User user = new User(5, "New User 5", "drs@drs.com", LocalDate.now(), true);

        User userUpdate = controlerUser.actualizar(user);

        System.out.println(user);

        assertThat(user.getEmail(), is(userUpdate.getEmail()));
    }

    @Test
    void dadoUsuarioNOValido_cuandoActualizar_entoncesExcepcion() {

        assertThrows(UserException.class, () -> {
            User user = new User(10, "New User 10", "drs.com", LocalDate.now(), true);
            controlerUser.actualizar(user);
        });
    }

    @Test
    void dadoUsuarioValido_cuandoBaja_entoncesUsuarioValido() {
        User user = new User();

        user.setId(15);

        boolean ok = controlerUser.baja(user);

        assertThat(ok, is(true));
    }

    @Test
    void dadoUsuarioNOValido_cuandoBaja_entoncesExcepcion() {
        User user = new User();
        user.setId(80);
        assertThrows(UserException.class, () -> {
            boolean checkOK = controlerUser.baja(user);
        });
    }
}