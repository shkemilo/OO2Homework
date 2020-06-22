package raspored;

public class Ponavljajuci extends Sadrzaj {
	private Vreme perioda;

	public Ponavljajuci(String naziv, Vreme trajanje, Vreme perioda) {
		super(naziv, trajanje);
		this.perioda = perioda;
	}

	public Vreme dohvPerioda() {
		return perioda;
	}

	@Override
	public Vreme preklapaSe(Sadrzaj sadrzaj) {
		if (sadrzaj instanceof Ponavljajuci) {
			return nadjiPreklapanje((Ponavljajuci) sadrzaj);
		} else {
			return nadjiPreklapanje(sadrzaj);
		}
	}

	private Vreme nadjiPreklapanje(Sadrzaj sadrzaj) {
		Vreme temp = pocetak;
		Vreme tempPreklapanje = null;

		while (true) {
			tempPreklapanje = super.preklapaSe(sadrzaj);
			if (tempPreklapanje == null) {
				if (kraj.vremeUMinutima() > Vreme.saberi(Vreme.saberi(pocetak, perioda), trajanje).vremeUMinutima())
					break;
				pomeri(perioda);
			} else {
				break;
			}
		}

		postaviPocetak(temp);
		return tempPreklapanje;
	}

	private Vreme nadjiPreklapanje(Ponavljajuci sadrzaj) {
		Vreme temp1 = pocetak;
		Vreme tempPreklapanje = null;

		while (true) {
			tempPreklapanje = sadrzaj.nadjiPreklapanje((Sadrzaj) this);
			if (tempPreklapanje != null)
				break;

			if (kraj.vremeUMinutima() > Vreme.saberi(Vreme.saberi(pocetak, perioda), trajanje).vremeUMinutima())
				break;
			pomeri(perioda);
		}

		postaviPocetak(temp1);
		return tempPreklapanje;
	}

	@Override
	public char oznaka() {
		return 'P';
	}

	@Override
	public String toString() {
		return super.toString() + "T" + perioda;
	}
}
