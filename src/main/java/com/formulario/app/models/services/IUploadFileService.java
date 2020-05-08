package com.formulario.app.models.services;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IUploadFileService {	

	
	// es el metodo que guarda
	public String copy(MultipartFile file) throws IOException;
	
	public boolean  delete (String file);
}
