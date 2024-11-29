import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtils.class);
    
    public static void log(String message) {
        logger.info(message);
    }
    
    public static void logf(String format, Object... args) {
        logger.info(String.format(format, args));
    }
    
    public static void logln(String message) {
        logger.info(message);
    }
}