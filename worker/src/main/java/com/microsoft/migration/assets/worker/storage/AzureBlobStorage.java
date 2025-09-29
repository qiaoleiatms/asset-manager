package com.microsoft.migration.assets.worker.storage;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Azure Blob Storage implementation of ObjectStorage interface.
 * This class wraps the Azure SDK to provide a provider-neutral storage abstraction.
 */
@Component
@RequiredArgsConstructor
public class AzureBlobStorage implements ObjectStorage {
    
    private final BlobContainerClient blobContainerClient;
    
    @Override
    public InputStream open(String key) throws Exception {
        BlobClient blobClient = blobContainerClient.getBlobClient(key);
        return blobClient.openInputStream();
    }
    
    @Override
    public void upload(Path source, String key, String contentType) throws Exception {
        BlobClient blobClient = blobContainerClient.getBlobClient(key);
        
        try (InputStream inputStream = Files.newInputStream(source)) {
            blobClient.upload(inputStream, Files.size(source), true);
            
            // Set content type if provided
            if (contentType != null && !contentType.isEmpty()) {
                blobClient.setHttpHeaders(
                    new com.azure.storage.blob.models.BlobHttpHeaders()
                        .setContentType(contentType)
                );
            }
        }
    }
    
    @Override
    public String publicUrl(String key) {
        BlobClient blobClient = blobContainerClient.getBlobClient(key);
        return blobClient.getBlobUrl();
    }
}