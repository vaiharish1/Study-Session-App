
public class RepetitiveTask extends Task {
	private String howOften;
	
	public RepetitiveTask(String n, int d, int eT, String ho) {
		super(n, d, eT);
		howOften = ho;
	}
	
	public String getHowOften() {
		return howOften;
	}
	
	public void setHowOften(String ho) {
		howOften = ho;
	}
}
