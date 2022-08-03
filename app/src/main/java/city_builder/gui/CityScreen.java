package city_builder.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

public class CityScreen extends JFrame{
    
    public CityScreen() {
        createUI();
    }

    private void createUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("City Builder");
        setResizable(true);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        int[][] tempTestingGrid = new int[4][4];
        createButtonGrid(tempTestingGrid, backgroundPanel, gbc);

        // TODO: Add action listener
        JButton stepButton = new JButton("Step");
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.gridy = 1;
        backgroundPanel.add(stepButton, gbc);

        // TODO: Add action listener
        JButton solveButton = new JButton("Solve");
        gbc.gridx = 1;
        backgroundPanel.add(solveButton, gbc);
        setContentPane(backgroundPanel);
        // validate();
        setVisible(true);
    }

    private void createButtonGrid(int[][] grid, JPanel parentPanel, Object parentConstraint) {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(1,1,1,1);
        gbc.fill = GridBagConstraints.BOTH;
        for (int i = 0; i < grid.length; i++) {
            gbc.gridy = i;
            for (int j = 0; j < grid[i].length; j++) {
                gbc.gridx = j;
                // TODO: Have this button have an action listener to select the image/number
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(50, 50));
                button.setBackground(Color.white);
                button.setBorderPainted(false);
                buttonPanel.add(button, gbc);
            }
        }
        parentPanel.add(buttonPanel, parentConstraint);
    }
}
