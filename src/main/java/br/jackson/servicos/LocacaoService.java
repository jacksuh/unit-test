package br.jackson.servicos;

import static br.jackson.utils.DataUtils.adicionarDias;


import java.util.Date;

import br.jackson.entidades.Filme;
import br.jackson.entidades.Locacao;
import br.jackson.entidades.Usuario;
import br.jackson.exceptions.FilmeSemEstoqueException;


public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) throws Exception {
		if(filme.getEstoque() == 0)
			throw new FilmeSemEstoqueException();
			
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

}