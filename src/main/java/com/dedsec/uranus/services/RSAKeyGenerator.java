package com.dedsec.uranus.services;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSAKeyGenerator {

    private static final Logger logger = LoggerFactory.getLogger(RSAKeyGenerator.class);

    public String genRSA() throws Exception {

        logger.info("[METHOD: genRSA() ]: Generando llave privada (2048)");
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);

        KeyPair keyPair = keyPairGen.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();

        logger.info("[METHOD: genRSA() ]: Convertir la llave privada a formato PKCS#8");
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        String privateKeyPem = convertToPEMFormat(pkcs8EncodedKeySpec.getEncoded());

        logger.info("[METHOD: genRSA() ]: Llave generada con exito");
        return privateKeyPem;
    }

    // Convierte el arreglo de bytes a formato PEM
    private static String convertToPEMFormat(byte[] keyBytes) {
        String base64Encoded = Base64.getEncoder().encodeToString(keyBytes);
        StringBuilder pemFormat = new StringBuilder();
        pemFormat.append("-----BEGIN PRIVATE KEY-----");
        pemFormat.append(base64Encoded);
        pemFormat.append("-----END PRIVATE KEY-----");
        return pemFormat.toString();
    }
}

