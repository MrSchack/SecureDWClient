package dke.extension.logic.bixindex;

import com.googlecode.javaewah.EWAHCompressedBitmap;

public class Produkt implements BixObject {

    private String id;
    private long value;
    private EWAHCompressedBitmap bitmap;


    public Produkt(String id, long value) {
        this.id = id;
        this.value = value;
        bitmapIndex();
    }


    public void bitmapIndex() {
        if (this.value == 8804794913L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf();
        }
        if (this.value == 36043177910587L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(1, 2, 3);
        }
        if (this.value == 6533169462640L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 3);
        }
        if (this.value == 7410084461539L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(1, 3);
        }
        if (this.value == 65680420036L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(1);
        }
        if (this.value == 4806980850897L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(1, 2);
        }
        if (this.value == 5250224943989L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(3);
        }
        if (this.value == 656424836471307L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(1, 4);
        }
        if (this.value == 8865478337872L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 1, 3);
        }
        if (this.value == 84019613101423L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 1, 2, 3);
        }
        if (this.value == 95045375109053L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(4);
        }
        if (this.value == 452653091723L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 1);
        }
        if (this.value == 6448652927354897L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 1, 4);
        }
        if (this.value == 9767282644522L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(2, 3);
        }
        if (this.value == 5238224565630L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 1, 2);
        }
        if (this.value == 4591143210430L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 2);
        }
        if (this.value == 22719771170053L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 2, 3);
        }
        if (this.value == 743549087354L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(2);
        }
        if (this.value == 9214705873L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0);
        }
        if (this.value == 430047151719730L) {
            this.bitmap = EWAHCompressedBitmap.bitmapOf(0, 4);
        }
    }

    public EWAHCompressedBitmap getBitmap() {
        return this.bitmap;
    }
}

