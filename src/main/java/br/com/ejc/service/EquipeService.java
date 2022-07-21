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

import br.com.ejc.dto.Agenda;
import br.com.ejc.dto.Aniversariante;
import br.com.ejc.dto.CamisasPorEquipe;
import br.com.ejc.dto.EquipeEstatisticaPorPessoa;
import br.com.ejc.dto.Etiqueta;
import br.com.ejc.mail.Mailer;
import br.com.ejc.model.Ejc;
import br.com.ejc.model.Equipe;
import br.com.ejc.model.Pessoa;
import br.com.ejc.repository.EjcRepository;
import br.com.ejc.repository.EquipeRepository;
import br.com.ejc.repository.filter.EquipeFilter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class EquipeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EquipeService.class);

	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private EjcRepository ejcRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private Mailer mailer;
	
	
	public void avisarLocalTrabalhoEncontreiro(Equipe equipe) {
		
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("Preparando envio de e-mails de "
					+ "aviso de inscrição");
		}
		
		this.mailer.avisarLocalTrabalhoEncontreiro(equipe);
		
		LOGGER.info("Envio de e-mail de aviso concluído.");
	}
	
	
	public Equipe salvar(Equipe equipe) {
		
		Ejc ejc = ejcRepository.findOne(equipe.getEjc().getCodigo());
		
		if(ejc == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		equipe.setEjc(ejc);
		
		Pessoa coordenadorUm = pessoaService.atualizarTrabalhando(equipe.getCoordenadorUm().getCodigo(), Boolean.TRUE);
		equipe.setCoordenadorUm(coordenadorUm);
		
		Pessoa coordenadorDois = pessoaService.atualizarTrabalhando(equipe.getCoordenadorDois().getCodigo(), Boolean.TRUE);
		equipe.setCoordenadorDois(coordenadorDois);
		
		if(equipe.getCoordenadorTres().getCodigo() != null) {
			Pessoa coordenadorTres = pessoaService.atualizarTrabalhando(equipe.getCoordenadorTres().getCodigo(), Boolean.TRUE);
			equipe.setCoordenadorTres(coordenadorTres);
		} else {
			equipe.setCoordenadorTres(null);
		}
		
		equipe.getEncontreiros().forEach( 
				p -> pessoaService.atualizarTrabalhando(p.getCodigo(), Boolean.TRUE));
		
		Equipe equipeSalva = equipeRepository.save(equipe);
		
		avisarLocalTrabalhoEncontreiro(equipeSalva);
		
		return equipeSalva;
	}

	public Equipe atualizar(Long codigo, Equipe equipe) {
		
		Equipe equipeSalva = equipeRepository.findOne(codigo);
		
		if(equipeSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		List<Pessoa> listaRetirarEnvioEmail = new ArrayList<>();
		
		if(!equipe.getCoordenadorUm().equals(equipeSalva.getCoordenadorUm())) {
			listaRetirarEnvioEmail.add(equipeSalva.getCoordenadorUm());
			pessoaService.atualizarTrabalhando(equipeSalva.getCoordenadorUm().getCodigo(), Boolean.FALSE);
		}
		
		equipeSalva.setCoordenadorUm(pessoaService.atualizarTrabalhando(equipe.getCoordenadorUm().getCodigo(), Boolean.TRUE));
					
		equipeSalva.setCoordenadorDois(pessoaService.atualizarTrabalhando(equipe.getCoordenadorDois().getCodigo(), Boolean.TRUE));
		
		if(!equipe.getCoordenadorDois().equals(equipeSalva.getCoordenadorDois())) {
			listaRetirarEnvioEmail.add(equipeSalva.getCoordenadorDois());
			pessoaService.atualizarTrabalhando(equipeSalva.getCoordenadorDois().getCodigo(), Boolean.FALSE);
		}
		
		if(equipe.getCoordenadorTres().getCodigo() != null) {
			Pessoa coordenadorTres = pessoaService.atualizarTrabalhando(equipe.getCoordenadorTres().getCodigo(), Boolean.TRUE);
			equipe.setCoordenadorTres(coordenadorTres);
			if(equipeSalva.getCoordenadorTres() != null && 
					equipeSalva.getCoordenadorTres().getCodigo() != null &&
					!equipe.getCoordenadorTres().equals(equipeSalva.getCoordenadorTres())) {
				listaRetirarEnvioEmail.add(equipeSalva.getCoordenadorTres());
				pessoaService.atualizarTrabalhando(equipeSalva.getCoordenadorTres().getCodigo(), Boolean.FALSE);
			}
		} else {
			equipe.setCoordenadorTres(null);
			if(equipeSalva.getCoordenadorTres() != null) {
				listaRetirarEnvioEmail.add(equipeSalva.getCoordenadorTres());
				pessoaService.atualizarTrabalhando(equipeSalva.getCoordenadorTres().getCodigo(), Boolean.FALSE);
			}
		}
		
		
		if(equipe.getEncontreiros() != null) {
			
			for (Pessoa pessoa : equipeSalva.getEncontreiros()) {
				if(!equipe.getEncontreiros().contains(pessoa)) {
					listaRetirarEnvioEmail.add(pessoa);
				}
			}
			
			equipeSalva.getEncontreiros().forEach(
					p -> pessoaService.atualizarTrabalhando(p.getCodigo(), Boolean.FALSE));
			
			equipeSalva.getEncontreiros().clear();
			
			equipe.getEncontreiros().forEach(
					p -> equipeSalva.getEncontreiros().add(
							pessoaService.atualizarTrabalhando(p.getCodigo(), Boolean.TRUE)));
		}
		
		BeanUtils.copyProperties(equipe, equipeSalva, "codigo", "encontreiros", "ejc");
		
		
		Equipe equipeAtualizada = equipeRepository.save(equipeSalva);
		
		avisarLocalTrabalhoEncontreiro(equipeAtualizada);
		
		pessoaService.atualizarEnvioEmail(listaRetirarEnvioEmail, false);
		
		return equipeAtualizada;
	}

	public void excluir(Long codigo) {
		
		Equipe equipe = equipeRepository.findOne(codigo);
		
		List<Pessoa> listaAtualizarEnvioEmail = new ArrayList<>();
		listaAtualizarEnvioEmail.add(equipe.getCoordenadorUm());
		listaAtualizarEnvioEmail.add(equipe.getCoordenadorDois());
		listaAtualizarEnvioEmail.addAll(equipe.getEncontreiros());
		
		pessoaService.atualizarEnvioEmail(listaAtualizarEnvioEmail, Boolean.FALSE);
		
		pessoaService.atualizarTrabalhando(equipe.getCoordenadorUm().getCodigo(), Boolean.FALSE);
		
		pessoaService.atualizarTrabalhando(equipe.getCoordenadorDois().getCodigo(), Boolean.FALSE);
		
		for (Pessoa pessoa : equipe.getEncontreiros()) {
			pessoaService.atualizarTrabalhando(pessoa.getCodigo(), Boolean.FALSE);
		}
		
		equipeRepository.delete(codigo);		
	}

	public List<EquipeEstatisticaPorPessoa> porPessoa(Long codigoEjc) {
		
		Ejc ejc = ejcRepository.findOne(codigoEjc);
		
		if(ejc == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return equipeRepository.porPessoa(ejc.getCodigo());
	}
	
	public List<Agenda> pesquisarAgenda(EquipeFilter filter) {
		return equipeRepository.pesquisarAgenda(filter);
	}
	
	public byte[] relatorioAgenda(EquipeFilter filter) throws Exception {
		
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
		parametros.put("TITULO", "AGENDA DE ENCONTREIROS");
		parametros.put("CABECALHO_TABELA", "Encontreiros");
		
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/relatorios/agenda_por_equipe_a5.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				inputStream, parametros, new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
	}
	
	public byte[] relatorioCamisasPorEquipe(Long codigoEjc) throws Exception {
		
		Ejc ejc = ejcRepository.findOne(codigoEjc);
		
		if(ejc == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		List<CamisasPorEquipe> dados = equipeRepository.listarCamisasPorEquipe(codigoEjc);
		
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		parametros.put("EJC", ejc.getNumero()+" ENCONTRO DE JOVENS COM CRISTO");
		parametros.put("IGREJA", ejc.getIgreja().getNome().toUpperCase());
		if(ejc.getLogo() != null) {
			parametros.put("LOGO", ejc.getLogo());
		}
		parametros.put("TITULO", "CAMISAS DE ENCONTREIROS");
		parametros.put("TIPO", "Equipe");
		
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
		
		List<Aniversariante> dados = equipeRepository.listarAniversariantes(codigoEjc);
		
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		parametros.put("EJC", ejc.getNumero()+" ENCONTRO DE JOVENS COM CRISTO");
		parametros.put("IGREJA", ejc.getIgreja().getNome().toUpperCase());
		if(ejc.getLogo() != null) {
			parametros.put("LOGO", ejc.getLogo());
		}
		parametros.put("TITULO", "LISTA DE ENCONTREIROS ANIVERSARIANTES");
		parametros.put("TIPO", "Equipe");
		
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/relatorios/aniversariantes.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				inputStream, parametros, new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
	}
	
	public byte[] relatorioEtiquetasCrachas(Long codigoEjc) throws Exception {
		
		Ejc ejc = ejcRepository.findOne(codigoEjc);
		
		if(ejc == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		List<Etiqueta> dados = equipeRepository.listarEtiquetas(codigoEjc);
		
		
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		parametros.put("TIPO", "Equipe");
		
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/relatorios/etiquetas_crachas.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				inputStream, parametros, new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
	}

}
