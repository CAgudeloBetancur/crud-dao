package recursos_humanos_iud_controller;

import java.sql.SQLException;
import java.util.List;
import recursos_humanos_iud_dao.SexoDAO;
import recursos_humanos_iud_domain.Sexo;

public class SexoController {
    private SexoDAO sexoDao;
    public SexoController() {
        sexoDao = new SexoDAO();
    }
    public List<Sexo> obtenerSexos() throws SQLException {
        return sexoDao.obtenerSexos();
    }
    public Sexo obtenerSexoPorId(int id) throws SQLException {
        return sexoDao.obtenerSexoPorId(id);
    }
}
