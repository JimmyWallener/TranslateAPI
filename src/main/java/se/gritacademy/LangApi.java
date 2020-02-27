package se.gritacademy;


import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;





@RestController
public class LangApi {
			
			private String notFound = "Error: Not found";
			private String errorInput = "Error: String empty OR contain Integers";
			
			@Autowired
			private MessageSource messageSource;
			private LangBean bean = new LangBean();
			

			
			
			// Creating a RequestParameter for 'lang' which will set output language
			// word/Hello?lang=countrycode and translate individual word
			// Doing a whitespace trim and set to lowercase for matching value in properties file.
			
			@RequestMapping(path = "/word-translation/{word}", method = RequestMethod.GET, produces = "application/json")
			public String translateWord(@RequestParam(name = "lang", required = false) Locale locale,
				@PathVariable String word) throws NoSuchMessageException{
				String pattern = "{\"%s\"}";
				String json = String.format(pattern, messageSource.getMessage(word.trim().toLowerCase(), null, notFound, locale));
				String invalid = String.format(pattern, errorInput);
				if (Controller.isValid(word)) {
					return json;
				} return invalid;
			}
			
			
			// RequestHeader for phrases - Works for translating a website with setting Accept-Language.
			
			@RequestMapping(path = "/{phrases}", method = RequestMethod.GET, produces = "application/json")
			public String getPhrases(@RequestHeader(name = "Accept-Language", required = false) Locale locale,
					@PathVariable String phrases) throws NoSuchMessageException {
				String pattern = "{\"%s\"}";
				String json = String.format(pattern, messageSource.getMessage(phrases.trim().toLowerCase(), null,notFound, locale));
				String invalid = String.format(pattern, errorInput);
				if (Controller.isValid(phrases)) {
					return json;
				} return invalid;
			}
			
			
			// JSON formatted response from POST method that sets the word.
		
			@RequestMapping(path = "/get-word", method = RequestMethod.GET, produces = "application/json")
			public String getWords(@RequestHeader(name = "Accept-Language", required = false) Locale locale) throws NoSuchMessageException{
				String pattern = "{\"%s\": \"%s\"}"; 
				String altPattern = "{\"%s\"}";
				boolean wordCheck = messageSource.getMessage(bean.getWord(), null, notFound, locale).equals(notFound);
				
				String json = String.format(pattern, bean.getWord(),bean.getTranslatedWord()); 
				String messagesJson	= String.format(altPattern, messageSource.getMessage(bean.getWord(), null, notFound, locale));
				 
				
				if(wordCheck) {
					return json;
				} else {
					return messagesJson;
				}
				
			}
			
			
			// POST Mapping for setting a single word or phrase. Just checks that it's not empty or integers
			@RequestMapping(path = "/add-words/{word}/{translatedWord}", method = RequestMethod.POST, produces = "application/json")
			public String postWord(@PathVariable String word, @PathVariable String translatedWord) throws Exception {
				bean.addOwnWords(word, translatedWord);
				String pattern = "{\"%s\": \"%s\"}"; 
				String json = String.format(pattern, word, translatedWord);
				return json;
			}
			

}
