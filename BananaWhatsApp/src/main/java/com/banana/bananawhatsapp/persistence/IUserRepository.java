package com.banana.bananawhatsapp.persistence;

import com.banana.bananawhatsapp.models.User;

import java.sql.SQLException;
import java.util.Set;

public interface IUserRepository {
    public User crear(User user) throws SQLException;

    public User actualizar(User user) throws SQLException;

    public boolean borrar(User user) throws SQLException;

    public Set<User> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException;

}
