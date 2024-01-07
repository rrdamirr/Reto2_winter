package com.banana.bananawhatsapp.persistence;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UserException;
import com.banana.bananawhatsapp.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class UserRepositoryTest {
    @Autowired
    IUserRepository repo;

    @Test
    void testBeans() {
        assertThat(repo, notNullValue());
    }

    @Test
    void dadoUnUsuarioValido_cuandoCrear_entoncesUsuarioValido() throws SQLException {
        User user = new User(null, "New User 3", "drs@drs.com", LocalDate.now(), true);
        repo.crear(user);
        System.out.println(user);

        assertThat(user.getId(), greaterThan(0));

    }

    @Test
    void dadoUnUsuarioNOValido_cuandoCrear_entoncesExcepcion() {
        assertThrows(UserException.class, () -> {
            User user = new User(null, "New User 1", "drs.com", LocalDate.now(), true);
            repo.crear(user);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoActualizar_entoncesUsuarioValido() throws SQLException {
        User user = new User(11, "New User 15", "drs@drs.com", LocalDate.now(), true);
        User userUpdate = repo.actualizar(user);
        System.out.println(user);

        assertThat(user.getEmail(), is(userUpdate.getEmail()));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizar_entoncesExcepcion() {
        assertThrows(UserException.class, () -> {
            User user = new User(19, "New User 19", "drs.com", LocalDate.now(), true);
            repo.actualizar(user);
        });
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrar_entoncesOK() throws SQLException {
        User user = new User(16, null, null, null, false);
        boolean OK = repo.borrar(user);
        assertThat(OK, is(true));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrar_entoncesExcepcion() throws SQLException {
        User user = new User(9, null, null, null, false);
        assertThrows(SQLException.class, () -> {
            boolean OK = repo.borrar(user);
        });

    }

    @Test
    void dadoUnUsuarioValido_cuandoObtenerPosiblesDestinatarios_entoncesLista() {
        Set<User> listUser = new HashSet<>();

        try {
            listUser = repo.obtenerPosiblesDestinatarios(11,3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Destinatario posibles: " + listUser);
        assertThat(listUser.size(), greaterThan(0));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtenerPosiblesDestinatarios_entoncesExcepcion() {
        assertThrows(SQLException.class, () -> {
            Set<User> listUser = new HashSet<>();
            listUser = repo.obtenerPosiblesDestinatarios(55,5);
        });
    }
}