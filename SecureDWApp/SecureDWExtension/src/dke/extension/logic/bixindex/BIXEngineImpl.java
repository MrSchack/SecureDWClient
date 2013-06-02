package dke.extension.logic.bixindex;

import com.googlecode.javaewah.EWAHCompressedBitmap;

public class BIXEngineImpl implements BIXEngine {
    public BIXEngineImpl() {
        super();
    }

    public String calculateBIX() {


        EWAHCompressedBitmap testbitmapModell =
            EWAHCompressedBitmap.bitmapOf(1);

        return "calculated bitmap";
        //return toSetBitString(testbitmapModell);


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
