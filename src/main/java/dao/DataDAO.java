package dao;

import model.Data;
import db.MyConnection;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataDAO {

    //METHOD1 ==> Gets all hidden files for a user
    public static List<Data> getAllFiles(String email) throws SQLException {
        List<Data> files = new ArrayList<>();

        Connection conn = MyConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT id, file_name, file_path, email FROM data WHERE email = ?");

        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String path = rs.getString("path");

            files.add(new Data(id, name, path));
        }
        return files;
    }

    //METHOD2 ==> Hides a file by storing its metadata in database
    public static int hideFile(Data file)throws SQLException, IOException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(
                "insert into data(name, path, email, bin_data) values(?, ?, ?, ?)");
        ps.setString(1, file.getFileName());
        ps.setString(2, file.getPath());
        ps.setString(3, file.getEmail());
        File f = new File(file.getPath());
        FileReader fr = new FileReader(f);
        ps.setCharacterStream(4, fr, f.length());
        int ans = ps.executeUpdate();
        fr.close();
        f.delete();
        return ans;
    }

    //METHOD3 ==> Unhides a file by removing its metadata from database
    public static void unhideFile(int id) throws SQLException, IOException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("select path, bin_data from data where id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String path = rs.getString("path");
        Clob c = rs.getClob("bin_data");

        Reader r = c.getCharacterStream();
        FileWriter fw = new FileWriter(path);
        int i;
        while ((i = r.read()) != -1) {
            fw.write((char) i);
        }
        fw.close();

        PreparedStatement DS = connection.prepareStatement("delete from data where id = ?");
        DS.setInt(1, id);
        DS.executeUpdate();
        System.out.println("Successfully Unhidden");
    }
}