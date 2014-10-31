package mobile.Generics;

import com.truemesh.squiggle.*;

import com.truemesh.squiggle.output.Output;
import com.truemesh.squiggle.output.Outputable;

import com.truemesh.squiggle.output.ToStringer;

import java.util.*;

import mobile.Utils.Util;

import mobile.output.OutputableCriteria;

import mobile.output.ToStringerCriteria;

import oracle.bali.share.nls.StringUtils;

public class GenericCriteria implements OutputableCriteria {

    Table table = null;
    SelectQuery query = null;
    private List otherTables = null;


    public GenericCriteria(String tableName) {
        table = new Table(tableName);
        query = new SelectQuery(table);
        otherTables = new ArrayList();
        fieldsInsert = new ArrayList();
        valuesInsert = new ArrayList();
        fieldsUpdate = new ArrayList();
        valuesUpdate = new ArrayList();
        criteriaUpdate = new ArrayList();
        criteriaDelete = new ArrayList();
    }

    //Inicia-Area de Selects
    public String toString() {
        if (getAllColumns().size() == 0)
            query.addColumn(table, "*");
        return query.toString() + ";";

    }

    public void addColumn(String columnName) {
        query.addColumn(table, columnName);
    }

    public void addColumnFromOT(String tablename, String columnName) {
        Table newTable = new Table(tablename);
        if (otherTables.contains(newTable))
            query.addColumn(newTable, columnName);
        else
            System.out.println("Se debe seleccionar campos de una tabla con un join ya existente");
    }


    public List getAllColumns() {
        return query.listColumns();
    }

    public void makeSelectDistinct(boolean distinct) {
        query.setDistinct(distinct);
    }

    public boolean isDistinct() {
        return query.isDistinct();
    }


    public void addEqualsIfNotEmpty(String field, String value) {
        if (value.trim() != "" && value != null)
            query.addCriteria(new MatchCriteria(table, field, MatchCriteria.EQUALS, value));
    }

    public void addEqualsIfNotEmptyFromOT(String tablename, String field, String value) {
        Table newTable = new Table(tablename);
        if (otherTables.contains(newTable)) {
            if (value.trim() != "" && value != null)
                query.addCriteria(new MatchCriteria(newTable, field, MatchCriteria.EQUALS, value));
        } else
            System.out.println("Se debe seleccionar campos de una tabla con un join ya existente");
    }


    public void addLike(String field, String value) {
        if (value.trim() != "" && value != null)
            query.addCriteria(new MatchCriteria(table, field, MatchCriteria.LIKE, value));
    }

    public void addLikeFromOT(String tablename, String field, String value) {
        Table newTable = new Table(tablename);
        if (otherTables.contains(newTable)) {
            if (value.trim() != "" && value != null)
                query.addCriteria(new MatchCriteria(newTable, field, MatchCriteria.LIKE, value));
        } else
            System.out.println("Se debe seleccionar campos de una tabla con un join ya existente");

    }

    public void addValueIn(String field, String[] value) {


        if (value.length > 0) {
            query.addCriteria(new InCriteria(table, field, value));
        }

    }

    public void addValueInFromOT(String tablename, String field, String[] value) {
        Table newTable = new Table(tablename);
        if (otherTables.contains(newTable)) {
            if (value.length > 0) {
                query.addCriteria(new InCriteria(newTable, field, value));
            }
        }
    }


    public void addEqualsTrue(String field) {
        query.addCriteria(new MatchCriteria(table, field, MatchCriteria.EQUALS, true));
    }

    public void addEqualsTrueFromOT(String tablename, String field) {
        Table newTable = new Table(tablename);
        if (otherTables.contains(newTable)) {
            query.addCriteria(new MatchCriteria(newTable, field, MatchCriteria.EQUALS, true));
        }
    }

    public void addEqualsFalse(String field) {
        query.addCriteria(new MatchCriteria(table, field, MatchCriteria.EQUALS, false));
    }


    public void addEqualsFalseFromOT(String tablename, String field) {
        Table newTable = new Table(tablename);
        if (otherTables.contains(newTable)) {
            query.addCriteria(new MatchCriteria(newTable, field, MatchCriteria.EQUALS, false));
        }
    }

    public void addJoinToMainTable(String srcColumnname, String tablename, String destColumnname) {
        Table joinTable = new Table(tablename);
        if (!otherTables.contains(joinTable)) {
            otherTables.add(joinTable);
            query.addJoin(table, srcColumnname, joinTable, destColumnname);
        } else {
            System.out.println("Esta tratando de hacer Join a una Tabla ya registrada");
        }
    }


    public void addJoinToExistingTable(String existingTable, String srcColumnname, String tablename,
                                       String destColumnname) {
        Table localTable = new Table(existingTable);
        Table joinTable = new Table(tablename);
        if (otherTables.contains(localTable)) {
            if (!otherTables.contains(joinTable)) {
                otherTables.add(joinTable);
                query.addJoin(localTable, srcColumnname, joinTable, destColumnname);
            } else
                System.out.println("Esta tratando de hacer Join a una Tabla ya registrada");
        } else {
            System.out.println("Esa tabla no existe");
        }
    }

    //Finaliza - Area de Selects


    //Inicio - Area de Inserts


    private List fieldsInsert = null;
    private List valuesInsert = null;

    public void addInsertValues(String field, String value) {
        fieldsInsert.add(field);
        valuesInsert.add(value);
    }

    public void addInsertValues(String field, int value) {
        fieldsInsert.add(field);
        Integer newvalue = new Integer(value);
        valuesInsert.add(newvalue);
    }

    public String getInsertQuery() {
        String query = null;
        if (!fieldsInsert.isEmpty() && !valuesInsert.isEmpty()) {
            query = "INSERT INTO \n ";
            query += table + "\n (";
            int sizel = fieldsInsert.size();
            for (int i = 0; i < sizel; i++) {
                query += i == (sizel - 1) ? fieldsInsert.get(i) : fieldsInsert.get(i) + ",";
            }
            query += ") \nVALUES\n (";
            sizel = valuesInsert.size();
            for (int i = 0; i < sizel; i++) {
                String val = valuesInsert.get(i).toString();
                if (!Util.isNumeric(val))
                    val = "'" + val + "'";
                query += i == (sizel - 1) ? val : val + ",";
            }
            query += ");";
        }

        return query;
    }

    //Fin - Area de Inserts


    //Inicio - Area de Updates

    private List fieldsUpdate = null;
    private List valuesUpdate = null;
    private List criteriaUpdate = null;

    public void addUpdateValue(String field, String value) {
        fieldsUpdate.add(field);
        valuesUpdate.add(value);
    }

    public void addUpdateValue(String field, int value) {
        fieldsUpdate.add(field);
        Integer newvalue = new Integer(value);
        valuesUpdate.add(newvalue);
    }

    public void addUpdateEqualsCriteria(String field, int value) {
        criteriaUpdate.add(new MatchCriteria(table, field, MatchCriteria.EQUALS, value));
    }

    public void addUpdateEqualsCriteria(String field, String value) {
        criteriaUpdate.add(new MatchCriteria(table, field, MatchCriteria.EQUALS, value));
    }


    public void addUpdateLikeCriteria(String field, String value) {
        if (value.trim() != "" && value != null)
            criteriaUpdate.add(new MatchCriteria(table, field, MatchCriteria.LIKE, value));
    }

    public void addUpdateValueIn(String field, String[] value) {
        if (value.length > 0) {
            criteriaUpdate.add(new InCriteria(table, field, value));
        }
    }

    public void addUpdateEqualsTrue(String field) {
        criteriaUpdate.add(new MatchCriteria(table, field, MatchCriteria.EQUALS, true));
    }


    public void addUpdateEqualsFalse(String field) {
        criteriaUpdate.add(new MatchCriteria(table, field, MatchCriteria.EQUALS, false));
    }

    public String getUpdateQuery() {
        return valuesHeaderAndCriteria();
    }

    private String valuesHeaderAndCriteria() {
        return writeUpdateValues() + ToStringerCriteria.toStringCriteria(this, criteriaUpdate);

    }

    public String writeUpdateValues() {
        String query = null;
        if (!fieldsUpdate.isEmpty() && !valuesUpdate.isEmpty()) {
            query = "UPDATE \n ";
            query += table + "\n SET \n";
            int sizel = fieldsUpdate.size();
            for (int i = 0; i < sizel; i++) {
                String val = valuesUpdate.get(i).toString();
                if (!Util.isNumeric(val))
                    val = "'" + val + "'";
                query += i == (sizel - 1) ? fieldsUpdate.get(i) + "=" + val : fieldsUpdate.get(i) + "=" + val + ",";
            }
            query += " \n ";

        }

        return query;
    }

    public void writeCriteria(Output out, List criteriaw) {
        if (criteriaw.size() > 0) {
            out.println("WHERE");
            appendCriteriaList(out, criteriaw, "AND");
            out.print(";");
        }
    }

    private void appendCriteriaList(Output out, List criteriaw, String seperator) {
        for (int i = 0; i < criteriaw.size(); i++) {
            out.println(criteriaw.get(i));
            if (i != criteriaw.size() - 1)
                out.println(seperator);
        }
    }


    //Fin - Area de Updates

    //Inicio - Area de Delete


    private List criteriaDelete = null;

    public void addDeleteEqualsCriteria(String field, int value) {
        criteriaDelete.add(new MatchCriteria(table, field, MatchCriteria.EQUALS, value));
    }

    public void addDeleteEqualsCriteria(String field, String value) {
        criteriaDelete.add(new MatchCriteria(table, field, MatchCriteria.EQUALS, value));
    }


    public void addDeleteLikeCriteria(String field, String value) {
        if (value.trim() != "" && value != null)
            criteriaDelete.add(new MatchCriteria(table, field, MatchCriteria.LIKE, value));
    }

    public void addDeleteValueIn(String field, String[] value) {
        if (value.length > 0) {
            criteriaDelete.add(new InCriteria(table, field, value));
        }
    }

    public void addDeleteEqualsTrue(String field) {
        criteriaDelete.add(new MatchCriteria(table, field, MatchCriteria.EQUALS, true));
    }


    public void addDeleteEqualsFalse(String field) {
        criteriaDelete.add(new MatchCriteria(table, field, MatchCriteria.EQUALS, false));
    }

    public String getDeleteQuery() {
        return headerDeleteAndCriteria();
    }


    private String headerDeleteAndCriteria() {
        return writeDeleteHeader() + ToStringerCriteria.toStringCriteria(this, criteriaDelete);

    }

    public String writeDeleteHeader() {
        String query = null;

        query = "DELETE FROM \n ";
        query += table + "\n";


        return query;
    }


    //Fin - Area de Delete


}
