package br.com.ejc.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.ejc.model.Ejc;
import br.com.ejc.dto.Agenda;
import br.com.ejc.dto.Aniversariante;
import br.com.ejc.dto.CamisasPorEquipe;
import br.com.ejc.dto.CirculoEstatisticaPorPessoa;
import br.com.ejc.dto.Etiqueta;
import br.com.ejc.mail.Mailer;
import br.com.ejc.model.Circulo;
import br.com.ejc.model.Pessoa;
import br.com.ejc.repository.EjcRepository;
import br.com.ejc.repository.PessoaRepository;
import br.com.ejc.repository.filter.CirculoFilter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.ejc.repository.CirculoRepository;

@Service
public class CirculoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CirculoService.class);
	
	@Autowired
	private CirculoRepository circuloRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private EjcRepository ejcRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private Mailer mailer;
	
	public void avisarInscricaoEncontrista(Circulo circulo) {
		
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Preparando envio de e-mails de "
					+ "aviso de inscrição");
		}
		
		this.mailer.avisarInscricaoEncontrista(circulo);
		
		LOGGER.info("Envio de e-mail de aviso concluído.");
	}
	
	public Circulo salvar(Circulo circulo) {
		
		Ejc ejc = ejcRepository.findOne(circulo.getEjc().getCodigo());
		
		if(ejc == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		
		circulo.setEjc(ejc);
		
		Pessoa coordenadorUm = pessoaRepository.findOne(circulo.getCoordenadorUm().getCodigo());
		circulo.setCoordenadorUm(coordenadorUm);
		
		Pessoa coordenadorDois = pessoaRepository.findOne(circulo.getCoordenadorDois().getCodigo());
		circulo.setCoordenadorDois(coordenadorDois);
		
		if(circulo.getCoordenadorTres().getCodigo() != null) {
			Pessoa coordenadorTres = pessoaRepository.findOne(circulo.getCoordenadorTres().getCodigo());
			circulo.setCoordenadorTres(coordenadorTres);
		} else {
			circulo.setCoordenadorTres(null);
		}
		
		
		circulo.getEncontristas().forEach( 
				p -> pessoaService.atualizarTrabalhando(p.getCodigo(), Boolean.TRUE));
		
		Circulo circuloSalva = circuloRepository.save(circulo);
		
		this.avisarInscricaoEncontrista(circuloSalva);
		
		return circuloSalva;
	}


	public Circulo atualizar(Long codigo, Circulo circulo) {
		
		Circulo circuloSalva = circuloRepository.findOne(codigo);
		
		if(circuloSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		List<Pessoa> listaRetirarEnvioEmail = new ArrayList<>();
		
		circuloSalva.setCoordenadorUm(pessoaRepository.findOne(circulo.getCoordenadorUm().getCodigo()));
		circuloSalva.setCoordenadorDois(pessoaRepository.findOne(circulo.getCoordenadorDois().getCodigo()));
		
		if(circulo.getCoordenadorTres() != null && circulo.getCoordenadorTres().getCodigo() != null) {
			circuloSalva.setCoordenadorTres(pessoaRepository.findOne(circulo.getCoordenadorTres().getCodigo()));
		}
		
		if(circulo.getEncontristas() != null) {
			
			for (Pessoa pessoa : circuloSalva.getEncontristas()) {
				if(!circulo.getEncontristas().contains(pessoa)) {
					listaRetirarEnvioEmail.add(pessoa);
				}
			}
			
			circuloSalva.getEncontristas().forEach(
					p -> pessoaService.atualizarTrabalhando(p.getCodigo(), Boolean.FALSE));
			
			circuloSalva.getEncontristas().clear();
			
			circulo.getEncontristas().forEach(
					p -> circuloSalva.getEncontristas().add(
							pessoaService.atualizarTrabalhando(p.getCodigo(), Boolean.TRUE)));
		}
		
		BeanUtils.copyProperties(circulo, circuloSalva, "codigo", "encontristas", "ejc");
		
		Circulo circuloAtualizado = circuloRepository.save(circuloSalva);
		
		avisarInscricaoEncontrista(circuloAtualizado);
		
		pessoaService.atualizarEnvioEmail(listaRetirarEnvioEmail, false);
		
		return circuloAtualizado;
	}

	public void excluir(Long codigo) {
		
		Circulo circulo = circuloRepository.findOne(codigo);
		
		List<Pessoa> listaAtualizarEnvioEmail = new ArrayList<>();
		listaAtualizarEnvioEmail.add(circulo.getCoordenadorUm());
		listaAtualizarEnvioEmail.add(circulo.getCoordenadorDois());
		listaAtualizarEnvioEmail.add(circulo.getCoordenadorTres());
		listaAtualizarEnvioEmail.addAll(circulo.getEncontristas());
		
		pessoaService.atualizarEnvioEmail(listaAtualizarEnvioEmail, Boolean.FALSE);
		
		pessoaService.atualizarTrabalhando(circulo.getCoordenadorUm().getCodigo(), Boolean.FALSE);
		pessoaService.atualizarTrabalhando(circulo.getCoordenadorDois().getCodigo(), Boolean.FALSE);
		if(circulo.getCoordenadorTres() != null && circulo.getCoordenadorTres().getCodigo() != null) {
			pessoaService.atualizarTrabalhando(circulo.getCoordenadorTres().getCodigo(), Boolean.FALSE);
		}
		
		for (Pessoa pessoa : circulo.getEncontristas()) {
			pessoaService.atualizarTrabalhando(pessoa.getCodigo(), Boolean.FALSE);
		}
		
		circuloRepository.delete(codigo);		
	}

	public List<CirculoEstatisticaPorPessoa> porPessoa(Long codigoEjc) {
		
		Ejc ejc = ejcRepository.findOne(codigoEjc);
		
		if(ejc == null) {
			throw new EmptyResultDataAccessException(1);
		}

		return circuloRepository.porPessoa(ejc.getCodigo());
	}

	public List<Agenda> pesquisarAgenda(CirculoFilter filter) {
		return circuloRepository.pesquisarAgenda(filter);
	}
	
	public byte[] relatorioAgenda(CirculoFilter filter) throws Exception {
		
		Ejc ejc = ejcRepository.findOne(filter.getCodigoEjc());
		
		if(ejc == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		List<Agenda> dados = pesquisarAgenda(filter);
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		parametros.put("EJC", ejc.getNumero()+" ENCONTRO DE JOVENS COM CRISTO");
		parametros.put("IGREJA", ejc.getIgreja().getNome().toUpperCase());
		if(ejc.getLogo() != null) {
			parametros.put("LOGO", ejc.getLogo());
		}
		parametros.put("TITULO", "AGENDA DE ENCONTRISTAS");
		parametros.put("CABECALHO_TABELA", "Encontristas");
		
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/relatorios/agenda_por_equipe_a5.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				inputStream, parametros, new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
	}
	
	public byte[] relatorioCamisasPorCirculo(Long codigoEjc) throws Exception {
		
		Ejc ejc = ejcRepository.findOne(codigoEjc);
		
		if(ejc == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		List<CamisasPorEquipe> dados = circuloRepository.listarCamisasPorCirculo(codigoEjc);
		
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		parametros.put("EJC", ejc.getNumero()+" ENCONTRO DE JOVENS COM CRISTO");
		parametros.put("IGREJA", ejc.getIgreja().getNome().toUpperCase());
		if(ejc.getLogo() != null) {
			parametros.put("LOGO", ejc.getLogo());
		}
		parametros.put("TITULO", "CAMISAS DE ENCONTRISTAS");
		parametros.put("TIPO", "Círculo");
		
		
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/relatorios/camisas_por_equipe.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				inputStream, parametros, new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
	}
	
	public byte[] relatorioAniversariantes(Long codigoEjc) throws Exception {
		
		Ejc ejc = ejcRepository.findOne(codigoEjc);
		
		if(ejc == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		List<Aniversariante> dados = circuloRepository.listarAniversariantes(codigoEjc);
		
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		parametros.put("EJC", ejc.getNumero()+" ENCONTRO DE JOVENS COM CRISTO");
		parametros.put("IGREJA", ejc.getIgreja().getNome().toUpperCase());
		if(ejc.getLogo() != null) {
			parametros.put("LOGO", ejc.getLogo());
		}
		parametros.put("TITULO", "LISTA DE ENCONTRISTAS ANIVERSARIANTES");
		parametros.put("TIPO", "Circulo");
		
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/relatorios/aniversariantes.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				inputStream, parametros, new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
	}
	
	public byte[] relatorioEtiquetas(Long codigoEjc) throws Exception {
		
		Ejc ejc = ejcRepository.findOne(codigoEjc);
		
		if(ejc == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		List<Etiqueta> dados = circuloRepository.listarEtiquetas(codigoEjc);
		
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/relatorios/etiquetas_sacolas.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				inputStream, parametros, new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
	}
	
	public byte[] relatorioEtiquetasCrachas(Long codigoEjc) throws Exception {
		
		Ejc ejc = ejcRepository.findOne(codigoEjc);
		
		if(ejc == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		List<Etiqueta> dados = circuloRepository.listarEtiquetas(codigoEjc);
		
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		parametros.put("TIPO", "Círculo");
		
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/relatorios/etiquetas_crachas.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				inputStream, parametros, new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
	}
}
