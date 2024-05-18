/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos_humanos_iud_controller;

import java.sql.SQLException;
import java.util.List;
import recursos_humanos_iud_dao.EstadoCivilDAO;
import recursos_humanos_iud_domain.EstadoCivil;

/**
 *
 * @author Camilo
 */
public class EstadoCivilController {
    private EstadoCivilDAO estadoCivilDao;
    public EstadoCivilController() {
        estadoCivilDao = new EstadoCivilDAO();
    }
    public List<EstadoCivil> obtenerEstadosCiviles() throws SQLException {
        return estadoCivilDao.obtenerEstadosCiviles();
    }
    public EstadoCivil obtenerEstadoCivilPorId(int id) throws SQLException {
        return estadoCivilDao.obtenerEstadoCivilPorId(id);
    }
}
