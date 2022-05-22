package org.fis2022.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.Cursor;
import org.fis2021.exceptions.InvalidCredentials;
import org.fis2021.exceptions.UsernameAlreadyExists;
import org.fis2021.models.User;
import org.fis2021.models.Booking;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Objects;


import static org.fis2021.services.FileSystemService.getPathToFile;

public class UserService {

    private static ObjectRepository<User> userRepository;

    public static void initService() {
        userRepository = DBService.getDatabase().getRepository(User.class);
    }

    public static void addUser(String username, String password, String role) throws UsernameAlreadyExists {
        checkUserDoesNotAlreadyExist(username);
        userRepository.insert(new User(username, encodePassword(username, password), role));
    }

    public static User getUser(String username) throws InvalidCredentials {
        Cursor<User> cursor = userRepository.find(ObjectFilters.eq("username", username));
        for(User u : cursor){
            return u;
        }
        throw new InvalidCredentials(username);
    }

    public static String getHashedUserPassword(String username) throws InvalidCredentials{
        return getUser(username).getPassword();
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExists {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExists(username);
        }
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", "");
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("NOT GOOD!");
        }
        return md;
    }

    public static ObjectRepository<User> getUserRepository() {
        return userRepository;
    }
}
