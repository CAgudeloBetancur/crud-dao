/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos_humanos_iud_controller;

import java.sql.SQLException;
import java.util.List;
import recursos_humanos_iud_dao.FuncionarioDAO;
import recursos_humanos_iud_domain.Funcionario;

/**
 *
 * @author Camilo
 */
public class FuncionarioController {
    private FuncionarioDAO funcionarioDao;
    public FuncionarioController() {
        funcionarioDao = new FuncionarioDAO();
    }
    public List<Funcionario> obtenerFuncionarios() throws SQLException {
        return funcionarioDao.obtenerFuncionarios();        
    }
    public boolean crear(Funcionario funcionario) throws SQLException {
        return funcionarioDao.crearFuncionario(funcionario);
    }
    public Funcionario obtenerFuncionarioPorId(int funcionario_id) throws SQLException {
        return funcionarioDao.obtenerFuncionarioPorId(funcionario_id);
    }
    public void actualizarFuncionario(int funcionario_id, Funcionario funcionario) throws SQLException {
        funcionarioDao.actualizarFuncionario(funcionario_id, funcionario);
    }
    public boolean eliminarFuncionario(int funcionario_id) throws SQLException {
        return funcionarioDao.eliminarFuncionario(funcionario_id);
    }
}
