package mobile.DTO;

import java.util.Date;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class Empleado {
    private int id;
    private String nombre;
    private String email;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);


    public Empleado() {
        super();
    }

    public Empleado(String nombre, String email) {
         super();
         this.nombre = nombre;
         this.email = email; 
    }
    
    public Empleado(int id, String nombre, String email) {
         super();
         this.id=id;
         this.nombre = nombre;
         this.email = email; 
    }


    public void setId(int id) {
        int oldId = this.id;
        this.id = id;
        propertyChangeSupport.firePropertyChange("id", oldId, id);
    }

    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        String oldNombre = this.nombre;
        this.nombre = nombre;
        propertyChangeSupport.firePropertyChange("nombre", oldNombre, nombre);
    }

    public String getNombre() {
        return nombre;
    }

    public void setEmail(String email) {
        String oldEmail = this.email;
        this.email = email;
        propertyChangeSupport.firePropertyChange("email", oldEmail, email);
    }

    public String getEmail() {
        return email;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
}
