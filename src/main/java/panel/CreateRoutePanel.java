package panel;

import frame.Route;
import gridbaghelper.GBC;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class CreateRoutePanel extends JPanel {
    private static final String[] METHODS = new String[]{
        "GET", "POST", "PUT", "DELETE", "HEAD", "OPTION", "LINK", "UNLINK", "TRACE"
    };

    private final JTextField        textResponseCode = new JTextField(8);
    private final JTextField        textUrl          = new JTextField(30);
    private final JTextArea         textAreaBody     = new JTextArea(5, 24);
    private final JTextArea         textAreaHeaders  = new JTextArea(5, 24);
    private final JComboBox<String> textMethod       = new JComboBox<>(METHODS);

    private final ArrayList<Route> routes;

    public CreateRoutePanel(
        LayoutManager layout,
        ConfigurationPanel panelConfiguration,
        RoutingTablePanel panelRoutingTable,
        ArrayList<Route> routes
    ) {
        super(layout);

        this.routes = routes;

        Border panelBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

        Border compoundBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(panelBorder, "Create Route"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );

        Border textFieldBorder = BorderFactory.createCompoundBorder(
            panelBorder,
            BorderFactory.createEmptyBorder(4, 7, 4, 7)
        );

        JButton btnAddRoute = new JButton("Add route");
        btnAddRoute.addActionListener(new BtnAddRouteAction(
            panelConfiguration.getTextFieldPrefix(),
            panelRoutingTable.getRoutesTable()
        ));

        this.textResponseCode.setBorder(textFieldBorder);
        this.textUrl.setBorder(textFieldBorder);

        CreateRouteTextsPanel panelCreateRouteTexts = new CreateRouteTextsPanel(
            new GridBagLayout(),
            textAreaBody,
            textAreaHeaders
        );

        this.setBorder(compoundBorder);
        this.add(new JLabel("method"), new GBC(0, 0, 1, 1).setAnchor(GBC.WEST).setInsets(0, 0, 5, 0));
        this.add(new JLabel("response code"), new GBC(1, 0, 1, 1).setAnchor(GBC.WEST).setInsets(0, 5, 5, 0));
        this.add(new JLabel("url"), new GBC(2, 0, 3, 1).setAnchor(GBC.WEST).setInsets(0, 5, 5, 0));
        this.add(this.textMethod, new GBC(0, 1, 1, 1).setFill(GBC.HORIZONTAL).setInsets(0, -5, 0, 0));
        this.add(this.textResponseCode, new GBC(1, 1, 1, 1).setInsets(0, 5, 0, 0));
        this.add(this.textUrl, new GBC(2, 1, 3, 1).setInsets(0, 5, 0, 0));
        this.add(panelCreateRouteTexts, new GBC(0, 2, 5, 1).setInsets(10, 0, 0, 0));
        this.add(btnAddRoute, new GBC(2, 3, 5, 1).setAnchor(GBC.EAST).setInsets(10, 0, 5, 0));
    }

    private class BtnAddRouteAction implements ActionListener {
        private final JTextField textFieldPrefix;
        private final JTable     tableRouting;

        public BtnAddRouteAction(JTextField textFieldPrefix, JTable tableRouting) {
            this.textFieldPrefix = textFieldPrefix;
            this.tableRouting    = tableRouting;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String prefix              = this.textFieldPrefix.getText();
            String method              = textMethod.getSelectedItem().toString();
            String responseCode        = textResponseCode.getText();
            String responseBody        = textAreaBody.getText();
            String responseHeadersText = textAreaHeaders.getText();

            String url = null != prefix && prefix.length() > 0
                ? prefix + textUrl.getText()
                : textUrl.getText();

            List<String[]> responseHeaders = Arrays.stream(responseHeadersText.split("\\n", 2))
                .filter(s -> s.length() > 0)
                .map(s -> s.split(":"))
                .collect(Collectors.toList());

            Route route = new Route(method, url, responseCode, responseBody, responseHeaders);
            routes.add(route);

            DefaultTableModel model = (DefaultTableModel) this.tableRouting.getModel();
            model.addRow(new Object[]{route.getMethod(), route.getUrl(), route.getStatusCode()});
        }
    }
}
