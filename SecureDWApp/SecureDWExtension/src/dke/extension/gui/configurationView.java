package dke.extension.gui;

public class configurationView {
    public configurationView() {
        super();
    }
}

/*
 * Panel example
 */
//package dke.extension1.gui;
//
//import dke.extension1.jdbc.TestConnection;
//import dke.extension1.logging.MessageWindow;
//
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyVetoException;
//
//import java.beans.VetoableChangeListener;
//
//import javax.swing.JButton;
//import javax.swing.JPanel;
//import javax.swing.JPasswordField;
//import javax.swing.JTextField;
//
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//import javax.swing.text.JTextComponent;
//
//import oracle.bali.ewt.dialog.DialogHeader;
//import oracle.bali.ewt.dialog.JEWTDialog;
//
//import oracle.ide.Context;
//
//import oracle.ide.Ide;
//
//import oracle.javatools.icons.OracleIcons;
//import oracle.javatools.ui.layout.FieldLayoutBuilder;
//
///**
// * A panel which is displayed to the user in order for the user to input jdbc connection information.
// */
//public class ConnectionPanel extends JPanel {
//    private final JTextField host = new JTextField();
//    private final JTextField port = new JTextField();
//    private final JTextField sid = new JTextField();
//    private final JTextField name = new JTextField();
//    private final JPasswordField pwd = new JPasswordField();
//
//    private static MessageWindow messageWindow = null;
//
//    private VetoableChangeListener dialogListener;
//
//    ConnectionPanel() {
//        initComponents();
//        layoutComponents();
//    }
//
//    /**
//     * Defines the basic layout for the components.
//     */
//    private void layoutComponents() {
//        FieldLayoutBuilder b = new FieldLayoutBuilder(this);
//        b.add(b.field().label().withText("Host name").component(host));
//        b.add(b.field().label().withText("Port").component(port));
//        b.add(b.field().label().withText("SID").component(sid));
//        b.add(b.field().label().withText("User name").component(name));
//        b.add(b.field().label().withText("Password").component(pwd));
//    }
//
//    private void initComponents() {
//
//    }
//
//    /**
//     *Starts the dialog.
//     * @param context the currently active context
//     * @return
//     */
//    public boolean runDialog(Context context) {
//        if (context == null)
//            throw new NullPointerException("context is null");
//
//        final JEWTDialog dialog =
//            JEWTDialog.createDialog(Ide.getMainWindow(), "Create new example connection",
//                                    JEWTDialog.BUTTON_DEFAULT);
//        dialog.setContent(this);
//
//        dialog.setDialogHeader(new DialogHeader("Enter the details of your new connection.",
//                                                OracleIcons.toImage(OracleIcons.getIcon(OracleIcons.HEADER_SIMPLEFILE))));
//
//        // Standard idiom to do some task without hiding the dialog. This is a
//        // good practice to follow, because if there is an error, the dialog remains
//        // on screen and the user can correct any problems before clicking OK again.
//        dialogListener = createDialogListener(context, dialog);
//        dialog.addVetoableChangeListener(dialogListener);
//
//        setupRequiredFields(dialog);
//
//        return dialog.runDialog();
//    }
//
//    /**
//     * Sets up required fields so that the Connect and Test buttons in the dialog are enabled
//     * or disabled depending on whether they have a value.
//     *
//     * @param dialog the dialog to control the buttons of.
//     */
//    private void setupRequiredFields(final JEWTDialog dialog) {
//        dialog.setOKButtonEnabled(isButtonEnabled());
//        ButtonUpdater listener = new ButtonUpdater(dialog);
//        listener.attach(host);
//        listener.attach(port);
//        listener.attach(sid);
//        listener.attach(name);
//        listener.attach(pwd);
//    }
//
//    /**
//     * Creates a listener that will react when the user clicks on the OK
//     * button to dismiss the dialog.
//     */
//    private VetoableChangeListener createDialogListener(final Context c,
//                                                        final JEWTDialog dialog) {
//        return new VetoableChangeListener() {
//            public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
//                if (JEWTDialog.isDialogClosingEvent(evt)) {
//                    try {
//                        String pwdString = new String(pwd.getPassword());
//                        TestConnection.getInstance().connect(host.getText(),
//                                                             port.getText(),
//                                                             sid.getText(),
//                                                             name.getText(),
//                                                             pwdString);
//                        logMessage("Succesfully connected to " +
//                                   sid.getText());
//                    } catch (Exception e) {
//                        logMessage(e.getMessage());
//                        throw new PropertyVetoException(e.getMessage(), evt);
//                    }
//                }
//            }
//        };
//    }
//
//    /**
//     * Determine whether the OK button should be enabled.
//     */
//    private boolean isButtonEnabled() {
//        return host.getText().trim().length() > 0 &&
//            port.getText().trim().length() > 0 &&
//            sid.getText().trim().length() > 0 &&
//            name.getText().trim().length() > 0 && pwd.getPassword().length > 0;
//    }
//
//    /**
//     * Responds to keypresses in the text fields, and updates the OK button
//     * of the dialog.
//     */
//    private class ButtonUpdater implements DocumentListener {
//        private final JEWTDialog _dialog;
//
//        ButtonUpdater(JEWTDialog dialog) {
//            _dialog = dialog;
//        }
//
//        private void update() {
//            _dialog.setOKButtonEnabled(isButtonEnabled());
//        }
//
//        public void insertUpdate(DocumentEvent e) {
//            update();
//        }
//
//        public void removeUpdate(DocumentEvent e) {
//            update();
//        }
//
//        public void changedUpdate(DocumentEvent e) {
//            update();
//        }
//
//        void attach(JTextComponent tc) {
//            tc.getDocument().addDocumentListener(this);
//        }
//    }
//
//    private void logMessage(String message) {
//        if (messageWindow == null) {
//            messageWindow = new MessageWindow("Example extension Window");
//        }
//        messageWindow.show();
//        messageWindow.log(message);
//        messageWindow.log("\n");
//    }
//}
