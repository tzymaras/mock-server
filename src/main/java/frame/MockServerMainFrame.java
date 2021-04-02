package frame;

import spark.Route;
import spark.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.time.LocalDateTime;
import java.util.Arrays;

public class MockServerMainFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 700;
    private static final int DEFAULT_HEIGHT = 400;

    private static final String[] METHODS = new String[]{
        "GET", "POST", "PUT", "DELETE", "HEAD", "OPTION", "LINK", "UNLINK", "TRACE"
    };

    private final JPanel panelRoutes = new JPanel(new GridLayout(5, 1, 0, 5));
    private final JButton btnStartServer = new JButton("start");
    private final JButton btnStopServer = new JButton("stop");

    private final JTextArea logTextArea = new JTextArea(5, 5);

    public MockServerMainFrame(String title) throws HeadlessException {
        super(title);
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        this.btnStartServer.addActionListener(new BtnStartActionListener());

        this.btnStopServer.setEnabled(false);
        this.btnStopServer.addActionListener(e -> {
            this.btnStartServer.setEnabled(true);
            this.btnStopServer.setEnabled(false);
            Spark.stop();
        });


        JPanel panelButtons = new JPanel();
        panelButtons.add(this.btnStartServer);
        panelButtons.add(this.btnStopServer);

        JPanel panelRouteHeader = new JPanel(new GridLayout(1, 5));
        panelRouteHeader.add(this.createLabel("Request Method"));
        panelRouteHeader.add(this.createLabel("Request URL"));
        panelRouteHeader.add(this.createLabel("Response Code"));
        panelRouteHeader.add(this.createLabel("Response Body"));

        this.panelRoutes.add(panelRouteHeader);
        this.panelRoutes.add(this.createRoutePanel());
        this.panelRoutes.add(this.createRoutePanel());
        this.panelRoutes.add(this.createRoutePanel());
        this.panelRoutes.add(this.createRoutePanel());

        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout(100, 10));
        panelMain.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelMain.add(panelButtons, BorderLayout.SOUTH);
        panelMain.add(panelRoutes, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(logTextArea);
        panelMain.add(scrollPane, BorderLayout.CENTER);

        this.add(panelMain);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        return label;
    }

    private JPanel createRoutePanel() {
        JPanel panelRoute = new JPanel(new GridLayout(1, 4, 5, 0));
        panelRoute.add(new JComboBox<>(METHODS));

        Border textFieldBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(4, 7, 4, 7)
        );

        for (var i = 0; i < 3; i++) {
            JTextField textField = new JTextField();
            textField.setBorder(textFieldBorder);
            panelRoute.add(textField);
        }

        return panelRoute;
    }

    private final class BtnStartActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Component[] components              = MockServerMainFrame.this.panelRoutes.getComponents();
            Component[] componentsWithoutHeader = Arrays.copyOfRange(components, 1, components.length);

            btnStartServer.setEnabled(false);
            btnStopServer.setEnabled(true);

            for (var component : componentsWithoutHeader) {
                JPanel componentAsPanel = (JPanel) component;

                String url = ((JTextField) componentAsPanel.getComponent(1)).getText();
                if (url.length() > 0) {
                    String method     = ((JComboBox<String>) componentAsPanel.getComponent(0)).getSelectedItem().toString();
                    String statusCode = ((JTextField) componentAsPanel.getComponent(2)).getText();
                    String body       = ((JTextField) componentAsPanel.getComponent(3)).getText();

                    try {
                        Route routeHandler = (request, response) -> {
                            response.status(Integer.parseInt(statusCode));
                            response.header("Content-Type", "application/json");

                            logTextArea.append(String.format(
                                "[%s] [%s] %s %s %s %s\n",
                                LocalDateTime.now(),
                                request.ip(),
                                request.requestMethod(),
                                request.uri(),
                                response.status(),
                                request.userAgent()
                            ));

                            return body;
                        };

                        Method methodToRun = Spark.class.getMethod(method.toLowerCase(), String.class, spark.Route.class);
                        methodToRun.invoke(null, url, routeHandler);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            Spark.routes().forEach(routeMatch -> logTextArea.append(routeMatch.getHttpMethod() + routeMatch.getMatchUri() + "\n"));
        }
    }
}
