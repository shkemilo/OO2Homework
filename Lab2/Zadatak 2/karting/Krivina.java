package karting;

import java.util.HashMap;

public class Krivina extends Specificnost implements Cloneable {
	private double maksBrzina;

	private HashMap<Vozilo, Double> brzineVozila = new HashMap<Vozilo, Double>(); // malo overkill al ok

	public Krivina(double maksBrzina) {
		this.maksBrzina = maksBrzina;
	}

	public double dohvatiMaksBrzinu() {
		return maksBrzina;
	}

	@Override
	public void ispoljiEfekat(Object obj) throws GNeodgovarajuciObjekat {
		if (!(obj instanceof Vozilo))
			throw new GNeodgovarajuciObjekat();

		Vozilo vozilo = (Vozilo) obj;
		double novaBrzina = maksBrzina * vozilo.dohvUpravljivost();
		if (vozilo.dohvMaksBrzinu() > novaBrzina) {
			brzineVozila.put(vozilo, new Double(vozilo.dohvMaksBrzinu()));
			vozilo.postMaksBrzinu(novaBrzina);
		}

	}

	@Override
	public Krivina clone() {
		Krivina temp = (Krivina) super.clone();
		temp.brzineVozila = new HashMap<Vozilo, Double>(brzineVozila);
		return temp;
	}

	@Override
	public void ponistiEfekat(Object obj) throws GNeodgovarajuciObjekat {
		if (!(obj instanceof Vozilo))
			throw new GNeodgovarajuciObjekat();

		Vozilo vozilo = (Vozilo) obj;
		Double staraBrzina = brzineVozila.get(vozilo);
		if (staraBrzina == null)
			return;

		vozilo.postMaksBrzinu(staraBrzina.doubleValue());
		brzineVozila.remove(vozilo);
	}

	@Override
	public String toString() {
		return String.format("K%s", maksBrzina);
	}
}
