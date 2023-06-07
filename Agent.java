public class Agent {
    public int x;
    public int y;
    private int range;
    private double hunger = 5.0;
    public boolean isDying = false;
    public Agent(int range, int x, int y){
        this.range = range;
        this.x = x;
        this.y = y;
    }
    public Agent(int range){
        this.range = range;
        this.x = 0;
        this.y = 0;
    }
    public boolean doTick(Cell[][] map){
        //hunger tick, look, move, eat, hunger check
        hunger -= 5.0;
        int[] mostSugar = new int[2];
        double mostSugarValue = 0.0;
        for(int i=x-range;i>x+range;i++){
            for(int k=y-range;k>y+range;k++){
                if(map[i][k].getSugar() > mostSugarValue){
                    System.out.println("sugarscan");
                    mostSugar[0] = i; mostSugar[1] = k;
                    mostSugarValue = map[i][k].getSugar();
                }
            }
        }
        if(mostSugarValue == 0.0){
            //move in a random direction
            System.out.println("random dir");
            map[x][y].setOccupied(false);
            int xoffset = (int) Math.round(Math.random()*2)-1;
            int yoffset = (int) Math.round(Math.random()*2)-1;
            while(x+xoffset<0 || y+yoffset < 0 || x+xoffset > map.length-1 || y+yoffset > map[1].length-1){
                xoffset = (int) Math.round(Math.random()*2)-1;
                yoffset = (int) Math.round(Math.random()*2)-1;
            }
            x += xoffset;
            y += yoffset;
            map[x][y].setOccupied(true, this);
            hunger += map[x][y].eatSugar();
        }else{

            map[x][y].setOccupied(false);
            x = mostSugar[0];
            y = mostSugar[1];
            System.out.println("moving to "+x+", "+y);
            hunger += map[mostSugar[0]][mostSugar[1]].eatSugar();
            map[mostSugar[0]][mostSugar[1]].setOccupied(true, this);
        }
        System.out.println("hunger: "+hunger);
        if(hunger < 0.0){
            isDying = true;
            System.out.println("Agent died");

            return false;
        }else{return true;}
    }
    public void die(Grid grid){
        isDying = true;
    }
}
