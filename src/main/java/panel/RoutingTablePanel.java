package panel;

import frame.Route;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class RoutingTablePanel extends JPanel {
    private final JTable routesTable;

    public RoutingTablePanel(LayoutManager layout, ArrayList<Route> routes) {
        super(layout);

        Border panelBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

        Border compoundBorderRoutesTable = BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(panelBorder, "Registered Routes"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );

        this.routesTable = new JTable() {
            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(400, 100);
            }
        };

        this.routesTable.setModel(
            this.createTableModel(routes)
        );

        JScrollPane scrollPaneRoutesTable = new JScrollPane(this.routesTable);
        scrollPaneRoutesTable.setBorder(panelBorder);

        this.setBorder(compoundBorderRoutesTable);
        this.add(scrollPaneRoutesTable);
    }

    private TableModel createTableModel(ArrayList<Route> routes) {
        RoutingTableModel tableModel = new RoutingTableModel();
        tableModel.setColumnIdentifiers(new String[]{"method", "url", "response_code"});
        tableModel.addTableModelListener(new RoutingTableModelListener(routes));

        return tableModel;
    }

    public JTable getRoutesTable() {
        return routesTable;
    }

    private static class RoutingTableModel extends DefaultTableModel {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return this.getDataVector()
                .elementAt(0)
                .elementAt(columnIndex)
                .getClass();
        }
    }

    private static class RoutingTableModelListener implements TableModelListener {
        private final ArrayList<Route> routes;

        public RoutingTableModelListener(ArrayList<Route> routes) {
            this.routes = routes;
        }

        @Override
        public void tableChanged(TableModelEvent e) {
            if (TableModelEvent.UPDATE == e.getType()) {
                Route             editingRoute      = this.routes.get(e.getLastRow());
                RoutingTableModel routingTableModel = (RoutingTableModel) e.getSource();

                String value = routingTableModel.getDataVector()
                    .elementAt(e.getLastRow())
                    .elementAt(e.getColumn())
                    .toString();

                switch (e.getColumn()) {
                    case 0:
                        editingRoute.setMethod(value);
                        break;
                    case 1:
                        editingRoute.setUrl(value);
                        break;
                    case 2:
                        editingRoute.setStatusCode(value);
                        break;
                }
            }
        }
    }
}
