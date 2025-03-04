package com.everestuniversity.service;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordGenerator {



    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_+=<>?";
    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generatePassword() {
        int passwordLength = 6 + RANDOM.nextInt(5); // Generates length between 6 to 10

        StringBuilder password = new StringBuilder(passwordLength);

        // Ensure password contains at least one character of each type
        password.append(getRandomCharacter(UPPERCASE));
        password.append(getRandomCharacter(LOWERCASE));
        password.append(getRandomCharacter(DIGITS));
        password.append(getRandomCharacter(SPECIAL_CHARACTERS));

        // Fill the remaining characters randomly
        for (int i = 4; i < passwordLength; i++) {
            password.append(getRandomCharacter(ALL_CHARACTERS));
        }

        // Shuffle the password to make it unpredictable
        return shufflePassword(password.toString());
    }

    private static char getRandomCharacter(String characterSet) {
        return characterSet.charAt(RANDOM.nextInt(characterSet.length()));
    }

    private static String shufflePassword(String password) {
        char[] characters = password.toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int index = RANDOM.nextInt(i + 1);
            // Swap characters
            char temp = characters[i];
            characters[i] = characters[index];
            characters[index] = temp;
        }
        return new String(characters);
    }

    public static void main(String[] args) {
        // Example usage
        String password = generatePassword();
        System.out.println("Generated Password: " + password);
    }
}
