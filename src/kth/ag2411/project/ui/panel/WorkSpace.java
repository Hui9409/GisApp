package kth.ag2411.project.ui.panel;

import kth.ag2411.mapalgebra.Layer;
import kth.ag2411.mapalgebra.MapPanel;
import kth.ag2411.project.MainFrame;
import kth.ag2411.project.ui.UiConsts;
import kth.ag2411.project.ui.fileTree.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class WorkSpace extends JPanel {
    public JPanel filePane;
    public FileTree fileTree;
    public JScrollPane workTree;
    public String currentDir = null;
    private Dimension preferredSize = new Dimension(200, UiConsts.MAIN_WINDOW_HEIGHT);
    public MainFrame mf;
    private static int scale = 5;

    public WorkSpace() {
        initialize();
    }

    public WorkSpace(String dir) {
        initialize();
        addJTree(dir, mf);
    }

    private void initialize() {
        this.setPreferredSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setLayout(new BorderLayout());

        setBorder(new TitledBorder(null, "Contents", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        setName("Contents");

        filePane = new JPanel();
        this.add(filePane, BorderLayout.CENTER);


    }


    public void addJTree(String dir, MainFrame mf) {
        this.mf = mf;
        this.currentDir = dir;
        fileTree = new FileTree(dir);
        fileTree.setDragEnabled(true);

        FileTreeModel model=new FileTreeModel(new DefaultMutableTreeNode(
                new FileNode("user.dir",null,null,true)), dir);
        fileTree.setModel(model);
        fileTree.setCellRenderer(new FileTreeRenderer());
        workTree = new JScrollPane(fileTree);
        workTree.setLayout(new ScrollPaneLayout());

        filePane.setLayout(new GridBagLayout());

        filePane.removeAll();
        filePane.add(workTree, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.setBackground(UiConsts.WORKSPACE_BG_COLOR);

        fileTree.addMouseListener(new MouseClickListener());
    }


    class MouseClickListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {

            if (e.getClickCount() == 2 && !e.isConsumed()) {
                e.consume();
                mouseDoubleClicked(e);
            }

            if (e.getClickCount() == 1 && !e.isConsumed()) {
            }
        }

        public void mouseSingleClicked (MouseEvent e){
        }

        public void mouseDoubleClicked (MouseEvent e){
            /**
             * Update displayPanel
             */
            FileTree fileTree = (FileTree) e.getSource();
            DefaultMutableTreeNode lastTreeNode = (DefaultMutableTreeNode) fileTree.mouseInPath.getLastPathComponent();
            FileNode fileNode = (FileNode) lastTreeNode.getUserObject();
            File file = fileNode.file;

            String name = file.getName().replaceAll("[.][^.]+$", "");
            Layer layer = new Layer(name, file.getAbsolutePath());
            BufferedImage layerImage;
            layerImage = layer.toImage();

            MapPanel mapPanel = new MapPanel(layerImage, scale, layer);
            int w = mapPanel.image.getWidth()*mapPanel.scale;
            int h = mapPanel.image.getHeight()*mapPanel.scale;
            mapPanel.setPreferredSize(new Dimension(w, h));
            mf.displayPanel.updateImage(mapPanel);
            mf.setVisible(true);

        }
    }
}


