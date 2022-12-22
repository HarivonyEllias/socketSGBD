package launch;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import operation.DataOperation;
import process.Elsql_Core;
import process.Process;
import tool.Relation;
import tool.Database;
import tool.Query;
import view.Display;

public class Console {
    static Database database=new Database(null, null);
    public static void main(String[] args) throws Exception{
        Boolean False=true;
        ServerSocket ss=new ServerSocket(2006);
        System.out.println("En attente d'une connexion...");
        ArrayList<Socket> sckt_list=new ArrayList<>();
        Socket stp=ss.accept();
        System.out.println("Connexion etablie !");
        System.out.println(stp);
        while (stp != null) {
            sckt_list.add(stp);
            Process temp=new Process(stp);
            temp.start();
            stp=ss.accept();
        }
    }
}