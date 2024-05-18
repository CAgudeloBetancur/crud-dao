/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos_humanos_iud_controller;

import java.sql.SQLException;
import java.util.List;
import recursos_humanos_iud_dao.TipoIdentificacionDAO;
import recursos_humanos_iud_domain.TipoIdentificacion;

/**
 *
 * @author Camilo
 */
public class TipoIdentificacionController {
    private TipoIdentificacionDAO tipoIdentificaconDao;
    public TipoIdentificacionController() {
        tipoIdentificaconDao = new TipoIdentificacionDAO();
    }
    public List<TipoIdentificacion> obtenerTiposIdentificacion() throws SQLException {
        return tipoIdentificaconDao.obtenerTiposIdentificacion();
    }
    
    public TipoIdentificacion obtenerTipoIdentificacionPorId(String codigo) throws SQLException {
        return tipoIdentificaconDao.obtenerTipoIdentificacionPorId(codigo);
    }
}
