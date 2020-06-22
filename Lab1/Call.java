package OO2Lab1;

public class Call extends Service {

	private static final int domesticStartCost = 0;
	private static final int domesticMinuteCost = 10;
	private static final int internationalStartCost = 30;
	private static final int internationalMinuteCost = 50;

	private int duration;

	public Call(Number serviceStarter, Number serviceTarget, int duration) {
		super(serviceStarter, serviceTarget);

		if (duration < 0)
			System.exit(0);
		this.duration = duration;
	}

	@Override
	public int Cost() {
		if (duration == 0)
			return 0;

		if (serviceStarter.isInSameCountry(serviceTarget))
			return domesticStartCost + (duration / 60) * domesticMinuteCost;
		else
			return internationalStartCost + (duration / 60) * internationalMinuteCost;
	}

	@Override
	public String toString() {
		return super.toString() + String.format("(%d:%02d)", duration / 60, duration % 60);
	}

}
