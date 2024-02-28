import java.util.ArrayList;

//main.java
public class Main {
    //main admin methods
    public static void checkEndstate(){
        if(tick>Config.maxTicks || !theGrid.survivors){
            System.out.println("game ending, all agents dead or time ran out");
            System.out.println("living agents: "+theGrid.livingAgents);
            System.out.println("dying agents: "+theGrid.deadAgents);
            try{
                Thread.sleep(Config.windowRemainOpenTimeAfterDeath);
            } catch(Exception e){}
            System.exit(0);
        }
    }
    private static void spawnAgents(){
        for(int i = 0; i < Config.desiredNumOfAgents; i++){
            Agent temp = new Agent(i+((int) Math.round(Config.gridWidth*0.5)),i+((int) Math.round(Config.gridHeight*0.5)), theGrid);
            theGrid.addAgent(temp);
        }
    }
    private static void tickSleep(){
        try{
            Thread.sleep(Config.simulationSpeed);
        } catch(Exception e){}
    }
    //main public variable declarations (only accessible outside of Main.java through passed references)
    public static int tick;
    public static Grid theGrid;
    public static Visual visuals;
    public static void main(String[] args){//execution flow
        //instantiate gamespace grid and graphic handler
        theGrid = new Grid(Config.gridWidth,Config.gridHeight);
        visuals = new Visual(theGrid.toImageData());
        //tickloop setup
        boolean alive = true;
        spawnAgents();
        tick = 0;
        while(alive){//tickloop
            tick++;
            theGrid.doTick();
            visuals.updateData(theGrid.toImageData());
            visuals.repaint();

            //clean up and reconcile changes from tick cycle
            checkEndstate();
            tickSleep();
        }
    }
}
