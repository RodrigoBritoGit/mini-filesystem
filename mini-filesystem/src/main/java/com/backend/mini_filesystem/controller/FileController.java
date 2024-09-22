package com.backend.mini_filesystem.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.mini_filesystem.entity.Directory;
import com.backend.mini_filesystem.entity.File;
import com.backend.mini_filesystem.service.FileService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/files")
public class FileController {

	@Autowired
	private FileService fileService;

	@Transactional
	@GetMapping("/{directoryId}")
	public List<File> getFilesByDirectory(@PathVariable Long directoryId) {
		return fileService.getFilesByDirectory(directoryId);
	}

	@PostMapping
	public File createFile(@RequestBody File file) {
		return fileService.createFile(file);
	}

	@PutMapping("/{id}")
	public File updateFile(@PathVariable Long id, @RequestBody File fileDetails) {
		return fileService.updateFile(id, fileDetails);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
		fileService.deleteFile(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("directoryId") Directory directory) {
		try {
			String message = fileService.uploadFile(file, directory);
			return ResponseEntity.ok(message);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Erro ao processar o arquivo.");
		}
	}

	@GetMapping("/download/{id}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
		Optional<File> fileOptional = fileService.downloadFile(id);

		if (!fileOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		File file = fileOptional.get();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", file.getName());

		return new ResponseEntity<>(file.getContent(), headers, HttpStatus.OK);
	}
}
