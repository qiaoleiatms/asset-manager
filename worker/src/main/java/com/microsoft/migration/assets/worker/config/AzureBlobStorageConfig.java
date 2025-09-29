package com.microsoft.migration.assets.worker.config;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.microsoft.migration.assets.worker.storage.AzureBlobStorage;
import com.microsoft.migration.assets.worker.storage.ObjectStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * Configuration class for Azure Blob Storage.
 * Creates and configures Azure Blob Storage clients and exposes ObjectStorage bean.
 */
@Configuration
@Slf4j
public class AzureBlobStorageConfig {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container}")
    private String containerName;

    @PostConstruct
    public void validateConfiguration() {
        if (connectionString == null || connectionString.trim().isEmpty()) {
            throw new IllegalStateException("Azure storage connection string is required but not configured. Please set azure.storage.connection-string property.");
        }
        if (containerName == null || containerName.trim().isEmpty()) {
            throw new IllegalStateException("Azure storage container name is required but not configured. Please set azure.storage.container property.");
        }
        log.info("Using Provider B (Azure Blob Storage) backend");
        log.info("Azure Blob Storage configured with container: {}", containerName);
    }

    @Bean
    public BlobServiceClient blobServiceClient() {
        return new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }

    @Bean
    public BlobContainerClient blobContainerClient(BlobServiceClient blobServiceClient) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        
        // Create container if it doesn't exist
        if (!containerClient.exists()) {
            log.info("Creating Azure blob container: {}", containerName);
            containerClient.create();
        }
        
        return containerClient;
    }

    @Bean
    public ObjectStorage objectStorage(BlobContainerClient blobContainerClient) {
        return new AzureBlobStorage(blobContainerClient);
    }
}