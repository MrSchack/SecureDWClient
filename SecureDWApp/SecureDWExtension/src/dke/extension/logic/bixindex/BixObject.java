package dke.extension.logic.bixindex;

import com.googlecode.javaewah.EWAHCompressedBitmap;

public interface BixObject {

    public void bitmapIndex();

    /**
     * @return
     */
    public EWAHCompressedBitmap getBitmap();

}
