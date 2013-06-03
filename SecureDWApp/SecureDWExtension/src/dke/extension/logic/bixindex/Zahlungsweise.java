package dke.extension.logic.bixindex;

import com.googlecode.javaewah.EWAHCompressedBitmap;

public class Zahlungsweise implements BixObject {

    private String id;
    private int value;
    private EWAHCompressedBitmap bitmap;


    public Zahlungsweise(String id, int value) {
        this.id = id;
        this.value = value;
        bitmapIndex();
    }

    public void bitmapIndex() {
        if (this.value == 1) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf();
        }
        if (this.value == 2) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0);
        }
        if (this.value == 3) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(1);
        }
        if (this.value == 4) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 1);
        }
        if (this.value == 5) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(2);
        }
        if (this.value == 6) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 2);
        }
        if (this.value == 7) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(1, 2);
        }
        if (this.value == 8) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 1, 2);
        }
        if (this.value == 9) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(3);
        }
        if (this.value == 10) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 3);
        }
        if (this.value == 11) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(1, 3);
        }
        if (this.value == 12) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 1, 3);
        }

    }

    public EWAHCompressedBitmap getBitmap() {
        return this.bitmap;
    }

    public String bitMapToString() {
        return null;
    }
}
