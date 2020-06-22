package OO2Lab1;

public class Number {

	private String countryCode;
	private int positiveNumber;
	private String telephoneNumber;
	private boolean isLandline;

	public Number(String countryCode, int positiveNumber, String telephoneNumber, boolean isLandline) {
		if (positiveNumber <= 0)
			System.exit(1);

		this.countryCode = countryCode;
		this.positiveNumber = positiveNumber;
		this.telephoneNumber = telephoneNumber;
		this.isLandline = isLandline;
	}

	public boolean isInSameCountry(Number toCheck) {
		return countryCode.compareTo(toCheck.countryCode) == 0;
	}

	public boolean isLandline() {
		return isLandline;
	}

	@Override
	public String toString() {
		return String.format("+%s %d %s", countryCode, positiveNumber, telephoneNumber);
	}

}
