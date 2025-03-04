package com.everestuniversity.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFileToMaterialsFolder(MultipartFile file, String degreeName, Integer semNum, String courseName)
            throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is missing");
        }

        String folderPath = String.format("Materials/%s/Sem %d/%s",
                degreeName, semNum, courseName);

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.trim().isEmpty()) {
            throw new IOException("File name is missing");
        }

        String sanitizedFileName = originalFileName.replaceAll("\\s+", "_");
        String publicId = folderPath + "/" + sanitizedFileName;

        // Determine resource type (default to "raw" for non-image files)
        String resourceType = "raw"; // Use raw for non-image files

        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folderPath, // Use the folder structure
                            "public_id", sanitizedFileName,
                            "use_filename", true, // Use the provided file name as is
                            "unique_filename", false, // Avoid appending unique ID
                            "resource_type", resourceType // Use "raw" for all non-images
                    ));

            // Return the URL of the uploaded file
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new IOException("File upload failed: " + e.getMessage(), e);
        }
    }

    public String uploadFileToDocumentsFolder(MultipartFile file, String fullName) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is missing");
        }

        String folderPath = String.format("Documents/%s/",fullName);

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.trim().isEmpty()) {
            throw new IOException("File name is missing");
        }

        String sanitizedFileName = originalFileName.replaceAll("\\s+", "_");
        String publicId = folderPath + "/" + sanitizedFileName;

        // Determine resource type (default to "raw" for non-image files)
        String resourceType = "raw"; // Use raw for non-image files

        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folderPath, // Use the folder structure
                            "public_id", sanitizedFileName,
                            "use_filename", true, // Use the provided file name as is
                            "unique_filename", false, // Avoid appending unique ID
                            "resource_type", resourceType // Use "raw" for all non-images
                    ));

            // Return the URL of the uploaded file
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new IOException("File upload failed: " + e.getMessage(), e);
        }
    }

}
