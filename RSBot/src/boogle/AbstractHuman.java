package boogle;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.ClientContext;

public abstract class AbstractHuman  <X extends ClientContext<?>> extends ClientAccessor<X>{

	public AbstractHuman(X arg0) {
		super(arg0);
	}

}
