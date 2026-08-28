package ru.ilezzov.group.flowgoods.common.cursor.encoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.ilezzov.group.flowgoods.common.exception.CursorEncodingFailedException;
import ru.ilezzov.group.flowgoods.common.exception.InvalidCursorException;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class AesCursorEncoder implements CursorEncoder {
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;

    private final SecretKeySpec secretKeySpec;


    public AesCursorEncoder(@Value("${app.cursor.secret}") final String secret) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(secret.getBytes(StandardCharsets.UTF_8));
        this.secretKeySpec = new SecretKeySpec(key, "AES");
    }

    @Override
    public String encode(final Long id) {
        if (id == null) {
            return null;
        }

        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);

            final byte[] iv = new byte[GCM_IV_LENGTH];
            new SecureRandom().nextBytes(iv);

            final GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, parameterSpec);

            final ByteBuffer idBuffer = ByteBuffer.allocate(Long.BYTES);
            idBuffer.putLong(id);
            final byte[] cipherText = cipher.doFinal(idBuffer.array());

            final ByteBuffer messageBuffer = ByteBuffer.allocate(iv.length + cipherText.length);
            messageBuffer.put(iv);
            messageBuffer.put(cipherText);

            return Base64.getUrlEncoder().withoutPadding().encodeToString(messageBuffer.array());

        } catch (Exception e) {
            throw new CursorEncodingFailedException();
        }
    }

    @Override
    public Long decode(final String cursor) {
        if (cursor == null || cursor.isBlank()) {
            return null;
        }

        try {
            byte[] decodedMessage = Base64.getUrlDecoder().decode(cursor);

            if (decodedMessage.length < GCM_IV_LENGTH) {
                throw new IllegalArgumentException("Invalid cursor length");
            }

            final byte[] iv = new byte[GCM_IV_LENGTH];
            System.arraycopy(decodedMessage, 0, iv, 0, iv.length);

            final int cipherTextLength = decodedMessage.length - GCM_IV_LENGTH;
            final byte[] cipherText = new byte[cipherTextLength];
            System.arraycopy(decodedMessage, GCM_IV_LENGTH, cipherText, 0, cipherTextLength);

            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            final GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, parameterSpec);
            final byte[] plainText = cipher.doFinal(cipherText);

            final ByteBuffer buffer = ByteBuffer.wrap(plainText);
            return buffer.getLong();

        } catch (Exception e) {
            throw new InvalidCursorException(cursor);
        }
    }
}
