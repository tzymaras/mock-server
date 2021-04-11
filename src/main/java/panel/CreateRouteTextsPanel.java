package panel;

import gridbaghelper.GBC;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CreateRouteTextsPanel extends JPanel {
    public CreateRouteTextsPanel(LayoutManager layout, JTextArea textAreaBody, JTextArea textAreaHeaders) {
        super(layout);

        Border panelBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

        JScrollPane scrollPaneBody = new JScrollPane(textAreaBody);
        scrollPaneBody.setBorder(panelBorder);

        JScrollPane scrollPaneHeaders = new JScrollPane(textAreaHeaders);
        scrollPaneHeaders.setBorder(panelBorder);

        this.add(new JLabel("response body"), new GBC(0, 0, 2, 1).setAnchor(GBC.WEST).setInsets(0, 0, 5, 0));
        this.add(new JLabel("response headers"), new GBC(2, 0, 2, 1).setAnchor(GBC.WEST).setInsets(0, 10, 5, 0));
        this.add(scrollPaneBody, new GBC(0, 1, 2, 1).setFill(GBC.HORIZONTAL));
        this.add(scrollPaneHeaders, new GBC(2, 1, 2, 1).setFill(GBC.HORIZONTAL).setInsets(0, 10, 0, 0));
    }
}
