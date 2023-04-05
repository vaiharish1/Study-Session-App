import java.util.Date;

public class ProjectTask extends Task {
	private Date dueDate;
	
	public ProjectTask(String n, int d, int eT, Date dd) {
		super(n, d, eT);
		dueDate = dd;
	}
	
	public Date getDate() {
		return dueDate;
	}
	
	public void setDate(Date dd) {
		dueDate = dd;
	}
}
