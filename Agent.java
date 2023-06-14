import java.util.Random;
import java.util.ArrayList;

public class Agent {
    public int x;
    public int y;
    private double hunger = Config.defaultHunger;
    public boolean isDying = Config.allAgentsImmediatelyDieUponSpawning;
    public boolean isBlind = Config.allAgentsBlind;
    private int age = 0;
    public String name = "";
    private double sugarConsumed = 0;
    private double maxSugar = hunger;
    public static Random random = new Random();
    private ArrayList<Agent> friends = new ArrayList<Agent>();
    private int range;
    private double rateOfMetabolism = (Config.rateOfMetabolism*2*random.nextDouble())+0.1;
    private BinaryString immuneSystem;
    private BinaryString culture;
    private ArrayList<Agent> recentlySeenAgents = new ArrayList<Agent>();
    public Grid theGrid;
    public static boolean coin(){
        return (true == (random.nextDouble() < 0.5));
    }
    public String randomConsonant() {
        char[] consonants = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n',
                             'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'};

        // Generate a random index within the range of the consonant array
        int randomIndex = random.nextInt(consonants.length);
        // Get the random consonant character
        char randomConsonant = consonants[randomIndex];
        // Convert the character to a string
        String randomConsonantString = String.valueOf(randomConsonant);
        return randomConsonantString;
    }
    public String randomVowel() {
        char[] consonants = {'a','e','i','o','u','y','å','ø'};

        // Generate a random index within the range of the consonant array
        int randomIndex = random.nextInt(consonants.length);
        // Get the random consonant character
        char randomConsonant = consonants[randomIndex];
        // Convert the character to a string
        String randomConsonantString = String.valueOf(randomConsonant);
        return randomConsonantString;
    }
    private void generateName(){
        this.name = randomConsonant().toUpperCase()+randomVowel()+randomConsonant();
    };


    // public void distanceBetweenBinaryStrings(ArrayList<boolean> 1, ArrayList<boolean> 2){
    //     1+1;
    //     //return
    // }
    public Agent(int range, int x, int y, Grid theGrid){
        this.range = (int) Math.round(((double) range)*5*(double)random.nextDouble())+1;
        this.x = x;
        this.y = y;
        this.theGrid = theGrid;
        generateName();
        this.culture = new BinaryString((int) Config.culturelen);
        this.immuneSystem = new BinaryString((int) Config.immuneSystemlen);
    }
    public Agent(int range, int x, int y, BinaryString culture, BinaryString immuneSystem, Grid theGrid){
        this.range = (int) Math.round(((double) range)*5*(double)random.nextDouble())+1;
        this.x = x;
        this.y = y;
        this.theGrid = theGrid;
        generateName();
        this.culture = culture.copy().mutate();
        this.immuneSystem = immuneSystem.copy().mutate();
    }
    public Agent(int x, int y, Grid theGrid){
        this.range = (int) Math.round(((double) Config.defaultRange)*1.5*(double)random.nextDouble())+1;
        this.x = x;
        this.y = y;
        this.theGrid = theGrid;
        generateName();
        this.culture = new BinaryString((int) Config.culturelen);
        this.immuneSystem = new BinaryString((int) Config.immuneSystemlen);
    }
    public Agent(int range, Grid theGrid){
        this.range = range;
        this.x = 0;
        this.y = 0;
        this.theGrid = theGrid;
        generateName();
        this.culture = new BinaryString((int) Config.culturelen);
        this.immuneSystem = new BinaryString((int) Config.immuneSystemlen);
    }
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

        //hunger tick, look, move, eat, hunger check
        hunger -= rateOfMetabolism;
        //System.out.println("hunger is "+hunger);
        int[] mostSugar = new int[2];
        double mostSugarValue = 0.0;
        for(int i=constrain(x-range);i<constrain(x+range);i++){
            for(int k=constrain(y-range);k<constrain(y+range);k++){
                Cell cell = map[constrain(i)][constrain(k)];
                if(cell.getSugar() > mostSugarValue){
                    //System.out.println("sugarscan");
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

        if(mostSugarValue == 0.0 || isBlind){
            //System.out.println("moving randomly");
            if(Config.agentsCanMove){
                //move in a random direction
                //System.out.println("random dir");
                map[x][y].setOccupied(false);
                int xoffset = (int) Math.round(Math.random()*2)-1;
                int yoffset = (int) Math.round(Math.random()*2)-1;
                while(x+xoffset<0 || y+yoffset < 0 || x+xoffset > map.length-1 || y+yoffset > map[1].length-1){
                    xoffset = (int) Math.round(Math.random()*2)-1;
                    yoffset = (int) Math.round(Math.random()*2)-1;
                }
                x += xoffset;
                y += yoffset;
                map[x][y].setOccupied(true, this);

            }
            double sugarEaten = map[x][y].eatSugar();
            hunger += sugarEaten;
            sugarConsumed = sugarEaten;
        }else{
            //System.out.println("moving algorithmically");
            map[x][y].setOccupied(false);

            x = mostSugar[0];
            y = mostSugar[1];
            //System.out.println("moving to "+x+", "+y);
            hunger += map[mostSugar[0]][mostSugar[1]].eatSugar();
            map[mostSugar[0]][mostSugar[1]].setOccupied(true, this);
        }
        //System.out.println("hunger: "+hunger);
        if(hunger < 0.0){
            isDying = true;
            return false;
        }else if((int) age > (int) Config.maxAge){
            isDying = true;hunger = 0.0;map[x][y].setSugar(map[x][y].getSugar()+(double) Math.max(hunger, 0.0));
            return false;
        }else if(hunger > 100.0 && !map[x][y-2].occupied){
            Agent temp = new Agent(range, x,y-2, culture, immuneSystem, theGrid);
            theGrid.addAgent(temp);
            theGrid.livingAgents.add(temp);
            return true;
        }else{
            return true;
        }


    }
    public void die(Grid grid){
        isDying = true;
    }
}
