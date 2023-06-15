import java.awt.Graphics;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.*;
import java.util.ArrayList;


class Pixel {
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
}

public class Visual extends JPanel {
    private Pixel[][] data;
    private int x = Config.windowWidth;
    private int y = Config.windowHeight;
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

        for(Agent a : Grid.livingAgents){
            g.setColor(Config.labelBgColor);
            g.fillRect(round(d(a.x)*segmentWidth), round(d(a.y)*segmentHeight)-28, 25, 15);
            g.setColor(Config.labelTextColor);
            g.drawString(a.name, round(d(a.x)*segmentWidth), round(d(a.y)*segmentHeight)-16);
        }
    }
    public void updateData(Pixel[][] data){
        this.data = data;
    }
}
