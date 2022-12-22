package command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import tool.Query;
import tool.Relation;

public class Select {
    public static void Select_core(Query q)throws Exception{
        verifyQuery(q);
        Relation r=execute(q);
        r.UTF_encodeData(q);
    }

    private static void verifyQuery(Query q)throws Exception{
        if(q.getDatabase().getName()==null)throw new Exception("No database selected");
        String[] decomposed_query=q.getQuery().split(" ");
    ///check "from"
        if(!decomposed_query[decomposed_query.length-2].equalsIgnoreCase("from"))throw new Exception("error near "+decomposed_query[decomposed_query.length-2]);
    ///check table
        String[] table=getTable_name(q.getDatabase().getName());
        int index_table=-1;
        for (int index = 0; index < table.length; index++) {
            if(decomposed_query[decomposed_query.length-1].equalsIgnoreCase(table[index]))index_table=index;
        }
        if(index_table==-1)throw new Exception("table "+decomposed_query[decomposed_query.length-1]+" doesn't exist");
    ///check column
        String[] column=getColumn_name(q.getDatabase().getName(),table[index_table]);
        int valeur=0;
        for (int index = 0; index < column.length; index++) {
            if(decomposed_query[1].equalsIgnoreCase(column[index]))valeur=1;
        }
        if(valeur==0 && !decomposed_query[1].equalsIgnoreCase("*"))throw new Exception("column "+decomposed_query[1]+" doesn't exist in the table "+table[index_table]);
    }

    public static String[] getTable_name(String database){
        File dossier=new File("D:/Etude/S3/Mr Naina/SocketSGBD/database/"+database);
        return dossier.list();
    }

    public static String[] getColumn_name(String database,String table){
        File file=new File("D:/Etude/S3/Mr Naina/SocketSGBD/database/"+database+"/"+table+"/"+table+".heik");
        String column="";
        try{
            FileReader reader =new FileReader(file);
            BufferedReader b =new BufferedReader(reader);
            column=b.readLine();
            b.close();
        } catch(Exception ex){System.out.println("Something went wrong with the file");}
        return column.split("//");
    }

    public static ArrayList<ArrayList<String>> getData(String database,String table){
        File file=new File("D:/Etude/S3/Mr Naina/SocketSGBD/database/"+database+"/"+table+"/"+table+".heik");
        String fordata="";
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        try{
            FileReader reader =new FileReader(file);
            BufferedReader b =new BufferedReader(reader);
            b.readLine();
            fordata=b.readLine();
            int v=0;
            while(fordata!=null){
                String[] temp=fordata.split(";;");
                ArrayList<String> data_in=new ArrayList<>();
                for (int j = 0; j < temp.length; j++) {
                    data_in.add(j,temp[j]);
                }
                data.add(data_in);
                v++;
                fordata=b.readLine();
            }
            b.close();
        } catch(Exception ex){}
        return data;
    }

    public static Relation execute(Query q)throws Exception{
    ///get table
        String[] decomposed_query=q.getQuery().split(" ");
        String[] table=getTable_name(q.getDatabase().getName());
        int index_table=-1;
        for (int index = 0; index < table.length; index++) {
            if(decomposed_query[decomposed_query.length-1].equalsIgnoreCase(table[index]))index_table=index;
        }
        if(index_table==-1)throw new Exception("table "+decomposed_query[decomposed_query.length-1]+" doesn't exist");
    ///Etablish a relation
        ///set column
        String[] col = getColumn_name(q.getDatabase().getName(), table[index_table]);
        ArrayList<String> column = new ArrayList<>();
        for (int i = 0; i < col.length; i++) {
            column.add(col[i]);
        }
        ///set data
        ArrayList<ArrayList<String>> data = getData(q.getDatabase().getName(), table[index_table]);
        return new Relation(table[index_table],column,data);
    }
}