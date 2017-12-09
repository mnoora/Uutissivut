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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.*;
import wad.service.*;
import wad.repository.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.*;
import org.springframework.stereotype.Service;

/**
 *
 * @author mnoora
 */
@Controller
public class UutisController {
    
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
        Account example = new Account("hannu","lol");
        this.accountRepository.save(example);
    }
    
    @GetMapping("/")
    public String list(Model model) {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "time");
        Pageable pageable2 = PageRequest.of(0, Integer.MAX_VALUE,Sort.Direction.DESC,"time");
        model.addAttribute("uutiset", this.uutisetRepository.findAll(pageable));
        model.addAttribute("kategoriat",this.kategoriaRepository.findAll());
        model.addAttribute("sivuuutiset",this.uutisetRepository.findAll(pageable2));
        return "uutiset";
    } 
    @GetMapping("/jarjestys/kategoria")
    public String jarjestysKategorianMukaan(Model model){
        Pageable pageable = PageRequest.of(0,Integer.MAX_VALUE,Sort.Direction.DESC,"kategoriat");
        model.addAttribute("uutiset",this.uutisetRepository.findAll(pageable));
        model.addAttribute("kategoriat",this.kategoriaRepository.findAll());
        return "kategoriajarjestys";
        
    }
    @GetMapping("/{kategoria}")
    public String aihe(Model model,@PathVariable String kategoria) {
        
        model.addAttribute("uutiset", this.kategoriaRepository.findByNimi(kategoria).getUutiset());
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
        Kategoria a = new Kategoria();
        a.setNimi(kategoria);
        for(Kategoria kat : this.kategoriaRepository.findAll()){
            if(kat.getNimi().equals(kategoria)){
                a=kat;
            }
        }
        if(!this.kategoriaRepository.existsByNimi(kategoria)){
            this.kategoriaRepository.save(a);
            
        }
        i.getKategoriat().add(a);
        i.setContent(file.getBytes());
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
        
        
       
        uutisetRepository.save(i);
        
        return "redirect:/hallintapaneeli";
    }
    
    @GetMapping("/uutinen/{id}")
    public String viewUutinen(Model model, @PathVariable Long id) {
        if(this.uutisetRepository.existsById(id)){
            Optional<Uutinen> it =this.uutisetRepository.findById(id);
            
            model.addAttribute("uutinen", it.get());
            model.addAttribute("kategoriat",this.kategoriaRepository.findAll());
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
    
    @PostMapping("/login")
    public String loginInformation(@RequestParam String username, @RequestParam String password){
        if(this.customUserDetailsService.loadUserByUsername(username).getPassword().equals(password)){
            return "hallintapaneeli";
        }
        return "login";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    
   
}
