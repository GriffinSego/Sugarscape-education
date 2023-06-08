import java.util.Random;

public class Agent {
    public int x;
    public int y;
    private int range;
    private double hunger = Config.defaultHunger;
    public boolean isDying = Config.allAgentsImmediatelyDieUponSpawning;
    public boolean isBlind = Config.allAgentsBlind;
    private int age = 0;
    public String name = "";
    private double sugarConsumed = 0;
    private double maxSugar = hunger;
    Random random = new Random();
    private boolean[] immuneSystem = new boolean[1];
    private boolean[] culture = new boolean[1];
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
    public Agent(int range, int x, int y){
        this.immuneSystem[0] = ((random.nextDouble() < 0.5 ? 1 : 0) == 1);
        this.culture[0] = ((random.nextDouble() < 0.5 ? 1 : 0) == 1);
        this.range = range;
        this.x = x;
        this.y = y;
        generateName();

    }
    public Agent(int range){
        this.range = range;
        this.x = 0;
        this.y = 0;
    }
    public boolean doTick(Cell[][] map){
        if(!isDying){
            age++;
            if(maxSugar < hunger){
                maxSugar = hunger;
            }
        }
        //hunger tick, look, move, eat, hunger check
        hunger -= Config.rateOfMetabolism;
        int[] mostSugar = new int[2];
        double mostSugarValue = 0.0;
        for(int i=x-range;i>x+range;i++){
            for(int k=y-range;k>y+range;k++){
                if(map[i][k].getSugar() > mostSugarValue){
                    //System.out.println("sugarscan");
                    mostSugar[0] = i; mostSugar[1] = k;
                    mostSugarValue = map[i][k].getSugar();
                }
            }
        }
        if(mostSugarValue == 0.0 || isBlind){
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
            //System.out.println("Agent died");

            return false;
        }else{return true;}
    }
    public void die(Grid grid){
        isDying = true;
    }
}
