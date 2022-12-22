package tool;

import java.util.ArrayList;

import tool.Query;

public class Relation {
    String name;
    ArrayList<String> column_name;
    ArrayList<ArrayList<String>> data;
///Setters and getters
    public void setName(String name) {
        this.name = name;
    }
    public void setColumn_name(ArrayList<String> column_name) {
        this.column_name = column_name;
    }
    public void setData(ArrayList<ArrayList<String>> data) {
        this.data = data;
    }
    public String getName() {
        return name;
    }
    public ArrayList<String> getColumn_name() {
        return column_name;
    }
    public ArrayList<ArrayList<String>> getData() {
        return data;
    }
///Constructors
    public Relation(String name, ArrayList<String> column_name, ArrayList<ArrayList<String>> data) {
        this.setName(name);
        this.setColumn_name(column_name);
        this.setData(data);
    }
    public Relation(String name){
        this.setName(name);
    }
///Functions
    public int getColumn_number(){
    //return the number of the relation's columns
        return this.getColumn_name().size();
    }
    public int CountData(){
    //return the number of the relation's data
        return this.getData().size();
    }

    public void UTF_encodeData(Query q){
        String UTF_data="";
        String column="";
        for (int i = 0; i < this.getColumn_name().size(); i++) {
            column+=this.getColumn_name().get(i)+"//";
        }
        String data="";
        for (int i = 0; i < this.getData().size(); i++) {
            String data_i="";
            for (int j = 0; j < this.getData().get(i).size(); j++) {
                if(j==this.getData().get(i).size()-1)data_i+=this.getData().get(i).get(j);
                else data_i+=this.getData().get(i).get(j)+",";
            }
            if(i!=this.getData().size()-1)data_i+="/";
            data+=data_i;
        }

        UTF_data+=column+data;
        q.setDataUTF(UTF_data);
///     colonne1//colonne2//colonne3//colonne4//d1,d1,d1,d1/d2,d2,d2,d2
    }

    public void decode_setUTFdata(String UTFdata){
        // nom//prenom//classe//numero//Irina,Karen,11eme,1/Harivony,Ellias,12eme,2
        //columns
        String[] c=UTFdata.split("//");
        ArrayList<String> columns=new ArrayList<>();
        for (int i = 0; i < c.length-1; i++) {
            columns.add(c[i]);
        }
        //data
        ArrayList<ArrayList<String>> data=new ArrayList<>();
        String[] line=c[c.length-1].split("/");
        for (int i = 0; i < line.length; i++) {
            ArrayList<String> tp=new ArrayList<>();
            String[] tp_data=line[i].split(",");
            for (int j = 0; j < tp_data.length; j++) {
                tp.add(tp_data[j]);
            }
            data.add(tp);
        }

        this.setColumn_name(columns);
        this.setData(data);
    }

    public void displayRelation() {
        //affichage titre
        System.out.println();
        System.out.println("==> "+name+" <==");
        System.out.println();
        // Affichage Colonne
        for(int i = 0; i < column_name.size(); i++) {
            System.out.print(" | " + column_name.get(i) + " | ");
        }
        System.out.println("");

        
        // Affichage Donnees
        for(int i = 0; i < data.size(); i++) {
            for(int a = 0; a < data.get(i).size(); a++) {
                System.out.print(" | " + data.get(i).get(a) + " | ");
            }
            System.out.println(""); 
        }
    }
}