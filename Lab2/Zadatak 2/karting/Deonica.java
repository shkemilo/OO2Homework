package karting;

import java.util.ArrayList;
import java.util.List;

public class Deonica implements Cloneable {
	private double duzina;
	List<Specificnost> specificnosti = new ArrayList<Specificnost>();

	public Deonica(double duzina) {
		this.duzina = duzina;
	}

	public double dohvatiDuzinu() {
		return duzina;
	}

	public void dodajSpecificnost(Specificnost s) {
		specificnosti.add(s);
	}

	public void izbaciSpecificnost(int id) {
		specificnosti.removeIf(s -> s.dohvatiId() == id);
	}

	public Specificnost dohvSpecificnost(int indeks) {
		return specificnosti.get(indeks);
	}

	public int brojSpecificnosti() {
		return specificnosti.size();
	}

	@Override
	public Deonica clone() {
		Deonica temp;
		try {
			temp = (Deonica) super.clone();
		} catch (Exception e) {
			throw new Error();
		}

		temp.duzina = duzina;
		temp.specificnosti = new ArrayList<Specificnost>();
		for (Specificnost specificnost : specificnosti)
			temp.specificnosti.add(specificnost.clone());

		return temp;
	}

	@Override
	public String toString() {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(String.format("deonica(%sm)", duzina));
		for (Specificnost specificnost : specificnosti)
			sBuilder.append(specificnost.toString());

		return sBuilder.toString();
	}
}
