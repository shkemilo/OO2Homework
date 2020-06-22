package pitanja;

public class PitanjeSaPonudjenimOdgovorima extends Pitanje {
	private String[] ponudjeniOdgovori;
	
	public PitanjeSaPonudjenimOdgovorima(String tekst, int brojPoena, double tezina, String[] ponudjeniOdgovori) {
		super(tekst, brojPoena, tezina);
		this.ponudjeniOdgovori = ponudjeniOdgovori;
	}

	@Override
	public Pitanje clone() {
		PitanjeSaPonudjenimOdgovorima temp = (PitanjeSaPonudjenimOdgovorima)super.clone();
		
		temp.ponudjeniOdgovori = new String[temp.ponudjeniOdgovori.length];
		int i = 0;
		for(String s : ponudjeniOdgovori) {
			temp.ponudjeniOdgovori[i++] = new String(s);
		}
		
		return temp;
	}

	@Override
	public String toString() {
		StringBuilder sBuilder = new StringBuilder(super.toString());
		for(String s : ponudjeniOdgovori)
			sBuilder.append("\n").append(s);
		
		return sBuilder.toString();
	}
}
