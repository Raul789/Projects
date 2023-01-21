package com.example.guiex1.repository;


import com.example.guiex1.domain.ValidationException;
import com.example.guiex1.exceptions.RepositoryException;

import java.util.Optional;
import java.util.List;

public interface Repository<E,ID> {

    int size();

    List<E> getAll();

    void add(E entity) throws RepositoryException;

    void remove(E entity) throws RepositoryException;

    E find(ID id) throws RepositoryException;



}


