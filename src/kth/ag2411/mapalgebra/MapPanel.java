package kth.ag2411.mapalgebra;

import kth.ag2411.project.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import static kth.ag2411.project.ui.panel.CoordsPane.*;


public class MapPanel extends JPanel {
    public BufferedImage image;
    public int scale;
    public int x = 0;
    public int y = 0;
    private Point dragStart;
    private Point dragEnd;
    public Layer layer;

    public MapPanel(BufferedImage image, int scale) {
        super();
        this.image = image;
        this.scale = scale;
        this.setLayout(null);
    }

    public MapPanel(Layer layer, int scale, MainFrame mf) {
        super();
        this.layer = layer;
        this.image = layer.toImage();
        this.scale = scale;
        this.setLayout(null);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MapPanel map = new MapPanel(image, 5, layer);
                mf.displayPanel.updateImage(map);
                mf.setVisible(true);
            }
        });
    }



    public MapPanel(BufferedImage image, int scale, Layer layer) {
        super();
        this.image = image;
        this.scale = scale;
        this.layer = layer;
        this.setLayout(null);
        int w = image.getWidth() * scale;
        int h = image.getHeight() * scale;
        this.setBounds(0, 0, w, h);
        this.setBorder(new EmptyBorder(50, 50, 50, 50));


        /**
         *  Zoom in & Zoom out
         */
        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                MapPanel mapPanel = (MapPanel) e.getSource();
                if (e.getWheelRotation() == 1) {
                    mapPanel.scale += 1;
                    mapPanel.x = 0;
                    mapPanel.y = 0;
                    mapPanel.repaint();
                }

                if ((e.getWheelRotation()) == -1 && (mapPanel.scale != 1)) {
                    mapPanel.scale -= 1;
                    mapPanel.x = 0;
                    mapPanel.y = 0;
                    mapPanel.repaint();
                }
            }
        });


        /**
         * mouse press to drag image
         */
        // TODO：continuous drag doesn't work well
        this.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                dragStart=e.getPoint();
                dragEnd=null;
            }
        });


        this.addMouseMotionListener(new MouseInputAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                dragEnd = e.getPoint();
                int dx =(int) (dragEnd.getX() - dragStart.getX());
                int dy = (int) (dragEnd.getY() - dragStart.getY());
                MapPanel mapPanel = (MapPanel) e.getSource();
                mapPanel.x = mapPanel.getX() + dx;
                mapPanel.y = mapPanel.getY() + dy;
                mapPanel.repaint();
            }

            /**
            * Show mouse location
            */
            @Override
            public void mouseMoved(MouseEvent e) {
                MapPanel mapPanel = (MapPanel) e.getSource();
                x = e.getX();
                y = e.getY();
                int row = (int) (y/(double) mapPanel.scale);
                int col = (int) (x/(double) mapPanel.scale);

                xyLabel.setText("(" + x + "," + y + ")");
                lblImgName.setText("Layer: " + mapPanel.layer.name);
                rcLabel.setText("(" + row + "," + col + ")");
                try {
                    value.setText("value = " + mapPanel.layer.values[row][col]);
                } catch (ArrayIndexOutOfBoundsException ae) {
                    value.setText(" value = null ");
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, x, y, image.getWidth() * scale, image.getHeight() * scale, this);

    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        Layer layer = new Layer("layer", "./src/data/input/elevation.txt");
        BufferedImage image = layer.toImage();
        MapPanel mapPanel = new MapPanel(image, 1);
        jFrame.add(mapPanel);
        jFrame.setVisible(true);
    }

}
