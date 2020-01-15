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
import javax.swing.JOptionPane;
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
import kth.ag2411.mapalgebra.Layer;
import kth.ag2411.project.MainFrame;

public class ZonalOperations extends JDialog {
	
	private MainFrame mf;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnBrowse;
	private JTextField textField_2;
	private JButton btnBrowse_1;

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
			ZonalOperations dialog = new ZonalOperations();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	
	public ZonalOperations() {
	
	}
	public ZonalOperations(MainFrame mf) {
		this.mf = mf;
	}
	
	public void init() {
		setTitle("Zonal Operations");
		setBounds(100, 100, 450, 275);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblRaster = new JLabel("Raster Input:");
		JLabel lblRaster_1 = new JLabel("Zonal Raster:");

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
				int result = fileChooser.showOpenDialog(ZonalOperations.this);
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
				int result = fileChooser.showOpenDialog(ZonalOperations.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.print("Selected file: " + selectedFile.getAbsolutePath());
					textField.setText(selectedFile.getAbsoluteFile().toString());
				}
			}
		});
		JButton button = new JButton("Browse");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showOpenDialog(ZonalOperations.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.print("Selected file: " + selectedFile.getAbsolutePath());
					textField_1.setText(selectedFile.getAbsoluteFile().toString());
				}
			}
		});

		JLabel lblZonalOperations = new JLabel("Zonal Operations: ");
		lblZonalOperations.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));

		JComboBox<Object> comboBox = new JComboBox<Object>();
		comboBox.setModel(new DefaultComboBoxModel<Object>(new String[] { "Zonal Minimum", "Zonal Maximum",
				"Zonal Mean", "Zonal Sum", "Zonal Variety", "Zonal Minority", "Zonal Majority" }));

		JProgressBar progressBar = new JProgressBar();

		JLabel lblProgressBar = new JLabel("Progress Bar: ");

		JLabel lblSaveAs = new JLabel("Save as:");
		lblSaveAs.setFont(new Font("Tahoma", Font.PLAIN, 12));

		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);

		JButton button_1 = new JButton("Browse");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int select = fileChooser.showSaveDialog(ZonalOperations.this);
				if (select == fileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					textField_2.setText(file.getAbsoluteFile().toString());
					System.out.println("saved");
				} else
					System.out.println("cancelled");
			}
		});
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblProgressBar)
						.addComponent(lblSaveAs, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(lblRaster_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblRaster, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
								.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(lblZonalOperations)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(comboBox, 0, 129, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(button_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
								.addComponent(button_1)
								.addComponent(lblSaveAs))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblZonalOperations))
							.addGap(28)
							.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE))
						.addComponent(lblProgressBar, Alignment.TRAILING))
					.addContainerGap())
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JButton btnRun = new JButton("Run");
			btnRun.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					progressBar.setValue(0);
					progressBar.setStringPainted(true);
					progressBar.setMaximum(100);
					for (int q = 0; q <= 100; q++) {
						progressBar.setValue(q);
						progressBar.setString("Successful..");
					}
					String operations = (String)comboBox.getSelectedItem();
					Layer layer1 = new Layer(operations + "InLy",textField.getText());
					Layer zonalLayer = new Layer(operations + "ZoneLy",textField_1.getText());

					Layer result_layer = layer1;
					String outLayerName = operations + " Out";
		
					if (operations == "Zonal Minimum") {					
						result_layer = layer1.zonalMinimum(zonalLayer, outLayerName);
						}
					if (operations == "Zonal Maximum") {					
						result_layer = layer1.zonalMaximum(zonalLayer, outLayerName);
						}
					if (operations == "Zonal Mean") {					
						result_layer = layer1.zonalMean(zonalLayer, outLayerName);
						}
					if (operations == "Zonal Sum") {					
						result_layer = layer1.zonalSum(zonalLayer, outLayerName);
						}
					if (operations == "Zonal Variety") {					
						result_layer = layer1.zonalVariety(zonalLayer, outLayerName);
						}
					if (operations == "Zonal Minority") {					
						result_layer = layer1.zonalMinority(zonalLayer, outLayerName);
						}
					if (operations == "Zonal Majority") {					
						result_layer = layer1.zonalMinimum(zonalLayer, outLayerName);
						}

					mf.previewPanel.updateImage(layer1);
					mf.previewPanel.updateImage(zonalLayer);
					mf.previewPanel.updateImage(result_layer);
					mf.displayLayer(result_layer);
					mf.setVisible(true);
					if (textField_2.getText().length() > 1) {
						result_layer.save(textField_2.getText());
					}
					//dispose();
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
	public void run() {
		init();
	}
}
