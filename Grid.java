import java.lang.Math;
import java.util.Random;
//2d grid, sidelength 20
protected class Cell {
    private double sugar = 10.0;//initial value for sugar
    private boolean occupied = false;
    private double regenRate = 1.0;
    protected Cell(double regenRate, boolean occupied, double sugar) {
        this.regenRate = regenRate;
        this.occupied = occupied;
        this.sugar = sugar;
    }
    protected Cell(boolean occupied, double sugar) {
        this.occupied = occupied;
        this.sugar = sugar;
    }
    protected Cell(boolean occupied) {
        this.occupied = occupied;
    }
    protected Cell(double regenRate, boolean occupied) {
        this.regenRate = regenRate;
        this.occupied = occupied;
    }
    public boolean getOccupied(){
        return occupied;
    }
    public double getSugar(){
        return sugar;
    }
    public double eatSugar(){
        double tempSugar = sugar;
        sugar = 0;
        return tempSugar;
    }
    public setOccupied(boolean occupied){
        this.occupied = occupied;
    }
    public void doTick(){
        if(!occupied){
            this.sugar = Math.min(10.0, this.sugar+regenRate);
        }
    }
}
//A 2d grid of Cell to hold the sugar values of the map
public class Grid {
    private final int w;
    private final int h;
    private Cell[][] map;
    private Cell generateMap(){
        for(Cell[] a : map){
            for(Cell cell : a){
                cell = new Cell(false);
            }
        }
    }
    public static Grid(int width, int height){
        w = width; h = height; this.map = new Cell[w][h]; generateMap();
    }
    public void doTick(){
        for(Cell[] a : map){
            for(Cell cell : a){
                cell.doTick();
            }
        }
    }
}
