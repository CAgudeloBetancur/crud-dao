package recursos_humanos_iud_presentacion;

import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import recursos_humanos_iud_dao.FuncionarioDAO;
import recursos_humanos_iud_domain.Funcionario;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import recursos_humanos_iud_controller.CiudadController;
import recursos_humanos_iud_controller.EstadoCivilController;
import recursos_humanos_iud_controller.FuncionarioController;
import recursos_humanos_iud_controller.SexoController;
import recursos_humanos_iud_controller.TipoIdentificacionController;
import recursos_humanos_iud_dao.TipoIdentificacionDAO;
import recursos_humanos_iud_domain.Ciudad;
import recursos_humanos_iud_domain.EstadoCivil;
import recursos_humanos_iud_domain.Sexo;
import recursos_humanos_iud_domain.TipoIdentificacion;
import recursos_humanos_iud_util.ConnectionUtil;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Camilo
 */
public class Main extends javax.swing.JFrame {
    
    private final TipoIdentificacionController tipoIdentificacionController;
    private final FuncionarioController funcionarioController;
    private final EstadoCivilController estadoCivilController;
    private final SexoController sexoController;
    private final CiudadController ciudadController;
    
    private static final String DEFAULT_SELECT_OPTION = "-- Selecciona --";
    private static final String[] COLUMNS = {
        "Id", 
        "Nombre(s)", 
        "Apellidos", 
        "Tipo Identificacion", 
        "Documento", 
        "Estado Civil",
        "Sexo",
        "Direccion",
        "Ciudad",
        "Telefono",
        "Fecha Nacimiento"
    };
    
    private List<TipoIdentificacion> tiposIdentificacion;
    private List<EstadoCivil> estadosCiviles;
    private List<Sexo> sexos;
    private List<Ciudad> ciudades;
    
    private int operacion;
    private Funcionario currentFuncionarioValues;
    
    private void listarFuncionarios() {
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        for(String column : COLUMNS) {
            defaultTableModel.addColumn(column);
        }
        tbl_funcionarios.setModel(defaultTableModel);
        try {
            List<Funcionario> funcionarios = funcionarioController.obtenerFuncionarios();
            if(funcionarios.isEmpty()) {
                return;
            }
            defaultTableModel.setRowCount(funcionarios.size());
            int row = 0;
            for (Funcionario funcionario : funcionarios) {
                defaultTableModel.setValueAt(funcionario.getFuncionario_id(), row, 0);
                defaultTableModel.setValueAt(funcionario.getNombres(), row, 1);
                defaultTableModel.setValueAt(funcionario.getApellidos(), row, 2);
                var tipoIdentificacion = tipoIdentificacionController
                    .obtenerTipoIdentificacionPorId(funcionario.getTipo_identificacion_codigo());
                defaultTableModel.setValueAt(tipoIdentificacion.getNombre(), row, 3);
                defaultTableModel.setValueAt(funcionario.getNumero_identificacion(), row, 4);
                var estado_civil = estadoCivilController
                    .obtenerEstadoCivilPorId(funcionario.getEstado_civil_id());
                defaultTableModel.setValueAt(estado_civil.getNombre(), row, 5);
                var sexo = sexoController
                    .obtenerSexoPorId(funcionario.getSexo_id());
                defaultTableModel.setValueAt(sexo.getNombre(), row, 6);
                defaultTableModel.setValueAt(funcionario.getDireccion(), row, 7);
                var ciudad = ciudadController
                    .obtenerCiudadPorId(funcionario.getCiudad_id());
                defaultTableModel.setValueAt(ciudad.getNombre(), row, 8);
                defaultTableModel.setValueAt(funcionario.getTelefono(), row, 9);
                defaultTableModel.setValueAt(funcionario.getFecha_nacimiento(), row, 10);
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void crearFuncionario(FuncionarioDAO funcionarioDao) throws ParseException {
        Funcionario funcionario = new Funcionario();
        funcionario.setTipo_identificacion_codigo("CC");
        funcionario.setNumero_identificacion("2342134234");
        funcionario.setNombres("Ruperto Brocardinio");
        funcionario.setApellidos("Quinchia Yagari");
        funcionario.setEstado_civil_id(2);
        funcionario.setSexo_id(1);
        funcionario.setDireccion("calle 1 # 33 - 2");
        funcionario.setCiudad_id(2);
        funcionario.setTelefono("3423423434");
        String fechaTexto = "2024-02-22";
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = formato.parse(fechaTexto);
        java.sql.Date fechaDesdeTexto = new java.sql.Date(utilDate.getTime());
        funcionario.setFecha_nacimiento(fechaDesdeTexto);
        
        try {
            funcionarioDao.crearFuncionario(funcionario);
        } catch (Exception e) {
            System.out.println("Errorsito: " + e);
        }
    }
    
    public static void actualizarFuncionario(FuncionarioDAO funcionarioDao) throws ParseException {
        Funcionario funcionario = new Funcionario();
        funcionario.setTipo_identificacion_codigo("TI");
        funcionario.setNumero_identificacion("2342134237");
        funcionario.setNombres("Martin");
        funcionario.setApellidos("Estupinian Carabao");
        funcionario.setEstado_civil_id(2);
        funcionario.setSexo_id(1);
        funcionario.setDireccion("calle 55 # 22 - 2");
        funcionario.setCiudad_id(2);
        funcionario.setTelefono("3423423434");
        String fechaTexto = "2024-02-23";
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = formato.parse(fechaTexto);
        java.sql.Date fechaDesdeTexto = new java.sql.Date(utilDate.getTime());
        funcionario.setFecha_nacimiento(fechaDesdeTexto);
        try {
            funcionarioDao.actualizarFuncionario(3, funcionario);
        } catch (Exception e) {
            System.out.println("Errorsito: " + e);
        }
    }
    
    private <T> void jComboBoxTextColorSetter(JComboBox<T> combo) { // Para cambiar el color del texto cuando el combobox esté deshabilitado
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (!isSelected) {
                    component.setForeground(Color.DARK_GRAY); // Cambia el color del texto deshabilitado
                }
                return component;
            }
        });
    }
    
    private void listarTiposIdentificacion() {
        jComboBoxTextColorSetter(cbx_tiposIdentificacion);
        cbx_tiposIdentificacion.removeAllItems();
        var defaultOption = new TipoIdentificacion();
        defaultOption.setCodigo("");
        defaultOption.setNombre(DEFAULT_SELECT_OPTION);
        cbx_tiposIdentificacion.addItem(defaultOption);
        try {
            tiposIdentificacion = tipoIdentificacionController.obtenerTiposIdentificacion();
            for(TipoIdentificacion tipo : tiposIdentificacion) {
                cbx_tiposIdentificacion.addItem(tipo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void listarEstadosCiviles() {
        jComboBoxTextColorSetter(cbx_estadoCivil);
        cbx_estadoCivil.removeAllItems();
        var defaultOption = new EstadoCivil();
        defaultOption.setId(-1);
        defaultOption.setNombre(DEFAULT_SELECT_OPTION);
        cbx_estadoCivil.addItem(defaultOption);
        try {
            estadosCiviles = estadoCivilController.obtenerEstadosCiviles();
            for(EstadoCivil estadoCivil : estadosCiviles) {
                cbx_estadoCivil.addItem(estadoCivil);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void listarSexos() {
        jComboBoxTextColorSetter(cbx_sexo);
        cbx_sexo.removeAllItems();
        var defaultOption = new Sexo();
        defaultOption.setId(-1);
        defaultOption.setNombre(DEFAULT_SELECT_OPTION);
        cbx_sexo.addItem(defaultOption);
        try {
            sexos = sexoController.obtenerSexos();
            for(Sexo sexo : sexos) {
                cbx_sexo.addItem(sexo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void listarCiudades() {
        jComboBoxTextColorSetter(cbx_ciudad);
        cbx_ciudad.removeAllItems();
        var defaultOption = new Ciudad();
        defaultOption.setId(-1);
        defaultOption.setNombre(DEFAULT_SELECT_OPTION);
        cbx_ciudad.addItem(defaultOption);
        try {
            ciudades = ciudadController.obtenerCiudades();
            for(Ciudad ciudad : ciudades) {
                cbx_ciudad.addItem(ciudad);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Main() {
        
        initComponents();
        
        pnl_campos.setVisible(false);
        btn_enviar.setVisible(false);
        
        currentFuncionarioValues = new Funcionario();
        
        operacion = 0;
        btn_editar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        
        txt_funcionarioId.setEditable(false);
        tbl_funcionarios.setDefaultEditor(Object.class, null);
        
        tipoIdentificacionController = new TipoIdentificacionController();
        estadoCivilController = new EstadoCivilController();
        funcionarioController = new FuncionarioController();
        sexoController = new SexoController();
        ciudadController = new CiudadController();
        
        listarTiposIdentificacion();
        listarFuncionarios();
        listarEstadosCiviles();
        listarSexos();
        listarCiudades();
    }

    private void limpiarCampos() {
        txt_funcionarioId.setText("");
        txt_nombres.setText("");
        txt_apellidos.setText("");
        listarTiposIdentificacion();
        txt_numeroDocumento.setText("");
        date_fechaNacimiento.setDate(null);
        listarSexos();
        listarEstadosCiviles();
        txt_direccion.setText("");
        txt_telefono.setText("");
        listarCiudades();
    }
    
    private void habilitarCampos(boolean opcion) {
        txt_funcionarioId.setEnabled(opcion);
        txt_funcionarioId.setDisabledTextColor(Color.DARK_GRAY);
        txt_nombres.setEnabled(opcion);
        txt_nombres.setDisabledTextColor(Color.DARK_GRAY);
        txt_apellidos.setEnabled(opcion);
        txt_apellidos.setDisabledTextColor(Color.DARK_GRAY);
        cbx_tiposIdentificacion.setEnabled(opcion);
        txt_numeroDocumento.setEnabled(opcion);
        txt_numeroDocumento.setDisabledTextColor(Color.DARK_GRAY);
        date_fechaNacimiento.setEnabled(opcion);
        
        cbx_sexo.setEnabled(opcion);
        cbx_estadoCivil.setEnabled(opcion);
        txt_direccion.setEnabled(opcion);
        txt_direccion.setDisabledTextColor(Color.DARK_GRAY);
        txt_telefono.setEnabled(opcion);
        txt_telefono.setDisabledTextColor(Color.DARK_GRAY);
        cbx_ciudad.setEnabled(opcion);
        
    }
    
    private boolean camposDiligenciados() {
        return (
            !txt_nombres.getText().equals("") &&
            !txt_apellidos.getText().equals("") &&
            !((TipoIdentificacion)cbx_tiposIdentificacion.getSelectedItem()).getNombre().equals(DEFAULT_SELECT_OPTION) &&
            !txt_numeroDocumento.getText().equals("") &&
            ((EstadoCivil)cbx_estadoCivil.getSelectedItem()).getId() > 0 && 
            ((Sexo)cbx_sexo.getSelectedItem()).getId() > 0 && 
            !txt_direccion.getText().equals("") && 
            ((Ciudad)cbx_ciudad.getSelectedItem()).getId() > 0 &&
            !txt_telefono.getText().equals("") &&
            (!date_fechaNacimiento.getDate().equals(null) || !date_fechaNacimiento.getDate().equals(""))
        );
    }
    
    private void setCurrentFuncionarioValues (ResultSet result) throws SQLException {
        currentFuncionarioValues.setNombres(result.getString("nombres"));
        currentFuncionarioValues.setApellidos(result.getString("apellidos"));
        currentFuncionarioValues.setTipo_identificacion_codigo(result.getString("tipo_identificacion_codigo"));
        currentFuncionarioValues.setNumero_identificacion(result.getString("numero_identificacion"));
        currentFuncionarioValues.setEstado_civil_id(result.getInt("estado_civil_id"));
        currentFuncionarioValues.setSexo_id(result.getInt("sexo_id"));
        currentFuncionarioValues.setDireccion(result.getString("direccion"));
        currentFuncionarioValues.setCiudad_id(result.getInt("ciudad_id"));
        currentFuncionarioValues.setTelefono(result.getString("telefono"));
        currentFuncionarioValues.setFecha_nacimiento(new java.sql.Date(result.getDate("fecha_nacimiento").getTime()));
    }
    
    private void cambiarAVistaDetalle(Funcionario funcionario) {
        habilitarCampos(false);
        currentFuncionarioValues = funcionario;
        btn_crear.setEnabled(true);
        btn_editar.setEnabled(true);
        btn_eliminar.setEnabled(true);
        btn_enviar.setEnabled(false);
        listarFuncionarios();
        for (int row = 0; row < tbl_funcionarios.getRowCount(); row++) {
            String identificacion = tbl_funcionarios.getValueAt(row, 4).toString();
            if(identificacion.equals(funcionario.getNumero_identificacion())) {
                tbl_funcionarios.setRowSelectionInterval(row, row);
                lbl_titulo.setText("Detalle Registro ( " + tbl_funcionarios.getValueAt(row, 0)  + " )");
                txt_funcionarioId.setText(tbl_funcionarios.getValueAt(row, 0).toString());
            }
        }
        operacion = 1;
    }
    
    /** 
     * 
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btn_crear = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_editar = new javax.swing.JButton();
        btn_enviar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_funcionarios = new javax.swing.JTable();
        pnl_campos = new javax.swing.JPanel();
        txt_funcionarioId = new javax.swing.JTextField();
        txt_nombres = new javax.swing.JTextField();
        txt_apellidos = new javax.swing.JTextField();
        cbx_estadoCivil = new javax.swing.JComboBox<>();
        cbx_sexo = new javax.swing.JComboBox<>();
        txt_direccion = new javax.swing.JTextField();
        cbx_ciudad = new javax.swing.JComboBox<>();
        txt_telefono = new javax.swing.JTextField();
        date_fechaNacimiento = new com.toedter.calendar.JDateChooser();
        txt_numeroDocumento = new javax.swing.JTextField();
        lbl_funcionarioId = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbx_tiposIdentificacion = new javax.swing.JComboBox<TipoIdentificacion>();
        jPanel3 = new javax.swing.JPanel();
        lbl_titulo = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_crear.setText("Crear Registro");
        btn_crear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_crearMouseClicked(evt);
            }
        });
        jPanel2.add(btn_crear, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        btn_eliminar.setText("Eliminar Registro");
        btn_eliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_eliminarMouseClicked(evt);
            }
        });
        jPanel2.add(btn_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 10, -1, -1));

        btn_editar.setText("Editar Registro");
        btn_editar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_editarMouseClicked(evt);
            }
        });
        jPanel2.add(btn_editar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, -1, -1));

        btn_enviar.setText("Confirmar");
        btn_enviar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_enviarMouseClicked(evt);
            }
        });
        jPanel2.add(btn_enviar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 122, 30));

        tbl_funcionarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbl_funcionarios.setRowHeight(30);
        tbl_funcionarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_funcionariosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_funcionarios);

        pnl_campos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pnl_campos.add(txt_funcionarioId, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 25, 223, -1));
        pnl_campos.add(txt_nombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 59, 223, -1));
        pnl_campos.add(txt_apellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 93, 223, -1));

        cbx_estadoCivil.setModel(new javax.swing.DefaultComboBoxModel<>(new EstadoCivil[] {}));
        pnl_campos.add(cbx_estadoCivil, new org.netbeans.lib.awtextra.AbsoluteConstraints(789, 59, 150, -1));

        cbx_sexo.setModel(new javax.swing.DefaultComboBoxModel<>(new Sexo[] {}));
        pnl_campos.add(cbx_sexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 25, 186, -1));
        pnl_campos.add(txt_direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(778, 93, 407, -1));

        cbx_ciudad.setModel(new javax.swing.DefaultComboBoxModel<>(new Ciudad[] {}));
        pnl_campos.add(cbx_ciudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(1001, 59, 184, -1));
        pnl_campos.add(txt_telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(1009, 25, 176, -1));

        date_fechaNacimiento.setDateFormatString("y-MM-d");
        pnl_campos.add(date_fechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 93, 232, -1));
        pnl_campos.add(txt_numeroDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(459, 59, 218, -1));

        lbl_funcionarioId.setText("Id");
        pnl_campos.add(lbl_funcionarioId, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 28, -1, -1));

        jLabel2.setText("Nombre(s)");
        pnl_campos.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 62, -1, -1));

        jLabel3.setText("Apellidos");
        pnl_campos.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 96, -1, -1));

        jLabel4.setText("Estado Civil");
        pnl_campos.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 62, -1, -1));

        jLabel5.setText("Tipo Documento");
        pnl_campos.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(337, 28, -1, -1));

        jLabel6.setText("Numero Documento");
        pnl_campos.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(337, 62, -1, -1));

        jLabel7.setText("Telefono");
        pnl_campos.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(951, 28, -1, -1));

        jLabel8.setText("Sexo");
        pnl_campos.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 28, -1, -1));

        jLabel9.setText("Fecha Nacimiento");
        pnl_campos.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(337, 93, -1, -1));

        jLabel10.setText("Ciudad");
        pnl_campos.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(951, 62, -1, -1));

        jLabel11.setText("Direccion");
        pnl_campos.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 96, -1, -1));

        cbx_tiposIdentificacion.setModel(new javax.swing.DefaultComboBoxModel<>(new TipoIdentificacion[] {}));
        pnl_campos.add(cbx_tiposIdentificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(438, 25, 239, -1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        pnl_campos.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(564, 18, -1, -1));

        lbl_titulo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1607, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnl_campos, javax.swing.GroupLayout.DEFAULT_SIZE, 1220, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(657, 657, 657))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lbl_titulo, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnl_campos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_funcionariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_funcionariosMouseClicked
        // TODO add your handling code here:
        operacion = 1;
        btn_crear.setEnabled(true);
        btn_enviar.setVisible(false);
        pnl_campos.setVisible(true);
        btn_editar.setVisible(true);
        btn_editar.setEnabled(true);
        btn_eliminar.setEnabled(true);
        habilitarCampos(false);
        txt_funcionarioId.setVisible(true);
        lbl_funcionarioId.setVisible(true);
        lbl_titulo.setVisible(true);
        
        int filaSeleccionada = tbl_funcionarios.getSelectedRow();
        tbl_funcionarios.setRowSelectionInterval(filaSeleccionada, filaSeleccionada);
        lbl_titulo.setText("Detalle Registro ( " +  tbl_funcionarios.getValueAt(filaSeleccionada, 0) + " )");

        final String GET_FUNCIONARIO_BY_ID = "SELECT * FROM funcionarios WHERE funcionario_id = ?;";
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Funcionario funcionario = null;
        
         try {
             
            connection = ConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(GET_FUNCIONARIO_BY_ID);
            preparedStatement.setInt(1, (int)tbl_funcionarios.getValueAt(filaSeleccionada, 0));
            resultSet = preparedStatement.executeQuery();
            
             
            if(resultSet.next()) {
                setCurrentFuncionarioValues(resultSet);
                txt_funcionarioId.setText(String.valueOf(resultSet.getInt("funcionario_id")));
                txt_nombres.setText(resultSet.getString("nombres"));
                txt_apellidos.setText(resultSet.getString("apellidos"));
                for(TipoIdentificacion tipo : tiposIdentificacion) {
                    if(tipo.getCodigo().equals(resultSet.getString("tipo_identificacion_codigo"))) {
                        cbx_tiposIdentificacion.setSelectedItem(tipo);                        
                    }
                }
                txt_numeroDocumento.setText(resultSet.getString("numero_identificacion"));
                for(EstadoCivil estado : estadosCiviles) {
                    if( estado.getId() == resultSet.getInt("estado_civil_id") ) {
                        cbx_estadoCivil.setSelectedItem(estado);
                    }
                }
                for(Sexo sexo : sexos) {
                    if(sexo.getId() == resultSet.getInt("sexo_id")) {
                        cbx_sexo.setSelectedItem(sexo);
                    }
                }
                txt_direccion.setText(resultSet.getString("direccion"));
                for(Ciudad ciudad : ciudades) {
                    if(ciudad.getId() == resultSet.getInt("ciudad_id")) {
                        cbx_ciudad.setSelectedItem(ciudad);
                    }
                }
                txt_telefono.setText(resultSet.getString("telefono"));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                
                Date fechaConvertida  = dateFormat.parse(resultSet.getDate("fecha_nacimiento").toString());
                date_fechaNacimiento.setDate(fechaConvertida);
            } else {
                JOptionPane.showMessageDialog(
                    null, 
                    "No se encuentra Funcionario con el id " + tbl_funcionarios.getValueAt(filaSeleccionada, 0).toString(), 
                    "Entendido", 
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
            
            if(connection != null) {
                connection.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(resultSet != null) {
                resultSet.close();
            }
            
        }catch(Exception ex) {
             ex.printStackTrace();
        }
    }//GEN-LAST:event_tbl_funcionariosMouseClicked

    private void btn_crearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_crearMouseClicked
        // TODO add your handling code here:
        lbl_titulo.setText("Crear Registro");
        lbl_titulo.setVisible(true);
        pnl_campos.setVisible(true);
        habilitarCampos(true);
        operacion = 2;
        btn_crear.setEnabled(false);
        tbl_funcionarios.clearSelection();
        btn_editar.setVisible(true);
        btn_editar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        txt_funcionarioId.setVisible(false);
        lbl_funcionarioId.setVisible(false);
        txt_nombres.requestFocus();
        btn_enviar.setVisible(true);
        btn_enviar.setEnabled(true);
        limpiarCampos();        
    }//GEN-LAST:event_btn_crearMouseClicked

    private void btn_editarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_editarMouseClicked
        // TODO add your handling code here:
        if(operacion == 1) {
            btn_enviar.setVisible(true);
            btn_enviar.setEnabled(true);
            btn_editar.setEnabled(false);
            btn_crear.setEnabled(true);
            lbl_titulo.setVisible(true);
            lbl_titulo.setText("Editar Registro ( " + txt_funcionarioId.getText() + " )");
            operacion = 3;
            txt_funcionarioId.setVisible(false);
            lbl_funcionarioId.setVisible(false);
            habilitarCampos(true);
        }
    }//GEN-LAST:event_btn_editarMouseClicked

    private void btn_enviarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_enviarMouseClicked
        // TODO add your handling code here:
        
        var funcionario = new Funcionario();
        
        if(camposDiligenciados()) {
            
            funcionario.setNombres(txt_nombres.getText());
            funcionario.setApellidos(txt_apellidos.getText());
            funcionario.setTipo_identificacion_codigo(((TipoIdentificacion)cbx_tiposIdentificacion.getSelectedItem()).getCodigo());
            funcionario.setNumero_identificacion(txt_numeroDocumento.getText());
            funcionario.setEstado_civil_id(((EstadoCivil)cbx_estadoCivil.getSelectedItem()).getId());
            funcionario.setSexo_id(((Sexo)cbx_sexo.getSelectedItem()).getId());
            funcionario.setDireccion(txt_direccion.getText());
            funcionario.setCiudad_id(((Ciudad)cbx_ciudad.getSelectedItem()).getId());
            funcionario.setTelefono(txt_telefono.getText());
            funcionario.setFecha_nacimiento(new java.sql.Date(date_fechaNacimiento.getDate().getTime())); 
            
            if(operacion == 3) {
                if(!funcionario.equals(currentFuncionarioValues)) {
                    try {
                        var funcionarioId_parsed = Integer.parseInt(txt_funcionarioId.getText());
                        funcionarioController.actualizarFuncionario(funcionarioId_parsed, funcionario);
                        JOptionPane.showMessageDialog(
                            null, 
                            "Funcionario actualizado", 
                            "Entendido", 
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        cambiarAVistaDetalle(funcionario);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    JOptionPane.showMessageDialog(
                    null, 
                    "No realizaste cambios al funcionario con el id " + txt_funcionarioId.getText(), 
                    "Entendido", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                }
            }
            
            if(operacion == 2) {
                try {
                    var creado = funcionarioController.crear(funcionario);  
                    if(creado) {
                        JOptionPane.showMessageDialog(
                            null, 
                            "Funcionario creado", 
                            "Entendido", 
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        cambiarAVistaDetalle(funcionario);
                    }else {
                        JOptionPane.showMessageDialog(
                            null, 
                            "El documento " + funcionario.getNumero_identificacion() + " ya está registrado", 
                            "Entendido", 
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }  
            }
            
        } else {
            JOptionPane.showMessageDialog(
                null, 
                "Debes diligenciar todos los campos", 
                "Entendido", 
                JOptionPane.INFORMATION_MESSAGE
            );
        }            
    }//GEN-LAST:event_btn_enviarMouseClicked

    private void btn_eliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_eliminarMouseClicked
        // TODO add your handling code here:
        try {
            int opcion = JOptionPane.showConfirmDialog(
                null, 
                "¿Estás seguro que quieres eliminar a " + txt_nombres.getText() + " " + txt_apellidos.getText() + " ( " + Integer.parseInt(txt_funcionarioId.getText()) + " )", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE
            );
        
            if (opcion == JOptionPane.YES_OPTION) {
                System.out.println("El usuario confirmó que desea salir.");
                // Aquí puedes agregar la lógica para realizar la acción deseada
                var eliminado = funcionarioController.eliminarFuncionario(Integer.parseInt(txt_funcionarioId.getText()));
                if(eliminado) {
                    operacion = 0;
                    pnl_campos.setVisible(false);
                    limpiarCampos();
                    currentFuncionarioValues = new Funcionario();
                    btn_editar.setEnabled(false);
                    btn_eliminar.setEnabled(false); 
                    btn_enviar.setVisible(false);
                    lbl_titulo.setVisible(false);
                    lbl_titulo.setText("");
                    JOptionPane.showMessageDialog(
                        null, 
                        "Funcionario eliminado", 
                        "Entendido", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    listarFuncionarios();
                }else {
                    JOptionPane.showMessageDialog(
                        null, 
                        "No se pudo eliminar el registro con  id " + txt_funcionarioId.getText(), 
                        "Entendido", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_eliminarMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
        
        // Instancia DAO
        FuncionarioDAO funcionarioDao = new FuncionarioDAO();
        TipoIdentificacionDAO tipoIdentificacionDao = new TipoIdentificacionDAO();
        
        try {

            // obtener funcionario            
            // obtenerFuncionarios(funcionarioDao);

            // crear funcionario            
            //crearFuncionario(funcionarioDao);
            
            // obtener funcionario por id            
            //Funcionario funcionario = funcionarioDao.obtenerFuncionarioPorId(2);
            //System.out.println("se encontró esto: " + funcionario);
            
            // editar funcionario
            //actualizarFuncionario(funcionarioDao);
            
            tipoIdentificacionDao.obtenerTiposIdentificacion();
            
        } catch (Exception e) {
            System.out.println("errorsito malparido: " + e);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_crear;
    private javax.swing.JButton btn_editar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_enviar;
    private javax.swing.JComboBox<Ciudad> cbx_ciudad;
    private javax.swing.JComboBox<EstadoCivil> cbx_estadoCivil;
    private javax.swing.JComboBox<Sexo> cbx_sexo;
    private javax.swing.JComboBox<TipoIdentificacion> cbx_tiposIdentificacion;
    private com.toedter.calendar.JDateChooser date_fechaNacimiento;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_funcionarioId;
    private javax.swing.JLabel lbl_titulo;
    private javax.swing.JPanel pnl_campos;
    private javax.swing.JTable tbl_funcionarios;
    private javax.swing.JTextField txt_apellidos;
    private javax.swing.JTextField txt_direccion;
    private javax.swing.JTextField txt_funcionarioId;
    private javax.swing.JTextField txt_nombres;
    private javax.swing.JTextField txt_numeroDocumento;
    private javax.swing.JTextField txt_telefono;
    // End of variables declaration//GEN-END:variables
}
