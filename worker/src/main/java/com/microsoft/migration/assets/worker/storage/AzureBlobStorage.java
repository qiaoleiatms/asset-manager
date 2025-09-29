package com.microsoft.migration.assets.worker.storage;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Azure Blob Storage implementation of the ObjectStorage interface.
 * Encapsulates all Azure-specific logic for storage operations.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AzureBlobStorage implements ObjectStorage {

    private final BlobContainerClient blobContainerClient;

    @Override
    public InputStream open(String key) throws Exception {
        try {
            log.debug("Opening blob for reading: {}", key);
            return blobContainerClient.getBlobClient(key).openInputStream();
        } catch (Exception e) {
            log.error("Failed to open blob: {}", key, e);
            throw e;
        }
    }

    @Override
    public void upload(Path source, String key, String contentType) throws Exception {
        try {
            log.debug("Uploading file to blob storage: {} -> {}", source, key);
            
            BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(contentType);
            
            try (InputStream inputStream = Files.newInputStream(source)) {
                blobContainerClient.getBlobClient(key)
                    .upload(inputStream, Files.size(source), true);
                
                // Set content type
                blobContainerClient.getBlobClient(key).setHttpHeaders(headers);
            }
            
            log.debug("Successfully uploaded file to blob storage: {}", key);
        } catch (Exception e) {
            log.error("Failed to upload file to blob storage: {} -> {}", source, key, e);
            throw e;
        }
    }

    @Override
    public String publicUrl(String key) {
        try {
            String url = blobContainerClient.getBlobClient(key).getBlobUrl();
            log.debug("Generated public URL for blob: {} -> {}", key, url);
            return url;
        } catch (Exception e) {
            log.error("Failed to generate public URL for blob: {}", key, e);
            // Return a fallback URL instead of throwing exception to maintain service availability
            return blobContainerClient.getBlobContainerUrl() + "/" + key;
        }
    }
}