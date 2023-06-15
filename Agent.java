import java.util.Random;
import java.util.ArrayList;

public class Agent {//agent AI entity within gamespace grid
    //declare static Agent variables and methods
    public static String randomConsonant() {
        char[] consonants = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n',
                             'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'};
        // Generate a random index within the range of the consonant array
        int randomIndex = random.nextInt(consonants.length);
        // Get the random consonant character
        char randomConsonant = consonants[randomIndex];
        // Convert the character to a string
        String randomConsonantString = String.valueOf(randomConsonant);
        return randomConsonantString;}
    public static String randomVowel() {
        char[] consonants = {'a','e','i','o','u','y','å','ø'};
        // Generate a random index within the range of the consonant array
        int randomIndex = random.nextInt(consonants.length);
        // Get the random consonant character
        char randomConsonant = consonants[randomIndex];
        // Convert the character to a string
        String randomConsonantString = String.valueOf(randomConsonant);
        return randomConsonantString;}
    public static Random random = new Random();
    public static boolean coin(){
        return (true == (random.nextDouble() < 0.5));
    }
    private static String generateName(){
           return ""+randomConsonant().toUpperCase()+randomVowel()+randomConsonant();
    }
    //agent personal attributes (updated during gameloop)
    public int x;
    public int y;
    private int age = 0;
    private double hunger = Config.defaultHunger;
    public boolean isDying = Config.allAgentsImmediatelyDieUponSpawning;
        //agent personal ArrayLists
    private ArrayList<Agent> recentlySeenAgents = new ArrayList<Agent>();
    private ArrayList<Agent> friends = new ArrayList<Agent>();
        //agent personal BinaryStrings
    private BinaryString culture;
    private BinaryString immuneSystem;

    //agent consts
    public final boolean isBlind = Config.allAgentsBlind;

    //agent references
    public Grid theGrid;

    //agent initconsts
    public String name = "";
    private double sugarConsumed = 0;
    private double maxSugar = hunger;
    private double rateOfMetabolism = (Config.rateOfMetabolism*2*random.nextDouble())+0.1;
    private int range;

    //agent constructors
        //constructor for invokation by parent
    public Agent(int x, int y, BinaryString culture, BinaryString immuneSystem, Grid theGrid){
        this.range = (int) Math.round(((double) Config.defaultRange)*5*(double)random.nextDouble())+1;
        this.x = x;
        this.y = y;
        this.theGrid = theGrid;
        this.name = generateName();
        this.culture = culture.copy().mutate();
        this.immuneSystem = immuneSystem.copy().mutate();
    }
        //constructor for initial spawns
    public Agent(int x, int y, Grid theGrid){
        this.range = (int) Math.round(((double) Config.defaultRange)*1.5*(double)random.nextDouble())+1;
        this.x = x;
        this.y = y;
        this.theGrid = theGrid;
        this.name = generateName();
        this.culture = new BinaryString((int) Config.culturelen);
        this.immuneSystem = new BinaryString((int) Config.immuneSystemlen);
    }

    //agent instance methods
    private int constrain(int x){
        int upperBound = Config.gridWidth;
        int lowerBound = 0;
        return (int) Math.max(lowerBound, Math.min(x, upperBound-1));
    }
    public boolean doTick(){
        Cell[][] map = theGrid.map;
        if(!isDying){
            age++;
            if(maxSugar < hunger){
                maxSugar = hunger;
            }
        }
        //Action stage: hunger tick, look, move, eat, state check
        hunger -= rateOfMetabolism;
        int[] mostSugar = new int[2];
        double mostSugarValue = 0.0;
        for(int i=constrain(x-range);i<constrain(x+range);i++){
            for(int k=constrain(y-range);k<constrain(y+range);k++){
                Cell cell = map[constrain(i)][constrain(k)];
                if(cell.getSugar() > mostSugarValue){
                    mostSugar[0] = constrain(i); mostSugar[1] = constrain(k);
                    mostSugarValue = cell.getSugar();
                }
                if(cell.occupied){
                    Agent tempAgent = cell.occupier;
                    if(!friends.contains(tempAgent)){
                        friends.add(tempAgent);
                    }
                }
            }
        }
        //run checks for agent motion and state update
        if(mostSugarValue == 0.0 || isBlind || mostSugarValue < range/100){
            if(Config.agentsCanMove){//move in a random direction (in the hopes that from there, sugar will be visible)
                map[x][y].setOccupier(null);
                int xoffset = (int) Math.round(Math.random()*2)-1;
                int yoffset = (int) Math.round(Math.random()*2)-1;
                while(x+xoffset<0 || y+yoffset < 0 || x+xoffset > map.length-1 || y+yoffset > map[1].length-1){
                    xoffset = (int) Math.round(Math.random()*2)-1;
                    yoffset = (int) Math.round(Math.random()*2)-1;
                }
                x += xoffset;
                y += yoffset;
                map[x][y].setOccupier(this);
            }
            double sugarEaten = map[x][y].eatSugar();
            hunger += sugarEaten;
            sugarConsumed = sugarEaten;
        }else{//move towards the highest amount of sugar nearby
            map[x][y].setOccupier(null);
            x = mostSugar[0];
            y = mostSugar[1];
            hunger += map[mostSugar[0]][mostSugar[1]].eatSugar();
            map[mostSugar[0]][mostSugar[1]].setOccupier(this);
        }
        //state check
        if(hunger < 0.0){
            isDying = true;
            return false;
        }else if((int) age > (int) Config.maxAge){
            isDying = true;hunger = 0.0;map[x][y].setSugar(map[x][y].getSugar()+(double) Math.max(hunger, 0.0));
            return false;
        }else if(hunger > 100.0 && !map[x][y-2].occupied){
            Agent temp = new Agent(x,y-2, culture, immuneSystem, theGrid);
            theGrid.addAgent(temp);
            theGrid.livingAgents.add(temp);
            return true;
        }else{
            return true;
        }
    }//end of Agent().doTick

    public void die(Grid grid){
        isDying = true;
    }
}
