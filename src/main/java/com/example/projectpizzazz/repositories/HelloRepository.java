package com.example.projectpizzazz.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.projectpizzazz.models.Hello;

public interface HelloRepository
extends CrudRepository<Hello, Integer> {}
