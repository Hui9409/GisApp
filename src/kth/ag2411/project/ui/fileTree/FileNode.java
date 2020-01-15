package kth.ag2411.project.ui.fileTree;

import javax.swing.*;
import java.io.File;

public class FileNode{
    public boolean isInit;
    public boolean isDummyRoot;
    public String name;
    public Icon icon;
    public File file;

    public FileNode(String name, Icon icon, File file, boolean isDummyRoot) {
        this.name=name;
        this.icon=icon;
        this.file=file;
        this.isDummyRoot=isDummyRoot;
    }

}
