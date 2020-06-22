package pitanja;

import java.util.ArrayList;
import java.util.List;

public class ZbirkaPitanja {
	List<Pitanje> pitanja = new ArrayList<Pitanje>();
	
	public void dodaj(Pitanje pitanje) {
		pitanja.add(pitanje);
	}
	
	public Pitanje dohvati(int indeks) throws GNemaPitanja {
		if(indeks >= pitanja.size())
			throw new GNemaPitanja();
		
		return pitanja.get(indeks);
	}
	
	public int dohvatiBrojPitanja() {
		return pitanja.size();
	}
	
	public IteratorPitanja iterator() {
		return new IteratorZbirke();
	}
	
	@Override
	public String toString() {
		StringBuilder sBuilder = new StringBuilder();
		for(Pitanje p : pitanja)
			sBuilder.append(p).append("/n");
		
		return sBuilder.toString();
	}

	private class IteratorZbirke extends IteratorPitanja {
		int pos = 0;
		
		public IteratorZbirke() {
			trenutnoPitanje = pitanja.get(0);
		}

		@Override
		public void sledece() throws GNemaPitanja {
			if(trenutnoPitanje == null) 
				throw new GNemaPitanja();
			
			if(pos == pitanja.size() - 1) {
				pos = -1;
				trenutnoPitanje = null;
				return;
			}
			
			trenutnoPitanje = pitanja.get(++pos);
		}
		
		
	}
}
