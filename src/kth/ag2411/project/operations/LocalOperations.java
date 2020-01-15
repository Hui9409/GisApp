package kth.ag2411.project.operations;

import java.awt.*;
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
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import kth.ag2411.mapalgebra.Layer;
import kth.ag2411.project.MainFrame;

public class LocalOperations extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton btnBrowse_1;
	private JButton btnRun;
	private JProgressBar progressBar;
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
			LocalOperations dialog = new LocalOperations();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	
	public LocalOperations() {
		init();
	}
	
	public LocalOperations(MainFrame mf) {
		this.mf = mf;
	}
	
	public void init() {
		setTitle("Local Operations");
		setBounds(100, 100, 450, 275);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblRaster = new JLabel("Raster 1:");
		JLabel lblRaster_1 = new JLabel("Raster 2:");

		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
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
				int result = fileChooser.showOpenDialog(LocalOperations.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.print("Selected file: "+ selectedFile.getAbsolutePath());
					
					textField.setText(selectedFile.getAbsoluteFile().toString());
				}
			}
		});
		
		btnBrowse_1 = new JButton("Browse");
		btnBrowse_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showOpenDialog(LocalOperations.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.print("Selected file: "+ selectedFile.getAbsolutePath());
					textField.setText(selectedFile.getAbsoluteFile().toString());
				}
			}
			});
		JButton button = new JButton("Browse");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showOpenDialog(LocalOperations.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.print("Selected file: "+ selectedFile.getAbsolutePath());
					textField_1.setText(selectedFile.getAbsoluteFile().toString());
				}
			}
			});

		JLabel lblLocalOperations = new JLabel("Local Operations: ");
		lblLocalOperations.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Local Maximum", "Local Minimum",
				"Local Sum", "Local Mean", "Local Difference", "Local Product",}));

		progressBar = new JProgressBar();

		JLabel lblProgressBar = new JLabel("Progress Bar: ");
		
		JLabel lblSaveAs = new JLabel("Save as:");
		lblSaveAs.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		
		JButton btnBrowse1 = new JButton("Browse");
		btnBrowse1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 	int select = fileChooser.showSaveDialog(LocalOperations.this);
				   if(select == fileChooser.APPROVE_OPTION)
				   {
					   File file = fileChooser.getSelectedFile();
					   textField_2.setText(file.getAbsoluteFile().toString());
					   System.out.println("saved");
				   }
				   else
					   System.out.println("cancelled");
			}
		});
		
		JPopupMenu popupMenu_2 = new JPopupMenu();
		popupMenu_2.setToolTipText("Apply a local operation on Raster 1 by overlaying Raster 2");
		addPopup(contentPanel, popupMenu_2);
		
		JMenuItem mntmApplyALocal = new JMenuItem("Apply a local operation on Raster 1 by overlaying Raster 2");
		popupMenu_2.add(mntmApplyALocal);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblRaster_1)
						.addComponent(lblRaster)
						.addComponent(lblProgressBar)
						.addComponent(lblSaveAs, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
								.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblLocalOperations)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(comboBox, 0, 131, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnBrowse1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnBrowse_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowse_1)
						.addComponent(lblRaster))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(button)
						.addComponent(lblRaster_1))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnBrowse1)
								.addComponent(lblSaveAs))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblLocalOperations))
							.addGap(28)
							.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE))
						.addComponent(lblProgressBar, Alignment.TRAILING))
					.addContainerGap())
		);
		
		JPopupMenu popupMenu_3 = new JPopupMenu();
		popupMenu_3.setToolTipText("The operation result is returned through an output file (ASCII format).");
		addPopup(textField_2, popupMenu_3);
		
		JMenuItem mntmTheOperationResult = new JMenuItem("The operation result is returned through an output file (ASCII format)\r\n");
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
				public void actionPerformed(ActionEvent e){
					progressBar.setValue(0);
					progressBar.setStringPainted(true);
					for (int q = 0; q <= 100; q++) {
						progressBar.setValue(q);
						progressBar.setString("Successful...");
					}
					progressBar.setMaximum(100);

					String operations = (String)comboBox.getSelectedItem();
					Layer layer1 = new Layer(operations+" In1",textField.getText());
					Layer layer2 = new Layer(operations+" In2",textField_1.getText());

					Layer result_layer=null;
					String outLayerName = operations + " Out";
					
					if (operations == "Local Sum") {					
						result_layer = layer1.localSum(layer2, outLayerName);
						}
					
					if (operations == "Local Mean") {					
						result_layer = layer1.localMean(layer2, outLayerName);
						}
					
					if (operations == "Local Difference"){					
						result_layer = layer1.localDifference(layer2, outLayerName);
					}
					if (operations == "Local Product") {					
						result_layer = layer1.localProduct(layer2, outLayerName);
						}
					if (operations == "Local Maximum") {					
						result_layer = layer1.localMax(layer2, outLayerName);
						}
					if (operations == "Local Minimum") {					
						result_layer = layer1.localMin(layer2, outLayerName);
						}
					mf.displayLayer(result_layer);
					mf.previewPanel.updateImage(layer1);
					mf.previewPanel.updateImage(layer2);
					mf.previewPanel.updateImage(result_layer);

					if (textField_2.getText().length() > 1) {
						result_layer.save(textField_2.getText());
					}

					setVisible(true);
					mf.setVisible(true);
				}
			});
			
			JButton btnOk = new JButton("OK");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
			buttonPane.add(btnOk);
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
						}
						catch (Exception ex) {
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
