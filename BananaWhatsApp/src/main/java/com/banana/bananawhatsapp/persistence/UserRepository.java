package com.banana.bananawhatsapp.persistence;

import com.banana.bananawhatsapp.models.User;
import lombok.Setter;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

@Setter
public class UserRepository implements IUserRepository {

    private String db_url = null;

    @Override
    public User crear(User user) throws SQLException {
        String sql = "INSERT INTO usuario values (NULL,?,?,?,?)";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            user.valido();

            stmt.setInt(1, user.isActivo() ? 1 : 0);
            stmt.setString(2, user.getAlta().toString());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getNombre());

            int rows = stmt.executeUpdate();

            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()) {
                user.setId(genKeys.getInt(1));

            } else {
                throw new SQLException("Usuario creado erroneamente!!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }

        return user;
    }

    @Override
    public User actualizar(User user) throws SQLException {
        String sql = "UPDATE usuario set activo=?, email=?, nombre=? WHERE id=?";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            user.valido();

            stmt.setInt(1, user.isActivo() ? 1 : 0);
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getNombre());
            stmt.setInt(4, user.getId());


            int rows = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return user;
    }

    @Override
    public boolean borrar(User user) throws SQLException {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db_url);
            conn.setAutoCommit(false);

            String sql ="DELETE FROM mensaje WHERE from_user = ? OR to_user = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, user.getId());
            stmt.setInt(2, user.getId());

            stmt.close();

            sql = "DELETE FROM usuario WHERE id=?";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, user.getId());

            int rows = stmt.executeUpdate();
            System.out.println(rows);

            if (rows <= 0) {
                throw new SQLException();
            }

            stmt.close();

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

        return true;
    }


    @Override
    public Set<User> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException {

        Set<User> listaDestinatarios = new HashSet<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db_url);

            String sql = "SELECT * FROM usuario WHERE id = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                boolean activo = rs.getBoolean("activo");
                if (!activo) {
                    throw new SQLException("Usuario inactivo");
                }
            } else {
                throw new SQLException("No existe usuario entrada");
            }
            pstm.close();

            sql = "SELECT * FROM usuario WHERE id <> ?";

            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, id);

            rs = pstm.executeQuery();

            int filas = 0;

            while (rs.next() && filas < max) {
                filas += 1;

                listaDestinatarios.add(new User(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getDate("alta").toLocalDate(),
                        rs.getBoolean("activo")));
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return listaDestinatarios;
    }
}
