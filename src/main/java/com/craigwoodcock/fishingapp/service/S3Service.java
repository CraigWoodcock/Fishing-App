package com.craigwoodcock.fishingapp.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Duration;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Handles storage and retrieval of catch photos in S3. Photos are kept
 * private; callers are given short-lived signed URLs to render them.
 */
@Service
public class S3Service {

    private static final Logger log = Logger.getLogger(S3Service.class.getName());
    private static final Duration PRESIGNED_URL_DURATION = Duration.ofMinutes(15);

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    /**
     * Uploads a catch photo to S3 under a unique key, so filename clashes
     * between different anglers or sessions are never a problem.
     *
     * @param file the uploaded photo from the form; may be null or empty
     * @return the S3 object key to persist against the Catch entity, or
     * null if no file was supplied
     */
    public String uploadCatchPhoto(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String key = "catches/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();
            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            log.info("Uploaded catch photo to S3 with key " + key);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to upload catch photo to S3", e);
        }

        return key;
    }

    /**
     * Generates a time-limited signed URL for a stored photo so it can be
     * rendered directly in an &lt;img&gt; tag without the bucket being public.
     *
     * @param key the S3 object key previously returned by uploadCatchPhoto
     * @return a signed URL valid for a short period, or null if there's no photo
     */
    public String getPhotoUrl(String key) {
        if (key == null || key.isBlank()) {
            return null;
        }

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(PRESIGNED_URL_DURATION)
                .getObjectRequest(getObjectRequest)
                .build();

        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }

    /**
     * Deletes a stored photo, used when a catch is removed or its photo replaced.
     *
     * @param key the S3 object key to delete; does nothing if null/blank
     */
    public void deletePhoto(String key) {
        if (key == null || key.isBlank()) {
            return;
        }

        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build());
    }
}