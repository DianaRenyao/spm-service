package buptspirit.spm.exception;

public class ServiceAssertionUtility {

    public static void serviceAssert(boolean condition, String message) throws ServiceAssertionException {
        if (!condition)
            throw new ServiceAssertionException(message);
    }

    public static void serviceAssert(boolean condition) throws ServiceAssertionException {
        serviceAssert(condition, "assertion error");
    }
}
