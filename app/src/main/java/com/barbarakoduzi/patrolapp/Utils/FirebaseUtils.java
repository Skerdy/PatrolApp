package com.barbarakoduzi.patrolapp.Utils;

import com.barbarakoduzi.patrolapp.Models.Perdorues;
import com.barbarakoduzi.patrolapp.Models.PerdoruesPolic;
import com.barbarakoduzi.patrolapp.Models.PerdoruesShofer;
import com.barbarakoduzi.patrolapp.Models.Shofer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseUtils {


      public static List<PerdoruesShofer> ktheShoferetNgaListaPerdoruesDheShofer(List<Perdorues> perdoruesList , Map<String,Shofer> shoferMap){
            List<PerdoruesShofer> perdoruesShoferList = new ArrayList<>();

            for(String key : shoferMap.keySet()){
                  for(Perdorues perdorues:perdoruesList){
                        if(key.equals(perdorues.getIdProfil())){
                              //kemi te bejme me shoferin dhe perdoruesin e njejte prandaj krijojme nje objekt perdoruesShofer te ri dhe e shtojme ne liste
                              PerdoruesShofer perdoruesShofer = new PerdoruesShofer(perdorues.getEmer(),perdorues.getMbiemer(),
                                      perdorues.getRol(),perdorues.getIdProfil(),perdorues.getEmail(),shoferMap.get(key));
                              perdoruesShoferList.add(perdoruesShofer);

                        }
                  }
            }
            return  perdoruesShoferList;
      }
}
