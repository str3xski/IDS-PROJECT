package service;

/*


Questa interfaccia rappresenta il componente "ascoltatore" nel Pattern Observer. 
Il suo unico scopo è definire un metodo standard che il sistema chiamerà ogni volta che i dati dei film cambiano.

*/


public interface Observer {
    void update();
}
