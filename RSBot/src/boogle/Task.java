package boogle;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.ClientContext;


public abstract class Task<X extends ClientContext<?>> extends ClientAccessor<X> {
	
	/**
	 * Constructs a Task which is initially active
	 */
    public Task(X ctx) {
        super(ctx);
        this.active = true;
    }
    
    /**
     * Constructs a Task which can be initially inactive
     * @param ctx ClientContext
     * @param initiallyActive If False, the task must wait for the enqueue condition to become active
     */
    public Task(X ctx, boolean initiallyActive) {
        super(ctx);
        this.active = initiallyActive;
    }
    
    //Flag which blocks activation (i.e. triggered() returns False) if False
    private boolean active;
    
    /**
     * Query to determine whether task execution should occur
     * @return True if task is active and the activate condition is satisfied, else False
     */
    public boolean triggered(){
    	if(enqueue())
    		active = true;
    	else if(dequeue())
    		active = false;
    	
    	return active && activate();
    }

    /**
     * Condition under which the task should execute 
     * @return True if condition is satisfied, else False
     */
    public abstract boolean activate();
    
    /**
     * Execute instructions when the activate condition is satisfied
     */
    public abstract void execute();
    
    /**
     * Flag which allows tasks to trigger execution
     * @return True if activation should be allowed, else False
     */
    public abstract boolean enqueue();
    
    /**
     * Flag which blocks tasks to trigger execution
     * @return True if activation should be blocked, else False
     */
    public abstract boolean dequeue();
}