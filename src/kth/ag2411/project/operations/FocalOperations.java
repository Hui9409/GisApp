package kth.ag2411.project.operations;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import kth.ag2411.mapalgebra.Layer;
import kth.ag2411.project.MainFrame;

public class FocalOperations extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JButton btnBrowse;
	private JTextField textField_2;
	private JButton btnBrowse_1;
	private JButton btnRun;
	private JProgressBar progressBar;
	private JTextField textField_1;
	private MainFrame mf;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			FocalOperations dialog = new FocalOperations();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	
	public FocalOperations(MainFrame mf) {
		this.mf = mf;
	}
	
	public FocalOperations() {
		
	}
	
	public void init() {
		setTitle("Focal Operations");
		setBounds(100, 100, 470, 278);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblRaster = new JLabel("Raster Input:");
		JLabel lblRaster_1 = new JLabel("Raster Output:");

		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileFilter() {
			public String getDescription() {
				return "ASCII(.txt)";
			}

			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					return f.getName().toLowerCase().endsWith(".txt");
				}
			}
		});
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setMultiSelectionEnabled(true);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showOpenDialog(FocalOperations.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.print("Selected file: " + selectedFile.getAbsolutePath());

					textField.setText(selectedFile.getAbsoluteFile().toString());
				}
			}
		});

		btnBrowse_1 = new JButton("Browse");
		btnBrowse_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showOpenDialog(FocalOperations.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.print("Selected file: " + selectedFile.getAbsolutePath());
					textField.setText(selectedFile.getAbsoluteFile().toString());
				}
			}
		});

		JLabel lblFocalOperations = new JLabel("Focal Operations: ");
		lblFocalOperations.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(
				new String[] { "Focal Variety", "Focal Percentage", "Focal Mean", "Focal Std", "Focal Drainage" }));

		progressBar = new JProgressBar();

		JLabel lblProgressBar = new JLabel("Progress Bar: ");

		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);

		JButton button_1 = new JButton("Browse");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int select = fileChooser.showSaveDialog(FocalOperations.this);
				if (select == fileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					textField_2.setText(file.getAbsoluteFile().toString());
					System.out.println("saved");
				} else
					System.out.println("cancelled");
			}
		});

		JPopupMenu popupMenu_2 = new JPopupMenu();
		popupMenu_2.setToolTipText("Apply a focal operation on Raster 1");
		addPopup(contentPanel, popupMenu_2);

		JMenuItem mntmApplyALocal = new JMenuItem("Apply a local operation on Raster 1 by overlaying Raster 2");
		popupMenu_2.add(mntmApplyALocal);

		JLabel lblRadiusOfNeighbour = new JLabel("Radius Neighbour:");
		lblRadiusOfNeighbour.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));

		JLabel lblShapeOfNeighbour = new JLabel("Neighbour Shape:");
		lblShapeOfNeighbour.setMinimumSize(new Dimension(70, 14));
		lblShapeOfNeighbour.setMaximumSize(new Dimension(70, 14));
		lblShapeOfNeighbour.setPreferredSize(new Dimension(70, 14));
		lblShapeOfNeighbour.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));

		textField_1 = new JTextField("1");
		textField_1.setColumns(10);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "Square", "Circle" }));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblRaster_1)
										.addComponent(lblRaster))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textField)
										.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblFocalOperations)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(button_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnBrowse_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblProgressBar)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblShapeOfNeighbour, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(comboBox_1, 0, 90, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(lblRadiusOfNeighbour, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGap(25)))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(16)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblRaster_1)
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(btnBrowse_1)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblRaster)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(button_1)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(19)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFocalOperations)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblShapeOfNeighbour, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRadiusOfNeighbour)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
						.addComponent(lblProgressBar))
					.addGap(9))
		);

		JPopupMenu popupMenu_3 = new JPopupMenu();
		popupMenu_3.setToolTipText("The operation result is returned through an output file (ASCII format).");
		addPopup(textField_2, popupMenu_3);

		JMenuItem mntmTheOperationResult = new JMenuItem(
				"The operation result is returned through an output file (ASCII format)\r\n");
		popupMenu_3.add(mntmTheOperationResult);

		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setToolTipText("Perform a search through the filesystem");
		addPopup(btnBrowse_1, popupMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Perform a search through the filesystem");
		popupMenu.add(mntmNewMenuItem);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			btnRun = new JButton("Run");
			btnRun.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					progressBar.setValue(0);
					progressBar.setStringPainted(true);
					progressBar.setMaximum(100);
					for (int q = 0; q <= 100; q+=10) {
						progressBar.setValue(q);
						progressBar.setString("Successful...");
						}


					String method = (String)comboBox.getSelectedItem();
					Layer inLayer = new Layer(method+" In",textField.getText());
					String neiShape = (String)comboBox_1.getSelectedItem();

					Layer outLayer = null;
					String outLayerName = method + neiShape+ " Out";
					if (neiShape == "Circle") {
						if (method == "Focal Variety") {
							outLayer = inLayer.focalVariety(Integer.parseInt(textField_1.getText()), false, outLayerName);}
						if (method == "Focal Std") {
							outLayer = inLayer.focalStd(Integer.parseInt(textField_1.getText()), false, outLayerName);}
						if (method == "Focal Percentage") {
							outLayer = inLayer.focalStd(Integer.parseInt(textField_1.getText()), false, outLayerName);}
						if (method == "Focal Drainage") {
							outLayer = inLayer.focalDrainage(Integer.parseInt(textField_1.getText()), false, outLayerName);}
						if (method == "Focal Mean") {
							outLayer = inLayer.focalMean(Integer.parseInt(textField_1.getText()), false, outLayerName);}
					}
					
					else {
						if (method == "Focal Variety") {
							outLayer = inLayer.focalVariety(Integer.parseInt(textField_1.getText()), true, outLayerName);}
						if (method == "Focal Std") {
							outLayer = inLayer.focalStd(Integer.parseInt(textField_1.getText()), true, outLayerName);}
						if (method == "Focal Percentage") {
							outLayer = inLayer.focalStd(Integer.parseInt(textField_1.getText()), true, outLayerName);}
						if (method == "Focal Drainage") {
							outLayer = inLayer.focalDrainage(Integer.parseInt(textField_1.getText()), true, outLayerName);}
						if (method == "Focal Mean") {
							outLayer = inLayer.focalMean(Integer.parseInt(textField_1.getText()), true, outLayerName);}
					}
					
					mf.displayLayer(outLayer);
					mf.previewPanel.updateImage(inLayer);
					mf.previewPanel.updateImage(outLayer);
					mf.setVisible(true);
					if (textField_2.getText().length() > 1) {
						outLayer.save(textField_2.getText());
					}
				}
				
				
				
			});
			btnRun.setActionCommand("OK");
			buttonPane.add(btnRun);

			JPopupMenu popupMenu_1 = new JPopupMenu();
			popupMenu_1.setToolTipText("Perform the specified computation");
			addPopup(btnRun, popupMenu_1);

			JMenuItem mntmNewMenuItem_1 = new JMenuItem("Perform the specified computation");
			popupMenu_1.add(mntmNewMenuItem_1);
			{
				JButton okButton = new JButton("Cancel");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);

				JPopupMenu popupMenu_4 = new JPopupMenu();
				popupMenu_4.setToolTipText("Terminate and Exit the process..");
				addPopup(okButton, popupMenu_4);

				JMenuItem mntmTerminateAndExit = new JMenuItem("Terminate and Exit the process..");
				popupMenu_4.add(mntmTerminateAndExit);
			}
			{
				JButton HelpButton = new JButton("Help");
				HelpButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + System.getProperty("user.dir") +
									"/src/kth/ag2411/project/WindowBuilder/resources/Help Manual.pdf");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Check your file details..");
						}
					}
				});
				buttonPane.add(HelpButton);
			}
		}
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	public void run() {
		init();
	}
}
