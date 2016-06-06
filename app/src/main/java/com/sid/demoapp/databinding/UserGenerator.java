package com.sid.demoapp.databinding;

/**
 * Created by Okis on 2016.05.26 @ 11:49.
 */
public class UserGenerator {
    private static User user;
    private static String[] users = {"Tomas Tomukas", "Algis Algiukas", "Petras Petriukas", "Zigmas Zigmukas"};

    public static User generateUser() {
        String randomUser = users[(int) (users.length * Math.random())];
        user.firstName = randomUser.split(" ")[0];
        user.lastName = randomUser.split(" ")[1];

        return user;
    }
}
