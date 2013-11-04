package ex03.startup;

import ex03.connector.http.HttpConnector;

public class Bootstarp {
	public static void main(String[] args) {
		HttpConnector connector = new HttpConnector();
		connector.start();
	}
}
