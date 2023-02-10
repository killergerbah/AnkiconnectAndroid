package com.kamwithk.ankiconnectandroid.routing.localaudiosource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class NHK16AudioSource extends StandardSQLite3Source {
    public NHK16AudioSource() {
        super("nhk16", "user_files/nhk16_files");
    }

    @Override
    protected PreparedStatement prepareQuery(Connection connection, Map<String, List<String>> parameters) throws SQLException {
        String query = "SELECT file, display FROM nhk16 WHERE (\n" +
                "    (expression = ? AND reading = ?)\n" +
                "    OR (expression = ? AND reading IS NULL)\n" +
                ") ORDER BY priority DESC";

        String term = getTerm(parameters);
        String reading = getReading(parameters);
        PreparedStatement pstmt = connection.prepareStatement(query);
        // indices start at 1
        pstmt.setString(1, term);
        pstmt.setString(2, reading);
        pstmt.setString(3, term);
        return pstmt;
    }

    @Override
    protected String getSourceName(ResultSet rs) throws SQLException {
        return "NHK16 " + rs.getString("display");
    }
}
