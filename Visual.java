import java.awt.Graphics;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.*;
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
    private int x = 500;
    private int y = 500;
    public Visual(Pixel[][] data){
        this.data = data;
        SwingUtilities.invokeLater(() -> {
            this.setBackground(Color.GREEN.darker());
            var frame = new JFrame("title name string text");
            frame.setSize(x, y);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(this, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        // g.fillRect(/*x,y,xl,yl*/)
        for(int i = 0; i < data.length; i++){
            for(int k = 0; k < data[0].length; k++){
                g.setColor(new Color(data[i][k].color[0],data[i][k].color[1],data[i][k].color[2]));
                g.fillRect(i*(x/data.length),k*(x/data.length),x/data.length,y/data[0].length);
            }
        }
    }
    public void updateData(Pixel[][] data){
        this.data = data;
    }
}
