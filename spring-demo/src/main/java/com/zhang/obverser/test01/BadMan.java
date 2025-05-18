package com.zhang.obverser.test01;

import java.util.ArrayList;

public class BadMan implements Observable {
	private ArrayList<Observer> observers = new ArrayList<Observer>();

	@Override
	public void addObserver(Observer observer) {
		this.observers.add(observer);

	}

	@Override
	public void deleteObserver(Observer observer) {
		this.observers.remove(observer);

	}

	@Override
	public void notifyObserver(String str) {

		for (Observer observer1 : observers) {
			observer1.make(str);

		}

	}

	public void run() {
		System.out.println("run...!!!!!");
		this.notifyObserver("chase!!!");
	}

	public void play(){
		System.out.println("playing ");
		this.notifyObserver("keep stay");
	}

}
