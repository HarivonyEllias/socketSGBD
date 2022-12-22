package sql;

import java.io.*;
import java.util.Vector;
import javax.lang.model.util.ElementScanner14;
import java.util.regex.*;
import file.*;
import relation.*;

public  class Operation {
    public Operation(){}
    public static  Relation Projection(Relation rel,String[] nomColonne){
        //identification ny colonne miasa 
        Relation resulta = new Relation();
        for(int i=0;i<nomColonne.length;i++){
            resulta.addColonne(nomColonne[i]);
        }
        Vector<Vector<String>> colonneM = new Vector();//objet mandray ni colonne prise en charge
        Vector<String> OldColonne = rel.getColonne();
        for(int i=0;i<OldColonne.size();i++){
            for( int a=0;a<nomColonne.length;a++){
                if(OldColonne.get(i).compareTo(nomColonne[a])==0){
                    colonneM.add(rel.Distinctecolonne(rel.getVerticaleColonne(i)));
                }
            }
        }
        int maxlength =colonneM.get(0).size();
        for(int i=1;i<colonneM.size();i++){
            if(maxlength<=colonneM.get(i).size()){
                maxlength=colonneM.get(i).size();
            }
        }
        for(int i=0;i<maxlength;i++){
            Vector<String> ligne = new Vector();
            for(int a=0;a<nomColonne.length;a++){
                if(i>=colonneM.get(a).size()){
                    ligne.add("null");
                }
                else{
                    ligne.add(colonneM.get(a).get(i));
                }
            }
            resulta.addRelation(ligne);
        }
        return resulta;
    }
    public static Relation Selection(Relation rel ,String[] condition){
        Relation resulta = new Relation();
        resulta.addColonne(rel.getColonne());
        String[] nomColonne = Fichier.ValueInsertFile(rel.getnomColonne()).split(",");
        for(int i=0;i<rel.getLignesplit().size();i++){
            String[] lineSec = Fichier.ValueInsertFile(rel.getLignesplit().get(i)).split(",");
            for(int a=0;a<nomColonne.length;a++){
                if(nomColonne[a].compareTo(condition[0])==0){
                    if(lineSec[a].compareTo(condition[1])==0){
                        resulta.setLigne(rel.getLignesplit().get(i));
                    }
                }
            }
        }
        return resulta;
    }

    public static Relation ProduitCartesienne(Relation tabe1,Relation tabe2){
        Relation resulta = new Relation();
        for(int i=0;i<tabe1.getColonne().size();i++){
            resulta.addColonne(tabe1.getColonne().get(i));
        }
        for(int i=0;i<tabe2.getColonne().size();i++){
            resulta.addColonne(tabe2.getColonne().get(i));
        }
        for(int i=0;i<tabe1.getLine().size();i++){
            for(int a=0; a<tabe2.getLine().size();a++){

                Vector<String> ligneplus = new Vector();
                for(int b=0;b<tabe1.getLine().get(i).size();b++){
                    ligneplus.add(tabe1.getLine().get(i).get(b));
                }
                for( int c =0; c< tabe2.getLine().get(a).size();c++){
                    ligneplus.add(tabe2.getLine().get(a).get(c));
                }
                resulta.addRelation(ligneplus);
            }
        }
        return resulta;
   }

   public static Relation Jointure(Relation tabe1,Relation tabe2,String[] colonne){
    Relation resulta = new Relation();
    int idt1=-1,idt2=-1; // maka ni indicen'le colonne ampiasaina
    
     for(int a=0;a<tabe2.getColonne().size();a++){
         if(tabe2.getColonne().get(a).compareTo(colonne[1])==0){
             idt2=a;
         }
         resulta.addColonne(tabe2.getColonne().get(a));
     }
     for(int i=0;i<tabe1.getColonne().size();i++){
        if(tabe1.getColonne().get(i).compareTo(colonne[0])==0){
            idt1=i;
            continue;
        }
        resulta.addColonne(tabe1.getColonne().get(i));
    }
     for(int i=0;i<tabe2.getLine().size();i++){
         for(int a=0;a<tabe1.getLine().size();a++){
             if(tabe2.getLine().get(i).get(idt2).compareTo(tabe1.getLine().get(a).get(idt1))==0){
                 Vector<String> jointure = new Vector();
                 for(int c=0;c<tabe2.getLine().get(i).size();c++){
                         jointure.add(tabe2.getLine().get(i).get(c));
                 }
                 for(int c=0;c<tabe1.getLine().get(a).size();c++){
                     if(c!=idt1){
                         jointure.add(tabe1.getLine().get(a).get(c));
                     }
                 }
                 resulta.addRelation(jointure);
             }
         }
     }
    return  resulta;
    }
    public static Relation Union(Relation tabe1,Relation tabe2){
        Relation resulta = new Relation();
        resulta.addColonne(tabe1.getColonne());
        for(int i=0;i<tabe1.getLine().size();i++){
            resulta.addRelation(tabe1.getLine().get(i));
        }
        for(int i=0;i<tabe2.getLine().size();i++){
            resulta.addRelation(tabe2.getLine().get(i));
        }
        return resulta;
    }

    
    //meme nombre de colonne 
    public static Relation Intersection(Relation tabe1,Relation tabe2){
        Relation resulta = new Relation();
        resulta.addColonne(tabe1.getColonne());
        for( int i=0;i<tabe1.getLine().size();i++){
            int p=0;
            for(int a=0;a<tabe2.getLine().size();a++){
                for(int c=0;c<tabe2.getLine().get(a).size();c++){
                    if(tabe1.getLine().get(i).get(c).compareTo(tabe2.getLine().get(a).get(c))==0){
                        p++;
                    }
                }
                if(p==tabe2.getLine().get(a).size()){
                    Vector<String> tabe= new Vector();
                    for(int i2=0;i2<tabe1.getLine().get(i).size();i2++){
                            tabe.add(tabe1.getLine().get(i).get(i2));
                    }
                    resulta.addRelation(tabe);
                        i++;
                    if(i>=tabe1.getLine().size())
                     break;
                }else{
                    p=0;
                }
            }
        }
        return resulta;
    }
    public static String removeAt(String nom,int b){
        char[] mot = new char[nom.length()-1];
        int a=0;
        for(int i=0;i<nom.length();i++){
            if(i!=b){
                mot[a]=nom.charAt(i);
                a++;
            }
        }
        return new String(mot);
    }
    public static String SupprimerEspDevantDeriere(String n)
    {
        String reponse = new String();
        if(n.charAt(0)==' ' && n.charAt(n.length()-1)==' '){
            reponse =removeAt(n,0);
            reponse =removeAt(reponse,reponse.length()-1);
        }
        else if(n.charAt(0)==' '){
            reponse =removeAt(n,0);
        }
        else if(n.charAt(n.length()-1)==' '){
            reponse =removeAt(n,n.length()-1);
        }
        else{
            return n;
        }
        return reponse;
        
    }
    //manao espace raika amin'ny elanelen'ny phrase
    public static String separateur(String phrase){
        for(int i=0;i<phrase.length()-1;i++){
            if(phrase.charAt(i)==' '  && phrase.charAt(i+1)==' '){
                    phrase=removeAt(phrase,i);
                    i = 0;
                }
        }
        return phrase;
    }

    public static String getMotcleUse(String requete) throws Exception{
        String resulta=new String("null"); //maka ny mot sequence n'ny mot cle nampiasaina sy an'le requete
        String[] motcleSelect= new String[12];
        motcleSelect[0]="select from";
        motcleSelect[1]="select from where";
        motcleSelect[2]="select from join on";
        motcleSelect[3]="select from join on where";
        motcleSelect[4]="insert into values";
        motcleSelect[5]="update set where";
        motcleSelect[6]="create table";
        motcleSelect[7]="delete from where";
        motcleSelect[8]="select from difference";
        motcleSelect[9]="select from division";
        motcleSelect[10]="create database";
        motcleSelect[11]="use database";
        String requeteMin= requete.toLowerCase();
        for(int i=0;i<motcleSelect.length;i++){
            String[] motcle = motcleSelect[i].split(" ");
            int p=0;
            for(int a=0;a<motcle.length;a++){
                    if(requeteMin.contains(motcle[a])==true){
                        p++;
                    }
            }
            if(p==motcle.length){
                resulta=motcleSelect[i];
            }
        }
        if(resulta.compareTo("null")==0){
                throw new Exception("requete non valide");
        }
        return resulta;
    }
    //indice fonction utiliser
    public static int fonctionUse(String requete) throws Exception{
        String motCle = getMotcleUse(requete);
        String[] motcleSelect= new String[12];
        motcleSelect[0]="select from";
        motcleSelect[1]="select from where";
        motcleSelect[2]="select from join on";
        motcleSelect[3]="select from join on where";
        motcleSelect[4]="insert into values";
        motcleSelect[5]="update set where";
        motcleSelect[6]="create table";
        motcleSelect[7]="delete from where";
        motcleSelect[8]="select from difference";
        motcleSelect[9]="select from division";
        motcleSelect[10]="create database";
        motcleSelect[11]="use database";
        for(int i=0;i<motcleSelect.length;i++){
            if(motcleSelect[i].compareTo(motCle)==0){
                return i;
            }
        }
        return -1;
    }
    //position mots cles dans la requete
    public static int[] emplacementMotcle(String motcle,String requete){
        String[] sectionrequete= requete.toLowerCase().split(" ");
        String[] sectionMotcle = motcle.split(" ");
        int[] numpositionmotcle = new int[sectionMotcle.length];
        for(int a=0;a<sectionMotcle.length;a++){
            for(int i=0;i<sectionrequete.length;i++){
                if(sectionrequete[i].contains(sectionMotcle[a])==true){
                    numpositionmotcle[a]=i;
                }
            }
        }
        return numpositionmotcle;
    }

       //verifi si mots cles est en ordre
    public static void rangmotCle(String motcle,String requete) throws Exception {
        int[] numpositionmotcle = emplacementMotcle(motcle,requete);
        int min= numpositionmotcle[0];
        for(int a=1;a<numpositionmotcle.length;a++){
            if(min<numpositionmotcle[a]){
                min=numpositionmotcle[a];
            }else{
                throw new Exception("mot cle mal organiser");
            }
        }
    }
    //separation arakan'ny mot cle 
    public static Vector<String> NonMotCle(String requete) throws Exception{
        String requetV = separateur(requete);
        String[] motcleSec = getMotcleUse(requetV).split(" ");
        Vector<String> resulta = new Vector();
        String motS = requetV;
        for(int i=0;i<motcleSec.length;i++){
            String[] sectionR = new String[2];
            if(motS.contains(motcleSec[i])==true){
                sectionR = motS.split(motcleSec[i]);
            }
            else{
                sectionR = motS.split(motcleSec[i].toUpperCase());
            }
            if(sectionR[0].compareTo(new String())!=0 && sectionR[0].compareTo(" ")!=0){
                resulta.add(sectionR[0]);
            }
            motS=sectionR[1];
        }
        resulta.add(motS);
        return resulta;
    }
    public static  String AvoirValeurEntreparenthese(String requete)throws Exception{
        Matcher m = Pattern.compile("\\((.*?)\\)").matcher(requete);
        String ecriture = new String();
        while(m.find()) {
            ecriture=m.group(1);
        }
        return ecriture;
    }
    public static  void Affichage(Relation tabe){
        
        for(int i=0;i<tabe.getColonne().size();i++){
            System.out.print(tabe.getColonne().get(i)+" ");
        }
        System.out.println(" ");
        for(int a=0;a<tabe.getLine().size();a++){
            for(int c=0;c<tabe.getLine().get(a).size();c++){
                System.out.print(tabe.getLine().get(a).get(c)+" ");
            }
            System.out.println(" ");
        }
    }
    public static Relation Difference(Relation tabe1 ,Relation tabe2){
        Relation resulta = new Relation();
       
        resulta.addColonne(tabe1.getColonne());
        for( int i=0;i<tabe1.getLine().size();i++){
            int p=0;
            for(int a=0;a<tabe2.getLine().size();a++){
                for(int c=0;c<tabe2.getLine().get(a).size();c++){
                    if(tabe1.getLine().get(i).get(c).compareTo(tabe2.getLine().get(a).get(c))==0){
                        p++;
                    }
                }
                if(p==tabe2.getLine().get(a).size()){
                    break;
                }
                else{ p=0;}
            }
            if(p<tabe2.getLine().size()){
                Vector<String> tabe= new Vector();
                    for(int i2=0;i2<tabe1.getLine().get(i).size();i2++){
                            tabe.add(tabe1.getLine().get(i).get(i2));
                    }
                    resulta.addRelation(tabe);
            }
        }
        return resulta;
    }
    public static int[] emplacementColonneDivision(Relation tabe1,Relation tabe2){
        Vector<String> nomColonneT1=tabe1.getColonne();//supposons que le nombre de colonne de la table1 est plus grand que le nombre de colonne de tabe2
        Vector<String> nomColonneT2 = tabe2.getColonne();
        int[] resulta = new int[nomColonneT2.size()];
        for(int i=0;i<nomColonneT1.size();i++){
            for(int a=0;a<nomColonneT2.size();a++){
                if(nomColonneT1.get(i).compareTo(nomColonneT2.get(a))==0){
                    resulta[a]=i;
                }
            }
        }
        return resulta;
    }
    public static Vector<String> Lignedivision(Vector<String> Lignetabe1 ,Relation tabe2,int[] emplacement) {
        Vector<String> Resulta = new Vector();
        int op=0;
        for(int i=0;i<tabe2.getLine().size();i++){
            int p=0;
            for(int a=0;a<tabe2.getLine().get(i).size();a++){
                if(Lignetabe1.get(emplacement[a]).compareTo(tabe2.getLine().get(i).get(a))==0) p++;
            }
            if(p==tabe2.getLine().get(i).size()){
                for(int c=0;c<Lignetabe1.size();c++){
                    int k=0;
                    for(int f=0;f<emplacement.length;f++){
                            if (emplacement[f]==c) k++;
                    }
                    if(k==0){ 
                        op++;
                        Resulta.add(Lignetabe1.get(c));
                    }
                }
            }
        }
       if(op!=0) return Resulta;
       else return null;
    }
    public static Relation Division(Relation tabe1,Relation tabe2){
        Vector<String> nomColonneT1=tabe1.getColonne();
        Vector<String> nomColonneT2 = tabe2.getColonne();
        Relation resulta = new Relation();
        for(int i=0;i<nomColonneT1.size();i++){
            int p=0;
            for(int a=0;a<nomColonneT2.size();a++){
                if(nomColonneT1.get(i).compareTo(nomColonneT2.get(a))==0){
                    p++;
                }
            }
            if(p==0){
                resulta.addColonne(nomColonneT1.get(i));
            }else{
                p=0;
            }
        }{
        for(int i=0;i<tabe1.getLine().size();i++){
            Vector<String> tabe =Lignedivision(tabe1.getLine().get(i) ,tabe2,emplacementColonneDivision(tabe1,tabe2) );
            if(tabe!=null){
                resulta.addRelation(tabe);
            }
        }
        return resulta;
    }
}