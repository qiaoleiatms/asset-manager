package com.microsoft.migration.assets.worker.config;

import org.springframework.context.annotation.Configuration;

/**
 * @deprecated This configuration is deprecated and will be removed in a future version.
 * AWS S3 support has been migrated to Azure Blob Storage using the ObjectStorage abstraction.
 * 
 * Decommission checklist (for follow-up PR):
 * - Remove AWS S3 dependency from pom.xml
 * - Remove legacy AWS properties from application.properties  
 * - Consider renaming S3FileProcessingService to AzureFileProcessingService or CloudFileProcessingService
 * - Update getStorageType() to return "azure" and update message producers accordingly
 * - Remove this configuration class entirely
 */
@Deprecated(since = "0.0.2", forRemoval = true)
@Configuration
public class AwsS3Config {
    // This configuration class has been neutralized.
    // All AWS S3 client bean logic has been removed.
    // Use AzureBlobStorageConfig and ObjectStorage abstraction instead.
}