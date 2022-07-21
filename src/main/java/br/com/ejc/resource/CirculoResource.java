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
import br.com.ejc.dto.CirculoEstatisticaPorPessoa;
import br.com.ejc.event.RecursoCriadoEvent;
import br.com.ejc.model.Circulo;
import br.com.ejc.model.CorEnum;
import br.com.ejc.repository.CirculoRepository;
import br.com.ejc.repository.filter.CirculoFilter;
import br.com.ejc.repository.projection.CirculoResumo;
import br.com.ejc.repository.projection.NomeCorResumo;
import br.com.ejc.service.CirculoService;

@RestController
@RequestMapping("/circulos")
public class CirculoResource {
	
	@Autowired
	private CirculoRepository circuloRepository;
	
	@Autowired
	private CirculoService circuloService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping("/estatistica/por-circulo/{codigoEjc}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CIRCULO') and #oauth2.hasScope('read')")
	public List<CirculoEstatisticaPorPessoa> porPessoa(@PathVariable Long codigoEjc) {
		return circuloService.porPessoa(codigoEjc);
	}
	
	@GetMapping("/agenda")
	@PreAuthorize("hasAuthority('ROLE_AGENDA_CIRCULO') and #oauth2.hasScope('read')")
	public List<Agenda> pesquisarAgenda(CirculoFilter filter){
		return circuloService.pesquisarAgenda(filter);
	}
	
	@GetMapping("/relatorios/agenda")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CIRCULO') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> consultarAgenda(CirculoFilter filter) throws Exception {
		
		byte[] relatorio = this.circuloService.relatorioAgenda(filter);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}
	
	@GetMapping("/relatorios/camisas_por-circulo/{codigoEjc}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CIRCULO') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> relatorioCamisasPorCirculo(@PathVariable Long codigoEjc) throws Exception {
		
		byte[] relatorio = this.circuloService.relatorioCamisasPorCirculo(codigoEjc);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}
	
	@GetMapping("/relatorios/aniversariantes/{codigoEjc}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CIRCULO') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> relatorioAniversariantes(@PathVariable Long codigoEjc) throws Exception {
		
		byte[] relatorio = this.circuloService.relatorioAniversariantes(codigoEjc);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}
	
	@GetMapping("/relatorios/etiquetas_sacolas/{codigoEjc}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CIRCULO') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> relatorioEtiquetasSacolas(@PathVariable Long codigoEjc) throws Exception {
		
		byte[] relatorio = this.circuloService.relatorioEtiquetas(codigoEjc);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}
	
	@GetMapping("/relatorios/etiquetas_crachas/{codigoEjc}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CIRCULO') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> relatorioEtiquetasCrachas(@PathVariable Long codigoEjc) throws Exception {
		
		byte[] relatorio = this.circuloService.relatorioEtiquetasCrachas(codigoEjc);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CIRCULO') and #oauth2.hasScope('read')")
	public Page<Circulo> pesquisar(CirculoFilter filter, Pageable pageable){
		return this.circuloRepository.pesquisar(filter, pageable);
	}
	
	@GetMapping("cores")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CIRCULO') and #oauth2.hasScope('read')")
	public List<NomeCorResumo> listarCores(){
		
		List<NomeCorResumo> lista = new ArrayList<>();
		
		for (CorEnum corEnum : CorEnum.values()) {
			lista.add(new NomeCorResumo(corEnum));
		}
		
		return lista;
	}
	
	@GetMapping(params = "resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CIRCULO') and #oauth2.hasScope('read')")
	public Page<CirculoResumo> resumir(CirculoFilter filter, Pageable pageable){
		return circuloRepository.resumir(filter, pageable);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CIRCULO') and #oauth2.hasScope('write')")
	public ResponseEntity<Circulo> criar(@Valid @RequestBody Circulo circulo, HttpServletResponse response) {
		
		Circulo circuloSalva = this.circuloService.salvar(circulo);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, circuloSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(circuloSalva);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CIRCULO') and #oauth2.hasScope('read')")
	public ResponseEntity<Circulo> buscarPeloCodigo(@PathVariable Long codigo) {
		Circulo circuloSalva = circuloRepository.findOne(codigo);
		
		return circuloSalva != null ? ResponseEntity.ok(circuloSalva) : 
			ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_CIRCULO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		circuloService.excluir(codigo);
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CIRCULO') and #oauth2.hasScope('write')")
	public ResponseEntity<Circulo> atualizar(@PathVariable Long codigo, @Valid @RequestBody Circulo circulo){
		
		Circulo circuloSalva = circuloService.atualizar(codigo, circulo);		
		return ResponseEntity.ok(circuloSalva);
		
	}

}
