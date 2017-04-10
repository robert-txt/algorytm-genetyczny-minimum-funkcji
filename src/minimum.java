import java.awt.*;

import java.awt.event.*;

import static java.lang.Math.*;

import javax.swing.*;

public class minimum extends JApplet{

            plotno mplotno = new plotno();

        public void init(){

              setSize(300,301);

              add(mplotno,BorderLayout.CENTER);

              (new Thread(mplotno)).start();

        }

}   // end of minimum

 

class plotno extends JPanel implements Runnable{

       int n=10000;

       int zakres=10;  

       int plotno_size=300;

       osobnik [] tabosob = new osobnik [n];

       public plotno(){

             for(int i=0;i<n;i++){tabosob[i]=new osobnik(zakres);}            

    }

       public void run(){

             while(true){

                     int k,l;          

                        for(int step=0;step<n;step++){

                          k = (int)(n*Math.random());

                          do {l = (int)(n*Math.random());} while (k==l);

                          if(tabosob[k].koszt>tabosob[l].koszt)

                          {tabosob[k].mutate(zakres,tabosob[l].x,tabosob[l].y);

                          }

                          else

                          {tabosob[l].mutate(zakres,tabosob[k].x,tabosob[k].y);

                          }

                        }

                        double current_min=10.e8;

                        int ktory_min=0;

                        for(int i=0;i<n;i++){

                           if(tabosob[i].koszt<current_min) {current_min=tabosob[i].koszt;ktory_min=i;}

                        }

                        System.out.println(tabosob[ktory_min].x+" "+tabosob[ktory_min].y+" "+tabosob[ktory_min].koszt);

                           try {

                                Thread.sleep(3000);               // sleep for 10 msec

                              } catch (InterruptedException t){}

                          repaint();               

             }   // end of while

       }   // end of run

       public void paintComponent(Graphics g){

             super.paintComponent(g);

             g.drawLine(0, 0,plotno_size,0);

             g.drawLine(0, plotno_size,plotno_size,plotno_size);

              double faktor=(1.0*plotno_size)/zakres;           

              for(int i=0;i<n;i++)

              {g.drawRect(plotno_size/2+(int)(tabosob[i].x*faktor),plotno_size/2+(int)(tabosob[i].y*faktor),1,1);

              }

        }

}

class osobnik{

       double x,y;

       double koszt;

       public osobnik(int zakres){

             x=zakres*Math.random()-0.5*zakres;

             y=zakres*Math.random()-0.5*zakres;

             koszt=rosen(x, y);

       }  // end of constructor

       double rosen(double x, double y){

//           return x*x+y*y;

  //         return (1-x)*(1-x)+10.*(y-x*x)*(y-x*x);    // rosenbrock function
    	   
    	   return pow((1-x), 2)+100.*pow((y-x*x), 2);

//             return Math.exp(Math.sin(50*x))+Math.sin(60*Math.exp(y))+Math.sin(70*Math.sin(x))+Math.sin(Math.sin(80*y))-

//                          Math.sin(10*(x+y))+(x*x+y*y)/4.;      

       } // end of rosen

       void mutate(double zakres, double x0, double y0){

           double delta=0.1;
           double x1, y1;
           do{
          	 x1=x0+delta*(1-2*Math.random());
           }while (Math.abs(x1)>zakres/2.);
           x=x1;
           do{
          	 y1=y0+delta*(1-2*Math.random());
           }while (Math.abs(y1)>zakres/2.);
           y=y1;
           koszt=rosen(x, y);
           }

}