import java.awt.Color;

public class Config {
    //general
    public static int maxTicks = 50000;
    public static int windowRemainOpenTimeAfterDeath = 15000;//ms
    public static int desiredNumOfAgents = 4;
    public static int simulationSpeed = 50;//ms per frame
    //grid
    public static int gridWidth = 200;//does not affect window size
    public static int gridHeight = 200;
    public static double initialSugarValuePerTile = 10.0;
    public static double regenRate = 0.005;//rate of sugar regrowth per tick
    public static double MAX_SUGAR = 10.0;//do not change
    //agent behavior
    public static boolean allAgentsBlind = true;
    public static boolean allAgentsImmediatelyDieUponSpawning = false;
    public static boolean agentsCanMove = true;
    //agent traits
    public static double rateOfMetabolism = 0.1;
    public static double defaultHunger = 5.0;
    public static int culturelen = 5;
    public static int immuneSystemlen = 5;
    //window
    public static int windowWidth = 500;
    public static int windowHeight = 500;
    public static Color labelBgColor = new Color(180,180,180);
    public static Color labelTextColor = new Color(0,0,255);
}
