package tool;

import tool.Database;

public class Query {
    String query;
    Database database;
    String dataUTF="(none)";
    public String getDataUTF() {
        return dataUTF;
    }

    public void setDataUTF(String dataUTF) {
        this.dataUTF = dataUTF;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    public Query(String query,Database database){
        this.setQuery(query);
        this.setDatabase(database);
    }
    ///functions
    public void verify()throws Exception{
        String[] decomposed_query=query.split(" ");
        if(!decomposed_query[decomposed_query.length-1].equalsIgnoreCase("pls!"))throw new Exception("More formal please, missing 'pls!' at the end.");
        String up_query="";
        for (int i = 0; i < decomposed_query.length-1; i++) {
            if(i==decomposed_query.length-2)up_query+=decomposed_query[i];
            else up_query+=decomposed_query[i]+" ";
        }
        this.setQuery(up_query);
    }
}