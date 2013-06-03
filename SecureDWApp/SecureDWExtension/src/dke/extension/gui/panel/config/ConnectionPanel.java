package dke.extension.gui.panel.config;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import oracle.javatools.ui.TransparentPanel;
import oracle.javatools.ui.layout.FieldLayoutBuilder;


/**
 * Panel used for connection data input.
 */
public class ConnectionPanel extends TransparentPanel {
    private final JTextField host = new JTextField();
    private final JTextField port = new JTextField();
    private final JTextField sid = new JTextField();
    private final JTextField name = new JTextField();
    private final JPasswordField pwd = new JPasswordField();
    private final JButton connect = new JButton();
    private final JButton save = new JButton();

    ConnectionPanel() {
        initComponents();
        layoutComponents();
    }

    /**
     * Defines the basic layout for the components.
     */
    private void layoutComponents() {
        FieldLayoutBuilder b = new FieldLayoutBuilder(this);
        b.add(b.field().label().withText("Host name").component(host));
        b.add(b.field().label().withText("Port").component(port));
        b.add(b.field().label().withText("SID").component(sid));
        b.add(b.field().label().withText("User name").component(name).button(connect).withText("Connect"));
        b.add(b.field().label().withText("Password").component(pwd).button(save).withText("Save"));
    }

    private void initComponents() {
        connect.addActionListener(new ConnectListener());
        save.addActionListener(new SaveListener());
        save.setEnabled(false);
        setupRequiredFields();
    }

    /**
     * Sets up required fields so that the Connect and Test buttons in the dialog are enabled
     * or disabled depending on whether they have a value.
     *
     * @param dialog the dialog to control the buttons of.
     */
    private void setupRequiredFields() {
        setOKButtonEnabled(isButtonEnabled());
        ButtonUpdater listener = new ButtonUpdater();
        listener.attach(host);
        listener.attach(port);
        listener.attach(sid);
        listener.attach(name);
        listener.attach(pwd);
    }

    private void setOKButtonEnabled(boolean enabled) {
        connect.setEnabled(enabled);
    }

    /**
     * Determine whether the OK button should be enabled.
     */
    private boolean isButtonEnabled() {
        return host.getText().trim().length() > 0 &&
            port.getText().trim().length() > 0 &&
            sid.getText().trim().length() > 0 &&
            name.getText().trim().length() > 0 && pwd.getPassword().length > 0;
    }

    /**
     * Responds to keypresses in the text fields, and updates the OK button
     * of the dialog.
     */
    private class ButtonUpdater implements DocumentListener {
        ButtonUpdater() {

        }

        private void update() {
            setOKButtonEnabled(isButtonEnabled());
        }

        public void insertUpdate(DocumentEvent e) {
            update();
        }

        public void removeUpdate(DocumentEvent e) {
            update();
        }

        public void changedUpdate(DocumentEvent e) {
            update();
        }

        void attach(JTextComponent tc) {
            tc.getDocument().addDocumentListener(this);
        }
    }

    /**
     * Responds to clicks on the connect button. Validates the connection data and enables the save button if connection data is valid.
     */
    private class ConnectListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                //TODO: perform connect / test / rand00m stuff
                save.setEnabled(true);
            } catch (Exception ex) {
                //TODO: Handle exception
            }
        }
    }

    /**
     * Responds to clicks on the save button. Saves the connection data for further use.
     */
    private class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //TODO: Implement saving of preferences
        }
    }
}