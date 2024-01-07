package com.banana.bananawhatsapp.persistence;

import com.banana.bananawhatsapp.exceptions.UserException;
import com.banana.bananawhatsapp.models.Message;
import com.banana.bananawhatsapp.models.User;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Setter
public class MessageRepository implements IMessageRepository {

    private String db_url = null;

    @Override
    public Message crear(Message message) throws SQLException {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db_url);
            conn.setAutoCommit(false);

                        String sql = "SELECT * FROM usuario WHERE id = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, message.getRemitente().getId());

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                boolean activo = rs.getBoolean("activo");
                if (!activo) {
                    throw new UserException("Usuario remitente inactivo");
                }
            } else {
                throw new UserException("No existe usuario remitente");
            }
            pstm.close();

            sql = "SELECT * FROM usuario WHERE id = ?";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, message.getDestinatario().getId());

            rs = pstm.executeQuery();

            if (rs.next()) {
                boolean activo = rs.getBoolean("Usuario activo");
                if (!activo) {
                    throw new UserException("Usuario destinatario inactivo");
                }
            } else {
                throw new UserException("No existe usuario destinatario");
            }
            pstm.close();

            sql = "INSERT INTO mensaje values (NULL,?,?,?,?)";
            pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, message.getCuerpo());
            pstm.setString(2, message.getFecha().toString());
            pstm.setInt(3, message.getRemitente().getId());
            pstm.setInt(4, message.getDestinatario().getId());

            message.valido();

            int rows = pstm.executeUpdate();
            ResultSet genKeys = pstm.getGeneratedKeys();
            if (genKeys.next()) {
                message.setId(genKeys.getInt(1));
                message.valido();
            } else {
                throw new SQLException("Mensaje se ha creado erroneamente!");
            }

            pstm.close();

            System.out.println("Transaccion exitosa!!");
            conn.commit();

        } catch (Exception e) {
            System.out.println("Transaccion rollback!!");
            conn.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (conn != null) conn.close();
        }

        return message;
    }


    @Override
    public List<Message> obtener(User user) throws SQLException {

        List <Message> listMensa = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db_url);

            String sql = "SELECT * FROM usuario WHERE id = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, user.getId());

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                boolean activo = rs.getBoolean("activo");
                if (!activo) {
                    throw new UserException("Usuario inactivo");
                }
            } else {
                throw new UserException("No existe usuario entrada");
            }
            pstm.close();

            sql = "SELECT * FROM mensaje WHERE from_user = ? OR to_user = ?";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, user.getId());
            pstm.setInt( 2, user.getId());

            rs = pstm.executeQuery();

            while (rs.next()) {
                User usuRemi = new User();
                usuRemi.setId(rs.getInt("from_user"));

                User usuDest = new User();
                usuDest.setId(rs.getInt("to_user"));

                listMensa.add(new Message(rs.getInt("id"), usuRemi, usuDest,
                        rs.getString("cuerpo"), rs.getDate("fecha").toLocalDate()));
            }

            pstm.close();


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ha habido un error en la transacción: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) conn.close();
        }

        return listMensa;

    }

    @Override
    public boolean borrarTodos(User remitente, User destinatario) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db_url);

            String sql = "SELECT * FROM usuario WHERE id = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, remitente.getId());

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                boolean activo = rs.getBoolean("Usuario activo");
                if (!activo) {
                    throw new UserException("Usuario remitente inactivo");
                }
            } else {
                throw new UserException("No existe usuario remitente de entrada");
            }
            pstm.close();

            sql = "SELECT * FROM usuario WHERE id = ?";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, remitente.getId());

            rs = pstm.executeQuery();

            if (rs.next()) {
                boolean activo = rs.getBoolean("Usuario activo");
                if (!activo) {
                    throw new UserException("Usuario destinatario inactivo");
                }
            } else {
                throw new UserException("No existe usuario destinatario de entrada");
            }
            pstm.close();

            sql = "DELETE FROM mensaje WHERE from_user IN (?,?) AND to_user IN (?,?)";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, remitente.getId());
            pstm.setInt( 2, destinatario.getId());
            pstm.setInt(3, remitente.getId());
            pstm.setInt( 4, destinatario.getId());


            //rs = pstm.executeQuery();
            int rows = pstm.executeUpdate();

            if (rows <= 0) {
                throw new SQLException();
            }

            pstm.close();


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ha habido un error: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) conn.close();
        }

        return true;
    }
}
