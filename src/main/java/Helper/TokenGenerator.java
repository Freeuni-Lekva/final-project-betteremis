package Helper;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {

    // We will use SecureRandom and Base64 classes for thread-safeness.
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    /**
     * Generates a unique token for the user.
     * @return Token as a string.
     */
    public static String generateToken(){
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

}
