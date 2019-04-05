package com.web.repository;

import org.springframework.data.repository.CrudRepository;

import com.web.data.Message;

public interface MessageRepo extends CrudRepository<Message, Long>{

}
