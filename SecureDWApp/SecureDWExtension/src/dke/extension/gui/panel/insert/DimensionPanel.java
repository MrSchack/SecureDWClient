package dke.extension.gui.panel.insert;

import dke.extension.data.preferencesData.ConnectionData;
import dke.extension.gui.panel.config.ConnectionPanel;
import dke.extension.logging.MyLogger;
import dke.extension.logic.preferences.ManagePreferences;
import dke.extension.logic.preferences.ManagePreferencesImpl;
import dke.extension.logic.ControllerImpl;
import dke.extension.logic.Controller;

import dke.extension.logic.dimensionManagement.DimensionObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import oracle.javatools.ui.TransparentPanel;
import oracle.javatools.ui.layout.FieldLayoutBuilder;

public class DimensionPanel extends TransparentPanel {

    private DimensionObject dimObject;

    private final JTextField member1 = new JTextField();
    private final JTextField member2 = new JTextField();
    private final JTextField member3 = new JTextField();

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
        b.add(b.field().label().withText("partnernr:").component(member1));
        b.add(b.field().label().withText("adresse:").component(member2));
        b.add(b.field().label().withText("telnr:").component(member3).button(insert).withText("Insert"));

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

        String dataMember1 = member1.getText().toString();
        String dataMember2 = member2.getText().toString();
        String dataMember3 = member3.getText().toString();

        dimObject.setDimensionName("businesspartner");

        MyLogger.logMessage(dataMember1);
        MyLogger.logMessage(dataMember2);
        MyLogger.logMessage(dataMember3);


        dimObject.addDimensionMember("partnernr", dataMember1);
        dimObject.addDimensionMember("adresse", dataMember2);
        dimObject.addDimensionMember("telnr", dataMember3);


        ControllerImpl.getInstance(null).insertDimensionMember(dimObject);


    }

}
