package kth.ag2411.project.ui.panel;

import kth.ag2411.mapalgebra.MapPanel;
import kth.ag2411.project.ui.UiConsts;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;



public class DisplayPanel extends JPanel {
    private Dimension preferredSize = new Dimension(400, UiConsts.MAIN_WINDOW_HEIGHT);;
    public JPanel mapContainer;

    public DisplayPanel() {
        initialize();
    }

    private void initialize() {
        this.setBackground(Color.WHITE);
        this.setBorder(new TitledBorder(null, "Viewer", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        this.setPreferredSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setBackground(UiConsts.WORKSPACE_BG_COLOR);
        this.setLayout(new BorderLayout());

        mapContainer = new JPanel();
        mapContainer.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        this.add(mapContainer, BorderLayout.CENTER);

    }

    public void updateImage(MapPanel map) {
        mapContainer.removeAll();

        GridBagLayout layout = new GridBagLayout();
        mapContainer.setLayout(layout);
        mapContainer.add(map, new GridBagConstraints(0, 2, 0, 0, 1.0, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        DisplayPanel displayPanel = new DisplayPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(displayPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
