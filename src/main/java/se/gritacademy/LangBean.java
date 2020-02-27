package se.gritacademy;

public class LangBean {
		
	private String word;
	private String translatedWord;
	
	
	public void addOwnWords(String word, String translatedWord) {
		this.translatedWord = translatedWord;
		this.word = word;
	}


	public String getWord() {
		return word;
	}


	public String getTranslatedWord() {
		return translatedWord;
	}
	
	

	
}
