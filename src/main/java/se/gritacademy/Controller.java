package se.gritacademy;




import java.util.Scanner;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class Controller implements WebMvcConfigurer {
	

	
	// Locale Interceptor that listens for parameter name 'lang'. 
	@Bean
	public LocaleChangeInterceptor localeInterceptor() {
		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		localeInterceptor.setParamName("language");
		return localeInterceptor;
	}
	// Registers the locale interceptor
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeInterceptor());
	}
	// Checks if word is empty or holds numbers.
	public static boolean isValid(String word) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(word);
		if(word.isBlank() || scanner.hasNextInt()) {
			return false;
		} else {
			return true;
		}
		}
	

	}


