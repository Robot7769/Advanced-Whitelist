package robot7769.advancedwhitelist;

public enum WhiteListMode {
    NONE("none"),
    ALL("all"),
    DEV("dev"),
    DETI("deti");

    private final String name;

    WhiteListMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
