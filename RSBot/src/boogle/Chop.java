package boogle;

import static boogle.Methods.rand;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

public class Chop extends Task<ClientContext> {
	//Game IDs for tree objects
	private int[] treeIDs;
	
	//Testing how well this works
	private final HumanAction humanAction;

	public Chop(ClientContext ctx, int... treeIDs) {
		super(ctx);
		//assign tree IDs
		this.treeIDs = treeIDs;
		this.humanAction =  new HumanAction(ctx);
	}

	@Override
	public boolean activate() {
		return 	ctx.backpack.select().count()<28 &&
				ctx.players.local().animation()==-1 &&
				!ctx.objects.select().id(treeIDs).isEmpty() &&
				!ctx.players.local().inMotion();
	}

	@Override
	public void execute() {
		//Check for animation to avoid double clicking
		if(Condition.wait(new Condition.Check() {
			@Override
			public boolean poll() {
				return ctx.players.local().animation()!=-1;
			}
		}, 100, 3))
			//Player is already chopping; end execution
			return;
	
		//Select one of the nearest two trees at random
		GameObject tree = ctx.objects.nearest().limit(2).shuffle().poll();
		
		//Look for tree in view
		if(tree.inViewport()){
			
			//Testing how well this works
			if(rand(0,2)==0) humanAction.concurrentCamera();
			
			//Click tree
			tree.interact("Chop");
			//Move mouse after click
			ctx.input.move(rand(10,790), rand(10,590));
		}else{
			//Check camera zoom
			if(ctx.camera.z()>-3000)
				//Zoom out |TODO: Make concurrent
				for(int i=rand(30,50); i>0; --i){
					ctx.input.scroll(true);
					Condition.sleep(15);
				}
			
			//Testing how well this works
			if(rand(0,2)==0) humanAction.concurrentMouse();
			
			//Turn towards tree
			ctx.camera.turnTo(tree);
			//Tree still not in view; move towards it
			if(!tree.inViewport())
				ctx.movement.step(tree);
		}
	}

	@Override
	public boolean enqueue() {
		return ctx.backpack.select().count()==0;
	}

	@Override
	public boolean dequeue() {
		return ctx.backpack.select().count()==28;
	}

}
