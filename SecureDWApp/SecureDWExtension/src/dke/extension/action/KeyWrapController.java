package dke.extension.action;

import com.googlecode.javaewah.EWAHCompressedBitmap;

import dke.extension.data.preferencesData.AccessPreferences;
import dke.extension.logic.bixindex.BIXEngine;
import dke.extension.logging.MyLogger;
import dke.extension.logic.bixindex.BIXEngineImpl;


import java.io.FileNotFoundException;

import java.util.Map;

import javax.swing.JOptionPane;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.extension.RegisteredByExtension;


@RegisteredByExtension("dke.extension")
public final class KeyWrapController implements Controller {


    public KeyWrapController() {
        super();
    }

    public boolean handleEvent(IdeAction ideAction, Context context) {


        if (context == null)
            return false;


        BIXEngine bixEngine = new BIXEngineImpl();
        EWAHCompressedBitmap b = bixEngine.calculateBIX();


        MyLogger.logMessage("testing bix engine");
        // printing result
        String s = "";
        for (int k : b)
            s += k + " ";


        MyLogger.logMessage(s);
        return false;

    }

    public boolean update(IdeAction ideAction, Context context) {
        ideAction.setEnabled(true);
        return true;
    }

}
