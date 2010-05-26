package platform.evt;

import sdljava.event.SDLEvent;
import sdljavax.guichan.evt.Input;

public interface ExtendedInput extends Input {

	public void	pushInput(SDLEvent event);
}
