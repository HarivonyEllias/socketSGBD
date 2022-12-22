import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server{
    public static void main(String[] args) throws Exception{
        ServerSocket ss=new ServerSocket(2006);
        System.out.println("en attente...");
        int id=0;
        ArrayList<Socket> s_list=new ArrayList<>();
        while(ss.accept()!=null){
            s_list.add(ss.accept());
            System.out.println("connexion etablie to User:"+id);
            id++;
        }
        //recuperation donnee
        for (int j = 0; j < s_list.size(); j++) {
            DataInputStream in=new DataInputStream(s_list.get(j).getInputStream());
            String data=in.readUTF();
            //traitement
            String ans="vous etes bien "+data;
            //envoie donnees
            DataOutputStream out=new DataOutputStream(s_list.get(j).getOutputStream());
            out.writeUTF(ans);
        }
    }
}