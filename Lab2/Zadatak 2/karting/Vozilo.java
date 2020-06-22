package karting;

public class Vozilo {
	private String ime;
	
	private double maksBrzina;
	private double ubrzanje;
	private double upravljivost;
	
	private double trenBrzina = 0;
	
	public Vozilo(double maksBrzina, double ubrzanje, double upravljivost, String ime) {
		if(upravljivost < 0) upravljivost = 0;
		if(upravljivost > 1) upravljivost = 1;
		
		this.maksBrzina = maksBrzina;
		this.ubrzanje = ubrzanje;
		this.upravljivost = upravljivost;
		this.ime = ime;
	}

	public String dohvIme() {
		return ime;
	}

	public void postIme(String ime) {
		this.ime = ime;
	}

	public double dohvMaksBrzinu() {
		return maksBrzina;
	}

	public void postMaksBrzinu(double maksBrzina) {
		this.maksBrzina = maksBrzina;
		if(trenBrzina > maksBrzina) trenBrzina = maksBrzina;
	}

	public double dohvUbrzanje() {
		return ubrzanje;
	}

	public void postUbrzanje(double ubrzanje) {
		this.ubrzanje = ubrzanje;
	}

	public double dohvUpravljivost() {
		return upravljivost;
	}

	public void postUpravljivost(double upravljivost) {
		if(upravljivost < 0) upravljivost = 0;
		if(upravljivost > 1) upravljivost = 1;
		this.upravljivost = upravljivost;
	}

	public double dohvTrenBrzinu() {
		return trenBrzina;
	}

	public void postTrenBrzinu(double trenBrzina) {
		if(trenBrzina >= maksBrzina) 
			this.trenBrzina = maksBrzina;
		else 
			this.trenBrzina = trenBrzina;
	}

	public double pomeriVozilo(double t) {
		double tDoMaks = (maksBrzina - trenBrzina) / ubrzanje;
		double put;
		
		if(t <= tDoMaks) {
			put = izracunajRavnomernoUbrzaniPut(t);
			azurirajBrzinu(t);
		} else {
			put = izracunajRavnomernoUbrzaniPut(tDoMaks) + maksBrzina * (t - tDoMaks);
			trenBrzina = maksBrzina;
		}
		
		return put;
	}
	
	public double izracunajVreme(double put) {
		double tDoMaks = (maksBrzina - trenBrzina) / ubrzanje;
		double put1 = izracunajRavnomernoUbrzaniPut(tDoMaks);
		if(put1 < put) {
			return tDoMaks + (put - put1) / maksBrzina; 
		} else {
			return ( Math.sqrt(trenBrzina * trenBrzina + 2 * ubrzanje * put) - trenBrzina ) / ubrzanje;
		}
	}
	
	@Override
	public String toString() {
		return String.format("%s[%s, %s, %s]", ime, maksBrzina, ubrzanje, upravljivost);
	}

	private double izracunajRavnomernoUbrzaniPut(double t) {
		return trenBrzina * t + ubrzanje * t * t * 1/2;
	}
	
	private void azurirajBrzinu(double t) {
		trenBrzina = trenBrzina + ubrzanje * t;
	}
}
