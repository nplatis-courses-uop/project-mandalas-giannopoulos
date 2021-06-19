module Server {
    requires javafx.controls;
    requires javatuples;
    requires java.sql;
    requires sqlite.jdbc;
    requires ormlite.core;
    requires ormlite.jdbc;
    requires Common;
    opens server;
}