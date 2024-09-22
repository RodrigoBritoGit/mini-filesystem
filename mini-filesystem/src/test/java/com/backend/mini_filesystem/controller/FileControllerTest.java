package com.backend.mini_filesystem.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.backend.mini_filesystem.entity.File;
import com.backend.mini_filesystem.service.DirectoryService;
import com.backend.mini_filesystem.service.FileService;

public class FileControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private FileService fileService;

	@Mock
	private DirectoryService directoryService;

	@InjectMocks
	private FileController fileController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
	}

	@Test
	public void testGetFilesByDirectory() throws Exception {
		Long directoryId = 1L;
		File file1 = new File(1L, "File1.txt", new byte[] {});
		File file2 = new File(2L, "File2.txt", new byte[] {});
		List<File> files = Arrays.asList(file1, file2);

		when(fileService.getFilesByDirectory(directoryId)).thenReturn(files);

		mockMvc.perform(get("/api/files/{directoryId}", directoryId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("[{'id':1,'name':'File1.txt'},{'id':2,'name':'File2.txt'}]"));
	}

	@Test
	public void testCreateFile() throws Exception {
		File newFile = new File(null, "NewFile.txt", new byte[] {});
		File savedFile = new File(1L, "NewFile.txt", new byte[] {});

		when(fileService.createFile(any(File.class))).thenReturn(savedFile);

		mockMvc.perform(post("/api/files").contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"NewFile.txt\", \"content\":[] }")) // Ajuste conforme necessário
				.andExpect(status().isOk()).andExpect(content().json("{'id':1,'name':'NewFile.txt'}"));
	}

	@Test
	public void testUpdateFile() throws Exception {
		Long fileId = 1L;
		File updatedFile = new File(fileId, "UpdatedFile.txt", new byte[] {});

		when(fileService.updateFile(eq(fileId), any(File.class))).thenReturn(updatedFile);

		mockMvc.perform(put("/api/files/{id}", fileId).contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"UpdatedFile.txt\", \"content\":[] }")) // Ajuste conforme necessário
				.andExpect(status().isOk()).andExpect(content().json("{'id':1,'name':'UpdatedFile.txt'}"));
	}

	@Test
	public void testDeleteFile() throws Exception {
		Long fileId = 1L;

		doNothing().when(fileService).deleteFile(fileId);

		mockMvc.perform(delete("/api/files/{id}", fileId)).andExpect(status().isNoContent());

		verify(fileService, times(1)).deleteFile(fileId);
	}

}
