package kth.ag2411.project.ui.panel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CoordsPane extends JPanel {
    public JTextField txtTypeToLocate;
    public static JTextField xyLabel;
    public static JTextField rcLabel;
    public static JLabel lblImgName;
    public static JLabel value;


    public CoordsPane() {
        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
        JLabel lblCoordinatesXY = new JLabel("Coordinates: (x, y) =");
        JLabel lblCoordinatesRC = new JLabel("(r, c) =");
        lblImgName = new JLabel("Layer: ");
        value = new JLabel("value = ");


        xyLabel = new JTextField();
        xyLabel.setColumns(2);

        rcLabel = new JTextField();
        rcLabel.setColumns(2);

        txtTypeToLocate = new JTextField();
        txtTypeToLocate.setFont(new Font("Tahoma", Font.ITALIC, 11));
        txtTypeToLocate.setText(" Type to locate");
        txtTypeToLocate.setColumns(10);

        JLabel lblReady = new JLabel("Ready!");

        GroupLayout glCoordsPane = new GroupLayout(this);
        glCoordsPane.setHorizontalGroup(
                glCoordsPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(glCoordsPane.createSequentialGroup()
                                .addComponent(txtTypeToLocate, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
                                .addGap(10)
                                .addComponent(lblReady)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblImgName)
                                .addGap(20)
                                .addComponent(lblCoordinatesXY)
                                .addGap(6)
                                .addComponent(xyLabel, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                .addGap(27)
                                .addComponent(lblCoordinatesRC, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                .addGap(1)
                                .addComponent(rcLabel, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                                .addGap(13)
                                .addComponent(value)
                                .addGap(130))

        );
        glCoordsPane.setVerticalGroup(
                glCoordsPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(glCoordsPane.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(glCoordsPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblCoordinatesXY)
                                        .addComponent(txtTypeToLocate, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblReady)
                                        .addComponent(lblImgName)
                                        .addComponent(xyLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblCoordinatesRC)
                                        .addComponent(rcLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(value))
                                .addContainerGap())
        );
        this.setLayout(glCoordsPane);
    }
}
