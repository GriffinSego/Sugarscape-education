import java.util.ArrayList;

//main.java
public class Main {
    public static Grid theGrid;
    public static void main(String[] args){
        theGrid = new Grid(Config.gridWidth,Config.gridHeight);
        Visual v = new Visual(theGrid.toData());
        int desiredAgents = Config.desiredNumOfAgents;
        for(int i = 0; i <= desiredAgents; i++){
            Agent temp = new Agent(i+((int) Math.round(Config.gridWidth*0.5)),i+((int) Math.round(Config.gridHeight*0.5)), theGrid);
            theGrid.addAgent(temp);
        }
        boolean alive = true;
        int maxTicks = Config.maxTicks;
        int tick = 0;
        while(alive){
            theGrid.doTick();
            for(Agent agent : Grid.livingAgents){
                agent.doTick();
            }

            ArrayList<Agent> dyingAgents = new ArrayList<Agent>();
            for(Agent agent : Grid.livingAgents){
                if(agent.isDying){
                    dyingAgents.add(agent);
                    agent.die(theGrid);
                }
            }
            Grid.deadAgents.addAll(dyingAgents);
            Grid.livingAgents.removeAll(dyingAgents);
            tick++;
            if(tick>maxTicks || Grid.livingAgents.size() == 0){
                System.out.println("game ending, all agents dead or time ran out");
                try{
                    Thread.sleep(Config.windowRemainOpenTimeAfterDeath);
                } catch(Exception e){}
                System.exit(0);
                break;
            }
            v.updateData(theGrid.toData());
            v.repaint();
            try{
                Thread.sleep(Config.simulationSpeed);
            } catch(Exception e){}
        }
    }
}
