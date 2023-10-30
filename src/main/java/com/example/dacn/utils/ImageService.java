package com.example.dacn.utils;

import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
@Service
public class ImageService {
    @Autowired
    private Storage storage;
    private String bucketUrl="fooddeliveryv2-c6f80.appspot.com";

    public String uploadImage(MultipartFile file, String savePath, String objectName) throws IOException {
        if (!file.isEmpty()) {
            String storagePath = savePath+objectName+".jpg";
            BlobId blobId = BlobId.of(bucketUrl, storagePath);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType("image/jpeg")
                    .setAcl(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.OWNER)))
                    .build();
            // Upload tệp lên firebase
            Blob blob = storage.create(blobInfo, file.getBytes());
            // Lấy đường dẫn của file v trả về
            String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/" +
                    blob.getBucket() + "/o/" + URLEncoder.encode(blob.getName(), StandardCharsets.UTF_8) +
                    "?alt=media";
            return downloadUrl;
        }
        return null;
    }
}
