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

import com.backend.mini_filesystem.entity.Directory;
import com.backend.mini_filesystem.service.DirectoryService;

public class DirectoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private DirectoryService directoryService;

	@InjectMocks
	private DirectoryController directoryController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(directoryController).build();
	}

	@Test
	public void testGetAllDirectories() throws Exception {
		Directory directory1 = new Directory(1L, "Directory 1");
		Directory directory2 = new Directory(2L, "Directory 2");
		List<Directory> directories = Arrays.asList(directory1, directory2);

		when(directoryService.getAllDirectories()).thenReturn(directories);

		mockMvc.perform(get("/api/directories").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("[{'id':1,'name':'Directory 1'},{'id':2,'name':'Directory 2'}]"));
	}

	@Test
	public void testCreateDirectory() throws Exception {
		Directory newDirectory = new Directory(null, "New Directory");
		Directory savedDirectory = new Directory(1L, "New Directory");

		when(directoryService.createDirectory(any(Directory.class))).thenReturn(savedDirectory);

		mockMvc.perform(post("/api/directories").contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"New Directory\"}")).andExpect(status().isOk())
				.andExpect(content().json("{'id':1,'name':'New Directory'}"));
	}

	@Test
	public void testUpdateDirectory() throws Exception {
		Long directoryId = 1L;
		Directory updatedDirectory = new Directory(directoryId, "Updated Directory");

		when(directoryService.updateDirectory(eq(directoryId), any(Directory.class))).thenReturn(updatedDirectory);

		mockMvc.perform(put("/api/directories/{id}", directoryId).contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Updated Directory\"}")).andExpect(status().isOk())
				.andExpect(content().json("{'id':1,'name':'Updated Directory'}"));
	}

	@Test
	public void testDeleteDirectory() throws Exception {
		Long directoryId = 1L;

		doNothing().when(directoryService).deleteDirectory(directoryId);

		mockMvc.perform(delete("/api/directories/{id}", directoryId)).andExpect(status().isNoContent());

		verify(directoryService, times(1)).deleteDirectory(directoryId);
	}

}
