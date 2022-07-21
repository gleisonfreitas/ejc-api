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

import br.com.ejc.dto.LancamentoEstatistica;
import br.com.ejc.dto.LancamentoResumoTipo;
import br.com.ejc.dto.LancamentoResumoTipoPorDia;
import br.com.ejc.model.Ejc;
import br.com.ejc.model.Lancamento;
import br.com.ejc.repository.EjcRepository;
import br.com.ejc.repository.LancamentoRepository;
import br.com.ejc.repository.filter.LancamentoFilter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private EjcRepository ejcRepository;
	
	public Lancamento salvar(Lancamento lancamento) {
		
		Ejc ejc = ejcRepository.findOne(lancamento.getEjc().getCodigo());
		
		lancamento.setEjc(ejc);
		
		return lancamentoRepository.save(lancamento);
	}

	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		
		Lancamento lancamentoSalva = buscarPeloCodigo(codigo);
		
		if(lancamentoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(lancamento, lancamentoSalva, "codigo");
		
		return lancamentoRepository.save(lancamentoSalva);
	}
	
	public List<LancamentoResumoTipoPorDia> pesquisarResumoTipoPorDia(LancamentoFilter lancamentoFilter) {
		return lancamentoRepository.pesquisarResumoTipoDia(lancamentoFilter);
	}
	
	public List<LancamentoResumoTipo> pesquisarResumoTipo(LancamentoFilter lancamentoFilter) {
		return lancamentoRepository.pesquisarResumoTipo(lancamentoFilter);
	}
	
	public byte[] lancamentoEstatistica(Long codigoEjc) throws Exception {
		
		Ejc ejc = ejcRepository.findOne(codigoEjc);
		
		if(ejc == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		List<LancamentoEstatistica> dados = lancamentoRepository.estatisticaLancamento(codigoEjc);
		
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		parametros.put("EJC", ejc.getNumero()+" ENCONTRO DE JOVENS COM CRISTO");
		parametros.put("IGREJA", ejc.getIgreja().getNome().toUpperCase());
		if(ejc.getLogo() != null) {
			parametros.put("LOGO", ejc.getLogo());
		}
		
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/relatorios/lancamento_estatistica.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				inputStream, parametros, new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
	}
	
	private Lancamento buscarPeloCodigo(Long codigo) {
		Lancamento lancamentoSalva = lancamentoRepository.findOne(codigo);
		if(lancamentoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return lancamentoSalva;
	}

}
