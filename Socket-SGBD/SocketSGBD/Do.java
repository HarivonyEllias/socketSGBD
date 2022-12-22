package command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import command.Select;
import operation.DataOperation;
import tool.Query;
import tool.Relation;
import tool.Database;

public class Do {
    static int index_todo;
    static String[] tables;
    public static void Do_core(Query q) throws Exception {
        verifyQuery(q);
        execute(q);
    }

    private static void verifyQuery(Query q) throws Exception {
        // do difference :r1|r2
        if (q.getDatabase().getName() == null)
            throw new Exception("No database selected");
        String[] decomposed_query = q.getQuery().split(" ");
        /// check operation
        index_todo = verifyOperation(q.getQuery());
        /// check tables
        String[] table=q.getQuery().split(":");
        if(table[1].contains("><")){
            tables=table[1].split("><");
            for (int i = 0; i < tables.length; i++) {
                if (!tableExistence(tables[i], q.getDatabase().getName()))
                    throw new Exception("Table " + tables[i] + " doesn't exist in " + q.getDatabase().getName());
            }
        }else{
            tables=table[1].split("->");
            checkColumnSelected(q.getDatabase(), tables[1], tables[0]);
        }
    }
    private static void checkColumnSelected(Database d,String column,String table) throws Exception{
        File file=new File("D:/Etude/S3/Mr Tsinjo/sgbd.ELsql/database/"+d.getName()+"/"+table+"/"+table+".heik");
        String column_list="";
        try{
            FileReader reader =new FileReader(file);
            BufferedReader b =new BufferedReader(reader);
            column_list=b.readLine();
            b.close();
        } catch(Exception ex){System.out.println("Something went wrong with the file");}
        String[] columns=column_list.split("//");
        if(!arrayContains(columns, column))
            throw new Exception("Column " + column + " doesn't exist in table " + table);
    }

    private static boolean arrayContains(String[] tab,String value){
        for (int i = 0; i < tab.length; i++) {
            if(tab[i].compareTo(value)==0)return true;
        }
        return false;
    }

    public static void execute(Query q)throws Exception{
        Relation r1=new Relation(null);
        Relation r2=new Relation(null);
        switch (index_todo) {
            case 0:
                r1 = Select.execute(new Query("select * from "+tables[0], q.getDatabase()));
                r2 = Select.execute(new Query("select * from "+tables[1], q.getDatabase()));
                DataOperation.Union(r1, r2).UTF_encodeData(q);
            break;
            case 1:
                r1 = Select.execute(new Query("select * from "+tables[0], q.getDatabase()));
                r2 = Select.execute(new Query("select * from "+tables[1], q.getDatabase()));
                DataOperation.Intersection(r1, r2).UTF_encodeData(q);
            break;
            case 2:
                r1 = Select.execute(new Query("select * from "+tables[0], q.getDatabase()));
                ArrayList<String> c=new ArrayList<>();
                c.add(tables[1]);
                DataOperation.Projection(r1,c).UTF_encodeData(q);
            break;
            case 3:
                r1 = Select.execute(new Query("select * from "+tables[0], q.getDatabase()));
                r2 = Select.execute(new Query("select * from "+tables[1], q.getDatabase()));
                DataOperation.Cartesian_product(r1, r2).UTF_encodeData(q);
            break;
            case 4:
                r1 = Select.execute(new Query("select * from "+tables[0], q.getDatabase()));
                r2 = Select.execute(new Query("select * from "+tables[1], q.getDatabase()));
                DataOperation.Difference(r1, r2).UTF_encodeData(q);
            break;
            case 5:
                r1 = Select.execute(new Query("select * from "+tables[0], q.getDatabase()));
                r2 = Select.execute(new Query("select * from "+tables[1], q.getDatabase()));
                DataOperation.Division(r1, r2).UTF_encodeData(q);
            break;
        }
    }
    private static String[] Operation() {
        String[] options = new String[6];
        options[0] = "UNION";
        options[1] = "INTERSECTION";
        options[2] = "PROJECTION";
        options[3] = "CARTESIAN_PRODUCT";
        options[4] = "DIFFERENCE";
        options[5] = "DIVISION";
        return options;
    }

    public static int verifyOperation(String query) throws Exception {
        String[] decomposed_query = query.split(" ");
        String[] options = Operation();
        for (int i = 0; i < options.length; i++) {
            if (decomposed_query[1].equalsIgnoreCase(options[i]))
                return i;
        }
        throw new Exception("Unknown operation " + decomposed_query[1]);
    }

    public static String[] getTable_name(String database) {
        File dossier = new File("D:/Etude/S3/Mr Tsinjo/sgbd.ELsql/database/" + database);
        return dossier.list();
    }

    public static boolean tableExistence(String table, String database) throws Exception {
        String[] table_names = getTable_name(database);
        for (int i = 0; i < table_names.length; i++) {
            if (table.equalsIgnoreCase(table_names[i]))
                return true;
        }
        return false;
    }
}