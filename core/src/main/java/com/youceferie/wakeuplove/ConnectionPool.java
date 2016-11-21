package com.youceferie.wakeuplove;

/**
 * Created by YouCef on 27/02/2016.
 */

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 */
public interface ConnectionPool {

    Connection getConnection() throws SQLException;

}
