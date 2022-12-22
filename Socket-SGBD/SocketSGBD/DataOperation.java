package operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.management.relation.RelationException;
import javax.sql.rowset.serial.SerialJavaObject;

import tool.Relation;

public class DataOperation {
    public static Relation Union(Relation r1, Relation r2) throws Exception {
        ArrayList<ArrayList<String>> data = r1.getData();
        if (r1.getColumn_number() == r2.getColumn_number()) {
            for (int i = 0; i < r2.CountData(); i++) {
                data.add(r2.getData().get(i));
            }
            Relation answer = new Relation("union of " + r1.getName() + " and " + r2.getName(), r1.getColumn_name(),
                    data);
            removeDoublon(answer);
            return answer;
        } else
            throw new Exception("Relation's columns size doesn't match for the operation union ");
    }

    public static void removeDoublon(Relation answer) {
        for (int i = 0; i < answer.getData().size(); i++) {
            for (int j = 0; j < answer.getData().size(); j++) {
                if (i != j && answer.getData().get(i).equals(answer.getData().get(j))) {
                    answer.getData().remove(j);
                    answer.setData(answer.getData());
                }
            }
        }
    }

    public static Relation Intersection(Relation r1, Relation r2) throws Exception {
        /// check table comptability
        if (r1.getColumn_number() != r2.getColumn_number())
            throw new Exception("uncompatible table for the operation intersection");
        for (int i = 0; i < r1.getColumn_number(); i++) {
            if (!r1.getColumn_name().get(i).equalsIgnoreCase(r2.getColumn_name().get(i)))
                throw new Exception("uncompatible table for the operation intersection");
        }
        /// executing the operation
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (int i = 0; i < r1.getData().size(); i++) {
            for (int j = 0; j < r2.getData().size(); j++) {
                if (CompareLine(r1, i, r2, j))
                    result.add(r1.getData().get(i));
            }
        }
        return new Relation("Intersection of " + r1.getName() + "and " + r2.getName(), r1.getColumn_name(), result);
    }

    private static boolean CompareLine(Relation r1, int r1_dataindex, Relation r2, int r2_dataindex) {
        for (int i = 0; i < r1.getData().get(r1_dataindex).size(); i++) {
            if (!r1.getData().get(r1_dataindex).get(i).equals(r2.getData().get(r2_dataindex).get(i)))
                return false;
        }
        return true;
    }

    public static Relation Difference(Relation r1, Relation r2) throws Exception {
        /// check table comptability
        if (r1.getColumn_number() != r2.getColumn_number())
            throw new Exception("uncompatible table for the operation difference");
        for (int i = 0; i < r1.getColumn_number(); i++) {
            if (!r1.getColumn_name().get(i).equalsIgnoreCase(r2.getColumn_name().get(i)))
                throw new Exception("uncompatible table for the operation difference");
        }
        /// executing the operation
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        Relation intermRelation = Intersection(r1, r2);
        for (int i = 0; i < r1.getData().size(); i++) {
            if (!intermRelation.getData().contains(r1.getData().get(i)))
                result.add(r1.getData().get(i));
        }
        return new Relation("Difference of " + r1.getName() + " by " + r2.getName(), r1.getColumn_name(), result);
    }

    public static Relation Projection(Relation r1, ArrayList<String> column) throws Exception {
        ArrayList<Integer> index_list = new ArrayList<>();
        for (int i = 0; i < column.size(); i++) {
            if (r1.getColumn_name().indexOf(column.get(i)) != -1)
                index_list.add(r1.getColumn_name().indexOf(column.get(i)));
        }
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (int i = 0; i < r1.getData().size(); i++) {
            ArrayList<String> temp = new ArrayList<>();
            for (int j = 0; j < column.size(); j++) {
                temp.add(r1.getData().get(i).get(index_list.get(j)));
            }
            data.add(temp);
        }
        return new Relation("Projection of " + r1.getName() + " on theses column", column, data);
    }

    public static Relation Cartesian_product(Relation r1, Relation r2) throws Exception {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (int i = 0; i < r1.getData().size(); i++) {
            for (int j = 0; j < r2.getData().size(); j++) {
                result.add(concatList(r1.getData().get(i), r2.getData().get(j)));
            }
        }
        return new Relation("Cartesian product of " + r1.getName() + " and " + r2.getName(),
                concatList(r1.getColumn_name(), r2.getColumn_name()), result);
    }

    public static ArrayList<String> concatList(ArrayList<String> a, ArrayList<String> b) {
        ArrayList<String> result = new ArrayList<>();
        int j=0;
        for (int i = 0; i < a.size() + b.size(); i++) {
            if (i < a.size()){
                result.add(a.get(i));
            }
            else{
                result.add(b.get(j));
                j++;
            }
        }
        return result;
    }

    public static Relation Division(Relation r1, Relation r2) throws Exception {
        /// check comptability
        for (int j = 0; j < r2.getColumn_number(); j++) {
            if(!r1.getColumn_name().contains(r2.getColumn_name().get(j)))throw new Exception("uncompatible table for the operation division");
        }
        /// executing the operation
        Relation T1 = Projection(r1, r2.getColumn_name());
        Relation T2 = Projection((Difference(Cartesian_product(r2, T1), r1)), r2.getColumn_name());
        
        return Difference(T1, T2);
    }
}