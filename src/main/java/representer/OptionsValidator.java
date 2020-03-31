package representer;

class OptionsValidator {
    public boolean isValidContext(String contextPath) {
        return contextPath.endsWith("/");
    }

    public boolean isValid(String[] args) {
        return args.length == 2;
    }
}
