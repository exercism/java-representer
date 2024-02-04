class PLACEHOLDER_02 {
    private static final int PLACEHOLDER_08 = 5;

    private static final int PLACEHOLDER_09 = 26;

    private static final int PLACEHOLDER_10 = 97;

    private enum PLACEHOLDER_01 {
        PLACEHOLDER_11,
        PLACEHOLDER_12;
    }

    String PLACEHOLDER_03(String PLACEHOLDER_13, int PLACEHOLDER_14, int PLACEHOLDER_15) {
        return PLACEHOLDER_06(
                PLACEHOLDER_07(
                        PLACEHOLDER_13,
                        PLACEHOLDER_14,
                        PLACEHOLDER_15,
                        PLACEHOLDER_01.PLACEHOLDER_11));
    }

    String PLACEHOLDER_04(String PLACEHOLDER_16, int PLACEHOLDER_17, int PLACEHOLDER_18) {
        return PLACEHOLDER_07(
                PLACEHOLDER_16, PLACEHOLDER_17, PLACEHOLDER_18, PLACEHOLDER_01.PLACEHOLDER_12);
    }

    private static int PLACEHOLDER_05(int PLACEHOLDER_19) {
        int PLACEHOLDER_20 = Math.floorMod(PLACEHOLDER_19, PLACEHOLDER_09);
        for (int PLACEHOLDER_21 = 1; PLACEHOLDER_21 < PLACEHOLDER_09; PLACEHOLDER_21++) {
            if (Math.floorMod(PLACEHOLDER_20 * PLACEHOLDER_21, PLACEHOLDER_09) == 1) {
                return PLACEHOLDER_21;
            }
        }
        return 1;
    }

    private static String PLACEHOLDER_06(String PLACEHOLDER_22) {
        List<String> PLACEHOLDER_23 = new ArrayList<>();
        for (int PLACEHOLDER_24 = 0;
                PLACEHOLDER_24 < PLACEHOLDER_22.length();
                PLACEHOLDER_24 += PLACEHOLDER_08) {
            PLACEHOLDER_23.add(
                    PLACEHOLDER_22.substring(
                            PLACEHOLDER_24,
                            Math.min(PLACEHOLDER_22.length(), PLACEHOLDER_24 + PLACEHOLDER_08)));
        }
        return String.join(" ", PLACEHOLDER_23);
    }

    private static String PLACEHOLDER_07(
            String PLACEHOLDER_25,
            int PLACEHOLDER_26,
            int PLACEHOLDER_27,
            PLACEHOLDER_01 PLACEHOLDER_28) {
        int PLACEHOLDER_29 = PLACEHOLDER_05(PLACEHOLDER_26);
        if (PLACEHOLDER_29 == 1) {
            throw new IllegalArgumentException("Error: keyA and alphabet size must be coprime.");
        }
        StringBuilder PLACEHOLDER_30 = new StringBuilder();
        PLACEHOLDER_25
                .chars()
                .filter(Character::isLetterOrDigit)
                .map(Character::toLowerCase)
                .forEach(
                        PLACEHOLDER_31 -> {
                            int PLACEHOLDER_32 = PLACEHOLDER_31 - PLACEHOLDER_10;
                            if (PLACEHOLDER_32 < 0) {
                                PLACEHOLDER_30.appendCodePoint(PLACEHOLDER_31);
                            } else if (PLACEHOLDER_28 == PLACEHOLDER_01.PLACEHOLDER_11) {
                                PLACEHOLDER_30.appendCodePoint(
                                        PLACEHOLDER_10
                                                + Math.floorMod(
                                                        (PLACEHOLDER_26 * PLACEHOLDER_32)
                                                                + PLACEHOLDER_27,
                                                        PLACEHOLDER_09));
                            } else if (PLACEHOLDER_28 == PLACEHOLDER_01.PLACEHOLDER_12) {
                                PLACEHOLDER_30.appendCodePoint(
                                        PLACEHOLDER_10
                                                + Math.floorMod(
                                                        PLACEHOLDER_29
                                                                * (PLACEHOLDER_32 - PLACEHOLDER_27),
                                                        PLACEHOLDER_09));
                            }
                        });
        return PLACEHOLDER_30.toString();
    }
}
