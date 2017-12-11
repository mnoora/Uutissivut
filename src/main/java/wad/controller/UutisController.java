/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.*;
import wad.service.*;
import wad.repository.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.*;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.servlet.http.*;
import  org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

/**
 *
 * @author mnoora
 */
@Controller
public class UutisController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private UutisRepository uutisetRepository;
    
    @Autowired 
    private KategoriaRepository kategoriaRepository;
    
  
    @Autowired
    private KirjoittajaRepository kirjoittajaRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @PostConstruct
    public void init() {
        
        Account user = new Account();
        user.setUsername("hannu");
        user.setPassword(passwordEncoder.encode("lol"));
        user = this.accountRepository.save(user);
        
        Kirjoittaja kirjoittaja = new Kirjoittaja();
        kirjoittaja.setNimi("Noora");
        
        ArrayList<Kirjoittaja> kirjoittajat = new ArrayList<>();
        kirjoittajat.add(kirjoittaja);
        
        
        
        Uutinen uutinen = new Uutinen();
        uutinen.setOtsikko("Ensimmäinen uutinen");
        uutinen.setIngressi("eka ingressi");
        uutinen.setTeksti("wääääy");
        ArrayList<Uutinen> lista = new ArrayList<>();
        ArrayList<Uutinen> lista2 = new ArrayList<>();
        
        uutinen.setKirjoittajat(kirjoittajat);
        
        Uutinen uutinen2 = new Uutinen();
        uutinen2.setOtsikko("Toinen uutinen");
        uutinen2.setIngressi("toka ingressi");
        uutinen2.setTeksti("wow");
        
        Uutinen uutinen3 = new Uutinen();
        uutinen3.setOtsikko("Kolmas uutinen");
        uutinen3.setIngressi("kolmas ingressi");
        uutinen3.setTeksti("ok");
        
        uutinen2.setKirjoittajat(kirjoittajat);
        uutinen3.setKirjoittajat(kirjoittajat);
        
        
        
        Kategoria kategoria1 = new Kategoria("Politiikka",lista);
        Kategoria kategoria2 = new Kategoria("Urheilu",lista);
        lista2.add(uutinen);
        lista2.add(uutinen2);
        lista2.add(uutinen3);
        
        uutinen.getKategoriat().add(kategoria2);
        uutinen2.getKategoriat().add(kategoria1);
        uutinen3.getKategoriat().add(kategoria2);
        
        this.uutisetRepository.saveAll(lista2);
        this.kirjoittajaRepository.save(kirjoittaja);
        
        kategoria1.lisaaUutinen(uutinen2);
        kategoria2.lisaaUutinen(uutinen);
        kategoria2.lisaaUutinen(uutinen3);
        
        this.kategoriaRepository.save(kategoria1);
        this.kategoriaRepository.save(kategoria2);
        
        
        
        
    }
    
    @GetMapping("/")
    public String list(Model model) {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "time");
        Pageable pageable2 = PageRequest.of(0, Integer.MAX_VALUE,Sort.Direction.DESC,"time");
        Pageable pageable3 = PageRequest.of(0,Integer.MAX_VALUE,Sort.Direction.DESC,"uutistenMaara");
        
       
        model.addAttribute("uutiset", this.uutisetRepository.findAll(pageable));
        model.addAttribute("kategoriat",this.kategoriaRepository.findAll());
        model.addAttribute("sivuuutiset",this.uutisetRepository.findAll(pageable2));
        model.addAttribute("maarauutiset",this.kategoriaRepository.findAll(pageable3));
        return "uutiset";
    } 
    @GetMapping("/jarjestys/kategoria")
    public String jarjestysKategorianMukaan(Model model){
        Pageable pageable = PageRequest.of(0,Integer.MAX_VALUE,Sort.Direction.DESC,"kategoriat");
        Pageable pageable2 = PageRequest.of(0, Integer.MAX_VALUE,Sort.Direction.DESC,"time");
        Pageable pageable3 = PageRequest.of(0,Integer.MAX_VALUE,Sort.Direction.DESC,"uutistenMaara");
        model.addAttribute("uutiset",this.uutisetRepository.findAll(pageable));
        model.addAttribute("kategoriat",this.kategoriaRepository.findAll());
        model.addAttribute("sivuuutiset",this.uutisetRepository.findAll(pageable2));
        model.addAttribute("maarauutiset",this.kategoriaRepository.findAll(pageable3));
        return "kategoriajarjestys";
        
    }
    @GetMapping("/{kategoria}")
    public String kategoria(Model model,@PathVariable String kategoria) {
        Pageable pageable2 = PageRequest.of(0, Integer.MAX_VALUE,Sort.Direction.DESC,"time");
        Pageable pageable3 = PageRequest.of(0,Integer.MAX_VALUE,Sort.Direction.DESC,"uutistenMaara");
        model.addAttribute("uutiset", this.kategoriaRepository.findByNimi(kategoria).getUutiset());
         model.addAttribute("sivuuutiset",this.uutisetRepository.findAll(pageable2));
        model.addAttribute("maarauutiset",this.kategoriaRepository.findAll(pageable3));
        return "kaikkikategorianuutiset";
    }
    
    @GetMapping("/hallintapaneeli")
    public String hallintapaneeli(Model model) {
        model.addAttribute("uutiset", this.uutisetRepository.findAll());
        model.addAttribute("kategoriat",this.kategoriaRepository.findAll());
        return "hallintapaneeli";
    }

    @PostMapping("/hallintapaneeli")
    @Transactional
    public String create(@RequestParam String otsikko,@RequestParam String ingressi,@RequestParam String teksti,@RequestParam String kategoria,@RequestParam String nimi,@RequestParam("file") MultipartFile file) throws IOException {
        Uutinen i = new Uutinen();
        i.setOtsikko(otsikko);
        i.setIngressi(ingressi);
        i.setTeksti(teksti);
        
        ArrayList<Uutinen> uutiset = new ArrayList<>();
        
        Kategoria a = new Kategoria(kategoria,uutiset);
        
        
        
        for(Kategoria kat : this.kategoriaRepository.findAll()){
            if(kat.getNimi().equals(kategoria)){
                a=kat;
            }
        }
       /* if(!this.kategoriaRepository.existsByNimi(kategoria)){
            this.kategoriaRepository.save(a);
            
        }*/
       this.kategoriaRepository.save(a);
       
        i.getKategoriat().add(a);
        i.setContent(file.getBytes());
        
        a.lisaaUutinen(i);
        Kirjoittaja kirjoittaja = new Kirjoittaja();
        kirjoittaja.setNimi(nimi);
        
        for(Kirjoittaja ki : this.kirjoittajaRepository.findAll()){
            if(ki.getNimi().equals(nimi)){
                kirjoittaja=ki;
            }   
        }
         if(!this.kirjoittajaRepository.existsByNimi(nimi)){
            this.kirjoittajaRepository.save(kirjoittaja);
        }
        i.getKirjoittajat().add(kirjoittaja);
        i.getKategoriat().add(a);
        
       
        this.uutisetRepository.save(i);
        
        
       
        
        return "redirect:/hallintapaneeli";
    }
    
    @GetMapping("/uutinen/{id}")
    public String viewUutinen(Model model, @PathVariable Long id) {
        Pageable pageable2 = PageRequest.of(0, Integer.MAX_VALUE,Sort.Direction.DESC,"time");
        Pageable pageable3 = PageRequest.of(0,Integer.MAX_VALUE,Sort.Direction.DESC,"uutistenMaara");
        
        if(this.uutisetRepository.existsById(id)){
            Optional<Uutinen> it =this.uutisetRepository.findById(id);
            
            model.addAttribute("uutinen", it.get());
            model.addAttribute("kategoriat",this.kategoriaRepository.findAll());
            model.addAttribute("sivuuutiset",this.uutisetRepository.findAll(pageable2));
            model.addAttribute("maarauutiset",this.kategoriaRepository.findAll(pageable3));
            return "uutinen";
        }
        return "redirect:/";
    }
    
    @GetMapping(path="/uutinen/{id}/content", produces = "image/jpeg")
    @ResponseBody
    public byte[] get(@PathVariable Long id) {
        return this.uutisetRepository.getOne(id).getContent();
    }
    
    @DeleteMapping("/uutinen/{id}")
    @Transactional
    public String remove(Model model, @PathVariable Long id) {
        if(this.uutisetRepository.existsById(id)){
            this.uutisetRepository.deleteById(id);
            
    }
        return "redirect:/hallintapaneeli";
    }
    
    @GetMapping("/jarjestys/edellinenviikko")
    public String listaaEdellisenViikonUutiset(Model model){
        Pageable pageable2 = PageRequest.of(0, Integer.MAX_VALUE,Sort.Direction.DESC,"time");
        Pageable pageable3 = PageRequest.of(0,Integer.MAX_VALUE,Sort.Direction.DESC,"uutistenMaara");
        ArrayList lista = new ArrayList<>();
        LocalDateTime a = LocalDateTime.now().minusWeeks(1);
        for(Uutinen uutinen : this.uutisetRepository.findAll()){
            if(uutinen.getTime().isAfter(a)){
                lista.add(uutinen);
            }
        }
        Pageable pageable = PageRequest.of(0,Integer.MAX_VALUE,Sort.Direction.DESC,"time");
        model.addAttribute("uutiset",lista);
        model.addAttribute("sivuuutiset",this.uutisetRepository.findAll(pageable2));
        model.addAttribute("maarauutiset",this.kategoriaRepository.findAll(pageable3));
        return "edellisenviikonuutiset";
    }
    
    @PostMapping("/login")
    public String loginInformation(@RequestParam String username, @RequestParam String password){
        
        if(this.customUserDetailsService.loadUserByUsername(username).getPassword().equals(password)){
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            auth.setAuthenticated(true);
            
            return "redirect:/hallintapaneeli";
        }
        
        return "login";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
}
    
   
}
