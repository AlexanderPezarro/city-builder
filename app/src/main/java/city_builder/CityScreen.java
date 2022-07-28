package city_builder;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class CityScreen extends JFrame{
    
    public CityScreen() {
        createUI();
    }

    private void createUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("City Builder");
        setResizable(true);
        setLocationRelativeTo(null);
    }
}
