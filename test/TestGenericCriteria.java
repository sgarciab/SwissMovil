package test;


import junit.framework.TestCase;

import mobile.Generics.GenericCriteria;

public class TestGenericCriteria extends TestCase {
    public void testSave() {
        GenericCriteria criteria = new GenericCriteria("Empleado");
        criteria.addJoinToMainTable("idEmpresa", "Empresa", "id");
        criteria.addJoinToExistingTable("Empresa", "idCiudad", "Ciudad", "id");
        criteria.addColumn("*");
        criteria.addColumnFromOT("Empresa", "*");
        criteria.addEqualsTrue("Pasaporte");
        criteria.addEqualsFalse("Record_Policial");
        criteria.addEqualsIfNotEmpty("Zona", "Sierra");
        criteria.addEqualsIfNotEmptyFromOT("Empresa", "Zona", "Sierra");
        criteria.addLike("Apellido", "Perez");
        criteria.addValueIn("Lenguaje", new String[] { "Español","Ingles","Portugues" });
        System.out.println(criteria);
    }

    public void testInsert() {
        GenericCriteria criteria = new GenericCriteria("Empleado");
        criteria.addInsertValues("Nombre", "Derph");
        criteria.addInsertValues("Apellido", "Soul");
        criteria.addInsertValues("idEmpresa", 4);
        criteria.addInsertValues("Email", "email@empresa.com.ec");
        System.out.println(criteria.getInsertQuery());
    }

    public void testUpdate() {
        GenericCriteria criteria = new GenericCriteria("Empleado");
        criteria.addUpdateValue("Nombre", "JUAN");
        criteria.addUpdateValue("Apellido", "LOPEZ");
        criteria.addUpdateEqualsCriteria("id", 132);
         System.out.println(criteria.getUpdateQuery());
    }


    public void testDelete() {
        GenericCriteria criteria = new GenericCriteria("Empleado");
        criteria.addDeleteEqualsCriteria("Sueldo", 1000);
        criteria.addDeleteEqualsFalse("Certificado");
        criteria.addDeleteValueIn("Habilidad", new String[] { "PHP", "JAVA", "PYTHON" });
        System.out.println(criteria.getDeleteQuery());
    }


}
