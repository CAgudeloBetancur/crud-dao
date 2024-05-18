package recursos_humanos_iud_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import recursos_humanos_iud_domain.Funcionario;
import recursos_humanos_iud_util.ConnectionUtil;

public class FuncionarioDAO {
    
    // Consultas
    
    private static final String GET_FUNCIONARIOS = "SELECT * FROM funcionarios";
    private static final String CREATE_FUNCIONARIO = "INSERT INTO funcionarios " 
     + "(tipo_identificacion_codigo, "
     + "numero_identificacion, "
     + "nombres, apellidos, "
     + "estado_civil_id, "
     + "sexo_id, direccion, "
     + "ciudad_id, telefono, "
     + "fecha_nacimiento) "
     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_FUNCIONARIO_BY_ID = "SELECT * FROM funcionarios "
     + "WHERE funcionario_id = ?";
    private static final String UPDATE_FUNCIONARIO_DOCUMENTO_NUEVO = "UPDATE funcionarios"
     + " SET tipo_identificacion_codigo = ?, "
     + "numero_identificacion = ?, "
     + "nombres = ?, "
     + "apellidos = ?, "
     + "estado_civil_id = ?, "
     + "sexo_id = ?, "
     + "direccion = ?, "
     + "ciudad_id = ?, "
     + "telefono = ?, "
     + "fecha_nacimiento = ? "
     + "WHERE funcionario_id = ?";
    private static final String UPDATE_FUNCIONARIO_MISMO_DOCUMENTO = "UPDATE funcionarios"
     + " SET tipo_identificacion_codigo = ?, "
     + "nombres = ?, "
     + "apellidos = ?, "
     + "estado_civil_id = ?, "
     + "sexo_id = ?, "
     + "direccion = ?, "
     + "ciudad_id = ?, "
     + "telefono = ?, "
     + "fecha_nacimiento = ? "
     + "WHERE funcionario_id = ?";
    private static final String DELETE_FUNCIONARIO = "DELETE FROM funcionarios WHERE funcionario_id = ?";
    
    // Métodos

    public List<Funcionario> obtenerFuncionarios() throws SQLException {        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Funcionario> funcionarios = new ArrayList<>();        
        try {            
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_FUNCIONARIOS);
            resultSet = preparedStatement.executeQuery();            
            while(resultSet.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setFuncionario_id( resultSet.getInt("funcionario_id") );
                funcionario.setTipo_identificacion_codigo(resultSet.getString("tipo_identificacion_codigo"));
                funcionario.setNumero_identificacion(resultSet.getString("numero_identificacion"));
                funcionario.setNombres(resultSet.getString("nombres"));
                funcionario.setApellidos(resultSet.getString("apellidos"));
                funcionario.setEstado_civil_id(resultSet.getInt("estado_civil_id"));
                funcionario.setSexo_id(resultSet.getInt("sexo_id"));
                funcionario.setDireccion(resultSet.getString("direccion"));
                funcionario.setCiudad_id(resultSet.getInt("ciudad_id"));
                funcionario.setTelefono(resultSet.getString("telefono"));
                funcionario.setFecha_nacimiento(resultSet.getDate("fecha_nacimiento"));
                funcionarios.add(funcionario);
            }            
            return funcionarios;            
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
    
    public boolean crearFuncionario(Funcionario funcionario) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatementCrear = null, preparedStatementConsultarDocumentoBd = null;
        ResultSet resultFuncionarioBd = null;
        try {
            connection = ConnectionUtil.getConnection();
            preparedStatementConsultarDocumentoBd = connection.prepareStatement(
                "SELECT * FROM funcionarios WHERE numero_identificacion = ?"
            );
            preparedStatementConsultarDocumentoBd.setString(1, funcionario.getNumero_identificacion());
            resultFuncionarioBd = preparedStatementConsultarDocumentoBd.executeQuery();
            if(!resultFuncionarioBd.next()) {
                System.out.println("Hola");
                preparedStatementCrear = connection.prepareCall(CREATE_FUNCIONARIO);
                preparedStatementCrear.setString(1, funcionario.getTipo_identificacion_codigo());
                preparedStatementCrear.setString(2, funcionario.getNumero_identificacion());
                preparedStatementCrear.setString(3, funcionario.getNombres());
                preparedStatementCrear.setString(4, funcionario.getApellidos());
                preparedStatementCrear.setInt(5, funcionario.getEstado_civil_id());
                preparedStatementCrear.setInt(6, funcionario.getSexo_id());
                preparedStatementCrear.setString(7, funcionario.getDireccion());
                preparedStatementCrear.setInt(8, funcionario.getCiudad_id());
                preparedStatementCrear.setString(9, funcionario.getTelefono());
                preparedStatementCrear.setDate(10, funcionario.getFecha_nacimiento());
                preparedStatementCrear.executeUpdate();
                return true;
            } else {
                System.out.println("Ya existe ese documento");
                return false;
            }
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(preparedStatementCrear != null) {
                preparedStatementCrear.close();
            }
            if(preparedStatementConsultarDocumentoBd != null) {
                preparedStatementConsultarDocumentoBd.close();
            }
            
        }
    }
    
    public Funcionario obtenerFuncionarioPorId(int funcionario_id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Funcionario funcionario = null;
        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_FUNCIONARIO_BY_ID);
            preparedStatement.setInt(1, funcionario_id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                funcionario = new Funcionario();
                funcionario.setFuncionario_id( resultSet.getInt("funcionario_id") );
                funcionario.setTipo_identificacion_codigo(resultSet.getString("tipo_identificacion_codigo"));
                funcionario.setNumero_identificacion(resultSet.getString("numero_identificacion"));
                funcionario.setNombres(resultSet.getString("nombres"));
                funcionario.setApellidos(resultSet.getString("apellidos"));
                funcionario.setEstado_civil_id(resultSet.getInt("estado_civil_id"));
                funcionario.setSexo_id(resultSet.getInt("sexo_id"));
                funcionario.setDireccion(resultSet.getString("direccion"));
                funcionario.setCiudad_id(resultSet.getInt("ciudad_id"));
                funcionario.setTelefono(resultSet.getString("telefono"));
                funcionario.setFecha_nacimiento(resultSet.getDate("fecha_nacimiento"));
            }
            return funcionario;
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
    
    public void actualizarFuncionario(int funcionario_id, Funcionario funcionario) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Funcionario funcionarioBd = obtenerFuncionarioPorId(funcionario_id);            
            connection = ConnectionUtil.getConnection();
            if(!funcionarioBd.getNumero_identificacion().equals(funcionario.getNumero_identificacion())) {
                // si se actualiza el documento
                preparedStatement = connection.prepareStatement(UPDATE_FUNCIONARIO_DOCUMENTO_NUEVO);
                preparedStatement.setString(1, funcionario.getTipo_identificacion_codigo());
                preparedStatement.setString(2, funcionario.getNumero_identificacion());               
                preparedStatement.setString(3, funcionario.getNombres());
                preparedStatement.setString(4, funcionario.getApellidos());
                preparedStatement.setInt(5, funcionario.getEstado_civil_id());
                preparedStatement.setInt(6, funcionario.getSexo_id());
                preparedStatement.setString(7, funcionario.getDireccion());
                preparedStatement.setInt(8, funcionario.getCiudad_id());
                preparedStatement.setString(9, funcionario.getTelefono());
                preparedStatement.setDate(10, funcionario.getFecha_nacimiento());
                preparedStatement.setInt(11, funcionario_id);
            }else {
                // si no se actualiza el documento
                preparedStatement = connection.prepareStatement(UPDATE_FUNCIONARIO_MISMO_DOCUMENTO);
                preparedStatement.setString(1, funcionario.getTipo_identificacion_codigo());               
                preparedStatement.setString(2, funcionario.getNombres());
                preparedStatement.setString(3, funcionario.getApellidos());
                preparedStatement.setInt(4, funcionario.getEstado_civil_id());
                preparedStatement.setInt(5, funcionario.getSexo_id());
                preparedStatement.setString(6, funcionario.getDireccion());
                preparedStatement.setInt(7, funcionario.getCiudad_id());
                preparedStatement.setString(8, funcionario.getTelefono());
                preparedStatement.setDate(9, funcionario.getFecha_nacimiento());
                preparedStatement.setInt(10, funcionario_id);
            }
            preparedStatement.executeUpdate();
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }
    
    public boolean eliminarFuncionario(int funcionario_id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_FUNCIONARIO);
            preparedStatement.setInt(1, funcionario_id);
            int eliminado = preparedStatement.executeUpdate();
            return eliminado > 0;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }
    
}
