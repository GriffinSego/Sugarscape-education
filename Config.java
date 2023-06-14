import java.awt.Color;

public class Config {
    //general
    public static int maxTicks = 50000;
    public static int windowRemainOpenTimeAfterDeath = 15000;//ms
    public static int desiredNumOfAgents = 50;
    public static int simulationSpeed = 100;//ms per frame
    //grid
    public static int gridWidth = 200;//does not affect window size
    public static int gridHeight = 200;
    public static double initialSugarValuePerTile = 10.0;
    public static double regenRate = 0.001;//rate of sugar regrowth per tick
    public static double MAX_SUGAR = 10.0;//do not change
    //agent behavior
    public static boolean allAgentsBlind = false;
    public static boolean allAgentsImmediatelyDieUponSpawning = false;
    public static boolean agentsCanMove = true;
    public static int maxAge = 1500;
    //agent traits
    public static double rateOfMetabolism = 0.5;
    public static double defaultHunger = 5.0;
    public static int culturelen = 5;
    public static int immuneSystemlen = 5;
    public static int defaultRange = 5;
    //window
    public static int windowWidth = 500;
    public static int windowHeight = 500;
    public static Color labelBgColor = new Color(180,180,180);
    public static Color labelTextColor = new Color(0,0,255);
}
