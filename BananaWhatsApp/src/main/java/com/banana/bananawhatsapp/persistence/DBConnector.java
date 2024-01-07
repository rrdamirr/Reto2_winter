package com.banana.bananawhatsapp.persistence;

import com.banana.bananawhatsapp.properties.PropertyValues;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    PropertyValues props = new PropertyValues();

    public void connect() throws SQLException, IOException {
        String db_url = props.getPropValues().getProperty("db_url");
        Connection conn = DriverManager.getConnection(db_url);
        System.out.println("Conexión exitosa!");
        conn.close();
    }

}
