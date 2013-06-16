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
    
    /**
     * Called when connection data changed
     * @param e
     */
    public void connectionDataChanged(SecureDWEvent e);
    
    /**
     * Called when initialization process is complete
     * @param e
     */
    public void initComplete(SecureDWEvent e);
}
