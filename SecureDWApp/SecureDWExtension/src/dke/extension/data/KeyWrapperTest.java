package dke.extension.data;

import dke.extension.data.preferencesData.AccessPreferences;

import java.io.FileNotFoundException;

import javax.swing.JOptionPane;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.wizard.Wizard;

public class KeyWrapperTest extends Wizard {
    private static final String WIZARD_NAME = "KeyWrapperTest";
        
    public KeyWrapperTest() {
        super();
    }

    public boolean isAvailable(Context context) {
        return true;
    }

    public boolean invoke(Context context) {
        
      String msg = System.getenv("JAVA_HOME");
      String msg1 = System.getenv("SQLDEVELOPER_HOME");
      
      JOptionPane.showMessageDialog(Ide.getMainWindow(),
                                    "java home: " + msg + "\n sql home: " + msg1, WIZARD_NAME,
                                    JOptionPane.ERROR_MESSAGE);
      
      /*if (!isAvailable(context))
          return false;

      AccessPreferences pref = new AccessPreferences();

        try {
            if(pref.getKey() != null)
              return true;
            else
              return false;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(Ide.getMainWindow(),
                                          "Fehler beim Aufruf getKey()", WIZARD_NAME,
                                          JOptionPane.ERROR_MESSAGE);
          }*/
        return false;
    }

    public String getShortLabel() {
        return WIZARD_NAME;
    }
}
