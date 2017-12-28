package others.unde.listeners;

public class Test {
	 public static void main(String[] args) {
	        Initiater initiater = new Initiater();
	        Responder responder = new Responder();

	        initiater.addListener(responder);

	        initiater.sayHello();  // Prints "Hello!!!" and "Hello there..."
	    }
}
