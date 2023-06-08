import java.awt.Graphics;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.*;
import java.util.ArrayList;



class Pixel {
    public int[] color = new int[3];
    public Pixel(int[] color) {
        this.color = color;
    }
    public Pixel(int color) {
        this.color = new int[]{color,color,color};
    }
}

public class Visual extends JPanel {
    private Pixel[][] data;
    private int x = Config.windowWidth;
    private int y = Config.windowHeight;
    public static ArrayList<Label> labels = new ArrayList<Label>();
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
                g.setColor(new Color(Math.min(data[i][k].color[0], 255),Math.min(data[i][k].color[1], 255),Math.min(data[i][k].color[2], 255)));
                g.fillRect(round(segmentWidth*d(i)),round(segmentHeight*d(k)),ceil(segmentWidth),ceil(segmentHeight));

            }
        }

        System.out.println("labels size from vis"+labels.size());
        for(Label l : labels){
            System.out.println("drawing label");
            g.setColor(new Color(180,180,180));
            g.fillRect(round(d(l.x)*segmentWidth), round(d(l.y)*segmentHeight)-20, 40, 20);
            g.setColor(new Color(0,0,255));
            g.drawString(l.text, round(d(l.x)*segmentWidth), round(d(l.y)*segmentHeight));
        }
        labels.clear();
    }
    public void updateData(Pixel[][] data){
        this.data = data;
    }
}
