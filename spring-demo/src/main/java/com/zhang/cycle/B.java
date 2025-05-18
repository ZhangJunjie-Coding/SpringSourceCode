package com.zhang.cycle;

public class B {
	private A a;

	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}

	@Override
	public String toString() {
		return "B"+this.hashCode()+"{" +
				"a=" + a.hashCode() +
				'}'+ super.toString();
	}
}
