package dke.extension.logic.bixindex;

import com.googlecode.javaewah.EWAHCompressedBitmap;

public class Zeit implements BixObject {

    private String id;
    private String value;
    private EWAHCompressedBitmap bitmap;

    public Zeit(String id, String value) {
        this.id = id;
        this.value = value;
        bitmapIndex();
    }

    public void bitmapIndex() {
        if (this.value.equals("2012-05-22")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 1);
        }
        if (this.value.equals("2012-06-14")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(2);
        }
        if (this.value.equals("2013-04-27")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(2, 3);
        }
        if (this.value.equals("2013-05-15")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(1, 2, 3);
        }
        if (this.value.equals("2012-03-25")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf();
        }
        if (this.value.equals("2012-04-17")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0);
        }
        if (this.value.equals("2012-07-09")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 1, 2);
        }
        if (this.value.equals("2012-09-27")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 3);
        }
        if (this.value.equals("2012-09-08")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(3);
        }
        if (this.value.equals("2013-05-04")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 2, 3);
        }
        if (this.value.equals("2012-11-25")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 1, 3);
        }
        if (this.value.equals("2012-06-16")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 2);
        }
        if (this.value.equals("2012-06-21")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(1, 2);
        }
        if (this.value.equals("2012-05-14")) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(1);
        }
        if (this.value.equals(("2012-11-13"))) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(1, 3);
        }

    }

    public EWAHCompressedBitmap getBitmap() {
        return this.bitmap;
    }

    public String bitMapToString() {
        return null;
    }
}
