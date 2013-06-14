package dke.extension.logic.dimensionManagement;

import java.util.HashMap;

public class DimensionObject {

    private String dimensionName;
    private HashMap<String, String> dimensionMembers;
    private boolean encrypted;
    private DimensionObject encryptedObject;

    public DimensionObject(boolean encrypted) {
        super();
        setEncrypted(encrypted);
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public void setEncryptedObject(DimensionObject encObj) {
        if (this.isEncrypted() && encryptedObject != null) {
            this.encryptedObject = encObj;
        }
    }


    public void setDimensionMembers(HashMap<String, String> dimensionMembers) {
        this.dimensionMembers = dimensionMembers;
    }


    public DimensionObject getEncryptedObject() {
        return encryptedObject;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }
}
