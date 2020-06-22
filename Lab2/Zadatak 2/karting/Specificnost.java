package karting;

public abstract class Specificnost {

	private static int sledeciID = 0;

	private int id = sledeciID++;

	public int dohvatiId() {
		return id;
	}

	public abstract void ispoljiEfekat(Object obj) throws GNeodgovarajuciObjekat;

	public abstract void ponistiEfekat(Object obj) throws GNeodgovarajuciObjekat;

	@Override
	protected Specificnost clone() {
		Specificnost temp;
		try {
			temp = (Specificnost) super.clone();

		} catch (Exception e) {
			throw new Error();
		}
		temp.id = sledeciID++;

		return temp;
	}
}
