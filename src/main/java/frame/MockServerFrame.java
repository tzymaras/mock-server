package frame;

import gridbaghelper.GBC;
import panel.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MockServerFrame extends JFrame {
    public MockServerFrame(String title) throws HeadlessException {
        super(title);

        ArrayList<Route> routes = new ArrayList<>();

        ConfigurationPanel panelConfiguration = new ConfigurationPanel(new FlowLayout(FlowLayout.LEADING));
        RoutingTablePanel  panelRoutingTable  = new RoutingTablePanel(new BorderLayout(), routes);
        ServerLogPanel     panelServerLog     = new ServerLogPanel(new GridLayout(1, 1));

        CreateRoutePanel panelCreateRoute = new CreateRoutePanel(
            new GridBagLayout(),
            panelConfiguration,
            panelRoutingTable,
            routes
        );

        ActionsPanel panelActions = new ActionsPanel(
            new FlowLayout(FlowLayout.LEADING),
            panelConfiguration,
            panelServerLog,
            routes
        );

        JPanel panelMain = new JPanel(new GridBagLayout());
        panelMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelMain.add(panelConfiguration, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setInsets(0, 0, 10, 0));
        panelMain.add(panelCreateRoute, new GBC(0, 1, 1, 1).setFill(GBC.BOTH));
        panelMain.add(panelRoutingTable, new GBC(0, 2, 1, 1).setFill(GBC.BOTH));
        panelMain.add(panelServerLog, new GBC(0, 3, 1, 1).setFill(GBC.BOTH));
        panelMain.add(panelActions, new GBC(0, 4, 1, 1).setFill(GBC.BOTH).setInsets(0, -10, 0, 0));

        this.add(panelMain);
        this.pack();
    }
}