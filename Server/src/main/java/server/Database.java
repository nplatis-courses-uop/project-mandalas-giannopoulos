package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * <dt><strong>Data Access Object (DAO) class</strong></dt>
 * <dt>Connects to the database for CRUD operations</dt>
 */
public class Database {
    /**
     * Creates an SQLite table for logging the received cleaning orders
     */
    static void init() {
        try (ConnectionSource conn = new JdbcConnectionSource("jdbc:sqlite:Server/db/book.db")) {
            TableUtils.createTableIfNotExists(conn, Order.class);
            conn.close();
        } catch (SQLException|IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Creates an entry in database from a BookEntry object
     */
    static void create(Order entry) {
        try (ConnectionSource conn = new JdbcConnectionSource("jdbc:sqlite:Server/db/book.db")) {
            DaoManager.createDao(conn, Order.class).create(entry);
            conn.close();
        } catch (SQLException|IOException e) {
            System.err.println(e);
        }
    }

    /**
     * @return All orders (completed and pending)
     */
    static List<Order> read() {
        try (ConnectionSource conn = new JdbcConnectionSource("jdbc:sqlite:Server/db/book.db")) {
            var result = DaoManager.createDao(conn, Order.class).queryForAll();
            conn.close();
            if (result == null) {
                return new ArrayList<Order>();
            }
            return result;
        } catch (SQLException|IOException e) {
            System.err.println(e);
            return null;
        }
    }

    /**
     * @return Pending orders
     */
    static List<Order> readPending() {
        try (ConnectionSource conn = new JdbcConnectionSource("jdbc:sqlite:Server/db/book.db")) {
            var result = DaoManager.createDao(conn, Order.class).queryBuilder().where()
                .isNull("departureTime").query();
            conn.close();
            if (result == null) {
                return new ArrayList<Order>();
            }
            return result;
        } catch (SQLException|IOException e) {
            System.err.println(e);
            return null;
        }
    }

    /**
     * @param entry to be updated
     */
    static void update(Order entry) {
        try (ConnectionSource conn = new JdbcConnectionSource("jdbc:sqlite:Server/db/book.db")) {
            DaoManager.createDao(conn, Order.class).update(entry);
            conn.close();
        } catch (SQLException|IOException e) {
            System.err.println(e);
        }
    }

    /**
     * @param entry to be deleted
     */
    static void delete(Order entry) {
        try (ConnectionSource conn = new JdbcConnectionSource("jdbc:sqlite:Server/db/book.db")) {
            DaoManager.createDao(conn, Order.class).delete(entry);
            conn.close();
        } catch (SQLException|IOException e) {
            System.err.println(e);
        }
    }
}