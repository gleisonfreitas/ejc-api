package br.com.ejc.mail;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.ejc.model.Circulo;
import br.com.ejc.model.Equipe;
import br.com.ejc.model.Pessoa;
import br.com.ejc.repository.PessoaRepository;
import br.com.ejc.service.PessoaService;

@Component
public class Mailer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Mailer.class);
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private TemplateEngine thymeleaf;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public void avisarInscricaoEncontrista(Circulo circulo) {
		
		List<String> destinatariosEncontristas = new ArrayList<>();
		List<Pessoa> listaPessoasEmailEnviado = new ArrayList<>();
		
		for (Pessoa p : circulo.getEncontristas()) {
			Pessoa pessoa = pessoaRepository.findOne(p.getCodigo());
			if(pessoa.getEmail() != null && !pessoa.getEmail().trim().isEmpty() &&
					(pessoa.getEmailEnviado() == null || !pessoa.getEmailEnviado())) {
				destinatariosEncontristas.add(pessoa.getEmail());
				listaPessoasEmailEnviado.add(pessoa);
			}
		}
		
		if(!destinatariosEncontristas.isEmpty()) {
			
			LOGGER.info("enviado o email para encontristas..");
			
			System.out.println(destinatariosEncontristas.toString());
			
			
			Map<String, Object> variaveis = new HashMap<>();
			variaveis.put("ejc", circulo.getEjc().getNumero()+
					" EJC DA "+
					circulo.getEjc().getIgreja().getNome().toUpperCase());
			variaveis.put("tema", circulo.getEjc().getTema().toUpperCase());
			variaveis.put("equipe", "Circulo "+circulo.getCor());
			variaveis.put("igreja", circulo.getEjc().getIgreja().getNome());
			variaveis.put("local", circulo.getEjc().getEscola());
			variaveis.put("endereco", circulo.getEjc().getEnderecoEscola());
			variaveis.put("inicioDia", circulo.getEjc().getInicio().getDayOfMonth());
			variaveis.put("dataFim", circulo.getEjc().getFim().
					format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("pt", "Br"))));
			
			
			enviarEmail(destinatariosEncontristas, variaveis, "mail/aviso-inscricao-encontrista");
			
			
		}else {
			LOGGER.info("Não houve destinatários encontreiros para enviar o email");
		}
		
		pessoaService.atualizarEnvioEmail(listaPessoasEmailEnviado, true);
	}
		
	public void avisarLocalTrabalhoEncontreiro(Equipe equipe) {
		
		List<String> destinatarios = new ArrayList<>();
		List<Pessoa> listaPessoasEmailEnviado = new ArrayList<>();
		
		for (Pessoa p : equipe.getEncontreiros()) {
			Pessoa pessoa = pessoaRepository.findOne(p.getCodigo());
			if(pessoa.getEmail() != null && !pessoa.getEmail().trim().isEmpty() && 
					(pessoa.getEmailEnviado() == null || !pessoa.getEmailEnviado())) {
				destinatarios.add(pessoa.getEmail());
				listaPessoasEmailEnviado.add(pessoa);
			}
		}
		
		if(equipe.getCoordenadorUm().getEmail() != null && 
				!equipe.getCoordenadorUm().getEmail().trim().isEmpty() &&
				(equipe.getCoordenadorUm().getEmailEnviado() == null || 
				!equipe.getCoordenadorUm().getEmailEnviado())) {
			destinatarios.add(equipe.getCoordenadorUm().getEmail());
			listaPessoasEmailEnviado.add(equipe.getCoordenadorUm());
		}
		
		if(equipe.getCoordenadorDois().getEmail() != null && 
				!equipe.getCoordenadorDois().getEmail().trim().isEmpty() &&
				(equipe.getCoordenadorDois().getEmailEnviado() == null || 
				!equipe.getCoordenadorDois().getEmailEnviado())) {
			destinatarios.add(equipe.getCoordenadorDois().getEmail());
			listaPessoasEmailEnviado.add(equipe.getCoordenadorDois());
		}
		
		if(equipe.getCoordenadorTres() != null && 
				equipe.getCoordenadorTres().getEmail() != null &&
				!equipe.getCoordenadorTres().getEmail().trim().isEmpty()
				&& (equipe.getCoordenadorTres().getEmailEnviado() == null || !equipe.getCoordenadorTres().getEmailEnviado())) {
			destinatarios.add(equipe.getCoordenadorTres().getEmail());
			listaPessoasEmailEnviado.add(equipe.getCoordenadorTres());
		}
		
		
		if(!destinatarios.isEmpty()) {
			
			System.out.println(destinatarios.toString());
			
			LOGGER.info("enviado o email..");
			
			Map<String, Object> variaveis = new HashMap<>();
			variaveis.put("ejc", equipe.getEjc().getNumero()+
					" EJC DA "+
					equipe.getEjc().getIgreja().getNome().toUpperCase());
			variaveis.put("tema", equipe.getEjc().getTema().toUpperCase());
			variaveis.put("equipe", equipe.getNome().getDescricao());
			variaveis.put("igreja", equipe.getEjc().getIgreja().getNome());
			variaveis.put("local", equipe.getEjc().getEscola());
			variaveis.put("endereco", equipe.getEjc().getEnderecoEscola());
			variaveis.put("inicioDia", equipe.getEjc().getInicio().getDayOfMonth());
			variaveis.put("dataFim", equipe.getEjc().getFim().
					format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("pt", "Br"))));
			
			
			enviarEmail(destinatarios, variaveis, "mail/aviso-inscricao-encontreiro");
			
			pessoaService.atualizarEnvioEmail(listaPessoasEmailEnviado, true);
			
		}else {
			LOGGER.info("não houve destinatários para enviar o email");
		}
	}

	private void enviarEmail(List<String> destinatarios, 
			Map<String, Object> variaveis, String template) {
		
		this.montarTemplateEmail("ejcsuporte2@gmail.com",
				destinatarios,
				"Inscrição EJC",
				template,
				variaveis);
	}
	
	public void montarTemplateEmail(String remetente, 
			List<String> destinatarios, String assunto, String template,
			Map<String, Object> variaveis) {
		
		Context context = new Context(new Locale("pt", "BR"));
		
		variaveis.entrySet().forEach(
				e -> context.setVariable(e.getKey(), e.getValue()));
		
		String mensagem = thymeleaf.process(template, context);
		
		this.enviarEmail(remetente, destinatarios, assunto, mensagem);
	}
	
	public void enviarEmail(String remetente, 
			List<String> destinatarios, String assunto, String mensagem) {
		
		
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			
			MimeMessageHelper helper =  new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(remetente);
			helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
			helper.setSubject(assunto);
			helper.setText(mensagem, true);
			
			javaMailSender.send(mimeMessage);
			
		} catch (MessagingException e) {
			throw new RuntimeException("Erro ao enviar e-mail!", e);
		}
	}

}
