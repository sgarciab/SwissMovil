package mobile.DC;

import java.sql.SQLException;

import java.util.List;

import mobile.CapaG.CapaG;

import mobile.DTO.Empleado;

import oracle.adfmf.java.beans.ProviderChangeSupport;

public class EmpleadoDC {
    
    private List s_emps = null;
    private CapaG logica=new CapaG();
  
    public EmpleadoDC() {
        super();
    }

   
    
    public Empleado[] getEmpleados() throws SQLException { 
    return logica.getEmpleados();
    }


    public Empleado guardarEmpleados(String nombre, String email) {
        Empleado empleado=  logica.guardarEmpleados(nombre,email);
        providerChangeSupport.fireProviderRefresh("empleados");     
        return empleado;
    }
    
    public Empleado actualizarEmpleado(int id,String nombre, String cedula) {
        Empleado empleado=  logica.actualizarEmpleado(id,nombre,cedula);
        providerChangeSupport.fireProviderRefresh("empleados");     
        return empleado;
    }
    
    public void borrarEmpleado(int id) {
        logica.borrarEmpleado(id);
        providerChangeSupport.fireProviderRefresh("empleados");     
     
    }
 
    
    
    private transient ProviderChangeSupport providerChangeSupport = new ProviderChangeSupport(this);
    
    
    
    
}
