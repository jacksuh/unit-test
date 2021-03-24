package br.jackson.servicos;

import br.jackson.entidades.Usuario;

public interface SPCService {

	public boolean possuiNegativacao(Usuario usuario) throws Exception;
}
