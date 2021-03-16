package br.jackson.servicos;


import java.util.Arrays;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import br.jackson.daos.LocacaoDAO;
import br.jackson.daos.LocacaoDAOFake;
import br.jackson.entidades.Filme;
import br.jackson.entidades.Locacao;
import br.jackson.entidades.Usuario;
import br.jackson.exceptions.FilmeSemEstoqueException;
import br.jackson.exceptions.LocadoraException;
import br.jackson.matchers.DiaSemanaMatcher;
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
		LocacaoDAO dao = Mockito.mock(LocacaoDAO.class);
		service.setLocacaoDAO(dao);
	}
		
	
	@Test
	public void deveAlugarFilme() throws Exception{
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		
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
	public void deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception{
		
		//cenario
			
				Usuario usuario = new Usuario("Usuario 1");
				List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));
				
				//acao
				service.alugarFilme(usuario, filmes);
				
				
	}
	
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException, LocadoraException{
		//cenario

		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Usuario vazio");
	
		service.alugarFilme(null, filmes);
		
	}
	
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException{
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
	
	@Test
	public void devepagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException{
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 1", 2, 4.0),
				new Filme("Filme 1", 2, 4.0));
		
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		Assert.assertThat(resultado.getValor(), CoreMatchers.is(11.0));
	}
	
	
	@Test
	public void devepagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException{
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0));
		
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		Assert.assertThat(resultado.getValor(), CoreMatchers.is(13.0));
	}
	
	@Test
	public void devepagar25PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException{
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0),
				new Filme("Filme 5", 2, 4.0));
		
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		Assert.assertThat(resultado.getValor(), CoreMatchers.is(14.0));
	}
	
	
	@Test
	public void devepagar0PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException{
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0),
				new Filme("Filme 5", 2, 4.0),new Filme("Filme 6", 2, 4.0) );
		
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		Assert.assertThat(resultado.getValor(), CoreMatchers.is(14.0));
	}
	
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException{
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));
		
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
		
		Assert.assertTrue(ehSegunda);
		Assert.assertThat(retorno.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
//		Assert.assertThat(retorno.getDataRetorno(), caiEm(Calendar.MONDAY));
	}
}
