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

        JLabel weatherConditionImage = new JLabel(loadImage("src/main/resources/clouds-3.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);

        JLabel temperatureText = new JLabel("10 C°");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Ink Free", Font.BOLD, 48));

        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        JLabel weatherConditionDescription = new JLabel("Cloudy");
        weatherConditionDescription.setBounds(0, 405, 450, 36);
        weatherConditionDescription.setFont(new Font("Ink Free", Font.PLAIN, 32));
        weatherConditionDescription.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDescription);

        JLabel humidityImage = new JLabel(loadImage("src/main/resources/humidity.png"));
        humidityImage.setBounds(10, 530, 74, 66);
        add(humidityImage);

        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(80, 530, 85, 55);
        humidityText.setFont(new Font("Ink Free", Font.PLAIN, 16));
        add(humidityText);

        JLabel windspeedImage = new JLabel(loadImage("src/main/resources/wind-2.png"));
        windspeedImage.setBounds(220, 530, 74, 65);
        add(windspeedImage);

        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windspeedText.setBounds(300, 530, 90, 60);
        windspeedText.setFont(new Font("Ink Free", Font.PLAIN, 16));
        add(windspeedText);
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
