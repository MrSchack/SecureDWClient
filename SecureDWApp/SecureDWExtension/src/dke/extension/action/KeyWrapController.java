package dke.extension.action;

import dke.extension.data.preferencesData.AccessPreferences;

import dke.extension.logging.MyLogger;

import java.io.FileNotFoundException;

import java.util.Map;

import javax.swing.JOptionPane;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.extension.RegisteredByExtension;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

@RegisteredByExtension("dke.extension")
public final class KeyWrapController implements Controller{
    public KeyWrapController() {
        super();
    }

    public boolean handleEvent(IdeAction ideAction, Context context) {
      MyLogger.logMessage(Ide.getProductHomeDirectory());
      
      
      if (context == null)
          return false;

      AccessPreferences pref = new AccessPreferences();

        try {
            if(pref.getKey() != null)
              return true;
            else
              return false;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(Ide.getMainWindow(),
                                          "Fehler beim Aufruf getKey()", "KeyWrap Test",
                                          JOptionPane.ERROR_MESSAGE);
          }
        return false;
    }

    public boolean update(IdeAction ideAction, Context context) {
      ideAction.setEnabled(true);
      return true;
    }
}
