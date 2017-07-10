package view;

import helper.GlobalHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import service.AutorService;
import service.IzdavacService;
import service.KnjigaAutorService;
import service.KnjigaService;
import service.NastavnikService;
import service.PredmetService;
import model.Autor;
import model.Izdavac;
import model.Knjiga;
import model.KnjigaAutor;
import model.Korisnik;
import model.Nastavnik;
import model.Predmet;

public class NastavnikPredmetiView extends JFrame {
	private JTable tabelaOdabrani;
	private JButton btnZatvoriti;

    private String tabelaKolone[] = {"Sifra", "Naziv"};
    private DefaultTableModel tableModelOdabrani = new DefaultTableModel(tabelaKolone, 0);
    
    private Korisnik korisnik;
    private Nastavnik nastavnik;
    private PredmetService predmetService = new PredmetService();
    ArrayList<Predmet> listaSvi;
    ArrayList<Predmet> listaOdabrani;
    
	public NastavnikPredmetiView(Korisnik k, Nastavnik n) {
		this.korisnik = k;
		this.nastavnik = n;
		setTitle("Predmeti - " + this.nastavnik.toString());
		getContentPane().setLayout(null);
		
		tabelaOdabrani = new JTable();
		JScrollPane scrollPane1 = new JScrollPane(tabelaOdabrani);
		scrollPane1.setBounds(20, 25, 550, 195);
		getContentPane().add(scrollPane1);
		
		btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(412, 232, 138, 29);
		getContentPane().add(btnZatvoriti);
		
	    listaOdabrani = new ArrayList<Predmet>();
        listaOdabrani.addAll(nastavnik.getPredmetCollection());
		
		List<Predmet> lista = predmetService.findAllSortedAsc("naziv");
		listaSvi = new ArrayList<Predmet>();
		
		for(Predmet p : lista){
			if(!listaOdabrani.contains(p))
				listaSvi.add(p);
		}
        
		osvjeziTabeluOdabrani();
		
		btnZatvoriti.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				zatvoriti();			
			}
			
		});
				
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	zatvoriti();  
		    }
		});
	}
		
	private void zatvoriti(){
		NastavnikPanel nastavniciView = new NastavnikPanel(korisnik);
		nastavniciView.setResizable(false);
		nastavniciView.setSize(new Dimension(610, 530));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
        nastavniciView.setLocation(dim.width/2-nastavniciView.getSize().width/2, dim.height/2-nastavniciView.getSize().height/2);
        nastavniciView.getContentPane().setBackground(new Color(245, 245, 245));
        nastavniciView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	 
        nastavniciView.setIconImage( (new ImageIcon(NastavnikPanel.class.getResource("/ikone/library.png"))).getImage() );
        nastavniciView.setVisible(true);		        
		dispose();
	}
	
	private void osvjeziTabeluOdabrani(){
		tableModelOdabrani=new DefaultTableModel(tabelaKolone, 0){
	    	@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };
        
		for(Predmet p : listaOdabrani){
            Object[] data={p.getSifra(), p.getNaziv()};            
            tableModelOdabrani.addRow(data);
        }
    
        tabelaOdabrani.setModel(tableModelOdabrani); 
        tabelaOdabrani.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
}
