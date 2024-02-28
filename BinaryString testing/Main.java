import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        BinaryString boolarr = new BinaryString(new boolean[]{true, false, false, true, false});
        BinaryString stringin = new BinaryString("11011");
        BinaryString intarr = new BinaryString(new int[]{1,0,1,0,1});
        BinaryString randgen = new BinaryString(5l);
        BinaryString intin = new BinaryString(30);
        //BinaryString doublein = new BinaryString(10.0);
        ArrayList<BinaryString> binaryStringList = new ArrayList<BinaryString>();
        binaryStringList.add(boolarr);
        binaryStringList.add(stringin);
        binaryStringList.add(intarr);
        binaryStringList.add(randgen);
        binaryStringList.add(intin);

        for(BinaryString string : binaryStringList){
            //string.print(""+string.coin());
            for(BinaryString str2 : binaryStringList){
                string.print("finding compared to "+binaryStringList.indexOf(str2));
                string.print("distance between "+string.toString()+" and "+str2.toString()+": "+string.getDistance(str2)+" l1"+string.length()+"l2"+str2.length());
                string.reduceDistanceFrom(str2, 1);
                string.print("distance between "+string.toString()+" and "+str2.toString()+": "+string.getDistance(str2)+" l1"+string.length()+"l2"+str2.length());
                string.print("done compared to "+binaryStringList.indexOf(str2));
            }

            string.length();

            string.toInt();
            string.toDouble();
            string.mutate();
            string.print(string.toString()+" ["+string.get(0)+","+string.get(1)+" #"+string.length()+" asint "+string.toInt()+" double "+string.toDouble()+" mutate "+string.mutate().toString());
        }

    }
}
