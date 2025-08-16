public class BugExample {
    public String riskyMethod(String input) {
        return input.toLowerCase();  // potential NullPointerException
    }
}
