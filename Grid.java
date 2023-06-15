import java.lang.Math;
import java.util.Random;
import java.util.ArrayList;
import java.awt.Color;

class Cell {
    //static variables and methods
    private static SimplexNoise simplex = new SimplexNoise(100l);
    //instance variables
    private double sugar = Config.initialSugarValuePerTile;//initial value for sugar
    public boolean occupied = false;
    public Agent occupier;
    public boolean isCorpse = false;
    private final double regenRate = Config.regenRate;
    private double noise;
    protected Cell(boolean occupied, double sugar) {
        this.occupied = occupied;
        this.sugar = sugar;
    }
    public Cell(Agent occupier, double x, double y) {
        if(occupier != null){
            this.occupier = occupier;
            occupied = true;
        }
        noise = Math.max(0,(simplex.noise(x, y)*Config.MAX_SUGAR)-(0.3*Config.MAX_SUGAR));
    }
    public Cell(double x, double y) {
        noise = Math.max(0,(simplex.noise(x, y)*Config.MAX_SUGAR)-(0.3*Config.MAX_SUGAR));
    }
    public double getSugar(){
        return sugar;
    }
    public void setSugar(double newSugar){
        sugar = newSugar;
    }
    public double eatSugar(){
        double tempSugar = sugar;
        sugar = 0;
        return tempSugar;
    }
    public void setOccupier(Agent occupier){
        if(occupier != null){
            this.occupied = true;
            this.occupier = occupier;
        }else{
            this.occupied = false;
            this.occupier = null;
        }

    }
    public void doTick(){
        if(!occupied){
            this.sugar = Math.min(noise, this.sugar+regenRate);
        }else{
            if(occupier.isDying){
                isCorpse = true;
            }else{
                isCorpse = false;
            }
        }
    }
}
//A 2d grid of Cell to hold the sugar values of the map
public class Grid {
    //static methods and variables
    public static ArrayList<Agent> livingAgents;
    public static ArrayList<Agent> deadAgents;
    //instance variables
    public boolean survivors = true;
    private final int w;
    private final int h;
    public Cell[][] map;
    private void generateMap(){
        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                map[x][y] = new Cell(null, x, y);
            }
        }
    }
    public Grid(int width, int height){
        livingAgents = new ArrayList<Agent>();
        deadAgents = new ArrayList<Agent>();
        w = width; h = height; map = new Cell[w][h]; generateMap();
    }
    public void doTick(){
        //iteration cycle: update map, then agents, then environment consequences
        for(Cell[] a : map){
            for(Cell cell : a){
                cell.doTick();
            }
        }
        for(int i=0;i<livingAgents.size();i++){
            livingAgents.get(i).doTick();
            if(livingAgents.get(i).isDying){
                deadAgents.add(livingAgents.get(i));
            }
        }
        livingAgents.removeIf(agent -> (agent.isDying));
        survivors = !livingAgents.isEmpty();
    }

    public void addAgent(Agent agent){
        map[agent.x][agent.y].setOccupier(agent);
        livingAgents.add(agent);
    }
    public Pixel[][] toImageData(){
        Pixel[][] newData = new Pixel[w][h];
        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                Cell cell = map[x][y];
                if(cell.occupied){
                    if(cell.isCorpse){
                        newData[x][y] = Config.corpseColor;
                    }else{
                        newData[x][y] = Config.bodyColor;
                    }
                }else if(cell.getSugar() > 1.0){
                    newData[x][y] = new Pixel((int) (Math.round(25*cell.getSugar())));
                }else{
                    newData[x][y] = new Pixel(0);
                }
            }
        }
        return newData;
    }
}
