package dke.extension.gui.panel.insert;

import dke.extension.data.preferencesData.ConnectionData;
import dke.extension.gui.panel.config.ConnectionPanel;
import dke.extension.logging.MyLogger;
import dke.extension.logic.preferences.ManagePreferences;
import dke.extension.logic.preferences.ManagePreferencesImpl;
import dke.extension.logic.ControllerImpl;
import dke.extension.logic.Controller;

import dke.extension.logic.dimensionManagement.DimensionObject;

import dke.extension.mvc.SecureDWModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import oracle.javatools.ui.TransparentPanel;
import oracle.javatools.ui.layout.FieldLayoutBuilder;

import org.bouncycastle.crypto.CryptoException;

public class DimensionPanel extends TransparentPanel {

    private DimensionObject dimObject;

    private final JTextField partnernr = new JTextField();
    private final JTextField adresse = new JTextField();
    private final JTextField telnr = new JTextField();
    private final JTextField email = new JTextField();
    private final JTextField weburl = new JTextField();

    private final JButton insert = new JButton();
    //for testing purposes
    private final JButton clear = new JButton();
    private ManagePreferences pref = new ManagePreferencesImpl();

    public DimensionPanel() {
        initComponents();
        layoutComponents();

    }

    /**
     * Defines the basic layout for the components.
     */
    private void layoutComponents() {
        FieldLayoutBuilder b = new FieldLayoutBuilder(this);
        b.add(b.field().label().withText("partnernr:").component(partnernr));
        b.add(b.field().label().withText("adresse:").component(adresse));
        b.add(b.field().label().withText("telnr:").component(telnr));
        b.add(b.field().label().withText("email:").component(email));
        b.add(b.field().label().withText("weburl:").component(weburl).button(insert).withText("Insert"));
        ;

    }

    private void initComponents() {
        insert.addActionListener(new InsertListener());

    }

    private class InsertListener implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            testInsertDimensionMembers();
        }
    }

    private void testInsertDimensionMembers() {

        dimObject = new DimensionObject(false);

        String dataMember1 = partnernr.getText();
        String dataMember2 = adresse.getText();
        String dataMember3 = telnr.getText();
        String dataMember4 = email.getText();
        String dataMember5 = weburl.getText();

        dimObject.setDimensionName("businesspartner");

        if (dataMember1 == null || dataMember2 == null ||
            dataMember3 == null) {
            MyLogger.logMessage("is null");
        } else {

            dimObject.addDimensionMember("partnernr", dataMember1);
            dimObject.addDimensionMember("adresse", dataMember2);
            dimObject.addDimensionMember("telnr", dataMember3);
            dimObject.addDimensionMember("email", dataMember4);
            dimObject.addDimensionMember("weburl", dataMember5);
            dimObject.addDimensionMember("vers", "1");

            SecureDWModel model = SecureDWModel.getInstance();
            Controller ctrl = ControllerImpl.getInstance(model);
            try {
                ctrl.insertDimensionMember(dimObject);
            } catch (CryptoException e) {
                MyLogger.logMessage(e.getMessage());
            } catch (NoSuchAlgorithmException e) {
                MyLogger.logMessage(e.getMessage());
            } catch (InvalidKeySpecException e) {
                MyLogger.logMessage(e.getMessage());
            }
        }

    }

}
