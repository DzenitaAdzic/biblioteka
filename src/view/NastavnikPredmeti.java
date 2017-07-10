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

public class NastavnikPredmeti extends JFrame {
	private JTable tabelaSvi;
	private JTable tabelaOdabrani;
	private JButton btnLeft;
	private JButton btnSpremitiIzmjene;
	private JButton btnZatvoriti;
	
	private Predmet odabraniObjekatLijevo = null;
	private Predmet odabraniObjekatDesno = null;
    private String tabelaKolone[] = {"Sifra", "Naziv"};
    private DefaultTableModel tableModelSvi = new DefaultTableModel(tabelaKolone, 0);
    private DefaultTableModel tableModelOdabrani = new DefaultTableModel(tabelaKolone, 0);
    
    private Korisnik korisnik;
    private Nastavnik nastavnik;
    private NastavnikService nastavnikService = new NastavnikService();
    private PredmetService predmetService = new PredmetService();
    private ArrayList<Predmet> listaSvi;
    private ArrayList<Predmet> listaOdabrani;
    
	public NastavnikPredmeti(Korisnik k, Nastavnik n) {
		this.korisnik = k;
		this.nastavnik = n;
		setTitle("Predmeti - " + this.nastavnik.toString());
		getContentPane().setLayout(null);
		
		JLabel lblSvi = new JLabel("Svi predmeti");
		lblSvi.setBounds(10, 10, 270, 16);
		getContentPane().add(lblSvi);
		
		JLabel lblOdabrani = new JLabel("Odabrani predmeti");
		lblOdabrani.setBounds(336, 10, 270, 16);
		getContentPane().add(lblOdabrani);
		 
		tabelaSvi = new JTable();
		JScrollPane scrollPane = new JScrollPane(tabelaSvi);
		scrollPane.setBounds(10, 25, 274, 195);
		getContentPane().add(scrollPane);
		
		tabelaOdabrani = new JTable();
		JScrollPane scrollPane1 = new JScrollPane(tabelaOdabrani);
		scrollPane1.setBounds(336, 25, 274, 195);
		getContentPane().add(scrollPane1);
		
		JButton btnRight = new JButton(">");
		btnRight.setBounds(290, 65, 40, 40);
		getContentPane().add(btnRight);
		
		btnLeft = new JButton("<");
		btnLeft.setBounds(290, 117, 40, 40);
		getContentPane().add(btnLeft);
		
		btnSpremitiIzmjene = new JButton("Spremiti izmjene");
		btnSpremitiIzmjene.setBounds(336, 232, 138, 29);
		getContentPane().add(btnSpremitiIzmjene);
		
		btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(476, 232, 138, 29);
		getContentPane().add(btnZatvoriti);
		
		listaOdabrani = new ArrayList<Predmet>();
        listaOdabrani.addAll(nastavnik.getPredmetCollection());
		
		List<Predmet> lista = predmetService.findAllSortedAsc("naziv");
		listaSvi = new ArrayList<Predmet>();
		
		for(Predmet p : lista){
			if(!listaOdabrani.contains(p))
				listaSvi.add(p);
		}
        
		osvjeziTabeluSvi();
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
		
		btnRight.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabelaSvi.getSelectedRowCount()==0) {
		            JOptionPane.showMessageDialog(null, "Niste odabrali nijedan unos na lijevoj strani.", "Greška", JOptionPane.WARNING_MESSAGE);
		            return;
		        }
				String sifra = (String) tabelaSvi.getModel().getValueAt(tabelaSvi.getSelectedRow(), 0);
	            odabraniObjekatLijevo=predmetService.find(sifra);
	            listaOdabrani.add(odabraniObjekatLijevo);
	           listaSvi.remove(odabraniObjekatLijevo);
	            osvjeziTabeluSvi();
	            osvjeziTabeluOdabrani();
			}			
		});
		
		btnLeft.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabelaOdabrani.getSelectedRowCount()==0) {
		            JOptionPane.showMessageDialog(null, "Niste odabrali nijedan unos na desnoj strani.", "Greška", JOptionPane.WARNING_MESSAGE);
		            return;
		        }
				String sifra = (String) tabelaOdabrani.getModel().getValueAt(tabelaOdabrani.getSelectedRow(), 0);
	            odabraniObjekatDesno=predmetService.find(sifra);
	            listaSvi.add(odabraniObjekatDesno);
	            listaOdabrani.remove(odabraniObjekatDesno);
	            osvjeziTabeluSvi();
	            osvjeziTabeluOdabrani();
			}			
		});

		btnSpremitiIzmjene.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {				
				nastavnik.setPredmetCollection(listaOdabrani);
				try{
					nastavnikService.editWithoutRefresh(nastavnik);
					JOptionPane.showMessageDialog(null, "Predmeti za odabranog nastavnika su uspješno izmijenjeni.", "Predmeti", JOptionPane.INFORMATION_MESSAGE);
					zatvoriti();
				} catch (Exception ex){	
					ex.printStackTrace();
	            	JOptionPane.showMessageDialog(null, "Pojavila se greška prilikom spremanja u bazu podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		
	}
	
	private void zatvoriti(){
		BibliotekarNastavnici nastavniciView = new BibliotekarNastavnici(korisnik);
		nastavniciView.setResizable(false);
		nastavniciView.setSize(new Dimension(520, 450));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
        nastavniciView.setLocation(dim.width/2-nastavniciView.getSize().width/2, dim.height/2-nastavniciView.getSize().height/2);
        nastavniciView.getContentPane().setBackground(new Color(245, 245, 245));
        nastavniciView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
        nastavniciView.setVisible(true);		        
		dispose();
	}
	
	private void osvjeziTabeluSvi(){
		tableModelSvi=new DefaultTableModel(tabelaKolone, 0){
	    	@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };
        
		for(Predmet p : listaSvi){
            Object[] data={p.getSifra(), p.getNaziv()};            
            tableModelSvi.addRow(data);
        }
        odabraniObjekatLijevo=null;
    
        tabelaSvi.setModel(tableModelSvi); 
        tabelaSvi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        odabraniObjekatDesno=null;
    
        tabelaOdabrani.setModel(tableModelOdabrani); 
        tabelaOdabrani.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
}
