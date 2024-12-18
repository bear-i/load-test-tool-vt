import org.apache.log4j.LogManager;

public enum LoggerSingleton {
    INSTANCE;
    private final org.apache.log4j.Logger logger = LogManager.getLogger(LoadTestTool.class);

    public org.apache.log4j.Logger getInstance() {
        return logger;
    }
}
