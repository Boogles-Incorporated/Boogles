package boogle;

import java.util.ArrayList;
import java.util.List;

import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;

@Script.Manifest(name = "PB Woodcutter", description = "Project Boogle :: Woodcutter")
public class PBWoodcutter extends PollingScript<ClientContext> {

	private final HumanAction humanAction = new HumanAction(ctx);
	
	private List<Task<ClientContext>> tasks = new ArrayList<Task<ClientContext>>();
	
	@Override
	public void start(){
		tasks.add(new Chop(ctx, 38616,38627));
		tasks.add(new Bonfire(ctx, 1519, 70758));
	}
	
	@Override
	public void poll() {
		for(Task<ClientContext> task : tasks){
			if(task.triggered()){
				task.execute();
				return;
			}
		}
		humanAction.perform();
	}
}
