package dke.extension.gui.panel.config;


import dke.extension.data.preferencesData.ConnectionData;
import dke.extension.logic.preferences.ManagePreferences;
import dke.extension.logic.preferences.ManagePreferencesImpl;
import dke.extension.logic.validation.InputValidateImpl;
import dke.extension.logic.validation.Validate;

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
    //for testing purposes
    private final JButton clear = new JButton();
    private ManagePreferences pref = new ManagePreferencesImpl();

    ConnectionPanel() {
        initComponents();
        layoutComponents();
    }

    /**
     * Defines the basic layout for the components.
     */
    private void layoutComponents() {
        FieldLayoutBuilder b = new FieldLayoutBuilder(this);
        b.add(b.field().label().withText("Host name").component(host).button(clear).withText("Clear Cache"));
        b.add(b.field().label().withText("Port").component(port));
        b.add(b.field().label().withText("SID").component(sid));
        b.add(b.field().label().withText("User name").component(name).button(connect).withText("Test Connection"));
        b.add(b.field().label().withText("Password").component(pwd).button(save).withText("Save"));
    }

    /**
     * Initializes the components, registers listener and sets up required data.
     */
    private void initComponents() {
        connect.addActionListener(new ConnectListener());
        save.addActionListener(new SaveListener());
        clear.addActionListener(new ClearListener());
        ConnectionData data = pref.getRemoteConnectionData();
        initFields(data);
        save.setEnabled(isButtonEnabled());
        setupRequiredFields();
    }

    /**
     *Inits fields based on any existing connection data.
     * @param data
     */
    private void initFields(ConnectionData data) {
        if (data != null) {
            host.setText(data.getHost());
            port.setText(data.getPort());
            sid.setText(data.getSid());
            name.setText(data.getUser());
            //TODO: init password field
        } else {
            host.setText("");
            port.setText("");
            sid.setText("");
            name.setText("");
            pwd.setText("");
        }
    }

    /**
     * Sets up required fields so that the Test and Save buttons in the dialog are enabled
     * or disabled depending on whether they have a value.
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

    /**
     * Changes the connect and save buttons enabled status
     * @param enabled
     */
    private void setOKButtonEnabled(boolean enabled) {
        connect.setEnabled(enabled);
        if (!enabled) {
            save.setEnabled(enabled);
        }
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
            Validate validator = new InputValidateImpl();
            boolean valid =
                validator.validateConnectionData(host.getText(), port.getText(),
                                                 sid.getText(), name.getText(),
                                                 new String(pwd.getPassword()));
            if (!valid) {
                //TODO:log stuff
            }
            save.setEnabled(valid);
        }
    }

    /**
     * Responds to clicks on the save button. Saves the connection data for further use.
     */
    private class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            pref.storeRemoteConnectionData(host.getText(), port.getText(),
                                     sid.getText(), name.getText(),
                                     new String(pwd.getPassword()));
        }
    }

    /**
     * Responds to clocks on the clear button. Deletes stored properties and resets the view.
     */
    private class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            pref.clearPreferences();
            initFields(null);
        }
    }
}
