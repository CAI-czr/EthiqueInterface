package etu.upmc.ethique;
import etu.upmc.ethique.model.component.Generator;

public class TestGenerator {
	public static void main(String[] args) {
		Generator g2 = new Generator("./data/testsw.lp");
		g2.simulation();
	}
	
}

