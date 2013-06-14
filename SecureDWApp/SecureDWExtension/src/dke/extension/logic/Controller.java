package dke.extension.logic;

import dke.extension.logic.dimensionManagement.DimensionObject;
import dke.extension.mvc.SecureDWModel;

import java.io.IOException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.sql.SQLException;

import org.bouncycastle.crypto.CryptoException;

public interface Controller {

    public void processQuery();

    public void insertFacts();

    /**
     * @param dimObject
     */
    public void insertDimensionMember(DimensionObject dimObject) throws CryptoException,
                                                                        NoSuchAlgorithmException,
                                                                        InvalidKeySpecException;

    /**
     * Checks, if local DB exists and last initialization was successful. If local DB does not exists or
     * last initialization wasnt successfull initialization is done.
     *
     * Start initializing by
     *  - set up local db
     *  - check if connection data exists
     *    if yes
     *      - update dimension tables
     *          Updates local database by first comparing remote version of dimension tables
     *          and local version. If remote version is newer, all new tuples are retrieved from the server.
     *          During the insert process of new dimension members into the local database, BIX values are
     *          calculated and stored.
     *      - generate BIX
     *    else
     *      - throw Exception, that no connection data is available
     *
     *  @throws SQLException that no connection data is available
     *  @throws IOException if SQL script can not be read
     */
    public void initialize() throws SQLException, IOException;

    /**
     * Forces new initialization no matter if DB has been initialized already or not
     * @param force, true if init should be forced
     *
     * @throws SQLException that no connection data is available
     * @throws IOException if SQL script can not be read
     */
    public void initialize(boolean force) throws SQLException, IOException;

    public void setModel(SecureDWModel model);

    public SecureDWModel getModel();
}
