package buptspirit.spm.message;

import buptspirit.spm.exception.ServiceAssertionException;

public interface InputMessage {

    void enforce() throws ServiceAssertionException;
}
