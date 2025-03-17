import javax.swing.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WeatherAppGui();
            }
        });
    }
}
