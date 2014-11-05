package test;


import junit.framework.TestCase;

import mobile.Generics.GenericCriteria;

public class TestGenericCriteria extends TestCase {
    public void testSave() {
        GenericCriteria criteria = new GenericCriteria("Hola");
        criteria.addJoinToMainTable("Holaid", "Como", "Comoid");
        criteria.addJoinToExistingTable("Como","Holaid", "Estas", "Comoid");
        criteria.addColumn("*");
        criteria.addColumnFromOT("Como", "*");
        criteria.addEqualsTrue("filaverdadera");
        criteria.addEqualsFalse("filaverdadera");
        criteria.addEqualsIfNotEmpty("igual", "algunosmas");
        criteria.addEqualsIfNotEmptyFromOT("Como","igual", "algunosmas");
        criteria.addLike("likecolumna", "Hola");
        criteria.addValueIn("likecolumna", new String[]{"hola"} );
        
        
        //System.out.println(criteria);
    }
    
    public void testInsert() {
        GenericCriteria criteria = new GenericCriteria("Hola");
        criteria.addInsertValues("string", "valor");
        
        criteria.addInsertValues("numero", 4);
    //    System.out.println(criteria.getInsertQuery());
    }
    
    public void testUpdate() {
        GenericCriteria criteria = new GenericCriteria("EMPLEADOS");
        criteria.addUpdateValue("NOMBRE", "JUAN" );
        criteria.addUpdateValue("EMAIL", "LOPEZ" );
        criteria.addUpdateEqualsCriteria("ID", 132);   
        System.out.println(criteria.getUpdateQuery());
    }
    
    
    public void testDelete() {
        GenericCriteria criteria = new GenericCriteria("Hola");
       criteria.addDeleteEqualsCriteria("esto", "estootro");
        criteria.addDeleteEqualsFalse("field");
        criteria.addDeleteValueIn("alguno", new String[]{"valor1","valor2","valor3"});        
    //    System.out.println(criteria.getDeleteQuery());
    }
    
    
    
   
}
