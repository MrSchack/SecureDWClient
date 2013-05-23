/* Copyright (c) 2009, Oracle and/or its affiliates.All rights reserved. */

/*
   DESCRIPTION
    Preference class to enable a user to control when the checkin dialog is 
    display when used in conjunction with the pending chanages window.

   MODIFIED    (MM/DD/YY)
      dedwards  01/21/09 - Add copyright
 */
package oracle.jdeveloper.extsamples.vcrcs;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import oracle.ide.panels.TraversalException;
import oracle.ide.util.ResourceUtils;

import oracle.jdeveloper.vcs.generic.VCSProfile;
import oracle.jdeveloper.vcs.generic.VCSProfiles;
import oracle.jdeveloper.vcs.spi.VCSPropertyTraversable;


public class RCSVersionToolsTraversable extends VCSPropertyTraversable {
    private UI _ui;
    private final VCSProfile _profile;

    public RCSVersionToolsTraversable() {
        super();
        _profile = VCSProfiles.getInstance().getProfile(
          RCSProfileListener.RCS_PROFILE_ID);
        
    }

    protected Component getPropertyPage() {
        if (_ui == null)
        {
          _ui = new UI();
        }
        return _ui;
    }

    protected String getDataKey() {
        return _profile.getQualifiedDataKey(RCSProfileListener.VTOOLS_SETTINGS_KEY);
    }

    protected void validateProperties() throws TraversalException {
    }

    protected Map<String, ?> getProperties() {
        Map<String, Integer> props = new HashMap<String,Integer>();
        props.put(
          RCSProfileListener.PENDING_CHANGES_DIALOG_KEY,
          new Integer(((UI)getPropertyPage()).getPendingChangesDialogUsage()));
        return props;
    }

    protected void setProperties(Map<String, ?> properties) {
        Integer usage = (Integer)properties.get(
          RCSProfileListener.PENDING_CHANGES_DIALOG_KEY);
        ((UI)getPropertyPage()).setPendingChangesDialogUsage(usage.intValue());

    }

    private final class UI extends JPanel
    {
      private JLabel _pendingLabel;
      private JLabel _useDialogLabel;
      private ButtonGroup _dialogUsageGroup;
      private JRadioButton _alwaysRadio;
      private JRadioButton _sometimesRadio;
      private JRadioButton _neverRadio;

      private UI()
      {
        super(new GridBagLayout());
        createComponents();
        layoutComponents();
      }
      
      public int getPendingChangesDialogUsage()
      {
        if (_alwaysRadio.isSelected())
        {
          return 0;
        }
        else if (_sometimesRadio.isSelected())
        {
          return 1;
        }
        else if (_neverRadio.isSelected())
        {
          return 2;
        }
        else
        {
          throw new IllegalStateException();
        }
      }
      
      public void setPendingChangesDialogUsage(int usage)
      {
        switch (usage)
        {
          case 0 :
            _alwaysRadio.setSelected(true);
          break;
          case 1 :
            _sometimesRadio.setSelected(true);
          break;
          case 2 :
            _neverRadio.setSelected(true);
          break;
          default :
            throw new IllegalArgumentException();
        }
      }

      private void createComponents()
      {
        _pendingLabel = new JLabel();
        _pendingLabel.setText("Pending Changes Window:");
        
        _useDialogLabel = new JLabel();
        _useDialogLabel.setText("Use Check in Dialog:");
        
        _alwaysRadio = new JRadioButton();
        ResourceUtils.resButton(
          _alwaysRadio,
          "&Always");
        
        _sometimesRadio = new JRadioButton();
        ResourceUtils.resButton(
          _sometimesRadio,
          "W&hen Comments Hidden");
        
        _neverRadio = new JRadioButton();
        ResourceUtils.resButton(
          _neverRadio,
          "&Never");
        
        _dialogUsageGroup = new ButtonGroup();
        _dialogUsageGroup.add(_alwaysRadio);
        _dialogUsageGroup.add(_sometimesRadio);
        _dialogUsageGroup.add(_neverRadio);
      }
      
      private void layoutComponents()
      {
        add(_pendingLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
          GridBagConstraints.NORTHWEST,
          GridBagConstraints.HORIZONTAL,
          new Insets(0, 0, 0, 0), 0, 0));
        
        add(_useDialogLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1,
          GridBagConstraints.NORTHWEST,
          GridBagConstraints.HORIZONTAL,
          new Insets(4, 26, 0, 0), 0, 0));
        
        add(_alwaysRadio, new GridBagConstraints(0, 2, 1, 1, 1, 1,
          GridBagConstraints.NORTHWEST,
          GridBagConstraints.HORIZONTAL,
          new Insets(4, 50, 0, 0), 0, 0));

        add(_sometimesRadio, new GridBagConstraints(0, 3, 1, 1, 1, 1,
          GridBagConstraints.NORTHWEST,
          GridBagConstraints.HORIZONTAL,
          new Insets(4, 50, 0, 0), 0, 0));

        add(_neverRadio, new GridBagConstraints(0, 4, 1, 1, 1, 1,
          GridBagConstraints.NORTHWEST,
          GridBagConstraints.HORIZONTAL,
          new Insets(4, 50, 0, 0), 0, 0));
      }
    }
    
}
