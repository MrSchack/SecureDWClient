package dke.extension.logic.crypto;

import dke.extension.data.dimension.DataDictionary;
import dke.extension.exception.SecureDWException;

import java.sql.SQLException;

public class CastObjectTo {

    public static int getInteger(String columnValue) {
        return Integer.parseInt(columnValue);
    }
}
