package com.microsoft.migration.assets.worker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @deprecated This configuration is deprecated in favor of AzureBlobStorageConfig.
 * Kept as placeholder for rollout stability. Will be removed in future versions.
 */
@Deprecated
@Configuration
public class AwsS3Config {
    
    @Value("${aws.accessKeyId:}")
    private String accessKeyId;

    @Value("${aws.secretKey:}")
    private String secretKey;

    @Value("${aws.region:}")
    private String region;

    // S3Client bean removed - use AzureBlobStorageConfig instead
}