package com.youceferie.wakeuplove;

import java.sql.ResultSet;
import java.util.Objects;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by YouCef on 27/02/2016.
 */
public class JdbcAlarm {

    // @formatter:off
    private static final String SQL_CREATE_TABLE_CLIENTS =
            "create table if not exists alarms ("
                    + "  id varchar(255) not null,"
                    + "  time varchar(255) not null,"
                    + "  title varchar(255) not null,"
                    + "  snooze varchar(255) not null,"
                    + "  song varchar(255) not null"
                    + ")";
    private static final String SQL_SELECT_CLIENTS =
            "select * from alarms order by time";

    private final ArrayList<Alarm> alarms = new ArrayList<>();
    /*
     * NOTE: The update and insert statements must list the columns in the same
     * order.
     */
    private static final String SQL_INSERT_CLIENT =
            "insert into alarms (id, time, title,snooze,song) values (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_CLIENT =
            "update alarms set title = ?, snooze = ? where id = ?";
    private static final String SQL_DELETE_CLIENT =
            "delete from alarms where id = ?";
    // @formatter:on


    private final ConnectionPool connectionPool;
    boolean dirty = true;

    public ArrayList getJdbcAlarm()
    {
        return  alarms;
    }
    public JdbcAlarm(ConnectionPool connectionPool, boolean preloadData) {
        this.connectionPool = Objects.requireNonNull(connectionPool, "connectionPool");
        createSchemaIfNeeded(preloadData);
    }

    private void createSchemaIfNeeded(boolean preloadData) {
        try {
            Connection conn = getConnection();
            ResultSet tables = conn.getMetaData().getTables(null, null, "alarms", null);
            if (!tables.next()) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(SQL_CREATE_TABLE_CLIENTS);
                    if (preloadData) {
                        preload();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    private void preload() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("alarms.csv"), "UTF8"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                Connection conn = getConnection();
                try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_CLIENT)) {
                    stmt.setString(1, parts[1]);
                    stmt.setString(2, parts[2]);
                    stmt.setString(3, parts[3]);
                    stmt.setString(4, parts[4]);
                    stmt.setString(5, parts[0]);
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Alarm> getAlarms() {
        if (dirty) {
            alarms.clear();
            try {
                Connection conn = getConnection();
                try (PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_CLIENTS)) {
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        Alarm alarm = new Alarm();
                        alarm.setIdFromStr(rs.getString("id"));
                        alarm.setDateFromStr(rs.getString("time"));
                        // TODO DEBUG prendre le titre à la fin
                        //alarm.setTitle(rs.getString("title"));
                        alarm.setTitle( rs.getString("id"));
                        alarm.setSnoozeFromStr(rs.getString("snooze"));
                        alarm.setSongs(rs.getString("song"));
                       alarms.add(alarm);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            dirty = false;
        }
        return alarms;
    }

    public void insert(Alarm alarm) {
        String sql = SQL_INSERT_CLIENT;
        try {
            Connection conn = getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, String.valueOf(alarm.getId()));
                stmt.setString(2, alarm.getDate().toString());
                stmt.setString(3, alarm.getTitle());
                stmt.setString(4, String.valueOf(alarm.isSnooze()));
                stmt.setString(5, alarm.getSongs());

                stmt.executeUpdate();
                System.out.print("INSERT ALARM " + alarm.getDate().toString()+ " with ID "+ alarm.getId() +"\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dirty = true;
        }
    }

    public boolean delete(Alarm alarm) {
        String id = String.valueOf(alarm.getId());
        try {
            Connection conn = getConnection();
           // taskManager.deleteForClient(client);
            try (PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_CLIENT)) {
                stmt.setString(1, id);
                return stmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dirty = true;
        }
    }

}
