package OO2Lab1;

public abstract class Service {

	protected Number serviceStarter;
	protected Number serviceTarget;

	public Service(Number serviceStarter, Number serviceTarget) {
		this.serviceStarter = serviceStarter;
		this.serviceTarget = serviceTarget;
	}

	public abstract int Cost();

	@Override
	public String toString() {
		return String.format("%s -> %s", serviceStarter.toString(), serviceTarget.toString());
	}

}
