package br.com.ejc.resource;

import java.io.IOException;

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
import br.com.ejc.model.Igreja;
import br.com.ejc.repository.IgrejaRepository;
import br.com.ejc.repository.filter.IgrejaFilter;
import br.com.ejc.service.IgrejaService;

@RestController
@RequestMapping("/igrejas")
public class IgrejaResource {
	
	@Autowired
	private IgrejaRepository igrejaRepository;
	
	@Autowired
	private IgrejaService igrejaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_IGREJA') and #oauth2.hasScope('read')")
	public Page<Igreja> pesquisar(IgrejaFilter filter, Pageable pageable){
		return this.igrejaRepository.pesquisar(filter, pageable);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_IGREJA') and #oauth2.hasScope('write')")
	public ResponseEntity<Igreja> criar(@Valid @RequestBody Igreja igreja, HttpServletResponse response) {
		
		Igreja igrejaSalva = this.igrejaRepository.save(igreja);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, igrejaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(igrejaSalva);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_IGREJA') and #oauth2.hasScope('read')")
	public ResponseEntity<Igreja> buscarPeloCodigo(@PathVariable Long codigo) {
		Igreja igrejaSalva = igrejaRepository.findOne(codigo);
		
		return igrejaSalva != null ? ResponseEntity.ok(igrejaSalva) : 
			ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_IGREJA') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		igrejaRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_IGREJA') and #oauth2.hasScope('write')")
	public ResponseEntity<Igreja> atualizar(@PathVariable Long codigo, @Valid @RequestBody Igreja igreja){
		
		Igreja igrejaSalva = igrejaService.atualizar(codigo, igreja);		
		return ResponseEntity.ok(igrejaSalva);
		
	}
	
	@PostMapping("/upload/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_IGREJA') and #oauth2.hasScope('write')")
	public void aploadLogo(@PathVariable Long codigo, @RequestParam MultipartFile file) throws IOException {
		igrejaService.atualizarLogo(codigo, file);
	}

}
