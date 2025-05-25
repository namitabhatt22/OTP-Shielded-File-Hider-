package service;

import java.security.SecureRandom;
import java.util.Random;

public class OTPService {
    // OTP configuration
    private static final int DEFAULT_OTP_LENGTH = 6;
    private static final int MAX_OTP_ATTEMPTS = 3;
    private static final long OTP_VALIDITY_DURATION_MS = 5 * 60 * 1000; // 5 minutes

    // Secure random generator
    private static final Random secureRandom = new SecureRandom();

    /**
     * Generates a secure OTP of default length (6 digits)
     * @return Generated OTP string
     */
    public static String generateOTP() {
        return generateOTP(DEFAULT_OTP_LENGTH);
    }

    /**
     * Generates a secure OTP of specified length
     * @param length Number of digits in OTP
     * @return Generated OTP string
     */
    public static String generateOTP(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("OTP length must be positive");
        }

        // Generate a number between 0 and 10^length - 1
        int maxValue = (int) Math.pow(10, length) - 1;
        int otpNumber = secureRandom.nextInt(maxValue + 1);

        // Format with leading zeros
        return String.format("%0" + length + "d", otpNumber);
    }

    /**
     * Validates OTP against expected value with time check
     * @param inputOTP OTP entered by user
     * @param generatedOTP OTP that was generated
     * @param generationTime Time when OTP was generated
     * @return true if valid, false otherwise
     */
    public static boolean validateOTP(String inputOTP, String generatedOTP, long generationTime) {
        // Basic null checks
        if (inputOTP == null || generatedOTP == null) {
            return false;
        }

        // Check if OTP expired
        long currentTime = System.currentTimeMillis();
        if (currentTime - generationTime > OTP_VALIDITY_DURATION_MS) {
            return false;
        }

        // Compare OTPs
        return inputOTP.equals(generatedOTP);
    }

    /**
     * Gets the maximum allowed OTP attempts
     * @return Maximum attempts before lockout
     */
    public static int getMaxAttempts() {
        return MAX_OTP_ATTEMPTS;
    }

    /**
     * Gets OTP validity duration in milliseconds
     * @return Validity duration in ms
     */
    public static long getOtpValidityDuration() {
        return OTP_VALIDITY_DURATION_MS;
    }
}