package com.microsoft.migration.assets.worker.storage;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * Provider-neutral object storage abstraction for handling file operations.
 * This interface abstracts away cloud provider-specific details for storage operations.
 */
public interface ObjectStorage {

    /**
     * Opens an object for reading from storage.
     * 
     * @param key the object key/path
     * @return InputStream for reading the object data
     * @throws Exception if the object cannot be opened or does not exist
     */
    InputStream open(String key) throws Exception;

    /**
     * Uploads a file to storage.
     * 
     * @param source the local file path to upload
     * @param key the destination key/path in storage
     * @param contentType the MIME type of the file
     * @throws Exception if the upload fails
     */
    void upload(Path source, String key, String contentType) throws Exception;

    /**
     * Generates a public URL for accessing an object in storage.
     * 
     * @param key the object key/path
     * @return the public URL as a string
     */
    String publicUrl(String key);
}