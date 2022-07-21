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

import br.com.ejc.dto.LancamentoResumoTipo;
import br.com.ejc.dto.LancamentoResumoTipoPorDia;
import br.com.ejc.event.RecursoCriadoEvent;
import br.com.ejc.model.Lancamento;
import br.com.ejc.model.TipoLancamentoEnum;
import br.com.ejc.repository.LancamentoRepository;
import br.com.ejc.repository.filter.LancamentoFilter;
import br.com.ejc.repository.projection.LancamentoResumo;
import br.com.ejc.repository.projection.NomeTipoLancamentoResumo;
import br.com.ejc.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<Lancamento> pesquisar(LancamentoFilter filter, Pageable pageable){
		return this.lancamentoRepository.pesquisar(filter, pageable);
	}
	
	@GetMapping(params = "resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<LancamentoResumo> resumir(LancamentoFilter filter, Pageable pageable){
		return lancamentoRepository.resumir(filter, pageable);
	}
	
	@GetMapping("/relatorios/estatistica_lancamento_resumo_dia")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public List<LancamentoResumoTipoPorDia> pesquisarResumoTipoPorDia(LancamentoFilter lancamentoFilter) {
		return lancamentoService.pesquisarResumoTipoPorDia(lancamentoFilter);
	}
	
	@GetMapping("/relatorios/estatistica_lancamento_resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public List<LancamentoResumoTipo> pesquisarResumoTipo(LancamentoFilter lancamentoFilter) {
		return lancamentoService.pesquisarResumoTipo(lancamentoFilter);
	}
	
	@GetMapping("/relatorios/estatistica_lancamento/{codigoEjc}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> relatorioLancamentoEstatistica(@PathVariable Long codigoEjc) throws Exception {
		
		byte[] relatorio = this.lancamentoService.lancamentoEstatistica(codigoEjc);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}
	
	@GetMapping("/tipos")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public List<NomeTipoLancamentoResumo> listarTipos(){
		
		List<NomeTipoLancamentoResumo> lista = new ArrayList<>();
		
		for (TipoLancamentoEnum tipoLancamentoEnum : TipoLancamentoEnum.values()) {
			lista.add(new NomeTipoLancamentoResumo(tipoLancamentoEnum));
		}
		
		return lista;
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		
		Lancamento lancamentoSalva = this.lancamentoService.salvar(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
		Lancamento lancamentoSalva = lancamentoRepository.findOne(codigo);
		
		return lancamentoSalva != null ? ResponseEntity.ok(lancamentoSalva) : 
			ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		lancamentoRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> atualizar(@PathVariable Long codigo, @Valid @RequestBody Lancamento lancamento){
		
		Lancamento lancamentoSalva = lancamentoService.atualizar(codigo, lancamento);		
		return ResponseEntity.ok(lancamentoSalva);
		
	}

}
