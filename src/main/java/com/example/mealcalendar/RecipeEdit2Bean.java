package com.example.mealcalendar;

public class RecipeEdit2Bean {

     private String ricettapresa;
     private String ricetta;
     private String tdieta;
     private String tpasto;
     private String numingred;
     private String ingred;
     private String desrcip;
     private String autor;

     public RecipeEdit2Bean(String ricp,String ric,String tdi,String tpas,String numi,String ingr,String des,String author) {
         this.ricettapresa=ricp;
         this.ricetta=ric;
         this.tdieta=tdi;
         this.tpasto=tpas;
         this.numingred=numi;
         this.ingred=ingr;
         this.desrcip=des;
         this.autor=author;
     }

     public String getRicettapresa() {
         return ricettapresa;
     }

     public String getricetta() {
         return ricetta;
     }

     public String getTdieta() {
         return tdieta;
     }

     public String getTpasto() {
         return tpasto;
     }

     public String getNumingred() {
         return numingred;
     }

     public String getIngred() {
         return ingred;
     }

     public String getDesrcip() {
         return desrcip;
     }

     public String getAutor() {
         return autor;
     }

}
