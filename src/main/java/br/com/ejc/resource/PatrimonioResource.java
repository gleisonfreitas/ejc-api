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

import br.com.ejc.event.RecursoCriadoEvent;
import br.com.ejc.model.EstadoConservacaoEnum;
import br.com.ejc.model.Patrimonio;
import br.com.ejc.repository.PatrimonioRepository;
import br.com.ejc.repository.filter.PatrimonioFilter;
import br.com.ejc.repository.projection.NomeEstadoConversacaoResumo;
import br.com.ejc.repository.projection.PatrimonioResumo;
import br.com.ejc.service.PatrimonioService;

@RestController
@RequestMapping("/patrimonios")
public class PatrimonioResource {
	
	@Autowired
	private PatrimonioRepository patrimonioRepository;
	
	@Autowired
	private PatrimonioService patrimonioService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PATRIMONIO') and #oauth2.hasScope('read')")
	public Page<Patrimonio> pesquisar(PatrimonioFilter filter, Pageable pageable){
		return this.patrimonioRepository.pesquisar(filter, pageable);
	}
	
	@GetMapping(params = "resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PATRIMONIO') and #oauth2.hasScope('read')")
	public Page<PatrimonioResumo> resumir(PatrimonioFilter filter, Pageable pageable){
		return patrimonioService.resumir(filter, pageable);
	}
	
	@GetMapping("/relatorios/{codigoEjc}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_COMPRA') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> gerarRelatorio(@PathVariable Long codigoEjc) throws Exception {
		
		byte[] relatorio = this.patrimonioService.gerarRelatorio(codigoEjc);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}
	
	@GetMapping("/estadosConservacao")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PATRIMONIO') and #oauth2.hasScope('read')")
	public List<NomeEstadoConversacaoResumo> estadosConservacao(){
		
		List<NomeEstadoConversacaoResumo> lista = new ArrayList<>();
		
		for (EstadoConservacaoEnum estado : EstadoConservacaoEnum.values()) {
			lista.add(new NomeEstadoConversacaoResumo(estado.name(), estado.getDescricao()));
		}
		
		return lista;
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PATRIMONIO') and #oauth2.hasScope('write')")
	public ResponseEntity<Patrimonio> criar(@Valid @RequestBody Patrimonio patrimonio, HttpServletResponse response) {
		
		Patrimonio patrimonioSalva = this.patrimonioService.salvar(patrimonio);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, patrimonioSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(patrimonioSalva);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PATRIMONIO') and #oauth2.hasScope('read')")
	public ResponseEntity<Patrimonio> buscarPeloCodigo(@PathVariable Long codigo) {
		Patrimonio patrimonioSalva = patrimonioRepository.findOne(codigo);
		
		return patrimonioSalva != null ? ResponseEntity.ok(patrimonioSalva) : 
			ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PATRIMONIO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		patrimonioRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PATRIMONIO') and #oauth2.hasScope('write')")
	public ResponseEntity<Patrimonio> atualizar(@PathVariable Long codigo, @Valid @RequestBody Patrimonio patrimonio){
		
		Patrimonio patrimonioSalva = patrimonioService.atualizar(codigo, patrimonio);		
		return ResponseEntity.ok(patrimonioSalva);
		
	}

}
