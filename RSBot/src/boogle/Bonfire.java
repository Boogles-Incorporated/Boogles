package boogle;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

public class Bonfire extends Task<ClientContext> {
	//Game IDs for the log and fire
	private int fireID, logID;
	
	//Whether the player is burning logs
	private boolean isBurning;
	
	//Testing how well this works
	private final HumanAction humanAction;

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
		this.humanAction =  new HumanAction(ctx);
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
			ctx.backpack.limit(8).shuffle().poll().interact("Light");
			//Hover to anticipate bonfire
			ctx.backpack.peek().hover();
			//Wait for lighting animation
			if(Condition.wait(new Condition.Check() {
				@Override
				public boolean poll() {
					return ctx.players.local().animation()==25600;
				}
			}, 300, 6)){
				Condition.wait(new Condition.Check() {
					private int polls=0;
					@Override
					public boolean poll() {
						if(this.polls++>4) humanAction.perform();
						return !ctx.objects.select().id(fireID).within(3.0).isEmpty();
					}
				}, 300, 30);
				
			}else
				//Lighting failed; end execution
				return;
		}
		
		//Craft logs
		ctx.backpack.poll().interact("Craft");
		
		//Wait for "Add to bonfire" interface 
		Condition.wait(new Condition.Check() {
			@Override
			public boolean poll() {
				return ctx.widgets.widget(1179).component(40).visible();
			}
		}, 300, 6);
		
		//Add to bonfire
		ctx.widgets.widget(1179).component(40).interact("");
				
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
		if(ctx.backpack.select().id(logID).count()==0){
			isBurning=false;
			return true;
		}	
		return false;
	}

}
