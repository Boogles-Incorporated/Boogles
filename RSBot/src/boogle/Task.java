package boogle;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.ClientContext;

@SuppressWarnings("rawtypes")
public abstract class Task<X extends ClientContext> extends ClientAccessor<X> {
    public Task(X ctx) {
        super(ctx);
    }
    
    private boolean active = true;
    
    public boolean triggered(){
    	if(enqueue())
    		active = true;
    	else if(dequeue())
    		active = false;
    	
    	return active && activate();
    }
    
    public abstract boolean activate();
    public abstract void execute();
    public abstract boolean enqueue();
    public abstract boolean dequeue();
}