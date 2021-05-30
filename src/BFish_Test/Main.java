package BFish_Test;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    static ArrayList<Integer> arr_fdata = new ArrayList<Integer>();

    // Function proves if ArrayList b contains an element of ArrayList s
    // if true -> add index of ArrayList s

    public static ArrayList<Integer> equalTo(ArrayList<String> s, ArrayList<byte[]> b){
        ArrayList<Integer> arr_ = new ArrayList<Integer>();
        for(int i = 0; i < s.size(); i++){

            // StringBuilder apply in other context so you can compare 2 Substrings
            StringBuilder sb = new StringBuilder();
            for (byte a : b.get(i)) {
                sb.append(String.format("%02x", a));
            }
            System.out.print("Test: " + sb + " " + s.get(i));

            if(b.contains(s.get(i).getBytes())){
                arr_.add(i);
                arr_fdata.add(i);
            }
        }
        return arr_;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static void writer(String args) {
        FileWriter writer;
        File datei = new File("Writer.txt");

        try {
            writer = new FileWriter(datei, true);
            writer.write(args);
            writer.write(System.getProperty("line.separator"));

            writer.flush(); // Zur Sicherheit das alles Reingeschrieben wurde
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        // Generates the encrypted part of the xml-Header (Choose 4 Byte or 8 Byte Version in blowfish_xml.java)
        blowfish_xml bf_xml = new blowfish_xml();

        // Generates data in string rows from pcap data (pcap data extracted with python script in Dir PCAP - output File = rawData_2.csv)
        readcsv rc = new readcsv();

        // Compare data from pcap-File with encrypted Header
        // Tasks: cast rc strings to byte[]
        // Store Index of true values
        // Take Index and search for key from bf_xml.KEY
        // Decrypt rc.fdata with bf_xml.KEY
        // Store decrypted text in txt
        ArrayList<Integer> arr_ = new ArrayList<Integer>();
        arr_ = equalTo(rc.udata, bf_xml.ENC);

        for(int i = 0; i < arr_.size(); i++){
            try {
                String s = new String(decrypt(bf_xml.KEY.get(arr_.get(i)), rc.fdata.get(arr_fdata.get(i)).getBytes()));
                writer(s);
            }
            catch(Exception e){
                System.out.println(e);
            }
        }

    }
}
