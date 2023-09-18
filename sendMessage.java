import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Scanner;

public class sendMessage {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException {
        class Addressee{
            PublicKey pub;
            String name;

            public Addressee(PublicKey pub, String name) {
                this.pub = pub;
                this.name = name;
            }
        }
        Scanner sc = new Scanner(System.in);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        String folderPath = "C:\\Users\\2020-e-franc\\Documents\\testGit\\test1";
        ArrayList<Addressee> addressees = new ArrayList<Addressee>();


        File dir = new File(folderPath);
        String files[] = dir.list();
        for(int i=0; i < files.length; i++) {
            if(files[i].contains(".key")){
                try {
                    byte[] publicKeyFile = Files.readAllBytes(Paths.get(folderPath+"\\"+files[i]));
                    PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(publicKeyFile));
                    addressees.add(new Addressee(publicKey, files[i].replace(".key","")));
                } catch (InvalidKeySpecException e) {
                    System.out.println(files[i].replace(".key","")+ " to podělal");
                }
            }
        }

        System.out.println("All possible addressees:");
        for(int i = 0; i < addressees.size(); i++){
            System.out.println(i + "\t" + addressees.get(i).name);
        }

        System.out.println("Write the message");
        String message = sc.nextLine();
        System.out.println("Write the index of an addressee");
        String indexS = sc.nextLine();
        int index = Integer.parseInt(indexS);
        System.out.println("Write the name of this message");
        String subject = sc.nextLine();


        Cipher cipher = Cipher.getInstance("RSA");
        try {
            cipher.init(Cipher.PUBLIC_KEY, addressees.get(index).pub);
            try {
                byte[] encryptedBytes = cipher.doFinal(message.getBytes());
                Files.write(Paths.get(folderPath+"\\messages\\"+subject+".message"), encryptedBytes);
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e) {
                System.out.println("cannot convert the message to bytes");
            }
        } catch (InvalidKeyException e) {
            System.out.println("Cannot encript the message");
        }



    }
}
