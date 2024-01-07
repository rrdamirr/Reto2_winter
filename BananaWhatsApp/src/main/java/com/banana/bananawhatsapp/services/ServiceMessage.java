package com.banana.bananawhatsapp.services;

import com.banana.bananawhatsapp.exceptions.MessageException;
import com.banana.bananawhatsapp.exceptions.UserException;
import com.banana.bananawhatsapp.models.Message;
import com.banana.bananawhatsapp.models.User;
import com.banana.bananawhatsapp.persistence.IMessageRepository;
import com.banana.bananawhatsapp.persistence.IUserRepository;
import lombok.Setter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
public class ServiceMessage implements IServiceMessage {

    private IMessageRepository repoMensajeria;
    private IUserRepository repoUsuario;


    @Override
    public Message enviarMensaje(User remitente, User destinatario, String texto) throws UserException, MessageException {
        Message nMensa = new Message(null, remitente, destinatario, texto, LocalDate.now());
        try {
            nMensa.valido();
            repoMensajeria.crear(nMensa);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nMensa;
    }

    @Override
    public List<Message> mostrarChatConUsuario(User remitente, User destinatario) throws UserException, MessageException {

        List<Message> mensaFinal = new ArrayList<>();

        try {
            Set<User> listUsers = repoUsuario.obtenerPosiblesDestinatarios(destinatario.getId(),100);
        } catch (SQLException e) {
            throw new UserException(e.getMessage());
        }


        try {
            List<Message> mensaRemi;

            mensaRemi = repoMensajeria.obtener(remitente);

            for (Message iMensa:mensaRemi
                 ) {
                    if (iMensa.getRemitente().getId() == destinatario.getId()
                    || iMensa.getDestinatario().getId() == destinatario.getId())
                    mensaFinal.add(iMensa);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return mensaFinal;
    }

    @Override
    public boolean borrarChatConUsuario(User remitente, User destinatario) throws UserException, MessageException {

        boolean borrarMsg = false;
        try {
            borrarMsg = repoMensajeria.borrarTodos(remitente, destinatario);
        } catch (SQLException e) {
            throw new MessageException(e.getMessage());
        }

        return borrarMsg;
    }
}
