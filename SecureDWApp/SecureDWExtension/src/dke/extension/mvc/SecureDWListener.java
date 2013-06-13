package dke.extension.mvc;

import java.util.EventListener;

/**
 * Interface that must be implemented by clients that are notified when 
 * some changes in SecureDW.
 */
public interface SecureDWListener extends EventListener {
    
    /**
     * Called when a tab gains focus
     * @param e
     */
    public void focusGained(SecureDWEvent e);
}
