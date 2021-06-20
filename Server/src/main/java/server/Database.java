package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class Database {
    static void init() {
        try (ConnectionSource conn = new JdbcConnectionSource("jdbc:sqlite:Server/db/book.db")) {
            TableUtils.createTableIfNotExists(conn, BookEntry.class);
            conn.close();
        } catch (SQLException|IOException e) {
            System.err.println(e);
        }
    }

    static void create(BookEntry entry) {
        try (ConnectionSource conn = new JdbcConnectionSource("jdbc:sqlite:Server/db/book.db")) {
            DaoManager.createDao(conn, BookEntry.class).create(entry);
            conn.close();
        } catch (SQLException|IOException e) {
            System.err.println(e);
        }
    }

    static List<BookEntry> read() {
        try (ConnectionSource conn = new JdbcConnectionSource("jdbc:sqlite:Server/db/book.db")) {
            var result = DaoManager.createDao(conn, BookEntry.class).queryForAll();
            conn.close();
            return result;
        } catch (SQLException|IOException e) {
            System.err.println(e);
            return null;
        }
    }

    static List<BookEntry> readPending() {
        try (ConnectionSource conn = new JdbcConnectionSource("jdbc:sqlite:Server/db/book.db")) {
            var result = DaoManager.createDao(conn, BookEntry.class).queryBuilder().where()
                .isNull("departureTime").query();
            conn.close();
            return result;
        } catch (SQLException|IOException e) {
            System.err.println(e);
            return null;
        }
    }

    static void update(BookEntry entry) {
        try (ConnectionSource conn = new JdbcConnectionSource("jdbc:sqlite:Server/db/book.db")) {
            DaoManager.createDao(conn, BookEntry.class).update(entry);
            conn.close();
        } catch (SQLException|IOException e) {
            System.err.println(e);
        }
    }

    static void delete(BookEntry entry) {
        try (ConnectionSource conn = new JdbcConnectionSource("jdbc:sqlite:Server/db/book.db")) {
            DaoManager.createDao(conn, BookEntry.class).delete(entry);
            conn.close();
        } catch (SQLException|IOException e) {
            System.err.println(e);
        }
    }
}