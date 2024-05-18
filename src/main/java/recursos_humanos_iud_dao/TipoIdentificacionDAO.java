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
import recursos_humanos_iud_domain.TipoIdentificacion;
import recursos_humanos_iud_util.ConnectionUtil;

/**
 *
 * @author Camilo
 */
public class TipoIdentificacionDAO {

    private static final String GET_TIPOS_IDENTIFICACION = "SELECT * FROM tipos_identificacion";
    private static final String GET_TIPO_IDENTIFICACION_BY_ID = "SELECT * FROM tipos_identificacion " 
    + "WHERE codigo = ?";
    
    public List<TipoIdentificacion> obtenerTiposIdentificacion() throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<TipoIdentificacion> tiposIdentificacion = new ArrayList<>();
        
        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_TIPOS_IDENTIFICACION);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                TipoIdentificacion tipoIdentificacion = new TipoIdentificacion();
                tipoIdentificacion.setCodigo(resultSet.getString("codigo"));
                tipoIdentificacion.setNombre(resultSet.getString("nombre"));
                tiposIdentificacion.add(tipoIdentificacion);
            }
            return tiposIdentificacion;
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
    
    public TipoIdentificacion obtenerTipoIdentificacionPorId(String codigo) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        TipoIdentificacion tipoIdentificacion = null;
        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_TIPO_IDENTIFICACION_BY_ID);
            preparedStatement.setString(1, codigo);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                tipoIdentificacion = new TipoIdentificacion();
                tipoIdentificacion.setCodigo(resultSet.getString("codigo"));
                tipoIdentificacion.setNombre(resultSet.getString("nombre"));
            }
            return tipoIdentificacion;
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
