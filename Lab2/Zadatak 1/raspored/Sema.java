package raspored;

import java.util.ArrayList;
import java.util.List;

public class Sema {
	private String naziv;
	
	private Vreme pocetak;	
	private Vreme kraj;
	
	private List<Sadrzaj> sadrzaji = new ArrayList<Sadrzaj>();
	
	public Sema(String naziv, Vreme pocetak, Vreme kraj) {
		this.naziv = naziv;
		this.pocetak = pocetak;
		this.kraj = kraj;
	}
	
	public Sema(String naziv) {
		this.naziv = naziv;
		
		try {
			pocetak = new Vreme(8, 0);
			kraj = new Vreme(22, 0);
		} catch (GVreme g) {
			throw new Error();
		}
	}
	
	public int brojSadrzaja() {
		return sadrzaji.size();
	}
	
	public double zauzetost() {
		return ((trajanjeSadrzaja() * 100.0) / trajanjeSeme());
	}
	
	public Sadrzaj dohvati(int indeks) throws GIndeks {
		if(indeks < 0 || indeks >= sadrzaji.size())
			throw new GIndeks();
		
		return sadrzaji.get(indeks);
	}
	
	public void dodaj(Sadrzaj sadrzaj) throws GDodaj {
		Vreme originalPocetak = sadrzaj.pocetak;
		final Vreme atom;
		try {
			atom = new Vreme(0, 15);
		} catch(GVreme g) {
			throw new Error();
		}
		
		Vreme pocetnoVreme = sadrzaj.pocetak;
		if(pocetnoVreme.vremeUMinutima() > kraj.vremeUMinutima())
			throw new GDodaj();
		if(pocetnoVreme.vremeUMinutima() < pocetak.vremeUMinutima())
			pocetnoVreme = pocetak;
		
		if(sadrzaji.size() == 0) {
			sadrzaj.postaviPocetak(pocetnoVreme);
			sadrzaji.add(sadrzaj);
			return;
		}
		
		for(; !pocetnoVreme.equals(kraj); pocetnoVreme = Vreme.saberi(pocetnoVreme, atom)) {
			sadrzaj.postaviPocetak(pocetnoVreme);
			
			boolean mozeDaSeDoda = true;
			for(Sadrzaj s : sadrzaji) {
				Vreme preklapanje = s.preklapaSe(sadrzaj);
				if(preklapanje != null) {
					mozeDaSeDoda = false;
					break;
				}
			}
			if(mozeDaSeDoda) break;
		}
		
		if(pocetnoVreme.equals(kraj)) {
			sadrzaj.postaviPocetak(originalPocetak);
			throw new GDodaj();
		}
		
		int pos = 0;
		for(Sadrzaj s : sadrzaji) {
			if(pocetnoVreme.vremeUMinutima() < s.dohvPocetak().vremeUMinutima())
				break;
			pos++;
		}
		
		sadrzaji.add(pos, sadrzaj);
	}
	
	@Override
	public String toString() {
		StringBuilder sBuilder = new StringBuilder(String.format("%s:%s-%s", naziv, pocetak.toString(), kraj.toString()));
		sadrzaji.forEach(s -> sBuilder.append("\n").append(s));
		
		return sBuilder.toString();
	}

	private int trajanjeSadrzaja() {
		int suma = 0;
		for(Sadrzaj sadrzaj : sadrzaji) {
			if(sadrzaj instanceof Ponavljajuci) {
				suma += trajanjePonavljajuceg((Ponavljajuci)sadrzaj);
			} else {
				suma += sadrzaj.trajanje.vremeUMinutima();
			}
		}
		
		return suma;
	}
	
	private int trajanjeSeme() {
		return kraj.vremeUMinutima() - pocetak.vremeUMinutima();
	}
	
	private int trajanjePonavljajuceg(Ponavljajuci sadrzaj) {
		Vreme temp = sadrzaj.dohvPocetak();
		int suma = 0;
		while(true) {
			suma += sadrzaj.dohvTrajanje().vremeUMinutima();
			Vreme noviKraj = Vreme.saberi(Vreme.saberi(sadrzaj.dohvPocetak(), sadrzaj.dohvPerioda()), sadrzaj.trajanje);
			if(sadrzaj.kraj.vremeUMinutima() >= noviKraj.vremeUMinutima() || noviKraj.vremeUMinutima() >= kraj.vremeUMinutima()) 
				break;
			
			sadrzaj.pomeri(sadrzaj.dohvPerioda());
		}
		
		sadrzaj.postaviPocetak(temp);
		return suma;
	}

	
}
