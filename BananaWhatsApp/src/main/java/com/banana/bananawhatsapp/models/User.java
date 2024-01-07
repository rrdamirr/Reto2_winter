package com.banana.bananawhatsapp.models;

import com.banana.bananawhatsapp.exceptions.UserException;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class User {

    private Integer id;
    private String nombre;
    private String email;
    private LocalDate alta;
    private boolean activo;

    private boolean validarNombre() {
        return this.nombre != null && this.nombre.length() >= 3;
    }

    private boolean validarEmail() {
        return this.email != null && this.email.indexOf("@") > 0 && this.email.indexOf(".") > 0;
    }

    private boolean validarAlta() {
        return this.alta != null && this.alta.compareTo(LocalDate.now()) <= 0;
    }

    public boolean valido() throws UserException {
        if (activo &&
                validarNombre() &&
                validarEmail() &&
                validarAlta()) return true;
        else throw new UserException("Usuario no valido");
    }
}
