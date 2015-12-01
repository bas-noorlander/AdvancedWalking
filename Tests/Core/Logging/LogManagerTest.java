package scripts.AdvancedWalking.Tests.Core.Logging;

import org.junit.Test;
import scripts.AdvancedWalking.Core.Logging.ILogger;
import scripts.AdvancedWalking.Core.Logging.LogManager;
import scripts.AdvancedWalking.Core.Logging.Loggers.ClientDebugLogger;

import static org.junit.Assert.*;

/**
 * @author Laniax
 */
public class LogManagerTest {

    @Test
    public void testAddLogger() throws Exception {

        ILogger log = new ClientDebugLogger();

        assertNotNull(log);

        LogManager.addLogger(log);

        assertTrue(LogManager._loggers.contains(log));
    }
}