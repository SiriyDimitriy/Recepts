/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anna.recept.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dimitriy
 */
@Aspect
@Component
public class NewAsp {
    @Before("within(com.anna.recept.service.impl.*)")
    public void round() {
            System.out.println("bla");

    }
}
