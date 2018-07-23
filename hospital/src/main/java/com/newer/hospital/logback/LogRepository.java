package com.newer.hospital.logback;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<MyLog,String>{
}
