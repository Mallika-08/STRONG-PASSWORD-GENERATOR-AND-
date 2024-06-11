import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class PasswordGenerator 
{
    private static final String ALGORITHM = "AES";
    private static final int KEY_LENGTH = 16; // For AES-128
    private static final String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;:,.<>?";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Get user input for password length
        System.out.print("Enter the desired password length: ");
        int passwordLength = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Generate AES key
        byte[] key = generateAESKey();

        // Generate strong password
        String password = generateStrongPassword(passwordLength);

        // Encrypt the password using AES
        String encryptedPassword = encrypt(password, key);

        System.out.println("Generated Password: " + password);
        System.out.println("Encrypted Password: " + encryptedPassword);

        scanner.close();
    }

    private static byte[] generateAESKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[KEY_LENGTH];
        random.nextBytes(key);
        return key;
    }

    private static String generateStrongPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(CHARSET.charAt(random.nextInt(CHARSET.length())));
        }
        return sb.toString();
    }

    private static String encrypt(String plainText, byte[] key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}

