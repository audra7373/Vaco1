package org.marrero.vaco;

import java.util.ArrayList;
import java.util.List;

public class All_Levels {
   private static List<Levels> niv;

   public All_Levels()
   {
      niv = new ArrayList<>();

      Levels level1 = new Levels(1,"facile",100,400,0,0,0,50,true, Levels.TypeObjectif.SCORE_MOUVEMENTS);
      Levels level2 = new Levels(2,"facile",150,200,10,6,0,60,false,Levels.TypeObjectif.SCORE_MOUVEMENTS);
      Levels level3 = new Levels(3,"facile",15,40,10,10,10,15,false,Levels.TypeObjectif.CANDY_COULEUR);
      Levels level4 = new Levels(4,"facile",80,400,15,0,0,20,false,Levels.TypeObjectif.CANDY_SCORE);
      Levels level5 = new Levels(5,"facile",100,300,15,0,20,80,false,Levels.TypeObjectif.SCORE_MOUVEMENTS);
      Levels level6 = new Levels(6,"facile",100,500,15,0,20,80,false,Levels.TypeObjectif.CANDY_SCORE);
      Levels level7 = new Levels(7,"facile",100,800,15,0,20,80,false,Levels.TypeObjectif.SCORE_MIN_MAX);
      Levels level8 = new Levels(8,"facile",100,900,15,0,20,80,false,Levels.TypeObjectif.CANDY_SCORE);

      niv.add(level1);
      niv.add(level2);
      niv.add(level3);
      niv.add(level4);
      niv.add(level5);
      niv.add(level6);
      niv.add(level7);
      niv.add(level8);
   }

   public static Levels getNiveau(int numeroNiveau) {
      if (numeroNiveau < 1 || numeroNiveau > niv.size()) {
         throw new IllegalArgumentException("Numéro de niveau invalide : " + numeroNiveau);
      }
      return niv.get(numeroNiveau - 1);
   }
}
