package boogle;

import static boogle.Methods.*;


import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;


public class HumanAction extends AbstractHuman<ClientContext>{

	
	public HumanAction(ClientContext ctx) {
		super(ctx);
	}

	public void perform(){
		if(pick(.12,.88)==1){
			idle();
			return;
		}
		
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
		switch(pick(.025,.025,.925,.025)){
			case 0:
				ctx.npcs.viewable().shuffle().poll().click(false);
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
		switch(pick(.15,.3,.1,.05,.05,.3,.05)){
			case 0:
				cameraPitch();
			break;
			case 1:
				cameraAngle();
			break;
			case 2:
				cameraZoom();
			break;
			case 3:
				concurrentZoom();
				cameraAngle();
			break;
			case 4:
				concurrentZoom();
				cameraPitch();
			break;
			case 5:
				concurrentPitch();
				cameraAngle();
			break;
			case 6:
				concurrentPitch();
				concurrentZoom();
				cameraAngle();
			break;
		}
	}
	
	public void cameraPitch(){
		ctx.camera.pitch(rand(40,80));
	}
	
	public void cameraAngle(){
		switch(pick(.9,.1)){
			case 0:
				ctx.camera.angle(rand(0,360));
			break;
			case 1:
				if(choose(.5))
					for(int i=rand(2,4); i>0; --i){
						ctx.camera.angle(ctx.camera.yaw()+rand(120,270));
						Condition.sleep(rand(100,800));
					}
				else
					for(int i=rand(2,4); i>0; --i){
						ctx.camera.angle(ctx.camera.yaw()-rand(120,270));
						Condition.sleep(rand(100,800));
					}
			break;
		}
	}
	
	public void cameraZoom(){
		int delay=rand(10,20);
		if(ctx.camera.z()>-9000)
			for(int i=rand(10,60); i>0; --i){
				ctx.input.scroll(true);
				Condition.sleep(delay);
			}
		else
			for(int i=rand(10,60); i>0; --i){
				ctx.input.scroll(false);
				Condition.sleep(delay);
			}
	}
	
	public void idle(){
		//do nothing
		if(pick(.995,.005)==0)
			return;
		
		switch(pick(.6,.4)){
			case 0:
				Condition.sleep(rand(6000,9000));
			break;
			case 1:
				Condition.sleep(rand(14000,20000));
				ctx.input.hop(rand(10,790), rand(10,590));
			break;
		}
	}
	
	public void concurrentAngle(){
		(new Thread(new Runnable(){
			@Override
			public void run() {
				cameraAngle();
			}
		})).start();
	}
	
	public void concurrentPitch(){
		(new Thread(new Runnable(){
			@Override
			public void run() {
				cameraPitch();
			}
		})).start();
	}
	
	public void concurrentZoom(){
		(new Thread(new Runnable(){
			@Override
			public void run() {
				cameraZoom();
			}
		})).start();
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
