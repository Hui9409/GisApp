package kth.ag2411.project;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.image.BufferedImage;

import kth.ag2411.mapalgebra.MapPanel;
import kth.ag2411.mapalgebra.Layer;
import kth.ag2411.project.operations.FocalOperations;
import kth.ag2411.project.operations.LocalOperations;
import kth.ag2411.project.operations.ZonalOperations;
import kth.ag2411.project.ui.UiConsts;
import kth.ag2411.project.ui.panel.*;
import kth.ag2411.project.ui.panel.MenuBar;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	public int scale = 3;

	private JPanel mainPanel;
	public DisplayPanel displayPanel;
	public Preview previewPanel;
	public WorkSpace workspacePanel;
	public JPanel coordsPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setFont(new Font("Adobe Caslon Pro", Font.BOLD, 20));
		initComponents();
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////// This method contains all the code for creating and initializing
	////////////////////////////////////////////////////////////////////////////////////////////////////////////// components
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1001, 700);

		MenuBar menuBar = new MenuBar(this);
		setJMenuBar(menuBar);

		workspacePanel = new WorkSpace();
		displayPanel = new DisplayPanel();
		previewPanel = new Preview(this);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, displayPanel, previewPanel);
        splitPane.setResizeWeight(0.95);
        splitPane.setBackground(UiConsts.SCROLL_BAR_COLOR);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, workspacePanel, splitPane);
        splitPane.setBackground(UiConsts.SCROLL_BAR_COLOR);

		coordsPane = new CoordsPane();

		mainPanel = new JPanel();
		mainPanel.setBackground(UiConsts.SCROLL_BAR_COLOR); // frame background
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(splitPane, BorderLayout.CENTER);
		mainPanel.add(coordsPane, BorderLayout.SOUTH);
		setTitle("Kringle 1.0");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(this.getClass().getResource("/kth/ag2411/project/resources/Santa_260.png")));

	}
	
	
	public void displayLayer(Layer layer) {
		BufferedImage layerImage = layer.toImage();
		MapPanel mapPanel = new MapPanel(layerImage, scale, layer);
		displayPanel.updateImage(mapPanel);
	}
	        
	// specific operations 
	public void localOperation() {
		LocalOperations lo = new LocalOperations(this);
		lo.run();
		lo.setModal(true);
		lo.setVisible(true);
	}
	
	public void focalOperation() {
		FocalOperations fo = new FocalOperations(this);
		fo.run();
		fo.setModal(true);
		fo.setVisible(true);
	}
	
	public void zonalOperation() {
		ZonalOperations zo = new ZonalOperations(this);
		zo.run();
		zo.setModal(true);
		zo.setVisible(true);
	}
}