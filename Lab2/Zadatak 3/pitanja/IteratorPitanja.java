package pitanja;

public abstract class IteratorPitanja {
	protected Pitanje trenutnoPitanje = null;
	
	public boolean postoji() {
		return trenutnoPitanje != null;
	}
	
	public Pitanje dohvati() throws GNemaPitanja {
		if(trenutnoPitanje == null)
			throw new GNemaPitanja();
		
		return trenutnoPitanje;
	}
	
	public abstract void sledece() throws GNemaPitanja;
}
