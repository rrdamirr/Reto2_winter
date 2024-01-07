package com.banana.bananawhatsapp.models;

import com.banana.bananawhatsapp.exceptions.MessageException;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Message {

    private Integer id;
    private User remitente;
    private User destinatario;
    private String cuerpo;
    private LocalDate fecha;

    private boolean validarFecha() {
        return this.fecha != null && this.fecha.compareTo(LocalDate.now()) <= 0;
    }

    public boolean valido() throws MessageException {
        if (remitente != null && destinatario != null && remitente.getId() > 0 && destinatario.getId() > 0 && cuerpo != null && cuerpo.length() > 10 && validarFecha())
            return true;
        else throw new MessageException("Mensaje no valido");
    }


}
