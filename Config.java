import java.awt.Color;

public final class Config {
    //general
    public static final int maxTicks = 25000;
    public static final int windowRemainOpenTimeAfterDeath = 1500000;//ms
    public static final int desiredNumOfAgents = 4;
    public static final int simulationSpeed = 1;//ms per frame
    //grid
    public static final int gridWidth = 200;//does not affect window size
    public static final int gridHeight = 200;
    public static final double initialSugarValuePerTile = 10.0;
    public static final double initialSpiceValuePerTile = 1.0;
    public static final double regenRate = 0.05;//rate of sugar regrowth per tick
    public static final double MAX_SUGAR = 10.0;//do not change
    public static final double MAX_SPICE = 2.0;//do not change
    public static final double corpseDecayRate = 0.1;//percent of decomposition per tick
    //agent behavior
    public static final boolean allAgentsBlind = false;
    public static final boolean allAgentsImmediatelyDieUponSpawning = false;
    public static final boolean agentsCanMove = true;
    public static final int maxAge = 1500;
    //agent traits
    public static final double rateOfMetabolism = 0.5;
    public static final double spiceMetabolism = 0.01;
    public static final double defaultHunger = 5.0;
    public static final double defaultSpice = 5.0;
    public static final int culturelen = 5;
    public static final int immuneSystemlen = 5;
    public static final int defaultRange = 5;
    //window
    public static final int windowWidth = 500;
    public static final int windowHeight = 500;
    //colors
    public static final Color labelBgColor = new Color(180,180,180);
    public static final Color labelTextColor = new Color(0,0,0);
    public static final Pixel corpseColor = new Pixel(new Color(50,0,255));
    public static final Color corpseColorColor = new Color(50,0,255);
    public static final Pixel bodyColor = new Pixel(new Color(0,255,0));

}
