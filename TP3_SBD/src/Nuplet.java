public class Nuplet {
	
	private int qid1;
	private int qid2;
	private String sd;
	
	//Constructeur de nUplet
	public Nuplet(int qid1, int qid2, String sd) {
		super();
		this.qid1 = qid1;
		this.qid2 = qid2;
		this.sd = sd;
	}

	public int getQid1() {
		return qid1;
	}

	public int getQid2() {
		return qid2;
	}

	public String getSd() {
		return sd;
	}

	public String toString(){
		String myString = this.qid1+" "+this.qid2+" "+this.sd;
		return myString;
	}
}