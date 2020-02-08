package com.example.thymeleaf.service;


import com.example.thymeleaf.model.SkierowanieDoLekarza;
import com.example.thymeleaf.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatientService {
    private Map<Integer, SkierowanieDoLekarza> skierowanieDoLekarzaMap = new HashMap<>();
    public SkierowanieDoLekarza createSkierowanieDoLekarza(String lekarz, String pacjent, Date termin){
        int id = new Random().nextInt();
        SkierowanieDoLekarza skierowanieDoLekarza = new SkierowanieDoLekarza(id, lekarz, pacjent, termin);
        skierowanieDoLekarzaMap.put(id, skierowanieDoLekarza);
        return skierowanieDoLekarza;
    }

    public void delete(int id){
        skierowanieDoLekarzaMap.remove(id);
    }
    public Collection<SkierowanieDoLekarza> listSkierowanieDoLekarza(){
        return skierowanieDoLekarzaMap.values();
    }

    public SkierowanieDoLekarza getSkierowanieDoLekarza(int id){
        return skierowanieDoLekarzaMap.get(id);
    }
}
