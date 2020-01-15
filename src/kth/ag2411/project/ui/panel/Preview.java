package kth.ag2411.project.ui.panel;

import kth.ag2411.mapalgebra.MapPanel;
import kth.ag2411.mapalgebra.Layer;
import kth.ag2411.project.MainFrame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Preview extends JPanel {
    public JScrollPane previewScroll;
    public JPanel previewContainer;
    private int cnt = 2;
    private JPanel infoPanel;
    public MainFrame mf;
    public JLabel label;
    private JButton btnClear;

    public Preview(MainFrame mf) {
        this.mf = mf;
        initComponents();
    }

    public void initComponents() {
        setName("Preview");
        setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Preview",
                TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
        setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        btnClear = new JButton("Clear");

        previewContainer = new JPanel();

        previewScroll = new JScrollPane();
        previewScroll.add(previewContainer);
        previewScroll.setViewportView(previewContainer);
        previewScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        previewScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        previewScroll.setSize(200, 100);
        previewContainer.setPreferredSize(new Dimension(previewScroll.getWidth()-50, previewScroll.getHeight()));

        JLabel appInfo = new JLabel();
        appInfo.setIcon(new ImageIcon(mf.getClass().getResource("/kth/ag2411/project/resources/KTH_100.png")));

        JSeparator separator = new JSeparator();

        JLabel associateInfo1 = new JLabel("In Academic association with ");
        associateInfo1.setFont(new Font("Georgia", Font.PLAIN, 10));

        JLabel associateInfo2 = new JLabel("KTH Royal Institute of Technology");
        associateInfo2.setFont(new Font("Georgia", Font.PLAIN, 10));

        infoPanel = new JPanel();
        BoxLayout layout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);

        associateInfo1.setAlignmentX(Component.CENTER_ALIGNMENT);
        associateInfo2.setAlignmentX(Component.CENTER_ALIGNMENT);
        appInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(associateInfo1);
        infoPanel.add(associateInfo2);
        infoPanel.add(separator);
        infoPanel.add(appInfo);
        infoPanel.setLayout(layout);
        this.add(btnClear, BorderLayout.NORTH);
        this.add(infoPanel, BorderLayout.SOUTH);
        this.add(previewScroll, BorderLayout.CENTER);
    }

    public void updateImage(MapPanel map) {
//        map.setPreferredSize(new Dimension(map.image.getWidth(), map.image.getHeight()));
        label = new JLabel(map.layer.name, JLabel.CENTER);
        label.setIcon(new ImageIcon(map.image));
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        previewContainer.add(label);
        previewContainer.setPreferredSize(new Dimension(previewScroll.getWidth()-50, map.image.getHeight() * cnt + 50));
        previewContainer.revalidate();
        cnt++;

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Double click
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    System.out.println("Double click on preview");
                    e.consume();
                    previewContainer.remove(label);
                }

                // Single click
                if (e.getClickCount() == 1 && e.isConsumed()) {
                    System.out.println("Double click on preview");
                    MapPanel mapPanel = new MapPanel(map.image, 5, map.layer);
                    mf.displayPanel.updateImage(mapPanel);
                    mf.setVisible(true);
                }
            }
        });
    }

    public void updateImage(Layer layer) {
        BufferedImage image = layer.toImage();
        label = new JLabel(layer.name, JLabel.CENTER);
        label.setIcon(new ImageIcon(image));
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        previewContainer.add(label);
        previewContainer.setPreferredSize(new Dimension(previewScroll.getWidth()-50, image.getHeight() * cnt + 50));
        previewContainer.revalidate();
        cnt++;

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Double click
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    previewContainer.remove(e.getComponent());
                    previewContainer.repaint();
                    mf.setVisible(true);
                }

                // Single click
                if (e.getClickCount() == 1 && !e.isConsumed()) {
                    MapPanel mapPanel = new MapPanel(image, 5, layer);
                    mf.displayPanel.updateImage(mapPanel);
                    mf.setVisible(true);
                }

            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                previewContainer.removeAll();
                previewContainer.repaint();
                mf.setVisible(true);
            }
        });
    }

}
