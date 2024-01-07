package com.banana.bananawhatsapp.persistence;

import com.banana.bananawhatsapp.models.Message;
import com.banana.bananawhatsapp.models.User;

import java.sql.SQLException;
import java.util.List;

public interface IMessageRepository {
    public Message crear(Message message) throws SQLException;

    public List<Message> obtener(User user) throws SQLException;

    public boolean borrarTodos(User remitente, User destinatario) throws SQLException;
}
