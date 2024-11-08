package com.api_rest.projetojava.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.api_rest.projetojava.entities.Consults;
import com.api_rest.projetojava.entities.Patients;
import com.api_rest.projetojava.repositories.ConsultsRepository;
import com.api_rest.projetojava.repositories.PatientsRepository;

@Component
public class ServiceConsult {

    @Autowired
    private ConsultsRepository consultsRepository;

    @Autowired
    private PatientsRepository patientsRepository;
    
    //verificar campos vazios id_patients e data
    public ResponseEntity<Object> verifyDate(Consults newConsult){

        LocalDateTime dateHours = newConsult.getDateConsult();

        //validar se os campos não estão vazios
        if(dateHours != null){

            //consultsRepository.save(newConsults);
            return ResponseEntity.ok(dateHours);

        } else {

            return ResponseEntity.badRequest().body("A data da consulta não pode ser nula.");

        }

    }

    //vai me retornar o valor do tipo
    public Object verifyType(ResponseEntity<Object> responseEntity){

        //pega o corpo do entity
        Object body = responseEntity.getBody(); 

        if(body instanceof LocalDateTime){

            return (LocalDateTime) body;

        } else if(body instanceof Consults){

            return (Consults) body;

        } else {

            /*throw faz com que o programa seja interrompido, a instancia da clase iLLeal vai indicar que um metodo recebeu um argumento 
            invalido, getclass e get name retorna o nome da classe e do obj q causou o erro*/
            throw new IllegalArgumentException("Tipo inesperado: " + body.getClass().getName());

        }

    }

    public void create(Consults consult){

        //vai pegar o id de patient em consult e retornar o patients do id e guardar em result
        Optional<Patients> result = patientsRepository.findById(consult.getId());

        Consults newConsult = new Consults();
        Patients patient = result.get(); //pegando o obj de patients e jogando na varialvel patient

        //mandando para um obj de uma nova consulta os dados de data e o obj de patient
        newConsult.setDateConsult(consult.getDateConsult());
        newConsult.setPatient(patient);
        consultsRepository.save(newConsult);

    }

}