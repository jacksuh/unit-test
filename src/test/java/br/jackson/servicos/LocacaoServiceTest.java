package br.jackson.servicos;


import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.jackson.entidades.Filme;
import br.jackson.entidades.Locacao;
import br.jackson.entidades.Usuario;
import br.jackson.exceptions.FilmeSemEstoqueException;
import br.jackson.exceptions.LocadoraException;
import br.jackson.servicos.LocacaoService;
import br.jackson.utils.DataUtils;

public class LocacaoServiceTest {

	private LocacaoService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup(){
		
		service = new LocacaoService();
	}
	
	@After
	public void tearDown(){
		
	}
	
	
	
	@Test
	public void testeLocacao() throws Exception{
		//cenario
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		
		error.checkThat(locacao.getValor(), CoreMatchers.is(5.0));
		error.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.not(6.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), CoreMatchers.is(true));
		
		/*
		//verificacao
		Assert.assertEquals(locacao.getValor(), 5.0, 0.01);
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		
		
		/*
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(5.0));
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.not(6.0)));
		Assert.assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		Assert.assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), CoreMatchers.is(true));
		*/
	}
	
	
	@Test(expected=FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception{
		
		//cenario
			
				Usuario usuario = new Usuario("Usuario 1");
				Filme filme = new Filme("Filme 1", 0, 5.0);
				
				//acao
				service.alugarFilme(usuario, filme);
				
				
	}
	
	@Test
	public void testLocacaoUsuarioVazio() throws FilmeSemEstoqueException, LocadoraException{
		//cenario

		Filme filme = new Filme("Filme 1", 1, 5.0);
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Usuario vazio");
	
		service.alugarFilme(null, filme);
		
	}
	
	@Test
	public void testLocacao_FilmeVazio() throws FilmeSemEstoqueException, LocadoraException{
		//cenario

		Usuario usuario = new Usuario("Usuario 1");
		
			exception.expect(LocadoraException.class);
			exception.expectMessage("Filme vazio");
			
			service.alugarFilme(usuario, null);
		
	}
	
	/*
	@Test
	public void testLocacao_filmeSemEstoque_2(){
		
		//cenario
				LocacaoService service = new LocacaoService();
				Usuario usuario = new Usuario("Usuario 1");
				Filme filme = new Filme("Filme 1", 0, 5.0);
				
				//acao
				try {
					service.alugarFilme(usuario, filme);
				} catch (Exception e) {
					Assert.assertThat(e.getMessage(), CoreMatchers.is("Filme sem estoque"));
				}
				
				
	}
	
	
	@Test
	public void testLocacao_filmeSemEstoque_3() throws Exception{
		
		//cenario
				LocacaoService service = new LocacaoService();
				Usuario usuario = new Usuario("Usuario 1");
				Filme filme = new Filme("Filme 1", 0, 5.0);
				
				exception.expect(Exception.class);
				exception.expectMessage("Filme sem Estoque");
				
				//acao
				service.alugarFilme(usuario, filme);
	}
	
*/
}
