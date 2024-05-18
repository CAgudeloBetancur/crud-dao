package recursos_humanos_iud_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import recursos_humanos_iud_domain.EstadoCivil;
import recursos_humanos_iud_util.ConnectionUtil;

public class EstadoCivilDAO {
    
    private static final String GET_ESTADOS_CIVILES = "SELECT * FROM estados_civiles";
    private static final String GET_ESTADO_CIVIL_BY_ID = "SELECT * FROM estados_civiles WHERE estado_civil_id = ?";
    
    public List<EstadoCivil> obtenerEstadosCiviles() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<EstadoCivil> estadosCiviles = new ArrayList<>();
         try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_ESTADOS_CIVILES);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                EstadoCivil estadoCivil = new EstadoCivil();
                estadoCivil.setId(resultSet.getInt("estado_civil_id"));
                estadoCivil.setNombre(resultSet.getString("nombre"));
                estadosCiviles.add(estadoCivil);
            }
            return estadosCiviles;
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
    
    public EstadoCivil obtenerEstadoCivilPorId(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        EstadoCivil estadoCivil = null;
        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_ESTADO_CIVIL_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                estadoCivil = new EstadoCivil();
                estadoCivil.setId(resultSet.getInt("estado_civil_id"));
                estadoCivil.setNombre(resultSet.getString("nombre"));
            }
            return estadoCivil;
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
