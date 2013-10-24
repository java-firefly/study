package core.polymorphic;

public class SpecificAction extends BaseAction{
	SpecificBO bo = new SpecificBO();
	@Override
	public void to_list() {
		bo.list();
		super.bo.list();
	}
	public void super_list(){
		super.to_list();
	}
}
