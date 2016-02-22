package com.muhardin.endy.belajar.ci.dao;

import com.muhardin.endy.belajar.ci.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductDao extends PagingAndSortingRepository<Product, String>{ }
