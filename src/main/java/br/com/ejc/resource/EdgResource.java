package br.com.ejc.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ejc.event.RecursoCriadoEvent;
import br.com.ejc.model.Edg;
import br.com.ejc.model.FuncaoEdgEnum;
import br.com.ejc.repository.EdgRepository;
import br.com.ejc.repository.projection.EdgResumo;
import br.com.ejc.repository.projection.NomeFuncaoResumo;
import br.com.ejc.service.EdgService;

@RestController
@RequestMapping("/edg")
public class EdgResource {
	
	@Autowired
	private EdgRepository edgRepository;
	
	@Autowired
	private EdgService edgService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping("/{codigoEjc}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EDG') and #oauth2.hasScope('read')")
	public List<EdgResumo> pesquisarPorEjc(@PathVariable Long codigoEjc){
		return this.edgRepository.pesquisarPorEjc(codigoEjc);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_EDG') and #oauth2.hasScope('write')")
	public ResponseEntity<Edg> criar(@Valid @RequestBody Edg edg, HttpServletResponse response) {
		
		Edg edgSalva = this.edgService.salvar(edg);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, edgSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(edgSalva);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_EDG') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		edgRepository.delete(codigo);
	}
	
	@GetMapping("/funcoes")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EDG') and #oauth2.hasScope('read')")
	public List<NomeFuncaoResumo> listarNomesFuncao() {
		
		List<NomeFuncaoResumo> lista = new ArrayList<>();
		
		for (FuncaoEdgEnum funcaoEdgEnum : FuncaoEdgEnum.values()) {
			lista.add(new NomeFuncaoResumo(funcaoEdgEnum));
		}
		
		return lista;
	}

}
