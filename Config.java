public class Config {
    //general
    public static int maxTicks = 50000;
    public static int windowRemainOpenTimeAfterDeath = 1500;//ms
    //grid
    public static int gridWidth = 200;//does not affect window size
    public static int gridHeight = 200;
    public static double initialSugarValuePerTile = 10.0;
    public static double regenRate = 0.5;//rate of sugar regrowth per tick
    public static double MAX_SUGAR = 10.0;//do not change
    //agent behavior
    public static boolean allAgentsBlind = true;
    public static boolean allAgentsImmediatelyDieUponSpawning = false;
    public static boolean agentsCanMove = true;
    //agent traits
    public static int desiredNumOfAgents = 10;
    public static double rateOfMetabolism = 0.5;
    public static double defaultHunger = 5.0;
    //window
    public static int windowWidth = 500;
    public static int windowHeight = 500;
}
