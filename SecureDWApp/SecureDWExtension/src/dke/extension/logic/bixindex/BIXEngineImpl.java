package dke.extension.logic.bixindex;

import com.googlecode.javaewah.EWAHCompressedBitmap;

public class BIXEngineImpl implements BIXEngine {
    public BIXEngineImpl() {
        super();
    }


    // for testing purposes

    public static void main(String[] args) throws Exception {

        VersandArt va = new VersandArt(1, "hermes");
        System.out.print(va.getBitmap());
    }

    public String calculateBIX() {

        /*
        EWAHCompressedBitmap testbitmapModell =
            EWAHCompressedBitmap.bitmapOf(0, 1);

        String s = "bitmap result: ";
        s += toSetBitString(testbitmapModell);

        return s;
*/

        return "";
    }


    public void getBIX() {
    }

    public void updateBIX() {
    }

    public String bitmapToString(EWAHCompressedBitmap b) {

        String s = "";
        for (int k : b)
            s += k + " ";

        return s;
    }
}
