package accounts;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static Map<String, UserProfile> loginToProfile = new HashMap<>(){
        {
            put("123", new UserProfile("123", "123", "123"));
        }
    };
    private static Map<String, UserProfile> sessionIdToProfile = new HashMap<>();

    public AccountService() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
    }

    public static void addNewUser(UserProfile userProfile){
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }
    public static void addNewSession(String idSession, UserProfile userProfile){
        sessionIdToProfile.put(idSession, userProfile);
    }
    public static UserProfile getUserByLogin(String login){
        return loginToProfile.get(login);
    }
    public static UserProfile getUserBySession(String sessionId){
        return sessionIdToProfile.get(sessionId);
    }

    public static void deleteSession(String sessionId){
        sessionIdToProfile.remove(sessionId);
    }
}
