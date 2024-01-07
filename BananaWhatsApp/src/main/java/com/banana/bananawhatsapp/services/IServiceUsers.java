package com.banana.bananawhatsapp.services;

import com.banana.bananawhatsapp.exceptions.UserException;
import com.banana.bananawhatsapp.models.User;

import java.util.Set;

public interface IServiceUsers {
    public User crearUsuario(User user) throws UserException;

    public boolean borrarUsuario(User user) throws UserException;

    public User actualizarUsuario(User user) throws UserException;

    public Set<User> obtenerPosiblesDesinatarios(User user, int max) throws UserException;
}
