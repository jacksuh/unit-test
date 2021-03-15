package br.jackson.matchers;




import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DiaSemanaMatcher extends TypeSafeMatcher<Date> {

	private Integer diaSemana;
	
	public DiaSemanaMatcher(Integer diaSemana){
		this.diaSemana = diaSemana;
	}
	
	public void describeTo(Description description) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean matchesSafely(Date item) {
		// TODO Auto-generated method stub
		return false;
	}



}
