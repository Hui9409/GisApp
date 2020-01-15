package kth.ag2411.project.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
import javax.swing.border.TitledBorder;
import java.awt.Color;

public class ContactSupport extends JDialog {

	private final JPanel contentPanel = new JPanel();

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
			ContactSupport dialog = new ContactSupport();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ContactSupport() {
		setBounds(100, 100, 460, 260);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
					.addGap(0))
		);
		
		JTextArea txtrAuthorsOmkarParishwad = new JTextArea();
		txtrAuthorsOmkarParishwad.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Java Powered", TitledBorder.RIGHT, TitledBorder.BOTTOM, null, new Color(0, 0, 0)));
		txtrAuthorsOmkarParishwad.setEditable(false);
		txtrAuthorsOmkarParishwad.setBackground(SystemColor.menu);
		txtrAuthorsOmkarParishwad.setText("Authors:\tOmkar Parishwad (omkarp@kth.se);\r\n\t\tIonut Rares Matei (irmatei@kth.se); \r\n\t\tHui Zhang (huzh@kth.se); \r\n\t\tShuo Chen (shuoc@kth.se)\r\n\r\nRelease Date: \tDecember 9, 2019\r\n\r\nVersion: \t0.1");
		scrollPane.setViewportView(txtrAuthorsOmkarParishwad);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}
}
