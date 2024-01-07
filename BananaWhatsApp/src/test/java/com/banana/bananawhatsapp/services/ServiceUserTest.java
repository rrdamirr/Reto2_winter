package com.banana.bananawhatsapp.services;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UserException;
import com.banana.bananawhatsapp.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ServiceUserTest {

    @Autowired
    IServiceUsers serviceUser;

    @Test
    void testBeans() {
        assertThat(serviceUser, notNullValue());
    }

    @Test
    void dadoUnUsuarioValido_cuandoCrearUsuario_entoncesUsuarioValido() {
        User user = new User(null, "New User 11", "drs@drs.com", LocalDate.now(), true);
        serviceUser.crearUsuario(user);
        System.out.println(user);

        assertThat(user.getId(), greaterThan(0));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoCrearUsuario_entoncesExcepcion() {
        assertThrows(UserException.class, () -> {
            User user = new User(null, "Nuevo Cliente 2", "mn.com", LocalDate.now(), true);
            serviceUser.crearUsuario(user);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrarUsuario_entoncesUsuarioValido() {
        User user = new User();
        user.setId(5);
        boolean OK = serviceUser.borrarUsuario(user);

        assertThat(OK, is(true));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarUsuario_entoncesExcepcion() {
        User user = new User();
        user.setId(33);
        assertThrows(UserException.class, () -> {
            boolean OK = serviceUser.borrarUsuario(user);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoActualizarUsuario_entoncesUsuarioValido() {
        User user = new User(11, "Nuevo Cliente 11", "drs@drs.com", LocalDate.now(), true);
        User userUpdate = serviceUser.actualizarUsuario(user);
        System.out.println(user);

        assertThat(user.getEmail(), is(userUpdate.getEmail()));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizarUsuario_entoncesExcepcion() {
        assertThrows(UserException.class, () -> {
            User user = new User(22, "Nuevo Cliente 22", "drs.com", LocalDate.now(), true);
            serviceUser.actualizarUsuario(user);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtenerPosiblesDesinatarios_entoncesUsuarioValidos() {
        Set<User> listUsers = new HashSet<>();
        User userTest = new User();
        userTest.setId(13);
        listUsers = serviceUser.obtenerPosiblesDesinatarios(userTest, 3);

        System.out.println("Lista de usuarios: " + listUsers);

        assertThat(listUsers.size(), greaterThan(0));


    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtenerPosiblesDesinatarios_entoncesExcepcion() {
        assertThrows(UserException.class, () -> {
            User userTest = new User();
            userTest.setId(100);

            Set<User> listUsers = new HashSet<>();
            listUsers = serviceUser.obtenerPosiblesDesinatarios(userTest, 3);
        });

    }
}