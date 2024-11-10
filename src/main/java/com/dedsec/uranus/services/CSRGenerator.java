package com.dedsec.uranus.services;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.KeyPairGenerator;

import java.util.Base64;

public class CSRGenerator {

    private final Logger logger = LoggerFactory.getLogger(CSRGenerator.class);

    public String generarCSR(String pemPrivateKey, String nombre, String email) throws Exception {
        logger.info("[ METHOD: generarCSR() ]: Convirtiendo llave pem a objeto private key");
        PrivateKey privateKey = getPrivateKeyFromPem(pemPrivateKey);

        logger.info("[ METHOD: generarCSR() ]: Generando CSR");
        String subjectDN = "CN=" + nombre + ", O=Dedsec Corp, OU=Certificados Personales, L=Santiago, ST=Region Metropolitana, C=CL, EMAILADDRESS=" + email;
        String csrPem = generateCSR(privateKey, subjectDN);

        logger.info("[ METHOD: generarCSR() ]: CSR generado con exito");
        return csrPem;
    }

    // Función para convertir una llave privada en formato PEM a un objeto PrivateKey
    public static PrivateKey getPrivateKeyFromPem(String pemKey) throws Exception {
        // Eliminar las líneas de encabezado y pie
        String privateKeyPEM = pemKey.replace("-----BEGIN PRIVATE KEY-----", "")
                                       .replace("-----END PRIVATE KEY-----", "")
                                       .replaceAll("\\s+", ""); // Eliminar espacios en blanco
    
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM); // Decodificar de Base64
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    // Función para generar el CSR
    public static String generateCSR(PrivateKey privateKey, String subjectDN) throws Exception {
        // Nombre del sujeto para el CSR
        X500Name subject = new X500Name(subjectDN);

        // Generar un nuevo par de claves para el CSR
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        var keyPair = keyPairGen.generateKeyPair();

        // Constructor del CSR usando la llave privada y el nombre
        JcaPKCS10CertificationRequestBuilder p10Builder =
                new JcaPKCS10CertificationRequestBuilder(subject, keyPair.getPublic());

        // ContentSigner firma la solicitud con la llave privada
        JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
        ContentSigner signer = csBuilder.build(privateKey);

        // Crear el CSR
        PKCS10CertificationRequest csr = p10Builder.build(signer);

        // Convertir el CSR a formato PEM
        StringWriter stringWriter = new StringWriter();
        try (JcaPEMWriter pemWriter = new JcaPEMWriter(stringWriter)) {
            pemWriter.writeObject(csr);
        }

        // Formatear el CSR en PEM sin saltos de línea
        return formatPem(stringWriter.toString());
    }

    // Función para formatear el CSR en el estilo PEM sin saltos de línea
    private static String formatPem(String csrPem) {
        // Eliminar encabezados y pies originales
        String formattedPem = csrPem
                .replace("-----BEGIN CERTIFICATE REQUEST-----", "")
                .replace("-----END CERTIFICATE REQUEST-----", "")
                .replaceAll("\\s+", ""); // Eliminar espacios en blanco
        
        return "-----BEGIN CERTIFICATE REQUEST-----" + formattedPem + "-----END CERTIFICATE REQUEST-----";
    }
}
