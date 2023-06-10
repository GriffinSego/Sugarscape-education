public class AgentLabel extends Label {
    public int x;
    public int y;
    public String text;
    public Agent agent;
    public AgentLabel(Agent agent){
        super(agent.x,agent.y,agent.name);
        this.agent = agent;
    }
    void updateLabel(){
        x = agent.x; y = agent.y;
        text = agent.name;
    }
}
