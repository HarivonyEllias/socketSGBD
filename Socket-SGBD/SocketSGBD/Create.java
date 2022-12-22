package command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import command.Select;
import operation.DataOperation;
import tool.*;
public class Create {
    public static void Create_core(Query q) throws Exception {
        verifyQuery(q);
        execute(q);
    }

    public static void execute(Query q)throws Exception{
        
    }

    public static void verifyQuery(Query q) throws Exception{
        ///create database/table huhu
        String[] decomposed_query=q.getQuery().split(" ");
        if(decomposed_query[1].equalsIgnoreCase("database")){
            if(databaseExistence(decomposed_query[1]))
                throw new Exception("Database "+decomposed_query[1]+" already exist");
        }
        if(decomposed_query[1].equalsIgnoreCase("table")){
            if(tableExistence(decomposed_query[1], q.getDatabase().getName()))
                throw new Exception("Table "+decomposed_query[1]+" already exist");
        }
    }

    public static boolean tableExistence(String table, String database) throws Exception {
        String[] table_names = getTable_name(database);
        for (int i = 0; i < table_names.length; i++) {
            if (table.equalsIgnoreCase(table_names[i]))
                return true;
        }
        return false;
    }

    public static boolean databaseExistence(String database){
        File dossier=new File("D:/Etude/S3/Mr Tsinjo/sgbd.ELsql/database");
        String[] list=dossier.list();
        for (int i = 0; i < list.length; i++) {
            if(database.equalsIgnoreCase(list[i]))return true;
        }
        return false;
    }

    public static String[] getTable_name(String database) {
        File dossier = new File("D:/Etude/S3/Mr Tsinjo/sgbd.ELsql/database/" + database);
        return dossier.list();
    }
}