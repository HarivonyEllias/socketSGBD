package tool;
public class QueryCheck {
    public static String[] Query_options(){
        String[] options=new String[8];
        options[0]="SELECT";
        options[1]="DELETE";
        options[2]="UPDATE";
        options[3]="INSERT";
        options[4]="DESC";
        options[5]="CREATE";
        options[6]="USE";
        options[7]="DO";
        return options;
    }
    public static int Query_checking(String query) throws Exception{
        String[] decomposed_query=query.split(" ");
        String[] options=Query_options();
        for (int i = 0; i < options.length; i++) {
            if(decomposed_query[0].equalsIgnoreCase(options[i]))return i;
        }
        throw new Exception("Unknown command "+decomposed_query[0]);
    }
}