package boogle;

import static boogle.Methods.rand;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

public class Bonfire extends Task<ClientContext> {
	private int fireID, logID;
	private boolean isBurning;

	public Bonfire(ClientContext ctx, int logID, int fireID) {
		super(ctx);
		this.logID=logID;
		this.fireID=fireID;
		isBurning=false;
	}

	@Override
	public boolean activate() {
		if(!isBurning)
			return ctx.backpack.select().id(logID).shuffle().count()>0;
		else
			return ctx.objects.select().id(fireID).within(3.0).isEmpty();
	}

	@Override
	public void execute() {
		if(ctx.objects.select().id(fireID).within(3.0).isEmpty()){
			ctx.backpack.poll().interact("Light");
			ctx.backpack.peek().hover();
			Condition.wait(new Condition.Check() {
				@Override
				public boolean poll() {
					return !ctx.objects.select().id(fireID).within(3.0).isEmpty();
				}
			}, 500, 6);
		}
		ctx.backpack.poll().interact("Craft");
		ctx.input.move(rand(500,550), rand(285,335));
		ctx.input.click(true);
		isBurning=Condition.wait(new Condition.Check() {
			@Override
			public boolean poll() {
				return ctx.players.local().animation()==16699;
			}
		},  200, 15);
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
