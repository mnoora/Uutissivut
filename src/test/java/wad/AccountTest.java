/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wad.domain.*;

/**
 *
 * @author mnoora
 */
public class AccountTest {
    
    Account account;
    
    public AccountTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        account = new Account("username","password");
    }
    
    @Test
    public void konstruktoriAsettaaNimenOikein(){
        assertEquals("username",account.getUsername());
    }
    
    @Test
    public void setUsernameToimiiOikein(){
        account.setUsername("newname");
        assertEquals("newname",account.getUsername());
    }
    
    @Test
    public void konstruktoriAsettaaSalasananOikein() {
        assertEquals("password",account.getPassword());
    }
    
    @Test
    public void setPasswordToimiiOikein() {
        account.setPassword("newpassword");
        assertEquals("newpassword", account.getPassword());
    }
    
    @After
    public void tearDown() {
    }

}
