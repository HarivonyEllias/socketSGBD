package tool;

import command.*;
import tool.Query;

public class QueryManagement {

    public QueryManagement(Query q,int queryid) throws Exception{
        switch (queryid) {
            case 0:
                Select.Select_core(q);
            break;
            case 1:
                
            break;
            case 2:
                
            break;
            case 3:
                
            break;
            case 4:
                
            break;
            case 5:
                // Create.create_core(q);
            break;
            case 6:
                Use.Use_core(q);
            break;
            case 7:
                Do.Do_core(q);
            break;
        }
    }
}
