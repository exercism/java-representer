class SwitchExpression {
    int rate(String book) {
        return switch (book) {
            case "Lord of the Rings" -> 5;
            case "Harry Potter" -> 4;
            default -> -1;
        };
    }
}
