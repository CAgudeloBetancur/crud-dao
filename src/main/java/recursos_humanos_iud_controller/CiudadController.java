/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos_humanos_iud_controller;

import java.sql.SQLException;
import java.util.List;
import recursos_humanos_iud_dao.CiudadDAO;
import recursos_humanos_iud_domain.Ciudad;

/**
 *
 * @author Camilo
 */
public class CiudadController {
    private CiudadDAO ciudadDao;
    public CiudadController() {
        ciudadDao = new CiudadDAO();
    }
    public List<Ciudad> obtenerCiudades() throws SQLException{
        return ciudadDao.obtenerCiudades();
    }
    public Ciudad obtenerCiudadPorId(int id) throws SQLException {
        return ciudadDao.obtenerCiudadPorId(id);
    }
}
