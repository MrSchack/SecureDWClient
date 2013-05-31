package dke.extension.logic.bixindex;

import com.googlecode.javaewah.EWAHCompressedBitmap;

public class BIXEngineImpl implements BIXEngine {
    public BIXEngineImpl() {
        super();
    }

    public EWAHCompressedBitmap calculateBIX() {


        EWAHCompressedBitmap testbitmapModellewahBitmap1 =
            EWAHCompressedBitmap.bitmapOf(0, 2, 64, 1 << 30);
        EWAHCompressedBitmap ewahBitmap2 =
            EWAHCompressedBitmap.bitmapOf(1, 3, 64, 1 << 30);

        EWAHCompressedBitmap testbitmapModell =
            EWAHCompressedBitmap.bitmapOf(1);


        return testbitmapModell;


    }

    //Converter-Methode

    private static String toSetBitString(EWAHCompressedBitmap b) {
        String s = "";
        for (int k : b)
            s += k + " ";
        return s;
    }


    public void getBIX() {
    }

    public void updateBIX() {
    }

}
