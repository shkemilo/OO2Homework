package pitanja;

public class Pitanje implements Cloneable {
	private static int sledeciID = 1;
	
	private int id = sledeciID++;
	private String tekst;
	private int brojPoena;
	private double tezina;
	
	public Pitanje(String tekst, int brojPoena, double tezina) {
		this.tekst = tekst;
		this.brojPoena = brojPoena;
		
		if(tezina > 10) tezina = 10;
		if(tezina < 0) tezina = 0;
		this.tezina = tezina;
	}
	
	public String dohvatiTekst() {
		return tekst;
	}
	
	public int dohvatiBrojPoena() {
		return brojPoena;
	}
	
	public double dohvatiTezinu() {
		return tezina;
	}

	@Override
	public Pitanje clone() {
		try {
			return (Pitanje)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error();
		}
	
	}

	@Override
	public String toString() {
		return String.format("Pitanje %s: %s", id, tekst);
	}
}
