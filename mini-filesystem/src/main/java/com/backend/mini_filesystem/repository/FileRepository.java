package com.backend.mini_filesystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.mini_filesystem.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long>{
	
	@Query(value = "SELECT * FROM File WHERE directory_id = :directory", nativeQuery = true)
    List<File> findByDirectoryId(@Param("directory") Long directory);
}
