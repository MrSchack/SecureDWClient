package dke.extension.logic.bixindex;

import com.googlecode.javaewah.EWAHCompressedBitmap;

public interface BIXEngine {

    public String calculateBIX();

    public void getBIX();

    public void updateBIX();

    /**
     * Bitmap to String Converter Method.
     * @param b
     * @return
     */
    public String bitmapToString(EWAHCompressedBitmap b);

}
