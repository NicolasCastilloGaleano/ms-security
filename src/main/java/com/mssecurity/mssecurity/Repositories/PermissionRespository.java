package com.mssecurity.mssecurity.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mssecurity.mssecurity.Models.Permission;
import org.springframework.data.mongodb.repository.Query;

public interface PermissionRespository extends MongoRepository<Permission, String> {
    @Query("{'url':?0,'method':?1}")
    Permission getPermission(String url, String method);
}
