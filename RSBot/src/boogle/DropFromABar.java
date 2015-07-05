package boogle;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.Action;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.IdQuery;

import static boogle.Methods.rand;

public class DropFromABar extends Task<ClientContext> {
	
	private int[] dropIDs;
	private int threshhold;
	
	//Testing how well this works
	private final HumanAction humanAction;

	public DropFromABar(ClientContext ctx, int... itemIDs) {
		super(ctx);
		dropIDs=itemIDs;
		threshhold = rand(18, 24);
		humanAction = new HumanAction(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().count() >= threshhold;
	}

	@Override
	public void execute() {
		int failCheck = 0;
		for(int i : dropIDs){
			failCheck=0;
			IdQuery<Action> action = ctx.combatBar.select().id(i);
			if(action.isEmpty())
				continue;
			action.peek().component().hover();
			if(!ctx.menu.items()[0].contains("Drop"))
				return;
			do{
				ctx.input.click(true);
				Condition.sleep(rand(40,80));
			}while(ctx.backpack.select().id(i).count()>0 && ++failCheck<28);
		}
		threshhold = rand(18, 24);
		Condition.sleep(500);
		humanAction.mouse();
		Condition.wait(new Condition.Check() {
			@Override
			public boolean poll() {
				humanAction.perform();
				return ctx.players.local().animation()!=-1;
			}
		}, 500, 10);
		
		
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
