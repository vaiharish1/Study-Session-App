
public class Session {
	private int startTime;
	private int endTime;
	
	private int nextStartTime;
	
	public Session(int st, int et) {
		startTime = st;
		endTime = et;

		nextStartTime = endTime + 10;
	}
	
	public int getStartTime() {
		return startTime;
	}
	
	public int getEndTime() {
		return endTime;
	}

	public int getNextStartTime() { return nextStartTime; }
}
