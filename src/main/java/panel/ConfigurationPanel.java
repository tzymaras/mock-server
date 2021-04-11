package panel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ConfigurationPanel extends JPanel {
    private static final String DEFAULT_SERVER_PORT = "9000";

    private final JTextField textFieldPort;
    private final JTextField textFieldPrefix;

    public ConfigurationPanel(LayoutManager layoutManager) {
        super(layoutManager);

        this.textFieldPort   = new JTextField(DEFAULT_SERVER_PORT, 8);
        this.textFieldPrefix = new JTextField(8);

        Border border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            "Settings"
        );

        Border textFieldBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(4, 7, 4, 7)
        );

        this.textFieldPort.setBorder(textFieldBorder);
        this.textFieldPrefix.setBorder(textFieldBorder);

        this.textFieldPort.setName("port");
        this.textFieldPrefix.setName("prefix");

        this.setLayout(layoutManager);
        this.setBorder(border);

        this.add(new JLabel("port"));
        this.add(textFieldPort);
        this.add(new JLabel("prefix"));
        this.add(textFieldPrefix);
    }

    public JTextField getTextFieldPort() {
        return this.textFieldPort;
    }

    public JTextField getTextFieldPrefix() {
        return this.textFieldPrefix;
    }
}
