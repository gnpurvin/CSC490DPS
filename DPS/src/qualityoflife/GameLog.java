package qualityoflife;

import java.util.ArrayList;

public class GameLog {
	
	private ArrayList<String> log;
	
	public GameLog() {
		log = new ArrayList<String>();
	}
	
	public void PostMessage(String post) {
		log.add(post);
	}
	
	public String getLatest() {
		return log.get(log.size() - 1);
	}
	
	public void PostRoll(String user, String roll) {
		log.add(user + " " + roll);
	}
	
	public void PostMovement(String user, int x, int y) {
		log.add(user + " moved to coordinates" + x + ", " + y);
	}

}
