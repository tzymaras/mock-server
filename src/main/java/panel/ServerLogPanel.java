package panel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ServerLogPanel extends JPanel {
    private final JTextArea textAreaServerLog = new JTextArea(10, 50);

    public ServerLogPanel(LayoutManager layout) {
        super(layout);

        Border panelBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

        Border compoundBorderServerLog = BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(panelBorder, "ServerLog"),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );

        this.textAreaServerLog.setEditable(false);

        JScrollPane scrollPaneServerLog = new JScrollPane(this.textAreaServerLog);
        scrollPaneServerLog.setBorder(panelBorder);

        this.setBorder(compoundBorderServerLog);
        this.add(scrollPaneServerLog);
    }

    public JTextArea getTextAreaServerLog() {
        return textAreaServerLog;
    }
}
