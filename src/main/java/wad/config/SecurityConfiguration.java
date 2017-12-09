/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/**
 *
 * @author mnoora
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
            .antMatchers("/free").permitAll()
            .antMatchers("/access").permitAll()
            .antMatchers("/to/*").permitAll()
            .anyRequest().authenticated().and()
            .formLogin().permitAll().and()
            .logout().permitAll();
        }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // käyttäjällä jack, jonka salasana on bauer, on rooli USER
        auth.inMemoryAuthentication()
                .withUser("jack").password("bauer").roles("USER");
    }
}
