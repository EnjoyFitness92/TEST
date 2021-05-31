package BFish_Test;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

class readcsv {
    // ArrayList fdata (Fulldata) and udata (Used Data)
    ArrayList<String> fdata = new ArrayList<String>();
    ArrayList<String> udata = new ArrayList<String>();


    public readcsv() {

        try {
            FileReader fr = new FileReader("rawData_2.csv");
            BufferedReader br = new BufferedReader(fr);
            try {
                String line = br.readLine();
                do {
                    if(line.equals("")) {
                        // Leerzeile ausschließen
                        continue;
                    } else {
                        // Was auch immer man mit der Zeile vorhat hier umsetzen
                        System.out.println(line);
                        this.fdata.add(line);

                        // Choose 4 or 8 Byte
                        this.udata.add(line.substring(0, 16));
                    }
                    line = br.readLine();
                }while (line != null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        readcsv rc = new readcsv();
        /* Test the data
        System.out.println(rc.fdata);
        System.out.println(rc.udata);
         */
    }
}
