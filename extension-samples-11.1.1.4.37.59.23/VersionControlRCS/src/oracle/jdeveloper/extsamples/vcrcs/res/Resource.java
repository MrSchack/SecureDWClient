/* $Header: jdev/src/esdk-samples/sample/VersionControlRCS/src/oracle/jdeveloper/extsamples/vcrcs/res/Resource.java /main/4 2009/01/22 10:04:01 dedwards Exp $ */

/* Copyright (c) 2007, 2009, Oracle and/or its affiliates.
All rights reserved. */

/*
   Description
    Text resources referenced from extension.xml
   MODIFIED    (MM/DD/YY)
    dedwards    01/21/09 - Add copyright
    dedwards    01/20/09 - Compare previous
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.jdeveloper.extsamples.vcrcs.res;

import java.util.ListResourceBundle;

public class Resource extends ListResourceBundle {
    public Object[][] getContents() {
        return contents;
    }
    static final Object[][] contents = {
        // LOCALIZE THIS
        { "RCS_NAME", "JDeveloper Extensions SDK - RCS" },
        { "RCS_OWNER", "Oracle" },
        { "RCS_DESCRIPTION", "Provides RCS source control integration (Example)" },
        { "RCS_SHORT_NAME", "RCS" },
        { "RCS_LONG_NAME", "RCS source control" },
        { "ADD_DIALOG_TITLE", "Add" },
        { "ADD_DIALOG_HINT", "The following files will be added to RCS" },
        { "CHECKIN_DIALOG_TITLE", "Check In" },
        { "CHECKIN_DIALOG_HINT", "The following files will be checked into RCS" },
        { "CHECKOUT_DIALOG_TITLE", "Check Out" },
        { "CHECKOUT_DIALOG_HINT", "The following files will bhe checked out from RCS" },
        { "OPERATION_ADD_CONTROL_NAME", "Add..." },
        { "OPERATION_ADD_CONTROL_TOOLTIP", "Add items" },
        { "OPERATION_ADD_CONTROL_MNEMONIC", "A" },
        { "OPERATION_CHECKOUT_CONTROL_NAME", "Check Out..." },
        { "OPERATION_CHECKOUT_CONTROL_TOOLTIP", "Check out items" },
        { "OPERATION_CHECKOUT_CONTROL_MNEMONIC", "O" },
        { "OPERATION_CHECKIN_CONTROL_NAME", "Check In..." },
        { "OPERATION_CHECKIN_CONTROL_TOOLTIP", "Check in items" },
        { "OPERATION_CHECKIN_CONTROL_MNEMONIC", "I" },        
        { "OPERATION_COMPARE_PREVIOUS_NAME", "Previous" },
        { "OPERATION_COMPARE_PREVIOUS_TOOLTIP", "Compare with previous revison" },
        { "OPERATION_COMPARE_PREVIOUS_MNEMONIC", "P" },
        { "OPERATION_COMPARE_OTHER_NAME", "Other" },
        { "OPERATION_COMPARE_OTHER_TOOLTOP", "Compare with another other" },
        { "OPERATION_COMPARE_OTHER_NAME", "O" },        
        { "LABEL_NOT_UNDER_RCS", "Not under RCS"},
        { "LABEL_CHECKOUT_RCS", "Checked out from RCS"},
        { "LABEL_CHECKIN_RCS", "Checked into RCS" },
        { "LABEL_GENERAL_TRAVERSABLE", "General" },
        { "CHANGELIST_OUTGOING", "Out Going" },
        { "CHANGELIST_OUTGOING_BUSY", "Out Going - Busy" },
        { "CHANGELIST_OUTGOING_STATUS_LABEL", "Checked Out" },
        { "CHANGELIST_CANDIDATE", "Candidates" },
        { "CHANGELIST_CANDIDATE_BUSY", "Candidate - Busy" },
        { "HISTORY_PROPERTY_AUTHOR_SHORT", "Author" },
        { "HISTORY_PROPERTY_AUTHOR_LONG", "Author" },    
        { "PREFERENCES_VTOOLS", "Version Tools" },
        }
      
    // END OF MATERIAL TO LOCALIZE
    ;

    public Resource() {
    }
}
