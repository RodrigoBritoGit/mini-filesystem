package com.backend.mini_filesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.mini_filesystem.entity.Directory;
import com.backend.mini_filesystem.repository.DirectoryRepository;

@Service
public class DirectoryService {
	@Autowired
	private DirectoryRepository directoryRepository;
	
	public Directory findByIdDirectory(Long id) {
		return directoryRepository.findById(id).orElse(null);
	}

	public List<Directory> getAllDirectories() {
		return directoryRepository.findAll();
	}

	public Directory createDirectory(Directory directory) {
		return directoryRepository.save(directory);
	}

	public Directory updateDirectory(Long id, Directory directoryDetails) {
		Directory directory = directoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Directory not found with id " + id));
		directory.setName(directoryDetails.getName());
		return directoryRepository.save(directory);
	}

	public void deleteDirectory(Long id) {
		directoryRepository.deleteById(id);
	}

}
