package com.unisinos.smart_health_data_core.edge.service;

import org.springframework.stereotype.Service;

import com.unisinos.smart_health_data_core.edge.model.EdgeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EdgeService {

	private final EdgeRepository repository;
	
	//TODO popular o banco
}
