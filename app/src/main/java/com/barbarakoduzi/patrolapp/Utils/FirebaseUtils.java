package com.barbarakoduzi.patrolapp.Utils;

import com.barbarakoduzi.patrolapp.Models.PerdoruesPolic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

      private static FirebaseDatabase database;

      private static FirebaseAuth auth;

      private static  DatabaseReference gjobatRef, policRef, perdoruesRef;


   /* public static PerdoruesPolic merrPerdoruesPolicNgaUID(String UID){
        database = FirebaseDatabase.getInstance();
        perdoruesRef = database.getReference(CodesUtil.REFERENCE_PERDORUES);
        policRef = database.getReference(CodesUtil.REFERENCE_POLIC);

        perdoruesRef.addValueEventListener()

    }
*/
}
