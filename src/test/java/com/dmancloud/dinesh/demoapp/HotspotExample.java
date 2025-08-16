import java.security.MessageDigest;

public class HotspotExample {
    public String weakHash(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");  // flagged as weak
        byte[] hash = md.digest(password.getBytes());
        return new String(hash);
    }
}
