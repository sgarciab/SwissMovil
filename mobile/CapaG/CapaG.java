package mobile.CapaG;


import database.ConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mobile.DTO.Empleado;

import mobile.Generics.Queryable;

import oracle.adfmf.util.Utility;

public class CapaG extends Queryable {
    public CapaG() {
        super();
    }

    private List s_emps = null;

    public Empleado[] getEmpleados() {
        Connection conn = null;
        List returnValue = new ArrayList();

        try {
            conn = ConnectionFactory.getConnection();

            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT ID, NOMBRE, EMAIL FROM EMPLEADOS;");
            Empleado empleado;
            while (result.next()) {
                empleado = new Empleado();
                empleado.setId(result.getInt("ID"));
                empleado.setNombre(result.getString("NOMBRE"));
                empleado.setEmail(result.getString("EMAIL"));
                returnValue.add(empleado);
                empleado = null;
            }
            result.close();
        } catch (Exception ex) {
            Utility.ApplicationLogger.severe(ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return (Empleado[]) returnValue.toArray(new Empleado[returnValue.size()]);

    }

    public Empleado guardarEmpleados(String nombre, String cedula) {

        Empleado empleado = new Empleado(nombre, cedula);
        Connection conn = null;

        try {
            conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO EMPLEADOS (NOMBRE,EMAIL) VALUES ('"+empleado.getNombre()+"','" + empleado.getEmail() +"');";
            stmt.execute(sql);
            /*   ResultSet result = stmt.executeQuery("SELECT ID, NOMBRE, EMAIL FROM EMPLEADOS ORDER BY ID DESC LIMIT 1; ");
            while (result.next()) {
            empleado.setId(result.getInt("ID"));
            empleado.setNombre(result.getString("NOMBRE"));
            empleado.setEmail(result.getString("EMAIL"));
            }  */
        } catch (Exception ex) {
            Utility.ApplicationLogger.severe(ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return empleado;
    }

    public Empleado actualizarEmpleado(int id, String nombre, String cedula) {

        Empleado empleado = new Empleado(id, nombre, cedula);
        Connection conn = null;

        try {
            conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            String sql =
                "UPDATE EMPLEADOS SET NOMBRE='" + empleado.getNombre() + "',EMAIL='" + empleado.getEmail() +
                "' WHERE ID=" + Integer.toString(empleado.getId()) + ";";
            stmt.executeUpdate(sql);
        } catch (Exception ex) {
            Utility.ApplicationLogger.severe(ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return empleado;
    }
}
