package br.com.ejc.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.ejc.model.Compra;
import br.com.ejc.model.Ejc;
import br.com.ejc.repository.CompraRepository;
import br.com.ejc.repository.EjcRepository;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Service
public class CompraService {

	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private EjcRepository ejcRepository;
	
	public Compra salvar(Compra compra) {
		
		Ejc ejc = ejcRepository.findOne(compra.getEjc().getCodigo());
		
		compra.setEjc(ejc);
		
		return compraRepository.save(compra);
	}

	public Compra atualizar(Long codigo, Compra compra) {
		
		Compra compraSalva = buscarPeloCodigo(codigo);
		
		if(compraSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(compra, compraSalva, "codigo");
		
		return compraRepository.save(compraSalva);
	}
	
	private Compra buscarPeloCodigo(Long codigo) {
		Compra compraSalva = compraRepository.findOne(codigo);
		if(compraSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return compraSalva;
	}
	
	public byte[] gerarRelatorio(Long codigoEjc) throws Exception {
		
		Ejc ejc = ejcRepository.findOne(codigoEjc);
		
		if(ejc == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		List<Compra> dados = compraRepository.gerarRelatorio(codigoEjc);
		
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		parametros.put("EJC", ejc.getNumero()+" ENCONTRO DE JOVENS COM CRISTO");
		parametros.put("IGREJA", ejc.getIgreja().getNome().toUpperCase());
		if(ejc.getLogo() != null) {
			parametros.put("LOGO", ejc.getLogo());
		}
		
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/relatorios/compras_estatistica.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				inputStream, parametros, new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
	}

}
