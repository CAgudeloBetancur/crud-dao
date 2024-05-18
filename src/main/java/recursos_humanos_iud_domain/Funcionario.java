/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos_humanos_iud_domain;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author Camilo
 */
public class Funcionario {
    
    private int funcionario_id;
    private String tipo_identificacion_codigo;
    private String numero_identificacion;
    private String nombres;
    private String apellidos;
    private int estado_civil_id;
    private int sexo_id;
    private String direccion;
    private int ciudad_id;
    private String telefono;
    private Date fecha_nacimiento;

    public int getFuncionario_id() {
        return funcionario_id;
    }

    public void setFuncionario_id(int funcionario_id) {
        this.funcionario_id = funcionario_id;
    }

    public String getTipo_identificacion_codigo() {
        return tipo_identificacion_codigo;
    }

    public void setTipo_identificacion_codigo(String tipo_identificacion_codigo) {
        this.tipo_identificacion_codigo = tipo_identificacion_codigo;
    }

    public String getNumero_identificacion() {
        return numero_identificacion;
    }

    public void setNumero_identificacion(String numero_identificacion) {
        this.numero_identificacion = numero_identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEstado_civil_id() {
        return estado_civil_id;
    }

    public void setEstado_civil_id(int estado_civil_id) {
        this.estado_civil_id = estado_civil_id;
    }

    public int getSexo_id() {
        return sexo_id;
    }

    public void setSexo_id(int sexo_id) {
        this.sexo_id = sexo_id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCiudad_id() {
        return ciudad_id;
    }

    public void setCiudad_id(int ciudad_id) {
        this.ciudad_id = ciudad_id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
    
    @Override
    public String toString() {
        return nombres + " " + apellidos;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Funcionario otroFuncionario = (Funcionario) obj;
        return (
            tipo_identificacion_codigo.equals(otroFuncionario.getTipo_identificacion_codigo()) &&
            numero_identificacion.equals(otroFuncionario.getNumero_identificacion()) &&
            nombres.toLowerCase().equals(otroFuncionario.getNombres().toLowerCase()) &&
            apellidos.toLowerCase().equals(otroFuncionario.getApellidos().toLowerCase()) &&
            estado_civil_id == otroFuncionario.getEstado_civil_id() &&
            sexo_id == otroFuncionario.getSexo_id() &&
            direccion.toLowerCase().equals(otroFuncionario.getDireccion().toLowerCase()) &&
            ciudad_id == otroFuncionario.getCiudad_id() &&
            telefono.equals(otroFuncionario.getTelefono()) &&
            fecha_nacimiento.equals(otroFuncionario.getFecha_nacimiento())
        );
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(
            funcionario_id, 
            tipo_identificacion_codigo,
            numero_identificacion, 
            nombres, 
            apellidos, 
            estado_civil_id,
            sexo_id, 
            direccion, 
            ciudad_id, 
            telefono, 
            fecha_nacimiento
        );
    }
    
}
