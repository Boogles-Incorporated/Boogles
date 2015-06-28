package boogle;

import static boogle.Methods.rand;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

public class Bonfire extends Task<ClientContext> {
	//Game IDs for the log and fire
	private int fireID, logID;
	
	//Whether the player is burning logs
	private boolean isBurning;

	/**
	 * Constructs a Bonfire Task which must wait for the enqueue condition to become active
	 * @param ctx ClientContext
	 * @param logID Game ID of log
	 * @param fireID Game ID of fire
	 */
	public Bonfire(ClientContext ctx, int logID, int fireID) {
		//Initially inactive
		super(ctx, false);
		
		this.logID=logID;
		this.fireID=fireID;
		this.isBurning=false;
	}

	@Override
	public boolean activate() {
		if(!isBurning)
			return ctx.backpack.select().id(logID).count()>0 && ctx.players.local().animation()==-1;
		else
			return ctx.objects.select().id(fireID).within(3.0).isEmpty();
	}

	@Override
	public void execute() {
		//Check for nearby fires
		if(ctx.objects.select().id(fireID).within(3.0).isEmpty()){
			//No fire, light log
			ctx.backpack.shuffle().poll().interact("Light");
			//Hover to anticipate bonfire interaction
			ctx.backpack.peek().hover();
			//Wait for lighting animation
			if(Condition.wait(new Condition.Check() {
				@Override
				public boolean poll() {
					return ctx.players.local().animation()==25600;
				}
			}, 300, 6))
				//Lighting failed; end execution
				return;
		}
		
		//Craft logs
		ctx.backpack.poll().interact("Craft");
		//Move then click to "Add to bonfire" interface |TODO: use widgets instead of hard-coded position
		ctx.input.move(rand(500,550), rand(285,335));
		ctx.input.click(true);
		
		//Wait for bonfire animation to determine whether player isBurning
		isBurning=Condition.wait(new Condition.Check() {
			@Override
			public boolean poll() {
				return ctx.players.local().animation()==16699;
			}
		},  300, 6);
	}

	@Override
	public boolean enqueue() {
		return ctx.backpack.select().count()==28;
	}

	@Override
	public boolean dequeue() {
		return ctx.backpack.select().id(logID).count()==0;
	}

}
