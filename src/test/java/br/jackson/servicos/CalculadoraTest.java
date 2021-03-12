package br.jackson.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.jackson.exceptions.NaoPodeDividirPorZeroException;



public class CalculadoraTest {

	
	private Calculadora calc;
	
	@Before
	public void setup(){
		calc = new  Calculadora();
	}
	
	@Test
	public void deveSomarDoisValores(){
		int a = 5;
		int b = 3;
	
		
		int resultado = calc.somar(a, b);
		
		Assert.assertEquals(8, resultado);
		
		
	}
	
	@Test
	public void deveSubtrairDoisValores(){
		int a = 8;
		int b = 5;		Calculadora calc = new Calculadora();
		
		int resultado = calc.subtrair(a,b);
		
		Assert.assertEquals(3, resultado);
		
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException{
		int a = 6;
		int b = 3;

		
		int resultado = calc.divide(a,b);
		
		Assert.assertEquals(2, resultado);
	}
	
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException{
		int a = 10;
		int b = 0;

		
		int resultado = calc.divide(a,b);
		
	}
}
