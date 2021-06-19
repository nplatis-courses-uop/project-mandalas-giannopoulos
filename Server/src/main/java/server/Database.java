package server;

import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class Database {
    static void init() {
        try (ConnectionSource conn =
                new JdbcConnectionSource("jdbc:sqlite:Server/db/book.db"))
        {
            TableUtils.createTableIfNotExists(conn, BookEntry.class);
        } catch (SQLException|IOException e) {
            System.err.println(e);
        }
    }

    static void create(BookEntry entry) {
        try (ConnectionSource conn =
                new JdbcConnectionSource("jdbc:sqlite:Server/db/book.db"))
        {
            DaoManager.createDao(conn, BookEntry.class).create(entry);
        } catch (SQLException|IOException e) {
            System.err.println(e);
        }
    }
}