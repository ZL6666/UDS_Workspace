package src.com.teamcenter11.demo.actions;

import java.awt.Frame;

import com.teamcenter.rac.aif.AbstractAIFApplication;
import com.teamcenter.rac.aif.AbstractAIFCommand;
import com.teamcenter.rac.aif.common.actions.AbstractAIFAction;
import com.teamcenter.rac.util.MessageBox;
import com.teamcenter11.demo.command.CreateFolderCommand;

public class CreateFolderAction extends AbstractAIFAction {

	public CreateFolderAction(AbstractAIFApplication arg0, Frame arg1) {
		super(arg0, arg1, null);
	}

	@Override
	public void run() {
		try {
			AbstractAIFCommand abstractaifcommand =  new CreateFolderCommand(parent,application);
			abstractaifcommand.executeModal();
		} catch (Exception e) {
			MessageBox.post(parent, e);
		}
	}

}
