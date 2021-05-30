// This code generates an ArrayList with 2^16 different encrypted versions of the string "<?xm" (4 Byte Version)
// Or "<?xml ve" (8 Byte Version)


package BFish_Test;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Random ;
import java.util.ArrayList;

class blowfish_xml {
    byte[] skey = new byte[1000];
    String skeyString;
    static byte[] raw;
    String encryptedData,decryptedMessage;
    ArrayList<byte[]> ENC = new ArrayList<byte[]>();
    ArrayList<byte[]> DEC = new ArrayList<byte[]>();
    ArrayList<byte[]> KEY = new ArrayList<byte[]>();

    /* Blowfish Instances as a GUI - For the Exercise we need a datastructure to store 2^16 possible encrypted messages */
    public blowfish_xml() {
        // 4 Byte String ("<?xm") or 8 Byte String ("<?xml ve")
        String xmlMessage = "<?xm";
        byte[] ibyte = xmlMessage.getBytes();

        /* Testing the Length of xmlMessage
        int a = xmlMessage.getBytes().length;
        JOptionPane.showMessageDialog(null, a);
         */

        for(int i = 0; i < 65536; i++) {
            try {
                generateSymmetricKey();

                byte[] ebyte = encrypt(raw, ibyte);
                // String encryptedData = new String(ebyte);
                this.ENC.add(ebyte);

                //System.out.println("Encrypted message "+encryptedData);
                //JOptionPane.showMessageDialog(null,"Encrypted Data "+"\n"+encryptedData);

                byte[] dbyte = decrypt(raw, ebyte);
                this.DEC.add(dbyte);
                String decryptedMessage = new String(dbyte);
                System.out.println("Decrypted message " + decryptedMessage);

                //JOptionPane.showMessageDialog(null,"Decrypted Data "+"\n"+decryptedMessage);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    void generateSymmetricKey() {
        try {
            Random r = new Random();
            int num = r.nextInt(10000);
            String knum = String.valueOf(num);
            byte[] knumb = knum.getBytes();
            skey=getRawKey(knumb);
            skeyString = new String(skey);
            this.KEY.add(skey);
            System.out.println("Blowfish Symmetric key = "+ skeyString);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("Blowfish");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        kgen.init(32, sr); // 128, 256 and 448 bits may not be available
        SecretKey skey = kgen.generateKey();
        raw = skey.getEncoded();
        return raw;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    public static void main(String args[]) {

        blowfish_xml bf = new blowfish_xml();

        // Test the code with 10 Examples - prints encrypted + decrypted + key
        for(int i = 0; i < 10; i++) {
            String encrypted = new String(bf.ENC.get(i));
            String key = new String(bf.KEY.get(i));
            String decrypted = new String(bf.DEC.get(i));
            System.out.println(encrypted + " " + key + " " + decrypted);
        }
        String dec = new String(bf.DEC.get(0));
        String enc = new String(bf.ENC.get(0));
        String key = new String(bf.KEY.get(0));
        JOptionPane.showMessageDialog(null,"Encrypted Data "+"\n"+enc + "\n" + "Key " + key + "\n" + "Encrypted Data " + dec );
    }
}