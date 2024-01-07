package com.banana.bananawhatsapp.services;

import com.banana.bananawhatsapp.exceptions.UserException;
import com.banana.bananawhatsapp.models.User;
import com.banana.bananawhatsapp.persistence.IUserRepository;
import lombok.Setter;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Setter
//@Service
public class ServiceUsers implements IServiceUsers {
    //@Autowired
    private IUserRepository repoUsuario;
    @Override
    public User crearUsuario(User user) throws UserException {
        try {
            user.valido();
            repoUsuario.crear(user);
        } catch (SQLException e) {
          e.printStackTrace();
          throw new UserException("Error en la creación: " + e.getMessage());
        }
        return user;
    }

    @Override
    public boolean borrarUsuario(User user) throws UserException {
        try {
            repoUsuario.borrar(user);
        } catch (SQLException e) {
          e.printStackTrace();
          throw new UserException("Error en borrar usuario: " +e.getMessage());
        }
        return true;
    }

    @Override
    public User actualizarUsuario(User user) throws UserException {

        User usuUpdate;

        try {
            user.valido();
            usuUpdate = repoUsuario.actualizar(user);
        } catch (SQLException e) {
            throw new UserException(e.getMessage());
        }

        return usuUpdate;
    }

    @Override
    public Set <User> obtenerPosiblesDesinatarios(User user, int max) throws UserException {

        Set <User> listaUser = new HashSet<>();

        try {
           listaUser = repoUsuario.obtenerPosiblesDestinatarios(user.getId(), max);

        } catch (SQLException e) {
            throw new UserException(e.getMessage());
        }
        return listaUser;
    }
}
