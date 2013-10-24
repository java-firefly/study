package core.polymorphic;

public class TestPolymorphic {
	public static void main(String[] args) {
		SpecificAction action = new SpecificAction();
		action.to_list();
		action.super_list();
	}
}
