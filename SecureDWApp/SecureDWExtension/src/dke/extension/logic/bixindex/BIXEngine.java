package dke.extension.logic.bixindex;

import com.googlecode.javaewah.EWAHCompressedBitmap;

public interface BIXEngine {

    public EWAHCompressedBitmap calculateBIX();

    public void getBIX();

    public void updateBIX();
}
