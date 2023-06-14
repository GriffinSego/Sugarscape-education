import java.util.Random;
import java.util.ArrayList;

public class BinaryString {
    private ArrayList<Boolean> digits = new ArrayList<Boolean>();
    public int length;
    public static BinaryString generateRandom(int length){
        boolean[] binaryString = new boolean[length];
        for(int i=0; i<length; i++){
            binaryString[i] = Agent.coin();
        }
        System.out.println(binaryString[0]);
        return new BinaryString(binaryString);
    }
    public static int getDistance(BinaryString binaryString1, BinaryString binaryString2){
        if(binaryString1.length() != binaryString2.length()){
            System.out.println("Cannot compare different sizes of binary string");
            return -1;
        }else{
            int distance = 0;
            for(int i=0; i<binaryString1.length(); i++){
                if(binaryString1.get(i) != binaryString2.get(i)){
                    distance++;
                }
            }
            return distance;
        }
    }
    public static void reduceDistance(BinaryString binaryString1, BinaryString binaryString2, int reduce){
        int distanceToReduce = reduce;
        int distance = getDistance(binaryString1, binaryString2);
        if(distance < 1){
            System.out.println("Cannot reduce distance, distance is already too small");
        }else if(distanceToReduce > distance){
            System.out.println("Cannot reduce distance more than distance already exists");
        }else{

            ArrayList<Integer> diffences = new ArrayList<Integer>();

            for(int i=0; i<binaryString1.length; i++){
                if(binaryString1.digits.get(i) != binaryString2.digits.get(i)){
                    diffences.add(i);
                }
            }

            while(distanceToReduce > 0){
                int newIndex = (int) Math.round(Agent.random.nextDouble()*(double) distanceToReduce);

                distanceToReduce--;
            }
        }
    }
    private int getDistance(BinaryString binaryString){
        return BinaryString.getDistance(this, binaryString);
    }
    public BinaryString(boolean[] binaryString){
        length = binaryString.length;
        for(int i=0; i<length; i++){
            digits.add(binaryString[i]);
        }
    }
    public BinaryString(Boolean[] binaryString){
        length = binaryString.length;
        for(int i=0; i<length; i++){
            digits.add(binaryString[i]);
        }
    }
    public BinaryString(String[] binaryString){
        length = binaryString.length;
        for(int i=0; i<length; i++){
            digits.add(binaryString[i] == "1" || binaryString[i] == "T");
        }
    }
    public BinaryString(int[] binaryString){
        length = binaryString.length;
        for(int i=0; i<length; i++){
            digits.add(binaryString[i] == 1);
        }
    }
    public BinaryString(int length){
        boolean[] binaryString = new boolean[length];
        for(int i=0; i<length; i++){
            digits.add(Agent.coin());
        }
        //System.out.println(binaryString[0]);
        this.length = length;
    }
    public boolean get(int i){
        return digits.get(i);
    }
    public int length(){
        return digits.size();
    }
    public static void print(String str){
        System.out.println(str);
    }
    public String toString(){
        String tempString = "";
        for(int i=0; i<this.length(); i++){
            tempString += (get(i) ? "1" : "0").toString();
        }
        return tempString;
    }
    public BinaryString mutate(){
        while(Agent.coin()){
            int indexOfMutation = (int) Math.round(Agent.random.nextDouble()*(double) (length-1));
            digits.set(indexOfMutation, !digits.get(indexOfMutation));
        }
        return this;
    }
    public BinaryString copy(){
        return new BinaryString(digits.toArray(new Boolean[length()]));
    }
}
