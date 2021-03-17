package br.jackson.servicos;

import br.jackson.entidades.Usuario;

public interface EmailService {
	
	public void notificarAtraso(Usuario usuario);

}
