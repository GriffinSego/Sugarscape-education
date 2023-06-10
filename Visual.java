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
    public static ArrayList<AgentLabel> labels = new ArrayList<AgentLabel>();
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

        //System.out.println("labels size from vis"+labels.size());
        for(AgentLabel l : labels){
            l.updateLabel();
            //System.out.println("drawing label");
            g.setColor(Config.labelBgColor);
            g.fillRect(round(d(l.x)*segmentWidth), round(d(l.y)*segmentHeight)-20, 40, 20);
            g.setColor(Config.labelTextColor);
            g.drawString(l.text, round(d(l.x)*segmentWidth), round(d(l.y)*segmentHeight));
        }
        //labels.clear();
    }
    public void updateData(Pixel[][] data){
        this.data = data;
    }
}
