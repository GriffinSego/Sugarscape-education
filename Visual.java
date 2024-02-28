import java.awt.Graphics;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.*;
import java.util.ArrayList;


class Pixel {
    private int rint(double x){
        return (int) Math.round(x);
    }
    private int limit(int x){
        int newX = x;
        if(newX<0) newX=0;
        if(newX>254) newX=254;
        return newX;
    }
    public Color color;
    public Pixel(int[] color) {
        this.color = new Color(Math.min(color[0],255),Math.min(color[1],255),Math.min(color[2],255));
    }
    public Pixel(int color) {
        int minval = Math.min(color,255);
        this.color = new Color(minval,minval,minval);
    }
    public Pixel(Color color){
        this.color = color;
    }
    public Pixel(int r, int g, int b){
        this.color = new Color(limit(r),limit(g),limit(b));
    }
    public Pixel(double r, double g, double b){
        this.color = new Color(limit(rint(r)),limit(rint(g)),limit(rint(b)));
    }
}

public class Visual extends JPanel {
    private Pixel[][] data;
    private int x = Config.windowWidth;
    private int y = Config.windowHeight+50;
    private double segmentWidth = ((double) Config.windowWidth/(double) Config.gridWidth);
    private double segmentHeight = ((double) Config.windowHeight/(double) Config.gridHeight);

    public Visual(Pixel[][] data){
        this.data = data;
        SwingUtilities.invokeLater(() -> {
            this.setBackground(Color.GREEN.darker());
            var frame = new JFrame("title name string text");
            frame.setUndecorated(true);
            frame.setSize(x, y);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(this, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }

    private double d(int abc){
        return (double) abc;
    }
    private int round(double abc){
        return (int) Math.round(abc);
    }
    private int ceil(double abc){
        return (int) Math.ceil(abc);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        // g.fillRect(/*x,y,xl,yl*/)
        for(int i = 0; i < Config.gridWidth; i++){
            for(int k = 0; k < Config.gridHeight; k++){
                g.setColor(data[i][k].color);
                g.fillRect(round(segmentWidth*d(i)),round(segmentHeight*d(k)),ceil(segmentWidth),ceil(segmentHeight));
            }
        }
        for(int i = 0; i < Grid.livingAgents.size(); i++){
            Agent a = Grid.livingAgents.get(i);
            g.setColor(Config.labelBgColor);
            g.fillRect(round(d(a.x)*segmentWidth), round(d(a.y)*segmentHeight)-28, 25, 15);
            g.setColor(Config.labelTextColor);
            g.drawString(a.name, round(d(a.x)*segmentWidth), round(d(a.y)*segmentHeight)-16);
        }
        for(int i = 0; i < Grid.deadAgents.size(); i++){
            Agent a = Grid.deadAgents.get(i);
            g.setColor(Config.corpseColorColor);
            g.fillRect(round(d(a.x)*segmentWidth), round(d(a.y)*segmentHeight)-28, 25, 15);
            g.setColor(Config.labelTextColor);
            g.drawString(a.name, round(d(a.x)*segmentWidth), round(d(a.y)*segmentHeight)-16);
        }
        g.setColor(Color.BLACK);
        g.fillRect(0, Config.windowHeight, Config.windowWidth, 50);
        g.setColor(Color.WHITE);
        g.drawString("Agents alive: "+Grid.livingAgents.size(), 25, Config.windowHeight+45);
        g.drawString("Agents decaying: "+Grid.deadAgents.size(), 150, Config.windowHeight+45);
        g.drawString("Agents dead: "+Grid.deaths, 325, Config.windowHeight+45);

    }
    public void updateData(Pixel[][] data){
        this.data = data;
    }
}
