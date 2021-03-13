package br.jackson.servicos;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.jackson.entidades.Filme;
import br.jackson.entidades.Locacao;
import br.jackson.entidades.Usuario;
import br.jackson.exceptions.FilmeSemEstoqueException;
import br.jackson.exceptions.LocadoraException;

public class CalculoValorLocacaoTest {
	
	private LocacaoService service;
	
	private List<Filme> filmes;
	
	private Double valorLocacao;
	
	@Before
	public void setup(){
		service = new LocacaoService();

	}

	
	@Test
	public void deveCalcularValorLocacao() throws FilmeSemEstoqueException, LocadoraException{
		Usuario usuario = new Usuario("Usuario 1");
		
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		Assert.assertThat(resultado.getValor(), CoreMatchers.is(valorLocacao));
	}
	

}
