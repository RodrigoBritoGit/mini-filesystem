package com.backend.mini_filesystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.mini_filesystem.entity.Directory;
import com.backend.mini_filesystem.service.DirectoryService;

@RestController
@RequestMapping("/api/directories")
public class DirectoryController {

	@Autowired
	private DirectoryService directoryService;

	@GetMapping
	public List<Directory> getAllDirectories() {
		return directoryService.getAllDirectories();
	}

	@PostMapping
	public Directory createDirectory(@RequestBody Directory directory) {
		return directoryService.createDirectory(directory);
	}

	@PutMapping("/{id}")
	public Directory updateDirectory(@PathVariable Long id, @RequestBody Directory directoryDetails) {
		return directoryService.updateDirectory(id, directoryDetails);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDirectory(@PathVariable Long id) {
		directoryService.deleteDirectory(id);
		return ResponseEntity.noContent().build();
	}
}
