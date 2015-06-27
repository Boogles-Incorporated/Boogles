package boogle;

import static boogle.Methods.rand;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

public class Chop extends Task<ClientContext> {
	public int[] treeIDs;

	public Chop(ClientContext ctx, int... ids) {
		super(ctx);
		
		treeIDs = ids;
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
		GameObject tree = ctx.objects.nearest().limit(3).shuffle().poll();
		if(tree.inViewport()){
			tree.interact("Chop");
			ctx.input.move(rand(10,790), rand(10,590));
			Condition.wait(new Condition.Check() {
				@Override
				public boolean poll() {
					return ctx.players.local().animation()!=-1 || ctx.players.local().inMotion();
				}
			}, 500, 6);
		}else{
			if(ctx.camera.z()>-3000)
				for(int i=rand(30,50); i>0; --i){
					ctx.input.scroll(true);
					Condition.sleep(8);
				}
			ctx.camera.turnTo(tree);
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
