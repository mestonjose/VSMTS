package com.team14.vsmts.controller;

import com.team14.vsmts.model.ServiceRecord;
import com.team14.vsmts.model.User;
import com.team14.vsmts.service.ServiceRecordService;
import com.team14.vsmts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

/**
 * VSMTS-11: Attach Invoice to Service Record
 * Handles invoice upload and download for service records.
 * Files are stored in uploads/invoices/ directory on the server.
 */
@Controller
public class InvoiceController {

    private static final Path UPLOAD_DIR = Paths.get("uploads/invoices");

    @Autowired
    private ServiceRecordService serviceRecordService;

    @Autowired
    private UserService userService;

    // --- Service Center: Upload Invoice ---

    @GetMapping("/service-center/invoice/upload/{recordId}")
    public String showUploadForm(@PathVariable Long recordId, Authentication auth, Model model) {
        ServiceRecord record = serviceRecordService.findById(recordId);
        if (record == null) return "redirect:/service-center/dashboard";
        model.addAttribute("record", record);
        return "service-center/upload-invoice";
    }

    @PostMapping("/service-center/invoice/upload/{recordId}")
    public String uploadInvoice(@PathVariable Long recordId,
                                @RequestParam("invoiceFile") MultipartFile file,
                                Authentication auth, Model model) {
        ServiceRecord record = serviceRecordService.findById(recordId);
        if (record == null) return "redirect:/service-center/dashboard";

        if (file.isEmpty()) {
            model.addAttribute("error", "Please select a file to upload.");
            model.addAttribute("record", record);
            return "service-center/upload-invoice";
        }

        String originalName = file.getOriginalFilename();
        if (originalName == null || (!originalName.toLowerCase().endsWith(".pdf")
                && !originalName.toLowerCase().endsWith(".jpg")
                && !originalName.toLowerCase().endsWith(".jpeg")
                && !originalName.toLowerCase().endsWith(".png"))) {
            model.addAttribute("error", "Only PDF, JPG, and PNG files are allowed.");
            model.addAttribute("record", record);
            return "service-center/upload-invoice";
        }

        try {
            Files.createDirectories(UPLOAD_DIR);
            // Prefix with record ID to avoid name collisions
            String fileName = "record-" + recordId + "-" + originalName;
            Path destination = UPLOAD_DIR.resolve(fileName);
            file.transferTo(destination);

            record.setInvoiceFileName(fileName);
            serviceRecordService.save(record);
        } catch (IOException e) {
            model.addAttribute("error", "Failed to upload file: " + e.getMessage());
            model.addAttribute("record", record);
            return "service-center/upload-invoice";
        }

        return "redirect:/service-center/records?invoiceUploaded";
    }

    // --- Download Invoice (accessible to both owner and service center) ---

    @GetMapping("/invoice/download/{recordId}")
    public ResponseEntity<Resource> downloadInvoice(@PathVariable Long recordId) {
        ServiceRecord record = serviceRecordService.findById(recordId);
        if (record == null || record.getInvoiceFileName() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path file = UPLOAD_DIR.resolve(record.getInvoiceFileName());
            Resource resource = new UrlResource(file.toUri());
            if (!resource.exists()) return ResponseEntity.notFound().build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + record.getInvoiceFileName() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
