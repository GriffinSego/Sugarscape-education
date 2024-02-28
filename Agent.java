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
    private int babiesHad = 0;
    private double hunger = Config.defaultHunger;
    private double spice = Config.defaultSpice;
    public boolean isDying = Config.allAgentsImmediatelyDieUponSpawning;
        //agent personal ArrayLists
    private ArrayList<Agent> recentlySeenAgents = new ArrayList<Agent>();
    private ArrayList<Agent> friends = new ArrayList<Agent>();
    private ArrayList<Agent> enemies = new ArrayList<Agent>();
        //agent personal BinaryStrings
    public BinaryString culture;
    private BinaryString immuneSystem;

    //agent consts
    public final boolean isBlind = Config.allAgentsBlind;

    //agent references
    public Grid theGrid;

    //agent initconsts
    public String name = "";
    private double sugarConsumed = 0;
    private double spiceConsumed = 0;
    private double maxSugar = hunger;
    private double rateOfMetabolism = (Config.rateOfMetabolism*2*random.nextDouble())+0.1;
    private int range;

    //agent constructors
        //constructor for invokation by parent
    public Agent(int x, int y, BinaryString culture, BinaryString immuneSystem, Grid theGrid, double rateOfMetabolism, int range){
        this.range = (int) Math.round(((double) range)*(double)random.nextDouble());
        this.x = x;
        this.y = y;
        this.hunger = 50;
        this.spice = 2;
        this.theGrid = theGrid;
        this.name = generateName();
        this.culture = culture.copy().mutate();
        this.immuneSystem = immuneSystem.copy().mutate();
        this.rateOfMetabolism = new BinaryString((int) Math.round(rateOfMetabolism*10)).mutate().toDouble();
        System.out.println("rom[i] "+rateOfMetabolism+" [s] "+new BinaryString((int) Math.round(rateOfMetabolism*10)).mutate()+" [nf] "+new BinaryString((int) Math.round(rateOfMetabolism*10)).mutate().toDouble()+" [si] "+this.rateOfMetabolism);
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
    public String getData(){
        // return "Agent "+name+" "
        return "Agent: {"+name+": pos: ["+x+","+y+"], hunger: "+hunger+", metabolism: "+rateOfMetabolism+", culture: "+culture+", immuneSystem: "+immuneSystem+", range: "+range+"}";
    }
    public String getName(){
        return name;
    }
    public String toString(){
        return getName();
    }
    private int constrain(int x){
        int upperBound = Config.gridWidth-1;
        int lowerBound = 0;
        return (int) Math.max(lowerBound, Math.min(x, upperBound));
    }
    private int randbetween(int x, int y){
        return (int) Math.round((Math.random()*y)-x);
    }
    private int hamiltonianDistance(int point1x, int point1y, int point2x, int point2y){
        //1, 1, 2, 3
        int dx = Math.abs(point1x - point2x);
        int dy = Math.abs(point1y - point2y);

        return (int) ((dx + dy) / 2.0);
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
        spice -= Config.spiceMetabolism;
        boolean targetSugar;
        if(rateOfMetabolism*hunger > Config.spiceMetabolism*spice){
            //prioritize spice
            targetSugar = false;
        }else{
            //prioritize sugar
            targetSugar = true;
        }
        int[] mostSugar = new int[2];
        double mostSugarValue = 0.0;
        for(int i=constrain(x-range);i<=constrain(x+range);i++){
            for(int k=constrain(y-range);k<=constrain(y+range);k++){
                int cnsx = constrain(i); int cnsy = constrain(k);
                Cell cell = map[cnsx][cnsy];
                if(targetSugar){
                    if(cell.getSugar() > mostSugarValue){
                        mostSugar[0] = cnsx; mostSugar[1] = cnsy;
                        mostSugarValue = cell.getSugar();
                    }
                }else{
                    if(cell.getSpice() > mostSugarValue){
                        mostSugar[0] = cnsx; mostSugar[1] = cnsy;
                        mostSugarValue = cell.getSpice();
                    }
                }
                if(cell.occupied){
                    Agent tempAgent = cell.occupier;
                    if(!friends.contains(tempAgent)){
                        friends.add(tempAgent);
                    }
                    if(hamiltonianDistance(x,y,cnsx,cnsy) < 2 && !cell.isCorpse && cell.occupier != null && !cell.occupier.isDying){
                        //test for reproduction
                        if(this.isCompatible(cell.occupier)){
                            this.hunger-=100;cell.occupier.hunger-=100;
                            this.spice-=1;cell.occupier.spice-=1;
                            this.babiesHad++;cell.occupier.babiesHad++;
                            Agent temp = new Agent(
                                cleanse(x-2,Config.gridWidth),
                                cleanse(y-2,Config.gridHeight),
                                this.culture.merge(cell.occupier.culture),
                                this.immuneSystem.merge(cell.occupier.immuneSystem),
                                theGrid,
                                (
                                    Math.min(
                                        (this.rateOfMetabolism+
                                        cell.occupier.rateOfMetabolism+
                                        randbetween(-6,6))
                                    ,0.08)
                                ),
                                (
                                    (int) ((
                                            this.range+
                                            cell.occupier.range)+(
                                            randbetween(-3,3)
                                        ))));
                            theGrid.addAgent(temp);
                        }
                    }
                }
            }
        }
        //run checks for agent motion and state update
        if(mostSugarValue == 0.0 || isBlind || mostSugarValue < range/100){
            if(Config.agentsCanMove){//move in a random direction (in the hopes that from there, sugar will be visible)
                map[x][y].setOccupier(null);
                int xoffset = (int) Math.round(Math.random()*2);
                int yoffset = (int) Math.round(Math.random()*2)-1;
                while(x+xoffset<0 || y+yoffset < 0 || x+xoffset > map.length-1 || y+yoffset > map[1].length-1){
                    xoffset = (int) Math.round(Math.random()*2);
                    yoffset = (int) Math.round(Math.random()*2)-1;
                }
                x += xoffset;
                y += yoffset;
                map[x][y].setOccupier(this);
            }
            double sugarEaten = map[x][y].eatSugar();
            hunger += sugarEaten;
            spice += map[x][y].eatSpice();
            sugarConsumed = sugarEaten;
        }else{//move towards the highest amount of sugar nearby
            map[x][y].setOccupier(null);
            x = mostSugar[0];
            y = mostSugar[1];
            hunger += map[mostSugar[0]][mostSugar[1]].eatSugar();
            spice += map[mostSugar[0]][mostSugar[1]].eatSpice();
            map[mostSugar[0]][mostSugar[1]].setOccupier(this);
        }
        //state check
        if(hunger < 0.0 || spice < 0.0){//starve check
            this.die();
            return false;
        }else if((int) age > (int) Config.maxAge){//old age check
            this.die();
            return false;
        }else if(false && hunger > 100.0 && !map[cleanse(x,Config.gridWidth)][cleanse(y-2,Config.gridHeight)].occupied){//birth check
            Agent temp = new Agent(cleanse(x,Config.gridWidth),cleanse(y-2,Config.gridHeight), culture, immuneSystem, theGrid, rateOfMetabolism, range);
            theGrid.addAgent(temp);
            theGrid.livingAgents.add(temp);
            return true;
        }else{
            return true;
        }
    }//end of Agent().doTick
    private int cleanse(int v, int threshold){
        if(v>threshold){
            return threshold;
        }else if(v<0){
            return 0;
        }else{
            return v;
        }
    }
    public void die(){
        BinaryString.print(this.toString());
        isDying = true;
        hunger = 0.0;
        theGrid.map[x][y].setSugar(theGrid.map[x][y].getSugar()+(double) Math.max(hunger, 0.0));
        theGrid.map[x][y].setSpice(theGrid.map[x][y].getSpice()+(double) Math.max(spice, 0.0));
    }
    public boolean isCompatible(Agent agent){
        return (
        this.hunger > 100
        && agent.hunger > 100
        && this.spice > 3
        && agent.spice > 3
        && this.babiesHad < 3
        && agent.babiesHad < 3
        && (this.age+(Config.maxAge*0.1) > agent.age-(Config.maxAge*0.1))
        && this.age > Config.maxAge*0.1
        && this.culture.getDistanceFrom(agent.culture) < 3)
        == true;
    }
}
