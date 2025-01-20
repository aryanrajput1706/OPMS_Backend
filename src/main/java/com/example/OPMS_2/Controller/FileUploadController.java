package com.example.OPMS_2.Controller;

import com.example.OPMS_2.Entity.Employee;
import com.example.OPMS_2.Repository.EmployeeRepo;
import okhttp3.*;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/recruiter")
@CrossOrigin(origins = "http://localhost:3000")
public class FileUploadController {

    @Autowired
    private EmployeeRepo employeeRepo;

    private static final String ACCESS_TOKEN = "sl.u.AFdikWE23DAH2Ul-1RWuCOy1nPowSw0kgR74boEcAqqTXs3Zz5T_KVcLa7RLQBJfAG_RtIJd7wIfzaAghDKTT_17td7ybNiFLWX-xmVuHDir8JIMQGq_J7jMCzsnbUjJ4aKNUbn5jVhJ21jHyOAw0rqbMHsnMsLYQ4QuydI6LyiAXruRWG917PkuZaVig1VNWPs83u3Bf6ZYdfc2cywokQH5K2pl8EPYf3-TfRNWpOJaYBohgxyfPmOu-NP_mda-vHMeCKzawh3sTxUm6jE0o5c36hFKC6tRFiedg0Qsefy5HlZVPVoH9Wi_Kmn2ttKEuAfNK2FtNnvqTGxNYnhgsgFXkMkfqQ41cbGjvEQsh1t-oO5zNvN3CIG2qailn9sjZu315mvWqeJK9dFkFXblIFrL-bfFyWskYGMkEW6IG-yvrW1CgJQBaNcfPeodZ3V6DrKxzC_m8BGntYcaDOpgPXBIbP3XsKquTOJqaK7-4kiUlMxB2hiTOv1fNooSjUDRvknNaFAm0HobBKI2ASOyg4NSnhxIZVc-8925AFnqLZ91wUgKgYUz18-cCLTyAlZYQ2-5FXXVI4tjlEl8Pm0yjPMRFI1aM9mVch_De6SDrh4rvL9uxZq1gxlBcdyOArXUcvxwj4wmzuSct0fNmpD1t3zTkckBVCF4QL-sMC_c-vuONkfP45tSoz1ZtZcWOU6SwK2OTB_tse6pUcibtfIcWYehZr79VhBj1BUaS_43w9B0WNQbJTuAEb5jQ0aubgc7V1xMPynGpKDTjkPlsla7v_eHhiYsmL9xofw5iFBh1CcOCkepKvtW7jPMZt8fZeq0aFz8uVdzZDcY8YqK4QMvtyr66QlqtV2mRGbWe8ATwF9VSkUg2W-6V8MgQK9DIDIUHCiqmI7Rwvf0k4GdN8x8i6_eB-LNh9e0itEnNLi9xZ4G4lJAM6e_TrPECK1PNYu5A5dMVKFgAy3JNRq1UhlGEEs_StSgS2fkY6BIbRNOnqpC4m2LY5RiVwfPgfBffYkSisjzQJ37bntWxGyizG6Wmdj8pHU1Bu7ktmlV-z91JfHqvrBN986RCIziDYR_caBOo29q3kdRWHJrEoft06kxj1U-bmV7rqTU8b-bmZZ_ZEgAB89y-REITzphFZftWzJlQAXZtZAXN8hmdGs4QUBppxGErqngCrW6ohY1Q53B5jjz4f50nJicx4o2quTyg1r3dCbr1cEmDngF4HeONBEqYU6YX_RrgfpmtM2h2u00y4VDB7eQM6NEi0pyJHPpP8dvnS838aMztzw9n-Lsz3u6WU_MYi9pZZou0TwMDhOKN3sG_QTdAtt0kt6LAyB367WAwETZqET4FUbG6BjkttcRKSiae2QK8OXICG_AQlNZ7zhpMap2nW_Y5mxf3oi-cOvFGXQdFm10xcmBiieQkqXXRxYwGeYhq2H5m_Yj0Nb5ItoEKA";  // Make sure to replace this with your actual Dropbox token
    private static final String DROPBOX_UPLOAD_URL = "https://content.dropboxapi.com/2/files/upload";
    private static final String DROPBOX_LIST_LINKS_URL = "https://api.dropboxapi.com/2/sharing/list_shared_links";
    private static final String DROPBOX_CREATE_LINK_URL = "https://api.dropboxapi.com/2/sharing/create_shared_link_with_settings";

    private final OkHttpClient httpClient = new OkHttpClient();

    // **Upload resume to Dropbox and generate a shareable link**
    @PostMapping("/{id}/upload-resume")
    public ResponseEntity<String> uploadResume(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file selected");
        }
        try {
            String dropboxPath = "/" + file.getOriginalFilename();

            // Upload file to Dropbox
            RequestBody requestBody = RequestBody.create(file.getBytes(), MediaType.parse("application/octet-stream"));
            Request request = new Request.Builder()
                    .url(DROPBOX_UPLOAD_URL)
                    .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                    .addHeader("Dropbox-API-Arg", "{\"path\":\"" + dropboxPath + "\",\"mode\":\"add\",\"autorename\":true,\"mute\":false}")
                    .addHeader("Content-Type", "application/octet-stream")
                    .post(requestBody)
                    .build();

            Response response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                return ResponseEntity.status(500).body("Dropbox Upload Failed: " + response.body().string());
            }

            // Get or create a shared link
            String resumeUrl = getOrCreateSharedLink(dropboxPath);
            if (resumeUrl == null || resumeUrl.equals("Failed to generate link")) {
                return ResponseEntity.status(500).body("Error generating Dropbox link");
            }

            // Store the Dropbox file URL in the employee's record
            Employee employee = employeeRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
            employee.setResumeUrl(resumeUrl);
            employeeRepo.save(employee);

            return ResponseEntity.ok("Resume uploaded successfully: " + resumeUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }

    // **Get existing Dropbox shared link or create a new one**
    private String getOrCreateSharedLink(String filePath) throws IOException {
        // Step 1: Check if a shared link already exists
        String existingLink = checkExistingSharedLink(filePath);
        if (existingLink != null) {
            return existingLink;
        }

        // Step 2: Create a new shared link
        return createSharedLink(filePath);
    }

    // **Check if Dropbox shared link already exists**
    private String checkExistingSharedLink(String filePath) throws IOException {
        String jsonBody = "{\"path\":\"" + filePath + "\", \"direct_only\": true}";
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(DROPBOX_LIST_LINKS_URL)
                .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        Response response = httpClient.newCall(request).execute();
        String responseBody = response.body().string();
        System.out.println("Check Shared Link Response: " + responseBody); // Debugging line

        if (response.isSuccessful() && responseBody.contains("\"url\":")) {
            return extractDropboxLink(responseBody);
        }

        return null;
    }

    // **Create a new Dropbox shared link**
    private String createSharedLink(String filePath) throws IOException {
        String jsonBody = "{\"path\":\"" + filePath + "\", \"settings\": {\"requested_visibility\": \"public\"}}";
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(DROPBOX_CREATE_LINK_URL)
                .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        Response response = httpClient.newCall(request).execute();
        String responseBody = response.body().string();
        System.out.println("Create Shared Link Response: " + responseBody); // Debugging line

        if (response.isSuccessful()) {
            return extractDropboxLink(responseBody);
        }

        return "Failed to generate link";
    }

    // **Extract the Dropbox shareable link from the JSON response**
    private String extractDropboxLink(String jsonResponse) {
        try {
            // Use regex to find the URL
            Pattern pattern = Pattern.compile("\"url\":\\s*\"(https://www\\.dropbox\\.com/scl/fi/[^\"]+)\"");
            Matcher matcher = pattern.matcher(jsonResponse);

            if (matcher.find()) {
                String url = matcher.group(1); // Extract the URL

                // Ensure it's a direct link by replacing ?dl=0 with ?dl=1
                if (url.contains("?dl=")) {
                    url = url.replace("?dl=0", "?dl=1");
                } else {
                    url += "?dl=1";
                }

                return url; // Return the corrected Dropbox link
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
        }
        return "No URL found";
    }

    // **Retrieve the stored resume URL**
    @GetMapping("/{id}/resume")
    public ResponseEntity<String> getResumeUrl(@PathVariable Long id) {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isPresent() && employee.get().getResumeUrl() != null) {
            return ResponseEntity.ok(employee.get().getResumeUrl());
        } else {
            return ResponseEntity.status(404).body("Resume URL not found");
        }
    }
}
