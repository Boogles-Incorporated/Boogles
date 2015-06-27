package boogle;

import static boogle.Methods.*;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;


public class AI extends Task<ClientContext> {
	public AI(ClientContext ctx) {
		super(ctx);
	}
	
	@Override
	public void execute() {
		if(rand(0,rand(7,10)) != 0)
			return;
		
		try{switch(pick(.3,.2,.5)){
			case 0:
				(new Thread(new cameraPattern())).start();
				(new Thread(new mousePattern())).start();
			break;
			case 1: 
				(new Thread(new cameraPattern())).start(); 
			break;
			case 2: 
				(new Thread(new mousePattern())).start(); 
			break;
		}}catch(Exception e) {}
		
	}
	
public class mousePattern implements Runnable{
		@Override
		public void run() {
			try{
			switch(pick(.15,.025,.8,.025)){
				case 0:
					if(rand(0,2)==1)
						for(int i=rand(10,60); i>0; --i){
							ctx.input.scroll(true);
							Condition.sleep(12);
						}
					else
						for(int i=rand(10,60); i>0; --i){
							ctx.input.scroll(false);
							Condition.sleep(12);
						}
				break;
				case 1:
					ctx.objects.viewable().poll().click(false);
				break;
				case 2:
					ctx.input.move(rand(10,790), rand(10,590));
				break;
				case 3:
					ctx.input.move(rand(10,790), rand(10,590));
					ctx.input.click(false);
				break;
			}
			}catch(Exception e) {}
			return;
		}
	}
	public class cameraPattern implements Runnable{
		@Override
		public void run() {
			try{
			switch(pick(.4,.6)){
				case 0:
					ctx.camera.pitch(rand(40,80));
				break;
				case 1:
					ctx.camera.angle(rand(0,360));
				break;
			}}catch(Exception e) {}
			return;
		} 
	}
	
	@Override
	public boolean enqueue() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean dequeue() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean activate() {
		// TODO Auto-generated method stub
		return false;
	}

}
