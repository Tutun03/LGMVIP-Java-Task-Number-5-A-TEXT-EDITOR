import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Task5 extends JFrame implements ActionListener {
    JTextArea textArea;
    JMenuBar menuBar;

    Task5() {
        setTitle("Text Editor");
        setSize(800, 600);

        // Initialize the components
        textArea = new JTextArea();
        textArea.setText("Start typing here...");
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save and Submit");
        JMenuItem printMenuItem = new JMenuItem("Print");
        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        printMenuItem.addActionListener(this);

        // Add menu items to the file menu
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(printMenuItem);

        // Add the file menu to the menu bar
        menuBar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        cutMenuItem.addActionListener(this);
        copyMenuItem.addActionListener(this);
        pasteMenuItem.addActionListener(this);

        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

        menuBar.add(editMenu);

        JMenuItem closeMenuItem = new JMenuItem("Close");
        closeMenuItem.addActionListener(this);
        menuBar.add(closeMenuItem);

        // Set the menu bar for the frame
        setJMenuBar(menuBar);

        // Add the text area to the frame with a JScrollPane
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Create and add the background label with the image
        getContentPane().setBackground(new Color(173, 216, 230));
        // Set the frame visible
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (actionCommand.equalsIgnoreCase("Copy")) {
            textArea.copy();
        } else if (actionCommand.equalsIgnoreCase("Print")) {
            // Print the contents of the text area using external program (e.g., Notepad on Windows)
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("print_temp.txt"));
                textArea.write(writer);
                writer.close();
                ProcessBuilder pb = new ProcessBuilder("notepad.exe", "print_temp.txt");
                pb.start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (actionCommand.equalsIgnoreCase("Cut")) {
            textArea.cut();
        } else if (actionCommand.equalsIgnoreCase("Save and Submit")) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Text Files", "txt");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(textFilter);

            int action = fileChooser.showSaveDialog(this);
            if (action != JFileChooser.APPROVE_OPTION) {
                return;
            } else {
                String filename = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filename.endsWith(".txt")) {
                    filename += ".txt";
                }
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                    textArea.write(writer);
                    writer.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } else if (actionCommand.equalsIgnoreCase("Close")) {
            dispose();
        }
    }

    public static void main(String args[]) {
        new Task5();
    }
}
