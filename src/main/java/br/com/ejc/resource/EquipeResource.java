package br.com.ejc.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ejc.dto.Agenda;
import br.com.ejc.dto.EquipeEstatisticaPorPessoa;
import br.com.ejc.event.RecursoCriadoEvent;
import br.com.ejc.model.Equipe;
import br.com.ejc.model.EquipeEnum;
import br.com.ejc.repository.EquipeRepository;
import br.com.ejc.repository.filter.EquipeFilter;
import br.com.ejc.repository.projection.EquipeResumo;
import br.com.ejc.repository.projection.NomeEquipeResumo;
import br.com.ejc.service.EquipeService;

@RestController
@RequestMapping("/equipes")
public class EquipeResource {
	
	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private EquipeService equipeService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping("/estatistica/por-equipe/{codigoEjc}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public List<EquipeEstatisticaPorPessoa> porPessoa(@PathVariable Long codigoEjc) {
		return equipeService.porPessoa(codigoEjc);
	}
	
	@GetMapping("/agenda")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EQUIPE') and #oauth2.hasScope('read')")
	public List<Agenda> pesquisarAgenda(EquipeFilter filter){
		return equipeService.pesquisarAgenda(filter);
	}
	
	@GetMapping("/relatorios/agenda")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EQUIPE') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> consultarAgenda(EquipeFilter filter) throws Exception {
		
		byte[] relatorio = this.equipeService.relatorioAgenda(filter);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}
	
	@GetMapping("/relatorios/camisas_por-equipe/{codigoEjc}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EQUIPE') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> relatorioCamisasPorEquipe(@PathVariable Long codigoEjc) throws Exception {
		
		byte[] relatorio = this.equipeService.relatorioCamisasPorEquipe(codigoEjc);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}
	
	@GetMapping("/relatorios/aniversariantes/{codigoEjc}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EQUIPE') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> relatorioAniversariantes(@PathVariable Long codigoEjc) throws Exception {
		
		byte[] relatorio = this.equipeService.relatorioAniversariantes(codigoEjc);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}
	
	@GetMapping("/relatorios/etiquetas_crachas/{codigoEjc}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EQUIPE') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> relatorioEtiquetasCrachas(@PathVariable Long codigoEjc) throws Exception {
		
		byte[] relatorio = this.equipeService.relatorioEtiquetasCrachas(codigoEjc);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EQUIPE') and #oauth2.hasScope('read')")
	public Page<Equipe> pesquisar(EquipeFilter filter, Pageable pageable){
		return this.equipeRepository.pesquisar(filter, pageable);
	}
	
	@GetMapping(params = "resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EQUIPE') and #oauth2.hasScope('read')")
	public Page<EquipeResumo> resumir(EquipeFilter filter, Pageable pageable){
		return equipeRepository.resumir(filter, pageable);
	}
	
	@GetMapping("/nomes")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EQUIPE') and #oauth2.hasScope('read')")
	public List<NomeEquipeResumo> listar(){
		
		List<NomeEquipeResumo> lista = new ArrayList<>();
		
		for (EquipeEnum equipeEnum : EquipeEnum.values()) {
			lista.add(new NomeEquipeResumo(equipeEnum));
		}
		
		return lista;
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_EQUIPE') and #oauth2.hasScope('write')")
	public ResponseEntity<Equipe> criar(@Valid @RequestBody Equipe equipe, HttpServletResponse response) {
		
		Equipe equipeSalva = this.equipeService.salvar(equipe);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, equipeSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(equipeSalva);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EQUIPE') and #oauth2.hasScope('read')")
	public ResponseEntity<Equipe> buscarPeloCodigo(@PathVariable Long codigo) {
		Equipe equipeSalva = equipeRepository.findOne(codigo);
		
		return equipeSalva != null ? ResponseEntity.ok(equipeSalva) : 
			ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_EQUIPE') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		equipeService.excluir(codigo);
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_EQUIPE') and #oauth2.hasScope('write')")
	public ResponseEntity<Equipe> atualizar(@PathVariable Long codigo, @Valid @RequestBody Equipe equipe){
		
		Equipe equipeSalva = equipeService.atualizar(codigo, equipe);		
		return ResponseEntity.ok(equipeSalva);
		
	}

}
