import frame.MockServerMainFrame;

import javax.swing.*;
import java.awt.*;

public class MockServer {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame mainFrame = new MockServerMainFrame("MockServerBuilder");
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.setResizable(false);
            mainFrame.setLocationByPlatform(true);
            mainFrame.setVisible(true);
        });
    }
}