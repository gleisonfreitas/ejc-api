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
import br.com.ejc.model.Compra;
import br.com.ejc.model.NomeUnidadeResumo;
import br.com.ejc.model.TipoUnidadeEnum;
import br.com.ejc.repository.CompraRepository;
import br.com.ejc.repository.filter.CompraFilter;
import br.com.ejc.repository.projection.CompraResumo;
import br.com.ejc.service.CompraService;

@RestController
@RequestMapping("/compras")
public class CompraResource {
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private CompraService compraService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_COMPRA') and #oauth2.hasScope('read')")
	public Page<Compra> pesquisar(CompraFilter filter, Pageable pageable){
		return this.compraRepository.pesquisar(filter, pageable);
	}
	
	@GetMapping(params = "resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_COMPRA') and #oauth2.hasScope('read')")
	public Page<CompraResumo> resumir(CompraFilter filter, Pageable pageable){
		return compraRepository.resumir(filter, pageable);
	}
	
	@GetMapping("/relatorios/{codigoEjc}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_COMPRA') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> gerarRelatorio(@PathVariable Long codigoEjc) throws Exception {
		
		byte[] relatorio = this.compraService.gerarRelatorio(codigoEjc);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}
	
	@GetMapping("/unidades")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_COMPRA') and #oauth2.hasScope('read')")
	public List<NomeUnidadeResumo> listarNomesUnidade(){
		List<NomeUnidadeResumo> lista = new ArrayList<>();
		
		for (TipoUnidadeEnum unidade : TipoUnidadeEnum.values()) {
			lista.add(new NomeUnidadeResumo(unidade.name(), unidade.getDescricao()));
		}
		
		return lista;
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_COMPRA') and #oauth2.hasScope('write')")
	public ResponseEntity<Compra> criar(@Valid @RequestBody Compra compra, HttpServletResponse response) {
		
		Compra compraSalva = this.compraService.salvar(compra);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, compraSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(compraSalva);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_COMPRA') and #oauth2.hasScope('read')")
	public ResponseEntity<Compra> buscarPeloCodigo(@PathVariable Long codigo) {
		Compra compraSalva = compraRepository.findOne(codigo);
		
		return compraSalva != null ? ResponseEntity.ok(compraSalva) : 
			ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_COMPRA') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		compraRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_COMPRA') and #oauth2.hasScope('write')")
	public ResponseEntity<Compra> atualizar(@PathVariable Long codigo, @Valid @RequestBody Compra compra){
		
		Compra compraSalva = compraService.atualizar(codigo, compra);		
		return ResponseEntity.ok(compraSalva);
		
	}

}
