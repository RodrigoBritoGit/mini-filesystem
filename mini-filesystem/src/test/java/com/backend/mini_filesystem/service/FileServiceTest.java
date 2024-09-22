package com.backend.mini_filesystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.backend.mini_filesystem.entity.Directory;
import com.backend.mini_filesystem.entity.File;
import com.backend.mini_filesystem.repository.FileRepository;

public class FileServiceTest {

	@Mock
	private FileRepository fileRepository;

	@InjectMocks
	private FileService fileService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetFilesByDirectory() {
		Long directoryId = 1L;
		File file1 = new File(1L, "File1.txt", new byte[] {});
		File file2 = new File(2L, "File2.txt", new byte[] {});
		List<File> files = Arrays.asList(file1, file2);

		when(fileRepository.findByDirectoryId(directoryId)).thenReturn(files);

		List<File> foundFiles = fileService.getFilesByDirectory(directoryId);

		assertNotNull(foundFiles);
		assertEquals(2, foundFiles.size());
	}

	@Test
	public void testCreateFile() {
		File newFile = new File(null, "NewFile.txt", new byte[] {});
		File savedFile = new File(1L, "NewFile.txt", new byte[] {});

		when(fileRepository.save(any(File.class))).thenReturn(savedFile);

		File createdFile = fileService.createFile(newFile);

		assertNotNull(createdFile);
		assertEquals(1L, createdFile.getId());
		assertEquals("NewFile.txt", createdFile.getName());
	}

	@Test
	public void testUpdateFile() {
		Long fileId = 1L;
		File existingFile = new File(fileId, "OldFile.txt", new byte[] {});
		File updatedFileDetails = new File(null, "UpdatedFile.txt", new byte[] {});

		when(fileRepository.findById(fileId)).thenReturn(Optional.of(existingFile));
		when(fileRepository.save(any(File.class))).thenReturn(new File(fileId, "UpdatedFile.txt", new byte[] {}));

		File updatedFile = fileService.updateFile(fileId, updatedFileDetails);

		assertNotNull(updatedFile);
		assertEquals(fileId, updatedFile.getId());
		assertEquals("UpdatedFile.txt", updatedFile.getName());
	}

	@Test
	public void testUpdateFileNotFound() {
		Long fileId = 1L;
		File updatedFileDetails = new File(null, "UpdatedFile.txt", new byte[] {});

		when(fileRepository.findById(fileId)).thenReturn(Optional.empty());

		Exception exception = assertThrows(RuntimeException.class, () -> {
			fileService.updateFile(fileId, updatedFileDetails);
		});

		assertNotNull(exception);
	}

	@Test
	public void testDeleteFile() {
		Long fileId = 1L;

		doNothing().when(fileRepository).deleteById(fileId);

		fileService.deleteFile(fileId);

		verify(fileRepository, times(1)).deleteById(fileId);
	}

	@Test
	public void testUploadFileSuccess() throws IOException {
		Directory directory = new Directory(1L, "Test Directory");
		MultipartFile multipartFile = mock(MultipartFile.class);

		when(multipartFile.getOriginalFilename()).thenReturn("test.txt");
		when(multipartFile.isEmpty()).thenReturn(false);
		when(multipartFile.getBytes()).thenReturn("content".getBytes());
		when(fileRepository.save(any(File.class))).thenReturn(new File(1L, "test.txt", "content".getBytes()));

		String message = fileService.uploadFile(multipartFile, directory);

		assertEquals("Arquivo enviado com sucesso", message);
	}

	@Test
	public void testUploadFileEmpty() throws IOException {
		Directory directory = new Directory(1L, "Test Directory");
		MultipartFile multipartFile = mock(MultipartFile.class);

		when(multipartFile.getOriginalFilename()).thenReturn("test.txt");
		when(multipartFile.isEmpty()).thenReturn(true);

		String message = fileService.uploadFile(multipartFile, directory);

		assertEquals("Por favor, envie um arquivo .txt.", message);
	}

	@Test
	public void testUploadFileWrongExtension() throws IOException {
		Directory directory = new Directory(1L, "Test Directory");
		MultipartFile multipartFile = mock(MultipartFile.class);

		when(multipartFile.getOriginalFilename()).thenReturn("test.jpg");
		when(multipartFile.isEmpty()).thenReturn(false);

		String message = fileService.uploadFile(multipartFile, directory);

		assertEquals("Por favor, envie um arquivo .txt.", message);
	}

	@Test
	public void testDownloadFile() {
		Long fileId = 1L;
		File file = new File(fileId, "File.txt", "content".getBytes());
		Optional<File> optionalFile = Optional.of(file);

		when(fileRepository.findById(fileId)).thenReturn(optionalFile);

		Optional<File> foundFile = fileService.downloadFile(fileId);

		assertTrue(foundFile.isPresent());
		assertEquals("File.txt", foundFile.get().getName());
	}

	@Test
	public void testDownloadFileNotFound() {
		Long fileId = 1L;

		when(fileRepository.findById(fileId)).thenReturn(Optional.empty());

		Optional<File> foundFile = fileService.downloadFile(fileId);

		assertFalse(foundFile.isPresent());
	}

}
