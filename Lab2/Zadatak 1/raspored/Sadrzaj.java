package raspored;

public abstract class Sadrzaj {
	private String naziv;

	protected Vreme kraj;
	protected Vreme pocetak = new Vreme();
	protected Vreme trajanje;
	
	public Sadrzaj(String naziv, Vreme trajanje) {
		this.naziv = naziv;
		this.trajanje = trajanje;
		kraj = Vreme.saberi(pocetak, trajanje);
	}
	
	public String dohvNaziv() {
		return naziv;
	}
	
	public Vreme dohvTrajanje() {
		return trajanje;
	}
	
	public Vreme dohvPocetak() {
		return pocetak;
	}
	
	public void postaviPocetak(Vreme pocetak) {
		this.pocetak = pocetak;
		kraj = Vreme.saberi(pocetak, trajanje);
	}
	
	public void pomeri(Vreme pomeraj) {
		pocetak = Vreme.saberi(pocetak, pomeraj);
		kraj = Vreme.saberi(pocetak, trajanje);
	}
	
	public Vreme preklapaSe(Sadrzaj sadrzaj) {
		int pocetak1 = pocetak.vremeUMinutima(); 
		int kraj1 = kraj.vremeUMinutima();
		int pocetak2 = sadrzaj.pocetak.vremeUMinutima();
		int kraj2 = sadrzaj.kraj.vremeUMinutima();
		
		if(pocetak1 == pocetak2 && kraj1 == kraj2)
			return pocetak;
		
		if(kraj1 <= pocetak2 || pocetak1 >= kraj2)
			return null;
		
		if(uIntervalu(pocetak1, kraj1, pocetak2))
			return sadrzaj.pocetak;
		
		return pocetak;
	}
	
	@Override
	public String toString() {
		return String.format("%c, %s|%s-%s", oznaka(), naziv, pocetak.toString(), kraj.toString());
	}

	public abstract char oznaka();
	
	private static boolean uIntervalu(int pocetak, int kraj, int broj) {
		return broj >= pocetak && broj <= kraj;
	}
}
