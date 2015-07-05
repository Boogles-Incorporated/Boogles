package boogle;

import java.util.ArrayList;
import java.util.List;

import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;

@Script.Manifest(name = "PB FlyFisher", description = "Project Boogle :: FlyFisher")
public class PBFlyFisher extends PollingScript<ClientContext> {

	private final HumanAction humanAction = new HumanAction(ctx);
	
	private List<Task<ClientContext>> tasks = new ArrayList<Task<ClientContext>>();
	
	@Override
	public void start(){
		tasks.add(new FlyFish(ctx));
		tasks.add(new DropFromABar(ctx, 11330,11328,11332));
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

