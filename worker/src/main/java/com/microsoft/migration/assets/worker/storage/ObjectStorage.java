package com.microsoft.migration.assets.worker.storage;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * Provider-neutral abstraction for storage operations.
 * This interface defines the minimal set of storage operations needed by the worker module.
 */
public interface ObjectStorage {
    
    /**
     * Opens/downloads an object from storage and returns an InputStream.
     * @param key the object key/path in storage
     * @return InputStream of the object content
     * @throws Exception if the operation fails
     */
    InputStream open(String key) throws Exception;
    
    /**
     * Uploads a file to storage.
     * @param source the local file path to upload
     * @param key the target object key/path in storage
     * @param contentType the MIME content type of the file
     * @throws Exception if the operation fails
     */
    void upload(Path source, String key, String contentType) throws Exception;
    
    /**
     * Generates a public URL for accessing the object.
     * @param key the object key/path in storage
     * @return the public URL to access the object
     */
    String publicUrl(String key);
}