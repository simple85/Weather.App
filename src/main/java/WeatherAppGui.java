import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGui extends JFrame {
    private JSONObject weatherData;

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

        JButton searchButton = new JButton(loadImage("src/main/resources/search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 45, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get location from user
                String userInput = searchTextField.getText();
                userInput = userInput.trim();

                //retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                //update gui

                //update weather image
                String weatherCondition = (String) weatherData.get("weather_condition");
                switch(weatherCondition) {
                    case "Clear", "Mainly Clear":
                        weatherConditionImage.setIcon(loadImage("src/main/resources/sun.png"));
                        break;
                    case "Cloudy", "Partly Cloudy", "Overcast", "Fog", "Depositing Rime Fog":
                        weatherConditionImage.setIcon(loadImage("src/main/resources/clouds-3.png"));
                        break;
                    case "Light Drizzle", "Moderate Drizzle", "Dense Drizzle",
                         "Light Freezing Drizzle", "Dense Freezing Drizzle",
                         "Slight Rain", "Moderate Rain", "Heavy Rain",
                         "Light Freezing Rain", "Heavy Freezing Rain",
                         "Slight Rain Showers", "Moderate Rain Showers",
                         "Violent Rain Showers":
                        weatherConditionImage.setIcon(loadImage("src/main/resources/rain.png"));
                        break;
                    case "Slight Snow Fall", "Moderate Snow Fall",
                         "Heavy Snow Fall", "Slight Snow Showers",
                         "Heavy Snow Showers":
                        weatherConditionImage.setIcon(loadImage("src/main/resources/snowy.png"));
                        break;
                    case "Thunderstorm":
                        weatherConditionImage.setIcon(loadImage("src/main/resources/thunder.png"));
                        break;
                    case "Thunderstorm With Slight Hail", "Thunderstorm With Heavy Hail":
                        weatherConditionImage.setIcon(loadImage("src/main/resources/thunderstorm.png"));
                        break;


                }

                //update temperature text
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + "°C");

                //update weather condition text
                weatherConditionDescription.setText(weatherCondition);

                //update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                //update windspeed text
                double windspeed = (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed</b> " + windspeed + " km/h</html>");
            }
        });
        add(searchButton);
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
