public enum Comands {
    ADD,
    LIST,
    EXIT;

    public static Comands fromControl(String input) {
        if (input == null) return null;
        try {
            return Comands.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
