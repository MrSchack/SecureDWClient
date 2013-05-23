/* $Header: CreateExternalToolAddin.java 16-aug-2007.22:01:29 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       08/07/07 - Initial Revision
 */

package oracle.ide.extsamples.externaltoolcreation;

import oracle.ide.Addin;
import oracle.ide.extension.RegisteredByExtension;
import oracle.ide.externaltools.ExternalToolManager;

import java.util.Collection;

import oracle.ide.Ide;
import oracle.ide.IdeEvent;
import oracle.ide.IdeListener;
import oracle.ide.externaltools.Availability;
import oracle.ide.externaltools.ExternalProgramToolProperties;
import oracle.ide.externaltools.ExternalTool;
import oracle.ide.externaltools.ExternalToolBaseProperties;
import oracle.ide.externaltools.ExternalToolFactory;
import oracle.ide.externaltools.IntegrationPoint;

/**
 * Creates an external tool automatically during startup.
 */
@RegisteredByExtension("oracle.ide.extsamples.externaltoolcreation")
final class CreateExternalToolAddin implements Addin {
    // Use a unique identifier here, don't just copy this sample vertabim.
    private static final String TOOL_ID = "org.foo.myTool";

    public void initialize() {
        Ide.addIdeListener(new IdeListener() {
                    public void addinsLoaded(IdeEvent e) {
                    }

                    public void mainWindowClosing(IdeEvent e) {
                    }

                    public void mainWindowOpened(IdeEvent e) {
                        Ide.removeIdeListener(this);
                        deferredInitialize();
                    }

                });
    }

    private void deferredInitialize() {
        if (toolExistsAlready())
            return;

        ExternalToolManager.getExternalToolManager().addExternalTool(createTool());
    }

    private boolean toolExistsAlready() {
        return ExternalToolManager.scannerTags(allTools()).contains(TOOL_ID);
    }

    private Collection<ExternalTool> allTools() {
        return ExternalToolManager.getExternalToolManager().tools();
    }

    private ExternalTool createTool() {
        ExternalTool tool =
            ExternalToolFactory.getInstance().createProgramTool();

        configureBaseProperties(tool);
        configureProgramProperties(tool);

        return tool;
    }

    private void configureBaseProperties(ExternalTool tool) {
        ExternalToolBaseProperties props =
            ExternalToolBaseProperties.getInstance(tool);

        props.setScannerTag(TOOL_ID); // Important, this is how we avoid creating duplicates.
        props.setCaption("Sample Tool (from the ESDK ExternalToolCreation sample)");
        props.setAvailability(Availability.ALWAYS);
        props.setIntegrated(IntegrationPoint.TOOLS_MENU, true);
        props.setIntegrated(IntegrationPoint.NAVIGATOR_MENU, true);
    }

    private void configureProgramProperties(ExternalTool tool) {
        ExternalProgramToolProperties props =
            ExternalProgramToolProperties.getInstance(tool);

        props.setExecutable("c:\\path\\to\\some\\program.exe");
        props.setRunDirectory("c:\\somepath");
        props.setArguments("${file.path}");
    }
}
