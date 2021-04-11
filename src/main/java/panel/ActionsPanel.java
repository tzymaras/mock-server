package panel;

import frame.Route;
import spark.Spark;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.time.LocalDateTime;
import java.util.*;

public class ActionsPanel extends JPanel {
    private final JButton btnStartServer = new JButton("start");
    private final JButton btnStopServer  = new JButton("stop");

    public ActionsPanel(
        LayoutManager layout,
        ConfigurationPanel panelConfiguration,
        ServerLogPanel panelLog,
        ArrayList<Route> routes
    ) {
        super(layout);

        this.btnStartServer.addActionListener(new BtnStartServerActionListener(
            routes,
            panelConfiguration.getTextFieldPort(),
            panelLog.getTextAreaServerLog()
        ));

        this.btnStopServer.addActionListener(
            new BtnStopServerActionListener(panelLog.getTextAreaServerLog())
        );

        this.btnStopServer.setEnabled(false);

        this.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        this.add(this.btnStartServer);
        this.add(this.btnStopServer);
    }

    private class BtnStartServerActionListener implements ActionListener {
        private final ArrayList<Route> routes;
        private final JTextField       textFieldPort;
        private final JTextArea        textAreaLog;

        public BtnStartServerActionListener(
            ArrayList<Route> routes,
            JTextField textFieldPort,
            JTextArea textAreaLog
        ) {
            this.routes        = routes;
            this.textFieldPort = textFieldPort;
            this.textAreaLog   = textAreaLog;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!routes.isEmpty()) {
                btnStartServer.setEnabled(false);
                btnStopServer.setEnabled(true);

                if (this.textFieldPort.getText().length() > 0) {
                    Spark.port(Integer.parseInt(this.textFieldPort.getText()));
                }

                this.routes.forEach(route -> {
                    try {
                        Method methodToRun = Spark.class.getMethod(
                            route.getMethod().toLowerCase(),
                            String.class,
                            spark.Route.class
                        );

                        methodToRun.invoke(null, route.getUrl(), createRoutehandlerForRoute(route));
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
                        exception.printStackTrace();
                    }
                });

                this.textAreaLog.append(String.format("> mock server now listening on:%s\n", Spark.port()));
            }
        }

        private spark.Route createRoutehandlerForRoute(Route route) {
            return (request, response) -> {
                response.status(Integer.parseInt(route.getStatusCode()));

                route.getHeaders().forEach(header -> {
                    if (header.length > 1) {
                        response.header(header[0], header[1]);
                    }
                });

                this.textAreaLog.append(String.format(
                    "[INFO] [%s] [%s] %s %s %s %s\n",
                    LocalDateTime.now(),
                    request.ip(),
                    request.requestMethod(),
                    request.uri(),
                    response.status(),
                    request.userAgent()
                ));

                return route.getBody();
            };
        }
    }

    private class BtnStopServerActionListener implements ActionListener {
        private final JTextArea textAreaLog;

        public BtnStopServerActionListener(JTextArea textAreaLog) {
            this.textAreaLog = textAreaLog;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            btnStartServer.setEnabled(true);
            btnStopServer.setEnabled(false);

            Spark.stop();

            this.textAreaLog.append("> stopped mock server\n");
        }
    }
}
