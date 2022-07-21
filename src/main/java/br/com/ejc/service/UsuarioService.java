package br.com.ejc.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ejc.model.Usuario;
import br.com.ejc.repository.UsuarioRepository;
import br.com.ejc.repository.usuario.RequisicaoAlteracaoSenha;


@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public Usuario atualizarSenha(RequisicaoAlteracaoSenha requisicao) {
		
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmailAndEjcAtivo(requisicao.getEmail(), true);
		Usuario usuarioSalvo = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou Email incorreto."));
		
		if(!passwordEncoder.matches(requisicao.getSenhaAtual(), usuarioSalvo.getSenha())) {
			throw new AuthenticationException("Senha atual informada incorreta.") {
				private static final long serialVersionUID = 1L;
			};
		}
		
		usuarioSalvo.setSenha(passwordEncoder.encode(requisicao.getNovaSenha()));
		
		return usuarioRepository.save(usuarioSalvo);		
	}
	
	public Usuario atualizar(Long codigo, Usuario usuario) {
		
		Usuario usuarioSalvo = usuarioRepository.findOne(codigo);
		
		if(usuarioSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(usuario, usuarioSalvo, "codigo");
		return usuarioRepository.save(usuarioSalvo);
	}
	
	public Usuario salvar(Usuario usuario) {
		
		String senhaEncode = passwordEncoder.encode(usuario.getSenha());
		
		usuario.setSenha(senhaEncode);
		
		return usuarioRepository.save(usuario);
	}
	
	public static void main(String[] args) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		System.out.println(passwordEncoder.matches("admin", "$2a$10$FNzBUMVZaN/zAccDVXbiE.ohq.6W/Ab2YypU0O5GVA/Irde6kobBK"));
	}
	
	

}
