package kth.ag2411.project.ui.panel;

import kth.ag2411.mapalgebra.Layer;
import kth.ag2411.mapalgebra.MapPanel;
import kth.ag2411.project.MainFrame;
import kth.ag2411.project.ui.dialogs.ContactSupport;
import kth.ag2411.project.ui.dialogs.FileTypeFilter;
import kth.ag2411.project.ui.dialogs.Register;
import kth.ag2411.project.ui.UiConsts;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class MenuBar extends JMenuBar {
    public JMenu fileMenu, mnItmOpen;
    public JMenuItem mntmNewProject, mnItmRasters, mnItmSave, mnItmSaveAs, mnItmExit;

    public JMenu optMenu;
    public JMenuItem localOpt, focalOpt, zonalOpt;

    public JMenu helpMenu;
    public JMenuItem contact, userGuide, mntmRegister;

    public MainFrame mf;
    static int scale = 5;

    public MenuBar(MainFrame mf) {
        this.mf = mf;
        initialize();
        addMenu();
        addListener();
    }


    private void initialize() {
        this.setBorder(UIManager.getBorder("PopupMenu.border"));
        this.setBackground(Color.WHITE);
        Dimension preferredSize = new Dimension(UiConsts.MAIN_WINDOW_WIDTH, 20);
        this.setPreferredSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setBackground(UiConsts.MENU_BAR_BG_COLOR);
    }


    private void addMenu() {
        /**
         * File menu
         */
        fileMenu = new JMenu("File");
        fileMenu.setFont(UiConsts.MENU_FONT);
        mntmNewProject = new JMenuItem("New Project");
        mnItmOpen = new JMenu("Open");
        mnItmRasters = new JMenuItem("Rasters");
        mnItmSave = new JMenuItem("Save");
        mnItmSaveAs = new JMenuItem("Save as");
        mnItmExit = new JMenuItem("Exit");
        mnItmExit.setIcon(new ImageIcon(mf.getClass().getResource("/kth/ag2411/project/resources/glove_16.png")));
        mnItmOpen.add(mnItmRasters);
        fileMenu.add(mntmNewProject);
        fileMenu.add(mnItmOpen);
        fileMenu.add(mnItmSave);
        fileMenu.add(mnItmSaveAs);
        fileMenu.add(mnItmExit);

        /**
         * Operation menu
         */
        optMenu = new JMenu("Operations");
        optMenu.setFont(UiConsts.MENU_FONT);
        localOpt = new JMenuItem("Local operations");
        focalOpt = new JMenuItem("Focal operations");
        zonalOpt = new JMenuItem("Zonal operations");
        optMenu.add(localOpt);
        optMenu.add(focalOpt);
        optMenu.add(zonalOpt);

        /**
         * Help menu
         */
        helpMenu = new JMenu("Help");
        helpMenu.setFont(UiConsts.MENU_FONT);
        userGuide = new JMenuItem("Help Manual");
        contact = new JMenuItem("Contact support");
        mntmRegister = new JMenuItem("Register");
        helpMenu.add(userGuide);
        helpMenu.add(contact);
        helpMenu.add(mntmRegister);

        this.add(fileMenu);
        this.add(optMenu);
        this.add(helpMenu);

    }



    private void addListener() {
        /**
         *  New project Listener
         */
        mntmNewProject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                mf.displayPanel.mapContainer.removeAll();
                mf.previewPanel.previewContainer.removeAll();
                mf.workspacePanel.filePane.removeAll();
                mf.workspacePanel.repaint();
                mf.displayPanel.repaint();
                mf.previewPanel.repaint();
                mf.setVisible(true);
            }
        });


        /**
         * Open raster listener
         */
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileFilter() {
            public String getDescription() {
                return "ASCII (*.txt)";
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
        mnItmRasters.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(mf);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File[] selectedFiles = fileChooser.getSelectedFiles();

                    for (int i = 0; i < selectedFiles.length; i++) {
                        System.out.println("Selected file: " + selectedFiles[i].getAbsolutePath());

                        /**
                         * Update workspace file tree
                         */
                        String dir = selectedFiles[i].getParent() ;
                        System.out.println(dir);
                        mf.workspacePanel.addJTree(dir, mf);


                        /**
                         * Show image
                         */
                        String layerName = selectedFiles[i].getName().replaceAll("[.][^.]+$", "");
                        Layer layer = new Layer(layerName, selectedFiles[i].getAbsolutePath());
                        BufferedImage layerImage = layer.toImage();

                        MapPanel mapPanel = new MapPanel(layerImage, scale, layer);
                        mf.displayPanel.updateImage(mapPanel);


                        /**
                         * Update previewPanel
                         */
                        mf.previewPanel.updateImage(layer);
                        mf.setVisible(true);
                    }
                }
            }
        });

        mnItmSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MapPanel map = (MapPanel) mf.displayPanel.mapContainer.getComponent(0);
                if (mf.workspacePanel.currentDir == null) {
                    map.layer.save(System.getProperty("user.dir") +"/"+ map.layer.name +".txt");
                } else {
                    map.layer.save(mf.workspacePanel.currentDir +"/"+ map.layer.name +".txt");
                    mf.workspacePanel.addJTree(mf.workspacePanel.currentDir, mf);
                    mf.setVisible(true);
                }
            }
        });

        /**
         * SaveAs listener
         */
        mnItmSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveas();
            }
        });


        /**
         * Exit Listener
         */
        mnItmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ret = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
                if (ret == JOptionPane.YES_NO_OPTION) {
                    int ret1 = JOptionPane.showConfirmDialog(null, "Did you save your work?");
                    if (ret1 == JOptionPane.YES_NO_OPTION)
                        System.exit(0);
                }
            }
        });

        /**
         * Operation Listener
         */
        localOpt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mf.localOperation();
            }
        });

        //The Focal Operation Window
        focalOpt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mf.focalOperation();
            } });


        // The Zonal Operation Window
        zonalOpt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mf.zonalOperation();
            }
        });


        /**
         * Register Listener
         */
        mntmRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Register register = new Register();
                register.setModal(true);
                register.setVisible(true);
                register.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
        });

        /**
         *  Help Manual Listener
         */
        userGuide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String inputPdf = "/kth/ag2411/project/resources/HelpManual.pdf";
            	Path tempOutput;
				try {
					tempOutput = Files.createTempFile("TempManual", ".pdf");
	            	try (InputStream is = mf.getClass().getResourceAsStream(inputPdf)) {
	            		Files.copy(is, tempOutput, StandardCopyOption.REPLACE_EXISTING);
	            	} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            	Desktop.getDesktop().open(tempOutput.toFile());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

            	
//                try { // "rundll32 url.dll, FileProtocolHandler "
//                
//                    Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " +
//                mf.getClass().getResource("/kth/ag2411/project/resources/HelpManual.pdf")
//                            );
//                }
//                catch (Exception ex) {
//                    JOptionPane.showMessageDialog(null, "Check your file details..");
//                }
            }
        });


        contact.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ContactSupport contactsupport = new ContactSupport();
                contactsupport.setModal(true);
                contactsupport.setVisible(true);
            }
        });
    }

    public void saveas() {
        final JFileChooser fileChooser = new JFileChooser();
        FileFilter jpgFilter = new FileTypeFilter(".jpg", "JPEG");
        FileFilter pngFilter = new FileTypeFilter(".png", "PNG");
        FileFilter tifFilter = new FileTypeFilter(".tif", "TIFF");
        FileFilter bmpFilter = new FileTypeFilter(".bmp", "BMP");
        fileChooser.addChoosableFileFilter(jpgFilter);
        fileChooser.addChoosableFileFilter(pngFilter);
        fileChooser.addChoosableFileFilter(tifFilter);
        fileChooser.addChoosableFileFilter(bmpFilter);

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int result = fileChooser.showSaveDialog(mf);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            BufferedImage image = new BufferedImage(mf.displayPanel.getWidth(), mf.displayPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
            // paints into image's Graphics
            mf.displayPanel.paint(image.getGraphics());
            // write the captured image as a PNG
            String strPath = selectedFile.toString();

            try {
                ImageIO.write(image, strPath.substring(strPath.length()-3), selectedFile);
                System.out.println("Image saved!");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }



}

