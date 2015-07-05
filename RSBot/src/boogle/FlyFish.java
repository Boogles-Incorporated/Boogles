package boogle;

import static boogle.Methods.choose;
import static boogle.Methods.rand;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;


public class FlyFish extends Task<ClientContext> {
	
	private int spotID = 2722;
	private int baitID = 313;
	private int rodID = 309;
	
	//mysterious rock
	//NPC 21086
	
	
	//Testing how well this works
	private final HumanAction humanAction;

	public FlyFish(ClientContext ctx) {
		super(ctx);
		humanAction = new HumanAction(ctx);
	}

	@Override
	public boolean activate() {

		return 	!ctx.backpack.select().id(baitID).isEmpty() && 
				!ctx.backpack.select().id(rodID).isEmpty() &&
				!ctx.npcs.select().id(spotID).isEmpty() &&
				(ctx.players.local().animation() == -1 || !ctx.players.local().interacting().valid()) &&
				ctx.backpack.select().count() < 28;
	}

	@Override
	public void execute() {
		//Select one of the nearest two trees at random
		Npc spot = ctx.npcs.nearest().first().poll();

		if(spot.inViewport()){
			//Click spot
			spot.interact("Use");
			//Move mouse after click
			if(choose(.7)){ctx.input.move(rand(10,790), rand(10,590)); Condition.sleep(215);}
			//Check for animation to avoid double clicking
			Condition.wait(new Condition.Check() {
				@Override
				public boolean poll() {
					humanAction.perform();
					return ctx.players.local().animation()!=-1;
				}
			}, 500, 10);
				
		}else{
			//Turn towards spot
			ctx.camera.turnTo(spot);
			//spot still not in view; move towards it
			if(!spot.inViewport())
				ctx.movement.step(spot);
		}
	}

	@Override
	public boolean enqueue() {
		return true;
	}

	@Override
	public boolean dequeue() {
		return false;
	}

}
