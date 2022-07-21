package br.com.ejc.resource;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.ejc.event.RecursoCriadoEvent;
import br.com.ejc.model.Ejc;
import br.com.ejc.repository.EjcRepository;
import br.com.ejc.repository.filter.EjcFilter;
import br.com.ejc.repository.projection.EjcResumo;
import br.com.ejc.service.EjcService;

@RestController
@RequestMapping("/ejcs")
public class EjcResource {
	
	@Autowired
	private EjcRepository ejcRepository;
	
	@Autowired
	private EjcService ejcService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EJC') and #oauth2.hasScope('read')")
	public Page<Ejc> pesquisar(EjcFilter filter, Pageable pageable){
		return this.ejcRepository.pesquisar(filter, pageable);
	}
	
	@GetMapping("/ativos")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EJC') and #oauth2.hasScope('read')")
	public List<EjcResumo> listarAtivos(){
		return this.ejcRepository.listarResumoAtivos();
	}
	
	@GetMapping(params = "resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EJC') and #oauth2.hasScope('read')")
	public Page<EjcResumo> resumir(EjcFilter filter, Pageable pageable){
		return ejcRepository.resumir(filter, pageable);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_EJC') and #oauth2.hasScope('write')")
	public ResponseEntity<Ejc> criar(@Valid @RequestBody Ejc ejc, HttpServletResponse response) {
		
		Ejc ejcSalva = ejcRepository.save(ejc);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, ejcSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(ejcSalva);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EJC') and #oauth2.hasScope('read')")
	public ResponseEntity<Ejc> buscarPeloCodigo(@PathVariable Long codigo) {
		Ejc ejcSalva = ejcRepository.findOne(codigo);
		
		return ejcSalva != null ? ResponseEntity.ok(ejcSalva) : 
			ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_EJC') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		ejcRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_EJC') and #oauth2.hasScope('write')")
	public ResponseEntity<Ejc> atualizar(@PathVariable Long codigo, @Valid @RequestBody Ejc ejc){
		
		Ejc ejcSalva = ejcService.atualizar(codigo, ejc);		
		return ResponseEntity.ok(ejcSalva);
		
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_EJC') and #oauth2.hasScope('write')")
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		ejcService.atualizarPropriedadeAtivo(codigo, ativo);
	}
	
	@PostMapping("/upload/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_EJC') and #oauth2.hasScope('write')")
	public void aploadLogo(@PathVariable Long codigo, @RequestParam MultipartFile file) throws IOException {
		ejcService.atualizarLogo(codigo, file);
	}

}
