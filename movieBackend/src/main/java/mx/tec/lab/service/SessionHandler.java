package mx.tec.lab.service;

import mx.tec.lab.entity.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class SessionHandler {

    private static SessionHandler single_instance = null;

    private static Map<String, Long> sessions = new HashMap<>();
    private static HashSet<Long> loggedUsers = new HashSet<>();

    private SessionHandler() {
    }

    public static SessionHandler getInstance() {
        if (single_instance == null) {
            single_instance = new SessionHandler();
        }
        return single_instance;
    }

    public String addNewSession(User user) {
        if (loggedUsers.contains(user.getId())) {
            return "";
        }
        String key = newKey();
        sessions.put(key, user.getId());
        loggedUsers.add(user.getId());
        return key;
    }

    public long getUserByKey(String key) {
        if (sessions.containsKey(key)) {
            return sessions.get(key);
        }
        return -1;
    }

    public void removeSession(String key) {
        loggedUsers.remove(sessions.get(key));
        sessions.remove(key);
    }

    private String newKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public String hashedString(String text) {
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(text);
    }

}
