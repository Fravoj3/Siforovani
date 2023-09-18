package org.example.Siforovani;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;

public class KeyParesGenerator {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        byte[] pub = publicKey.getEncoded();
        Files.write(Paths.get("mujVerejny.key"), pub);

        byte[] priv = privateKey.getEncoded();
        Files.write(Paths.get("mujSoukromy.key"), priv);
    }
}
