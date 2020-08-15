package br.com.jeffersonmarques.restapi.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import java.util.*

@Configuration
class MessageConfig {

    @Primary
    @Bean
    fun apiErrorMessageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:/errors")
        messageSource.setDefaultEncoding("UTF-8")
        messageSource.setDefaultLocale(Locale("pt", "BR"))

        return messageSource
    }
}