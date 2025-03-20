import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGui extends JFrame {
    WeatherAppGui() {
        super("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        addGuiComponents();

        setVisible(true);


    }

    private void addGuiComponents() {
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15, 15, 350, 40);
        searchTextField.setFont(new Font("New Times Roman", Font.PLAIN, 20));
        add(searchTextField);

        JButton searchButton = new JButton(loadImage("src/main/resources/search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 45, 45);
        add(searchButton);

        //JLabel weatherConditionImage = new JLabel(loadImage());
    }

    private ImageIcon loadImage(String filePath) {

        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Cannot find the file");
        return null;
    }
}
