package br.jackson.servicos;

import org.junit.Assert;
import org.junit.Test;



public class CalculadoraTest {

	@Test
	public void deveSomarDoisValores(){
		int a = 5;
		int b = 3;
		Calculadora calc = new Calculadora();
		
		int resultado = calc.somar(a, b);
		
		Assert.assertEquals(8, resultado);
		
		
	}
	
	@Test
	public void deveSubtrairDoisValores(){
		int a = 8;
		int b = 5;
		Calculadora calc = new Calculadora();
		
		int resultado = calc.subtrair(a,b);
		
		Assert.assertEquals(3, resultado);
		
	}
}
