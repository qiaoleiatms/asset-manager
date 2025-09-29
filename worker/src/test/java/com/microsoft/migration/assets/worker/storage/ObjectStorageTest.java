package com.microsoft.migration.assets.worker.storage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple test to validate ObjectStorage interface structure.
 * More detailed integration tests would require actual Azure Blob Storage or mocking.
 */
class ObjectStorageTest {

    @Test
    void testObjectStorageInterfaceExists() {
        // This test validates that the ObjectStorage interface is properly defined
        assertNotNull(ObjectStorage.class);
        
        // Check that the interface has the expected methods
        try {
            ObjectStorage.class.getMethod("open", String.class);
            ObjectStorage.class.getMethod("upload", java.nio.file.Path.class, String.class, String.class);
            ObjectStorage.class.getMethod("publicUrl", String.class);
        } catch (NoSuchMethodException e) {
            fail("ObjectStorage interface is missing required methods: " + e.getMessage());
        }
    }
    
    @Test
    void testAzureBlobStorageImplementsObjectStorage() {
        // This test validates that AzureBlobStorage properly implements ObjectStorage
        assertTrue(ObjectStorage.class.isAssignableFrom(AzureBlobStorage.class));
    }
}