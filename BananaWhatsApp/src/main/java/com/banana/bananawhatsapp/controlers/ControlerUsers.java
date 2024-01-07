package com.banana.bananawhatsapp.controlers;

import com.banana.bananawhatsapp.models.User;
import com.banana.bananawhatsapp.services.IServiceUsers;
import lombok.Setter;

@Setter
public class ControlerUsers {

    private IServiceUsers servicioUsuarios;

    public User alta(User nuevo) {
        try {
            User user = servicioUsuarios.crearUsuario(nuevo);
            System.out.println("Usuario creado: " + nuevo);
            return user;
        } catch (Exception e) {
            System.out.println("Ha habido un error: " + e.getMessage());
            throw e;
        }

    }

    public User actualizar(User user) {
        try {
            servicioUsuarios.actualizarUsuario(user);
            System.out.println("Usuario actualizado: " + user);
            return user;
        } catch (Exception e) {
            System.out.println("Ha habido un error: " + e.getMessage());
            throw e;
        }

    }

    public boolean baja(User user) {
        try {
            boolean checkOk = servicioUsuarios.borrarUsuario(user);
            System.out.println("Usuario borrado: " + user);
            return checkOk;
        } catch (Exception e) {
            System.out.println("Ha habido un error: " + e.getMessage());
            throw e;
        }
    }


}
