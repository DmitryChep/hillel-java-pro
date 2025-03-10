package ua.ithillel.javapro.exception;

import org.hibernate.service.spi.ServiceException;

public class NotFoundServiceException extends ServiceException {
    public NotFoundServiceException(String message) {
        super(message);
    }
}
