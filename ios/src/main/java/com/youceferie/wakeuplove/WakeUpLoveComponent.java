package com.youceferie.wakeuplove;

import org.robovm.apple.foundation.Foundation;
import java.io.File;

/**
 * Created by YouCef on 27/02/2016.
 */
//@Module
public class WakeUpLoveComponent {

    /**
     * Whether dummy data should be preloaded into new databases.
     */
    public static final boolean PRELOAD_DATA = true;

  //  @Provides
 //  @Singleton
    public ConnectionPool proivdeConnectionPool() {
        try {
            Class.forName("SQLite.JDBCDriver");
        } catch (ClassNotFoundException e) {
            throw new Error(e);
        }

        /*
         * The SQLite database is kept in
         * <Application_Home>/Documents/db.sqlite. This directory is backed up
         * by iTunes. See http://goo.gl/BWlCGN for Apple's docs on the iOS file
         * system.
         */
        File dbFile = new File(System.getenv("HOME"), "Documents/db.sqlite");
        dbFile.getParentFile().mkdirs();
        Foundation.log("Using db in file: " + dbFile.getAbsolutePath());

        return new SingletonConnectionPool(
                "jdbc:sqlite:" + dbFile.getAbsolutePath());
    }

//    @Provides
  //  @Singleton
    public JdbcAlarm proivdeClientManager(ConnectionPool connectionPool) {
        return new JdbcAlarm(connectionPool, PRELOAD_DATA);
    }

/*
    @Provides
    @Singleton
    public ClientModel proivdeClientModel(ClientManager clientManager, TaskManager taskManager) {
        return new ClientModel(clientManager, taskManager);
    }

    @Provides
    @Singleton
    public TaskModel proivdeTaskModel(ClientModel clientModel, TaskManager taskManager) {
        return new TaskModel(clientModel, taskManager);
    }
*/
}
