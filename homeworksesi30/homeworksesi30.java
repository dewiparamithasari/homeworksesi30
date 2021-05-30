import java.io.*;
import java.sql.*;


public class homeworksesi30 {
    public static void main(String[] args) {
        //untuk masuk ke database menggunakan userName, password dan url connection
        String userName = "digitalskola";
        String password = "digitalskola";
        String connectionString = "jdbc:postgresql://127.0.0.1:5432/etl_db?user="+userName+"&password="+password;

        try {
            String selectQuery = "SELECT * FROM REGISTRATION";

            Connection conn = DriverManager.getConnection(connectionString);
            Statement stmnt = conn.createStatement();

            //proses baca data
            FileReader file = new FileReader("ddk_tingkat_pendidikan.csv");
            BufferedReader reader = new BufferedReader(file);
            String lineText = null;

            //skip header
            reader.readLine();

            //looping tiap data dan akan di insert di posgresql
            while ((lineText = reader.readLine()) != null) {
                String[] data = lineText.split(",");
                int kode_provinsi = Integer.parseInt(data[0]);
                String nama_provinsi = data[1];
                String tingkat_pendidikan = data[2];
                String jenis_kelamin = data[3];
                int jumlah_individu = Integer.parseInt(data[4]);

                String query = "INSERT INTO REGISTRATION VALUES ("+kode_provinsi+", '"+nama_provinsi+"', '"+tingkat_pendidikan+"', '"+jenis_kelamin+"', "+jumlah_individu+")";

                stmnt.executeUpdate(query);
            }

            //SELECT DATA menampilkan data sesuai dengan kolomnya
            ResultSet rs = stmnt.executeQuery(selectQuery);
                while (rs.next()) {
                    System.out.println("====================");
                    System.out.println("Kode Provinsi" + rs.getInt("kode_provinsi"));
                    System.out.println("Nama Provinsi" + rs.getString("nama_provinsi"));
                    System.out.println("Tingkat Pendidikan" + rs.getString("tingkat_pendidikan"));
                    System.out.println("Jenis Kelamin" + rs.getString("jenis_kelamin"));
                    System.out.println("Jumlah Individu" + rs.getInt("jumlah_individu"));
                }
            //untuk menutup konseksi dan statement agar dapat menutup jalur resource
            stmnt.close();
            conn.close();
        } catch(SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}
