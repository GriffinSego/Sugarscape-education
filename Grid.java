import java.lang.Math;
import java.util.Random;

//2d grid, sidelength 20
class Cell {
    private double sugar = Config.initialSugarValuePerTile;//initial value for sugar
    private boolean occupied = false;
    private Agent occupier;
    public boolean isCorpse = false;
    private double regenRate = Config.regenRate;
    protected Cell(double regenRate, boolean occupied, double sugar) {
        this.regenRate = regenRate;
        this.occupied = occupied;
        this.sugar = sugar;
    }
    protected Cell(boolean occupied, double sugar) {
        this.occupied = occupied;
        this.sugar = sugar;
    }
    public Cell(boolean occupied) {
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
    public void setOccupied(boolean occupied, Agent occupier){
        this.occupied = occupied;
        this.occupier = occupier;
    }
    public void setOccupied(boolean occupied){
        assert(occupied == false);
        this.occupied = occupied;
        this.occupier = null;
    }
    public void doTick(){
        if(!occupied){
            this.sugar = Math.min(Config.MAX_SUGAR, this.sugar+regenRate);
        }
        if(occupied){
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
    private final int w;
    private final int h;
    public Cell[][] map;
    private void generateMap(){
        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                map[x][y] = new Cell(false);
            }
        }
    }
    public Grid(int width, int height){
        w = width; h = height; this.map = new Cell[w][h]; generateMap();
    }
    public void doTick(){
        for(Cell[] a : map){
            for(Cell cell : a){
                cell.doTick();
            }
        }
    }
    public void addAgent(Agent agent){
        map[agent.x][agent.y].setOccupied(true, agent);
    }
    public Pixel[][] toData(){
        Pixel[][] newData = new Pixel[w][h];
        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                Cell cell = map[x][y];
                if(cell.getOccupied()){
                    if(cell.isCorpse){
                        newData[x][y] = new Pixel(new int[]{255,0,0});
                    }else{
                        newData[x][y] = new Pixel(new int[]{0,255,0});
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
