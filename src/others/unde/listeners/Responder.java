package others.unde.listeners;

public class Responder implements HelloListener{
	@Override
    public void someoneSaidHello() {
        System.out.println("Hello there...");
    }
}
