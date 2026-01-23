package view;

import controller.MovieController;
import pojo.Movie;
import service.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/*
Questa classe è il cuore visivo del progetto e rappresenta la finestra principale dell'applicazione. 
Seguendo il pattern Model-View-Controller (MVC), si occupa di assemblare tutti i pezzi dell'interfaccia
 */
public class MovieView extends JFrame implements Observer {

    private MovieController controller;
    private JTable tabellaFilm;
    private DefaultTableModel modelloTabella;
    
    private MovieInputPanel pannelloInput;
    private ToolbarPanel pannelloToolbar;
    private ActionPanel pannelloAzioni;
    
    private List<Movie> listaFilmAttuale;
    private FormState statoCorrente;

   

    @Override
    public void update() {
       
    }

    
}