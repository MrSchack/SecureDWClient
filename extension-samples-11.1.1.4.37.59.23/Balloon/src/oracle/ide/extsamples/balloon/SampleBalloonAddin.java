/* $Header: SampleBalloonAddin.java 16-aug-2007.20:21:18 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       08/07/07 - Initial Revision
 */
package oracle.ide.extsamples.balloon;

import java.awt.Component;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

import oracle.bali.ewt.text.MultiLineLabel;

import oracle.bali.ewt.text.WordWrapper;

import oracle.ide.Addin;
import oracle.ide.Ide;
import oracle.ide.IdeEvent;
import oracle.ide.IdeListener;
import oracle.ide.extension.RegisteredByExtension;

import oracle.javatools.dialogs.MessageDialog;
import oracle.javatools.icons.OracleIcons;
import oracle.javatools.ui.balloon.Balloon;
import oracle.javatools.ui.balloon.BalloonConstraint;
import oracle.javatools.ui.balloon.BalloonManager;

/**
 * Demonstrates how to install a balloon notification into the IDE's status
 * bar.
 */
@RegisteredByExtension("oracle.ide.extsamples.balloon")
final class SampleBalloonAddin implements Addin {
    private JComponent statusIcon;

    public void initialize() {
        Ide.addIdeListener(new IdeListener() {
                    public void addinsLoaded(IdeEvent ideEvent) {
                    }

                    public void mainWindowClosing(IdeEvent ideEvent) {
                    }

                    public void mainWindowOpened(IdeEvent ideEvent) {
                        showBalloon();
                    }
                });
    }

    private void showBalloon() {
        statusIcon = installStatusBarIcon();
        Balloon balloon = createBalloon();
        balloon.addActionListener(createBalloonAction());

        BalloonManager manager = BalloonManager.forTarget(statusIcon);
        manager.show(balloon, BalloonManager.TTL_OPTIONAL_USER_TASK);
    }

    private JComponent installStatusBarIcon() {
        JLabel icon = new JLabel();
        icon.setIcon(icon());
        ideStatusTray().add(icon, 0);
        ideStatusTray().revalidate();

        return icon;
    }

    private Balloon createBalloon() {
        JLabel header = new JLabel("Hello World!");
        header.setIcon(icon());
        header.setFont(header.getFont().deriveFont(Font.BOLD));

        Component text =
            createMultiLineLabel("This is balloon was installed by the Balloon Extension SDK sample.");
        JComponent footer = new JCheckBox("Doesn't do anything");
        footer.setOpaque(false);

        Balloon balloon = new Balloon();
        balloon.add(header, BalloonConstraint.HEADER);
        balloon.add(text, BalloonConstraint.TEXT);
        balloon.add(footer, BalloonConstraint.FOOTER);

        return balloon;
    }

    private Action createBalloonAction() {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                MessageDialog.information(Ide.getMainWindow(),
                                          "Thanks for clicking!",
                                          "Balloon ESDK Sample", null);
                if (statusIcon != null) {
                    ideStatusTray().remove(statusIcon);
                    ideStatusTray().revalidate();
                }
            }
        };
    }

    private JComponent ideStatusTray() {
        return Ide.getStatusBar().getToolbar();
    }

    private Component createMultiLineLabel(String text) {
        MultiLineLabel label = new MultiLineLabel();
        label.setTextWrapper(WordWrapper.getTextWrapper());
        label.setPreferredColumns(40);
        label.setText(text);

        return label;
    }

    private Icon icon() {
        return OracleIcons.getIcon(OracleIcons.STAR);
    }
}
