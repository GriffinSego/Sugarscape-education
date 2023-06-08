import java.util.ArrayList;

//main.java
public class Main {

    public static void main(String[] args){
        ArrayList<Agent> livingAgents = new ArrayList<Agent>();
        ArrayList<Agent> deadAgents = new ArrayList<Agent>();

        Grid a = new Grid(Config.gridWidth,Config.gridHeight);
        Visual v = new Visual(a.toData());
        int desiredAgents = Config.desiredNumOfAgents;
        for(int i = 0; i < desiredAgents; i++){
            Agent temp = new Agent(3,i,i);
            a.addAgent(temp);
            livingAgents.add(temp);
        }
        boolean alive = true;
        int maxTicks = Config.maxTicks;
        int tick = 0;
        while(alive){
            a.doTick();
            //System.out.println("clearing labels");

            for(Agent agent : livingAgents){
                agent.doTick(a.map);
                Visual.labels.add(new Label(agent.x, agent.y-2, agent.name));
                //System.out.println("agent doTick");
            }
            System.out.println("label length"+Visual.labels.size());
            ArrayList<Agent> dyingAgents = new ArrayList<Agent>();
            for(Agent agent : livingAgents){
                if(agent.isDying){
                    dyingAgents.add(agent);
                    agent.die(a);
                }
            }
            deadAgents.addAll(dyingAgents);
            livingAgents.removeAll(dyingAgents);
            tick++;
            if(tick>maxTicks || livingAgents.size() == 0){
                try{
                    Thread.sleep(Config.windowRemainOpenTimeAfterDeath);
                } catch(Exception e){}
                System.exit(0);
                break;
            }
            v.updateData(a.toData());
            v.repaint();
            try{
                Thread.sleep(Config.simulationSpeed);
            } catch(Exception e){}
        }


    }
}
