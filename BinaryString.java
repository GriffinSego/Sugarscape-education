import java.util.Random;
import java.util.ArrayList;

public class BinaryString {//BinaryString custom data type for storing, creating, modifying, and manipulating binary
    //Agent temp additions
    public static Random random = new Random();
    public static boolean coin(){
        return (true == (random.nextDouble() < 0.5));
    }
    //static methods
    public static void print(String str){//output the provided string to console
        System.out.println(str.toString());
    }
    public static int getDistance(BinaryString binaryString1, BinaryString binaryString2){//find the distance between the provided BinaryStrings
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
    public int getDistanceFrom(BinaryString binaryString2){
        BinaryString binaryString1 = this;
        return getDistance(binaryString1, binaryString2);
    }
    public void reduceDistanceFrom(BinaryString binaryString2, int reduce){//reduce provided number of differences between the two provided binary strings
        BinaryString binaryString1 = this;
        int distanceToReduce = reduce;
        int distance = getDistance(binaryString1, binaryString2);
        if(distance < 1){
            System.out.println("Cannot reduce distance, binary strings are indentical");
        }else if(distanceToReduce > distance){
            System.out.println("Cannot reduce distance more than distance already exists. Distance between strings 1:{"+binaryString1.toString()+"}, 2:{"+binaryString2.toString()+"} is "+distance+", requested distance is "+distanceToReduce);
        }else{
            ArrayList<Integer> differences = new ArrayList<Integer>();
            for(int i=0; i<binaryString1.length(); i++){
                if(binaryString1.digits.get(i) != binaryString2.digits.get(i)){
                    differences.add(i);
                }
            }
            while(distanceToReduce > 0){
                int newIndex = (int) Math.round(random.nextDouble()*(double) (differences.size()-1));
                binaryString1.digits.set(differences.get(newIndex), !binaryString1.digits.get(differences.get(newIndex)));
                differences.remove(newIndex);
                distanceToReduce--;
            }
        }
    }
    public void reduceDistanceFrom(BinaryString binaryString2){
        reduceDistanceFrom(binaryString2, 1);
    }
    public static BinaryString merge(BinaryString source, BinaryString target){
        BinaryString sourcemod = source.copy();
        BinaryString targetmod = target.copy();
        while(getDistance(sourcemod, targetmod) > 0){
            sourcemod.reduceDistanceFrom(targetmod, 1);
            if(getDistance(sourcemod, targetmod) > 0){
                targetmod.reduceDistanceFrom(sourcemod, 1);
            }
        }
        return sourcemod;
    }
    public BinaryString merge(BinaryString target){
        return merge(this, target);
    }
    public static BinaryString generateRandom(int length){//generate a random binary string of specified length
        boolean[] binaryString = new boolean[length];
        for(int i=0; i<length; i++){
            binaryString[i] = coin();
        }
        System.out.println(binaryString[0]);
        return new BinaryString(binaryString);
    }
    //end of static methods

    //instance methods
    public int getDistance(BinaryString binaryString){
        return BinaryString.getDistance(this, binaryString);
    }
    public boolean get(int i){
        return digits.get(i);
    }
    public int length(){
        return digits.size();
    }
    public String toString(){
        String tempString = "";
        for(int i=0; i<this.length(); i++){
            tempString += (get(i) ? "1" : "0").toString();
        }
        return tempString;
    }
    public int toInt(){
        int sum = 0;
        for(int i=length()-1;i>-1;i--){
            int digitval = digits.get(i) ? 1 : 0;
            sum += Math.pow(2,length()-i-1)*digitval;
        }
        return sum;
    }
    public double toDouble(){
        return (double) (1.0/(toInt()+1));
    }
    public BinaryString mutate(){
        BinaryString temp = this;
        assert(temp.length() > 2 && temp != null && temp.digits.get(0) != null);
        while(coin()){
            int length = temp.length();
            int indexOfMutation = (int) Math.round(random.nextDouble()*(double) (length-1));
            temp.digits.set(indexOfMutation, !digits.get(indexOfMutation));
        }
        return temp;
    }
    public BinaryString copy(){
        return new BinaryString(this.toString());
    }
    //end of instance methods
    private ArrayList<Boolean> digits = new ArrayList<Boolean>();//if this class begins to use too much RAM, refactor to store digits as a String

    //constructors
    public BinaryString(boolean[] binaryString){//boolean[]
        for(int i=0; i<binaryString.length; i++){
            digits.add(binaryString[i]);
        }
    }
    public BinaryString(String binaryString){//String
        for(int i=0; i<binaryString.length(); i++){
            digits.add(binaryString.charAt(i) == '1' || binaryString.charAt(i) == 'T');
        }
    }
    public BinaryString(int[] binaryString){//int[]
        for(int i=0; i<binaryString.length; i++){
            digits.add(binaryString[i] == 1);
        }
    }
    public BinaryString(long length){//long length
        boolean[] binaryString = new boolean[(int) length];
        for(int i=0; i<length; i++){
            digits.add(coin());
        }
    }
    public BinaryString(int binaryString){
        this(Integer.toBinaryString(binaryString));
    }
    //end of constructors
}
