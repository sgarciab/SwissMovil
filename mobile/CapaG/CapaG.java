package mobile.CapaG;


import database.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mobile.DTO.Empleado;

import mobile.Generics.GenericCriteria;
import mobile.Generics.Queryable;

import oracle.adfmf.util.Utility;

public class CapaG extends Queryable {
    public CapaG() {
        super();
    }

    private List s_emps = null;

    public Empleado[] getEmpleados() throws SQLException {
        List returnValue = new ArrayList();
        GenericCriteria criteria = new GenericCriteria("EMPLEADOS");

        ResultSet result = query(criteria);
        Empleado empleado;
        while (result.next()) {
            empleado = new Empleado();
            empleado.setId(result.getInt("ID"));
            empleado.setNombre(result.getString("NOMBRE"));
            empleado.setEmail(result.getString("EMAIL"));
            returnValue.add(empleado);
            empleado = null;
        }
        return (Empleado[]) returnValue.toArray(new Empleado[returnValue.size()]);

    }

    public Empleado guardarEmpleados(String nombre, String cedula) {

        Empleado empleado = new Empleado(nombre, cedula);
        GenericCriteria criteria = new GenericCriteria("EMPLEADOS");
        criteria.addInsertValues("NOMBRE", empleado.getNombre());
        criteria.addInsertValues("EMAIL", empleado.getEmail());
        insert(criteria);
        return empleado;
    }

    public Empleado actualizarEmpleado(int id, String nombre, String cedula) {

        Empleado empleado = new Empleado(id, nombre, cedula);

        GenericCriteria criteria = new GenericCriteria("EMPLEADOS");
        criteria.addUpdateValue("NOMBRE", empleado.getNombre());
        criteria.addUpdateValue("EMAIL", empleado.getEmail());
        criteria.addUpdateEqualsCriteria("ID", empleado.getId());
        update(criteria);


        return empleado;
    }
    
    
    public void borrarEmpleado(int id) {

        GenericCriteria criteria = new GenericCriteria("EMPLEADOS");
        criteria.addDeleteEqualsCriteria("ID", id);
        delete(criteria);

    }
}
