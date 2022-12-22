import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

import tool.Relation;
import view.Display;

public class Client{
    public static void main(String[] args) throws Exception{
        Socket client=new Socket("127.0.0.1",2006);
        Display.headBrief();
        while (true) {
            Display.start();
            Scanner sc = new Scanner(System.in);
            String s=sc.nextLine();
            if(s.equalsIgnoreCase("exit"))break;
            //envoie de donnees
            DataOutputStream out=new DataOutputStream(client.getOutputStream());
            out.writeUTF(s);
            out.flush();
            //recuperation donnee
            DataInputStream in=new DataInputStream(client.getInputStream());
            String s1 = in.readUTF();
            if(s1.split("//").length!=1){
                Relation R=new Relation("Answer");
                R.decode_setUTFdata(s1);
                R.displayRelation();
            } else {
                System.out.println(s1);
            }
        }
        client.close();
        System.out.println("Disconnected");
    }
}