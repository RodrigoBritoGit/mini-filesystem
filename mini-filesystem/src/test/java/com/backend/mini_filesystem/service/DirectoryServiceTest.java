package com.backend.mini_filesystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.backend.mini_filesystem.entity.Directory;
import com.backend.mini_filesystem.repository.DirectoryRepository;

public class DirectoryServiceTest {

	@Mock
	private DirectoryRepository directoryRepository;

	@InjectMocks
	private DirectoryService directoryService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindByIdDirectory() {
		Long directoryId = 1L;
		Directory directory = new Directory(directoryId, "Test Directory");

		when(directoryRepository.findById(directoryId)).thenReturn(Optional.of(directory));

		Directory foundDirectory = directoryService.findByIdDirectory(directoryId);

		assertNotNull(foundDirectory);
		assertEquals(directoryId, foundDirectory.getId());
		assertEquals("Test Directory", foundDirectory.getName());
	}

	@Test
	public void testFindByIdDirectoryNotFound() {
		Long directoryId = 1L;

		when(directoryRepository.findById(directoryId)).thenReturn(Optional.empty());

		Directory foundDirectory = directoryService.findByIdDirectory(directoryId);

		assertNull(foundDirectory);
	}

	@Test
	public void testGetAllDirectories() {
		Directory dir1 = new Directory(1L, "Dir 1");
		Directory dir2 = new Directory(2L, "Dir 2");
		List<Directory> directories = Arrays.asList(dir1, dir2);

		when(directoryRepository.findAll()).thenReturn(directories);

		List<Directory> foundDirectories = directoryService.getAllDirectories();

		assertNotNull(foundDirectories);
		assertEquals(2, foundDirectories.size());
	}

	@Test
	public void testCreateDirectory() {
		Directory newDirectory = new Directory(null, "New Directory");
		Directory savedDirectory = new Directory(1L, "New Directory");

		when(directoryRepository.save(any(Directory.class))).thenReturn(savedDirectory);

		Directory createdDirectory = directoryService.createDirectory(newDirectory);

		assertNotNull(createdDirectory);
		assertEquals(1L, createdDirectory.getId());
		assertEquals("New Directory", createdDirectory.getName());
	}

	@Test
	public void testUpdateDirectory() {
		Long directoryId = 1L;
		Directory existingDirectory = new Directory(directoryId, "Old Directory");
		Directory updatedDirectory = new Directory(null, "Updated Directory");

		when(directoryRepository.findById(directoryId)).thenReturn(Optional.of(existingDirectory));
		when(directoryRepository.save(any(Directory.class)))
				.thenReturn(new Directory(directoryId, "Updated Directory"));

		Directory result = directoryService.updateDirectory(directoryId, updatedDirectory);

		assertNotNull(result);
		assertEquals(directoryId, result.getId());
		assertEquals("Updated Directory", result.getName());
	}

	@Test
	public void testUpdateDirectoryNotFound() {
		Long directoryId = 1L;
		Directory updatedDirectory = new Directory(null, "Updated Directory");

		when(directoryRepository.findById(directoryId)).thenReturn(Optional.empty());

		Exception exception = assertThrows(RuntimeException.class, () -> {
			directoryService.updateDirectory(directoryId, updatedDirectory);
		});

		assertEquals("Directory not found with id " + directoryId, exception.getMessage());
	}

	@Test
	public void testDeleteDirectory() {
		Long directoryId = 1L;

		doNothing().when(directoryRepository).deleteById(directoryId);

		directoryService.deleteDirectory(directoryId);

		verify(directoryRepository, times(1)).deleteById(directoryId);
	}

}
