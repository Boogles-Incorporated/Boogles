package boogle;

import static boogle.Methods.pick;
import static boogle.Methods.rand;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;


public class HumanAction extends AbstractHuman<ClientContext>{

	
	public HumanAction(ClientContext ctx) {
		super(ctx);
	}

	public void perform(){
		if(rand(0,rand(7,10)) != 0)
			return;
		
		switch(pick(.3,.1,.6)){
			case 0:
				concurrentMouse();
				camera();
			break;
			case 1:
				camera();
			break;
			case 2:
				mouse();
			break;
		}
	}

	public void mouse(){
		switch(pick(.15,.025,.8,.025)){
			case 0:
				if(ctx.camera.z()>-4200)
					for(int i=rand(10,60); i>0; --i){
						ctx.input.scroll(true);
						Condition.sleep(15);
					}
				else
					for(int i=rand(10,60); i>0; --i){
						ctx.input.scroll(false);
						Condition.sleep(15);
					}
			break;
			case 1:
				ctx.objects.viewable().shuffle().poll().click(false);
			break;
			case 2:
				ctx.input.move(rand(10,790), rand(10,590));
			break;
			case 3:
				ctx.input.move(rand(10,790), rand(10,590));
				ctx.input.click(false);
			break;
		}
	}
	
	public void camera(){
		switch(pick(.4,.6)){
			case 0:
				ctx.camera.pitch(rand(40,80));
			break;
			case 1:
				ctx.camera.angle(rand(0,360));
			break;
		}
	}
	
	public void idle(){
		
	}
	
	public void concurrentMouse(){
		(new Thread(new Runnable(){
			@Override
			public void run() {
				mouse();
			}
		})).start();
	}
	
	public void concurrentCamera(){
		(new Thread(new Runnable(){
			@Override
			public void run() {
				camera();
			}
		})).start();
	}
}
