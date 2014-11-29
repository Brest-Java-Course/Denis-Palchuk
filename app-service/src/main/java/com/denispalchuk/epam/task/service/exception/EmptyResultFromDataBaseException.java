package com.denispalchuk.epam.task.service.exception;

/**
 * Created by denis on 11/28/14.
 */
public class EmptyResultFromDataBaseException extends Exception {
    private String objectId;
    /**
     * Constructs a new exception with the specified detail message and objectId value.
     *
     * @param message the detail message
     * @param objectId id of the object that cannot be found.
     */
    public EmptyResultFromDataBaseException(String message, String objectId) {
        super(message);
        this.objectId = objectId;
    }
    public String getObjectId() {
        return objectId;
    }
}
