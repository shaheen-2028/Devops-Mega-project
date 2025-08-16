public class CodeSmellExample {
    private String dbUser = "admin";      // hardcoded
    private String dbPassword = "123456"; // hardcoded

    public void connect() {
        // unused method just to trigger "unused code" smell
    }
}
