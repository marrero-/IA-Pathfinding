package tilemaps;

import java.util.ArrayList;

/**
 *
 * @author Alejandro Marrero
 */
public class AlgoritmoAEstrella {

    private final Map matriz;
    private final NodoMapa nodoInicio;
    private final NodoMapa nodoFinal;
    private int iteraciones;
    
    private int F;
    
    public AlgoritmoAEstrella(Map mapa, NodoMapa inicio, NodoMapa fin) {

        this.matriz = mapa;
        this.nodoInicio = inicio;
        this.nodoFinal = fin;
        iteraciones = 0;
    }

    /* Ejecuta el algoritmo A* para encontrar el camino
     desde el nodo inicial hasta el nodo final
     en el caso de que ese camino exista nos devuelve
     un ArrayList con los puntos del sendero que contiene
     a la solucion del problema */
    public ArrayList calcularSendero() {

        Deap listaAbierta = new Deap(); //Creamos montículo doble
        ArrayList listaCerrada = new ArrayList<NodoMapa>(); //La lista cerrada con las soluciones de tipo NodoMapa
        NodoMapa nodoActual = null;
        boolean solucion = false;

        int filas = matriz.getFilas();
        int columnas = matriz.getColumnas();
     

        listaAbierta.push(nodoInicio); //Metemos en la lista abierta el nodo de salida
       

        //Mientras queden nodos en la lista abierta
        // O no hayamos encontrado la solucion
	while ((!listaAbierta.isEmpty()) && !solucion){
         
            System.out.println("En el bucle principal de A*\n");
            iteraciones++; //Nueva iteracion
            nodoActual = (NodoMapa)listaAbierta.popBottom(); //Sacamos un nodo (con menor F)de la lista abierta
            listaCerrada.add(nodoActual);
            
            ArrayList nodosAdyacentes = new ArrayList<NodoMapa>();
            boolean moDer = false, moIzq = false, movUp = false, moAbj = false;
            
            System.out.println("Primer IF");
            if ( (0 <= nodoActual.getX())   && (nodoActual.getX()< filas) &&
                 (0 <= nodoActual.getY())   && (nodoActual.getY()+1 < columnas))    
                if (matriz.getNodoAt(nodoActual.getX(),nodoActual.getY()+1).getTransitable())
                    nodosAdyacentes.add(matriz.getNodoAt(nodoActual.getX(),nodoActual.getY()+1));
                    moDer= true;
                                
            System.out.println("Segundo IF");
            if ( (0<= nodoActual.getX())    &&  (nodoActual.getX() < filas) &&
                 (0 <= nodoActual.getY()-1)   && (nodoActual.getY()< columnas))   
                if (matriz.getNodoAt(nodoActual.getX(),nodoActual.getY()-1).getTransitable())
                    nodosAdyacentes.add(matriz.getNodoAt(nodoActual.getX(),nodoActual.getY()-1));
                    moIzq= true;
                                
            System.out.println("Tercer IF");
            if ( (0 <= nodoActual.getX()-1)    && (nodoActual.getX() < filas) &&
                 ( 0 <= nodoActual.getY()) && (nodoActual.getY() < columnas))    
                if (matriz.getNodoAt(nodoActual.getX()-1,nodoActual.getY()).getTransitable())
                    nodosAdyacentes.add(matriz.getNodoAt(nodoActual.getX()-1,nodoActual.getY()));
                    movUp = true;
                         
            System.out.println("Cuarto IF");
              if ( (0 <= nodoActual.getX())  && (nodoActual.getX()+1 < filas) &&
                   ( 0 <= nodoActual.getY()) && (nodoActual.getY() < columnas))          
                if (matriz.getNodoAt(nodoActual.getX()+1,nodoActual.getY()).getTransitable())
                    nodosAdyacentes.add(matriz.getNodoAt(nodoActual.getX()+1,nodoActual.getY()));
                    moAbj = true;
   
            //Para cada uno de los nodos encontrados debemos comprobar si hemos llegado al final
            while(!nodosAdyacentes.isEmpty() && !solucion){
             System.out.println("Bucle secundario\n");
               NodoMapa nodoVecino = (NodoMapa) nodosAdyacentes.remove(0);
               
                if (!listaCerrada.contains(nodoVecino)){
                   if (!listaAbierta.contains(nodoVecino)){
                       nodoVecino.setNodoPadre(nodoActual);
                       listaAbierta.push(nodoVecino);
                   
                   //Si el nodo coincide con el final hemos encontrado la solucion
                   if (nodoFinal == nodoVecino){
                       System.out.println("Solucion encontrada");
                       solucion  = true;
                     }
                   }
                else{
                    int nuevoG = nodoActual.getG();
                    if (nodoVecino.getX()== nodoActual.getX() ||
                         nodoVecino.getY()== nodoActual.getY())
                            nuevoG += 10;
                        else
                            nuevoG += 14;

                    if (nuevoG < nodoVecino.getG()){
                        nodoVecino.setNodoPadre(nodoActual);
                        listaAbierta.reordenar();
                        }
                    }
                }
            }
        }
                                 
        
        if (solucion){
     
        ArrayList camino = new ArrayList<NodoMapa>();
        NodoMapa nodoAuxiliar = nodoFinal;
        while (nodoAuxiliar != null){
            camino.add(0, nodoAuxiliar);
            nodoAuxiliar = nodoAuxiliar.getNodoPadre();
        }
  
        return camino;
        }
        
        else
            return null;
        
        }
    
    
    //Nos devuelve el numero de iteraciones del algoritmo
    public final int getIteraciones(){
        return this.iteraciones;
    }
    
}