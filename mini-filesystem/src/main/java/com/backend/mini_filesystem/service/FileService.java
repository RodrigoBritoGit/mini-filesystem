package com.backend.mini_filesystem.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.mini_filesystem.entity.Directory;
import com.backend.mini_filesystem.entity.File;
import com.backend.mini_filesystem.repository.FileRepository;

@Service
public class FileService {

	@Autowired
	private FileRepository fileRepository;

	public List<File> getFilesByDirectory(Long directoryId) {
		return fileRepository.findByDirectoryId(directoryId);
	}

	public File createFile(File file) {
		return fileRepository.save(file);
	}

	public File updateFile(Long id, File fileDetails) {
		File file = fileRepository.findById(id).orElseThrow();
		file.setName(fileDetails.getName());
		return fileRepository.save(file);
	}

	public void deleteFile(Long id) {
		fileRepository.deleteById(id);
	}

	public String uploadFile(MultipartFile file, Directory directory) throws IOException {
		if (file.isEmpty() || !file.getOriginalFilename().endsWith(".txt")) {
			return "Por favor, envie um arquivo .txt.";
		}

		byte[] content = file.getBytes();

		File fileEntity = new File();
		fileEntity.setName(file.getOriginalFilename());
		fileEntity.setContent(content);
		fileEntity.setDirectory(directory);

		fileRepository.save(fileEntity);
		return "Arquivo enviado com sucesso";
	}

	public Optional<File> downloadFile(Long id) {
		return fileRepository.findById(id);
	}

}
