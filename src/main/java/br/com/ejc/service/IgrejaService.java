package br.com.ejc.service;

import java.io.IOException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.ejc.model.Igreja;
import br.com.ejc.repository.IgrejaRepository;

@Service
public class IgrejaService {

	@Autowired
	private IgrejaRepository igrejaRepository;

	
	public Igreja atualizar(Long codigo, Igreja igreja) {
		
		Igreja igrejaSalva = buscarPeloCodigo(codigo);
		
		BeanUtils.copyProperties(igreja, igrejaSalva, "codigo");
		
		return igrejaRepository.save(igrejaSalva);
		
	}

	private Igreja buscarPeloCodigo(Long codigo) {
		Igreja igrejaSalva = igrejaRepository.findOne(codigo);
		
		if(igrejaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return igrejaSalva;
	}
	
	public void atualizarLogo(Long codigo, MultipartFile file) throws IOException {
		Igreja igrejaSalva = buscarPeloCodigo(codigo);
		igrejaSalva.setLogo(file.getBytes());
		igrejaRepository.save(igrejaSalva);
	}
}
