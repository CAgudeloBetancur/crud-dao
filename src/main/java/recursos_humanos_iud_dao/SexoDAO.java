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
import recursos_humanos_iud_domain.Sexo;
import recursos_humanos_iud_util.ConnectionUtil;

/**
 *
 * @author Camilo
 */
public class SexoDAO {
    private static final String GET_SEXOS = "SELECT * FROM sexos";
    private static final String GET_SEXO_BY_ID = "SELECT * FROM sexos WHERE sexo_id = ?";
    
    public List<Sexo> obtenerSexos() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Sexo> sexos = new ArrayList<>();
        try {
           connection = ConnectionUtil.getConnection();
           preparedStatement = connection.prepareStatement(GET_SEXOS);
           resultSet = preparedStatement.executeQuery();
           while(resultSet.next()) {
               Sexo sexo = new Sexo();
               sexo.setId(resultSet.getInt("sexo_id"));
               sexo.setNombre(resultSet.getString("nombre"));
               sexos.add(sexo);
           }
           return sexos;
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
    
    public Sexo obtenerSexoPorId(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Sexo sexo = null;
        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_SEXO_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                sexo = new Sexo();
                sexo.setId(resultSet.getInt("sexo_id"));
                sexo.setNombre(resultSet.getString("nombre"));
            }
            return sexo;
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
