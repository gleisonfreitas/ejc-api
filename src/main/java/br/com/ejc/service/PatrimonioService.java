package br.com.ejc.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ejc.model.Ejc;
import br.com.ejc.model.Patrimonio;
import br.com.ejc.repository.EjcRepository;
import br.com.ejc.repository.PatrimonioRepository;
import br.com.ejc.repository.filter.PatrimonioFilter;
import br.com.ejc.repository.projection.PatrimonioResumo;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Service
public class PatrimonioService {

	@Autowired
	private PatrimonioRepository patrimonioRepository;
	
	@Autowired
	private EjcRepository ejcRepository;
	
	public Page<PatrimonioResumo> resumir(PatrimonioFilter patrimonioFilter, Pageable pageable) {
		
		Ejc ejc = ejcRepository.findOne(patrimonioFilter.getCodigoIgreja());
		
		patrimonioFilter.setCodigoIgreja(ejc.getIgreja().getCodigo());
		
		return patrimonioRepository.resumir(patrimonioFilter, pageable);
		
	} 
	
	public Patrimonio salvar(Patrimonio patrimonio) {
		
		Ejc ejc = ejcRepository.findOne(patrimonio.getIgreja().getCodigo());
		
		patrimonio.setIgreja(ejc.getIgreja());
		
		return patrimonioRepository.save(patrimonio);
	}

	public Patrimonio atualizar(Long codigo, Patrimonio patrimonio) {
		
		Patrimonio patrimonioSalva = buscarPeloCodigo(codigo);
		
		if(patrimonioSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(patrimonio, patrimonioSalva, "codigo");
		
		return patrimonioRepository.save(patrimonioSalva);
	}
	
	public byte[] gerarRelatorio(Long codigoEjc) throws Exception {
		
		Ejc ejc = ejcRepository.findOne(codigoEjc);
		
		if(ejc == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		List<Patrimonio> dados = patrimonioRepository.gerarRelatorio(ejc.getIgreja().getCodigo());
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		parametros.put("IGREJA", ejc.getIgreja().getNome().toUpperCase());
		if(ejc.getIgreja().getLogo() != null) {
			parametros.put("LOGO", ejc.getIgreja().getLogo() );
		}
		
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/relatorios/patrimonio_lista.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				inputStream, parametros, new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
	}
	
	private Patrimonio buscarPeloCodigo(Long codigo) {
		Patrimonio patrimonioSalva = patrimonioRepository.findOne(codigo);
		if(patrimonioSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return patrimonioSalva;
	}

}
