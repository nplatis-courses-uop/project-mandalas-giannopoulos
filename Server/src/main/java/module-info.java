module Server {
    requires javafx.controls;
    requires javatuples;
    requires java.sql;
    requires sqlite.jdbc;
    requires Common;
    exports server;
}