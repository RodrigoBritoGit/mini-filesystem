package com.backend.mini_filesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.mini_filesystem.entity.Directory;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {

}
