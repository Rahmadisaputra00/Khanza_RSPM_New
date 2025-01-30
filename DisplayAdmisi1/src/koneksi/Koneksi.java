package koneksi;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    // Method untuk mendapatkan koneksi ke database dari koneksi.xml
    public static Connection getKoneksi() {
        Connection koneksi = null;

        try {
            // Menggunakan DOM untuk membaca koneksi.xml
            File file = new File("setting/database.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            // Mendapatkan elemen database
            NodeList nodeList = doc.getElementsByTagName("database");
            Node node = nodeList.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String url = element.getElementsByTagName("url").item(0).getTextContent();
                String user = element.getElementsByTagName("user").item(0).getTextContent();
                String password = element.getElementsByTagName("password").item(0).getTextContent();

                // Membuat koneksi
                koneksi = DriverManager.getConnection(url, user, password);
                System.out.println("Berhasil terkoneksi ke database!");
            }
        } catch (ParserConfigurationException | SAXException | IOException | SQLException e) {
            // Tangani kesalahan koneksi
            System.out.println("Koneksi ke database gagal!");
            e.printStackTrace();
        }

        return koneksi;
    }

    // Method untuk menutup koneksi ke database
    public static void closeKoneksi(Connection koneksi) {
        if (koneksi != null) {
            try {
                koneksi.close();
                System.out.println("Koneksi ditutup.");
            } catch (SQLException e) {
                System.out.println("Gagal menutup koneksi!");
                e.printStackTrace();
            }
        }
    }
}
