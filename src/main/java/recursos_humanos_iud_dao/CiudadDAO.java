/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos_humanos_iud_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import recursos_humanos_iud_domain.Ciudad;
import recursos_humanos_iud_util.ConnectionUtil;

/**
 *
 * @author Camilo
 */
public class CiudadDAO {
    private static final String GET_CIUDADES = "SELECT * FROM ciudades";
    private static final String GET_CIUDAD_BY_ID = "SELECT * FROM ciudades WHERE ciudad_id = ?";
    
    public List<Ciudad> obtenerCiudades() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Ciudad> ciudades = new ArrayList<>();
         try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_CIUDADES);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Ciudad ciudad = new Ciudad();
                ciudad.setId(resultSet.getInt("ciudad_id"));
                ciudad.setNombre(resultSet.getString("nombre"));
                ciudades.add(ciudad);
            }
            return ciudades;
        } finally {
            if (connection != null) {
                connection.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (resultSet != null) {
                resultSet.close();
            }
        }
    }
    
    public Ciudad obtenerCiudadPorId(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Ciudad ciudad = null;
        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_CIUDAD_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                ciudad = new Ciudad();
                ciudad.setId(resultSet.getInt("ciudad_id"));
                ciudad.setNombre(resultSet.getString("nombre"));
            }
            return ciudad;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(resultSet != null) {
                resultSet.close();
            }
        }
    }
}
