package animals;

public enum Color {
    UNDEFINED("неизвестный"),
    WHITE("белый"),
    BLACK("чёрный");

    private String value;

    Color(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Color fromString(String text) {
        if (text == null || text.trim().isEmpty()) {
            return UNDEFINED;
        }
        String cleanText = text.trim().toLowerCase();
        for (Color color : Color.values()) {
            if (color.getValue().equalsIgnoreCase(cleanText)) {
                return color;
            }
        }
        return UNDEFINED;
    }
}
