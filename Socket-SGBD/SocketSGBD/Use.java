package command;

import java.io.File;
import java.util.ArrayList;

import tool.Query;

public class Use {
    public static void Use_core(Query q)throws Exception{
        verifyAndRunQuery(q);
    }
    private static void verifyAndRunQuery(Query q)throws Exception{
        String[] decomposed_query = q.getQuery().split(" ");
        String[] All_database = getAllDatabase();
        int index_database=-1;
        for (int i = 0; i < All_database.length; i++) {
            if(decomposed_query[1].equalsIgnoreCase(All_database[i]))index_database=i;
        }
        if(index_database==-1) throw new Exception("Database "+decomposed_query[1]+" doesn't exist");
        q.getDatabase().setName(All_database[index_database]);
        q.getDatabase().setPath("D:/Etude/S3/Mr Naina/SocketSGBD/database"+All_database[index_database]);
        System.out.println( All_database[index_database]+" selected");
        q.setDataUTF(All_database[index_database]+" selected");
    }
    private static String[] getAllDatabase(){
        File dossier=new File("D:/Etude/S3/Mr Naina/SocketSGBD/database");
        return dossier.list();
    }
}