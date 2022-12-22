package process;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import tool.Database;
import tool.Query;
public class Process extends Thread{
    static Database database=new Database(null, null);
    Socket socket;
    public Process(Socket socket){
        this.socket=socket;
    }
    @Override
    public void run() {
         try {
             DataInputStream in=new DataInputStream(socket.getInputStream());
             String query=null;
            while ((query=in.readUTF())!=null) {
            //recuperation donnee
            //traitement
                query = query.toLowerCase();
                Query q=new Query(query, database);
                q.setQuery(query);
                DataOutputStream out=new DataOutputStream(socket.getOutputStream());
                try {
                    q.verify();
                    new Elsql_Core(q);
                    //envoie donnees
                    out.writeUTF(q.getDataUTF());
                    out.flush();
                } catch (Exception e) {
                    out.writeUTF(e.getMessage());
                    out.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}