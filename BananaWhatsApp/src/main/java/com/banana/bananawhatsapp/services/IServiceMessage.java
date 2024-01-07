package com.banana.bananawhatsapp.services;

import com.banana.bananawhatsapp.exceptions.MessageException;
import com.banana.bananawhatsapp.exceptions.UserException;
import com.banana.bananawhatsapp.models.Message;
import com.banana.bananawhatsapp.models.User;

import java.util.List;

public interface IServiceMessage {

    public Message enviarMensaje(User remitente, User destinatario, String texto) throws UserException, MessageException;

    public List<Message> mostrarChatConUsuario(User remitente, User destinatario) throws UserException, MessageException;

    public boolean borrarChatConUsuario(User remitente, User destinatario) throws UserException, MessageException;

}
