package dke.extension.logic.bixindex;

import com.googlecode.javaewah.EWAHCompressedBitmap;

public class VersandArt implements BixObject {

    private int id;
    private String value;
    private EWAHCompressedBitmap bitmap;

    public VersandArt(int id, String value) {
        this.id = id;
        this.value = value;
        bitmapIndex();
    }

    public void bitmapIndex() {
        if (this.value.equalsIgnoreCase("dpd")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(1);
        }
        if (this.value.equalsIgnoreCase("hermes")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 1);
        }
        if (this.value.equalsIgnoreCase("dhl")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0);
        }
        if (this.value.equalsIgnoreCase("postzustellung")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(2);
        }
        if (this.value.equalsIgnoreCase("botendienst")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf();
        }
    }


    public EWAHCompressedBitmap getBitmap() {
        return this.bitmap;
    }


}
