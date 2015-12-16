package cn.edu.cuit.monitorpc;

public class WifiClientDetail {
	private String macAdd;
	private int sigals;
	private int noise;
	private double tx;
	private double rx;
	private int isself;

	public String getMacAdd() {
		return macAdd;
	}

	public void setMacAdd(String macAdd) {
		this.macAdd = macAdd;
	}

	public int getSigals() {
		return sigals;
	}

	public void setSigals(int sigals) {
		this.sigals = sigals;
	}

	public int getNoise() {
		return noise;
	}

	public void setNoise(int noise) {
		this.noise = noise;
	}

	public double getTx() {
		return tx;
	}

	public void setTx(double tx) {
		this.tx = tx;
	}

	public double getRx() {
		return rx;
	}

	public void setRx(double rx) {
		this.rx = rx;
	}

	public int getIsself() {
		return isself;
	}

	public void setIsself(int isself) {
		this.isself = isself;
	}
}
