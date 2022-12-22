package process;

import tool.QueryCheck;
import tool.QueryManagement;
import tool.Query;

public class Elsql_Core {

    public Elsql_Core(Query q) throws Exception{
        int queryId=QueryCheck.Query_checking(q.getQuery());
        new QueryManagement(q,queryId);
    }
}