package org.meruvian.yama.webapp;

import org.meruvian.yama.actions.DefaultAction;

import com.opensymphony.xwork2.ModelDriven;

public class MainAction extends DefaultAction implements ModelDriven<MainModel> {

	private static final long serialVersionUID = -4442714455678005985L;

	private MainModel schoolModel = new MainModel();

	

	@Override
	public String execute() {

		return SUCCESS;
	}

	public MainModel getModel() {
		return schoolModel;
	}


}
