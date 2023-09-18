package org.example.Siforovani;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;

public class readAllMessages {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
        class Message{
            String name;
            String text;

            public Message(String name, String text) {
                this.name = name;
                this.text = text;
            }
        }

        String folderPath = Configuration.pathToRep;
        File messagesF = new File(folderPath+"\\messages");

        KeyFactory kf = KeyFactory.getInstance("RSA");
        byte[] privateKeyFile = Files.readAllBytes(Paths.get("mujSoukromy.key"));
        PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyFile));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        String messages[] = messagesF.list();
        for(int i=0; i < messages.length; i++) {
            if(messages[i].contains(".message")){
                String content = new String(Files.readAllBytes(Paths.get(messagesF+"\\"+messages[i])));
                byte[] bytes = Files.readAllBytes(Paths.get(messagesF+"\\"+messages[i]));
                String res = new String(bytes);
                System.out.println("Message:");
                System.out.println("----------");
                System.out.println(messages[i].replace(".message", "")+":");
                System.out.println(res);
                System.out.println();

            }


        }
    }
}
