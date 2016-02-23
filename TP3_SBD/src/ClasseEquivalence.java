import java.util.List;

public class ClasseEquivalence {
	
	private int qid1Min;
	private int qid1Max;
	private int qid2Min;
	private int qid2Max;
	private List<String> sd;
	
	//Constructeur de classe d'équivalence
	public ClasseEquivalence(int qid1Min, int qid1Max, int qid2Min, int qid2Max, List<String> sd) {
		super();
		this.qid1Min = qid1Min;
		this.qid1Max = qid1Max;
		this.qid2Min = qid2Min;
		this.qid2Max = qid2Max;
		this.sd = sd;
	}

	public ClasseEquivalence() {
		super();
	}

	public int getQid1Min() {
		return qid1Min;
	}

	public int getQid1Max() {
		return qid1Max;
	}

	public int getQid2Min() {
		return qid2Min;
	}

	public int getQid2Max() {
		return qid2Max;
	}

	public List<String> getSd() {
		return sd;
	}

	public void setQid1Min(int qid1Min) {
		this.qid1Min = qid1Min;
	}

	public void setQid1Max(int qid1Max) {
		this.qid1Max = qid1Max;
	}

	public void setQid2Min(int qid2Min) {
		this.qid2Min = qid2Min;
	}

	public void setQid2Max(int qid2Max) {
		this.qid2Max = qid2Max;
	}

	public void setSd(List<String> sd) {
		this.sd = sd;
	}
	
	
}