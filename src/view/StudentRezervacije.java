package view;

import helper.GlobalHelper;

import javax.swing.JFrame;

import model.Autor;
import model.Knjiga;
import model.KnjigaAutor;
import model.Korisnik;
import model.Posudba;
import model.Primjerak;
import model.Rezervacija;
import model.Student;

import javax.swing.JLabel;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import service.AutorService;
import service.KnjigaService;
import service.KorisnikService;
import service.PosudbaService;
import service.PrimjerakService;
import service.RezervacijaService;
import service.StudentService;

public class StudentRezervacije extends JFrame {
	private JTextField txtDatum;
	
	private RezervacijaService service = new RezervacijaService();
    private String tabelaKolone[] = {"Knjiga", "Datum rezervacije", "Rok preuzimanja"};
    private DefaultTableModel tableModel=new DefaultTableModel(tabelaKolone, 0);
	private JTable table;
	private JButton btnRezervisati;	
	private JComboBox comboBox;
	
	Korisnik korisnik;
	
	public StudentRezervacije(Korisnik k){
		korisnik = k;
		setTitle("Rezervacije");
		getContentPane().setLayout(null);
		
		JLabel lblKnjiga = new JLabel("Knjiga:");
		lblKnjiga.setBounds(16, 21, 130, 16);
		getContentPane().add(lblKnjiga);
		
		comboBox = new JComboBox();
		comboBox.setBounds(148, 17, 284, 27);
		getContentPane().add(comboBox);
		
		JLabel lblDatumPreuzimanja = new JLabel("Datum preuzimanja:");
		lblDatumPreuzimanja.setBounds(16, 54, 130, 16);
		getContentPane().add(lblDatumPreuzimanja);
		
		JLabel lblDdmmgggg = new JLabel("dd.mm.gggg");
		lblDdmmgggg.setForeground(Color.GRAY);
		lblDdmmgggg.setBounds(16, 75, 130, 16);
		getContentPane().add(lblDdmmgggg);
		
		txtDatum = new JTextField();
		txtDatum.setBounds(148, 49, 284, 26);
		getContentPane().add(txtDatum);
		txtDatum.setColumns(10);
		
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(16, 106, 416, 122);
		getContentPane().add(scrollPane);
		
		btnRezervisati = new JButton("Rezervisati");
		btnRezervisati.setBounds(238, 240, 99, 29);
		getContentPane().add(btnRezervisati);
		
		JButton btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(333, 240, 99, 29);
		getContentPane().add(btnZatvoriti);
		
		btnRezervisati.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(korisnik.getRezervacijaCollection()!=null && korisnik.getRezervacijaCollection().size()>2){
					JOptionPane.showMessageDialog(null, "Maksimalno možete imati 3 aktivne rezervacije.", "Greška", JOptionPane.ERROR_MESSAGE);					
					return;
				}
				
				if(comboBox.getSelectedIndex()==-1){
					JOptionPane.showMessageDialog(null, "Polje Knjiga je obavezno.", "Greška", JOptionPane.ERROR_MESSAGE);					
					return;
				}
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Date datumPreuzimanja;
				long datum;
				try {
					datumPreuzimanja = dateFormat.parse(txtDatum.getText());
					datum = datumPreuzimanja.getTime();
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "Pogrešan format datuma. Unesite datum u obliku dd.mm.gggg", "Greška", JOptionPane.ERROR_MESSAGE);
					txtDatum.requestFocus();
					return;
				}								
						Rezervacija r = new Rezervacija();
						r.setKorisnik(korisnik);
						r.setPrimjerak((Primjerak)comboBox.getSelectedItem());
						r.setDatumPreuzimanja(datum/1000);
			            try{
			            	service.create(r);
			            	
			            	KorisnikService ks = new KorisnikService();
			            	Korisnik k = ks.find(korisnik.getSifra());
			            	ks.refresh(k);
			            		
			            	txtDatum.setText("");
			                osvjeziTabelu();
			                osvjeziCombo();			                
			            } catch(Exception ex){
			            	ex.printStackTrace();
			            	JOptionPane.showMessageDialog(null, "Pojavila se greska prilikom spremanja u bazu podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
			            }												
			}			
		});		
		
		osvjeziTabelu();		
		osvjeziCombo();        
		
		btnZatvoriti.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				StudentPanel panel=new StudentPanel(korisnik);
				panel.setResizable(false);
				panel.setSize(new Dimension(470, 175));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        panel.setLocation(dim.width/2-panel.getSize().width/2, dim.height/2-panel.getSize().height/2);
		        panel.getContentPane().setBackground(new Color(245, 245, 245));
		        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        dispose();
		        panel.setVisible(true);		        			
			}
			
		});
	}
	
	private void osvjeziCombo(){
		for(int i=0;i<comboBox.getItemCount();i++){
            comboBox.removeItemAt(0);
         }
		KorisnikService ks = new KorisnikService();
    	Korisnik k = ks.find(korisnik.getSifra());
    	korisnik = k;
		PrimjerakService primjerakService = new PrimjerakService();
		List<Primjerak> knjige = primjerakService.findAllSortedAsc("invBroj");
		for(Primjerak p : knjige){
        	primjerakService.refresh(p);
        	boolean vecIma = false;
        	for(Rezervacija r : korisnik.getRezervacijaCollection()){
        		if(r.getPrimjerak().getKnjigaId().getKnjigaId()==p.getKnjigaId().getKnjigaId()){
        			vecIma = true;
        			break;
        		}
        	}
        	if(!vecIma){
        	if(p.getRezervacijaCollection()!=null && p.getRezervacijaCollection().size()>0){
        		continue;
        	}
        	if(p.getPosudbaCollection()==null || p.getPosudbaCollection().size()==0){
        		comboBox.addItem(p);
        		continue;
        	}
        	for(Posudba posudba : p.getPosudbaCollection()){
        		(new PosudbaService()).refresh(posudba);
        		if(posudba.getDatumVracanja()>0){
        			comboBox.addItem(p);
        			break;
        		}
        	}
        	}
        }
	}
	
	private void osvjeziTabelu(){		
		tableModel=new DefaultTableModel(tabelaKolone, 0){
	    	@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };
        
	    KorisnikService ks = new KorisnikService();
    	Korisnik k = ks.find(korisnik.getSifra());
    	korisnik = k;
		Collection<Rezervacija> lista = k.getRezervacijaCollection();
            
        for(Rezervacija rezervacija : lista){
        	String datumPreuzimanja = "";
        	if(rezervacija.getDatumPreuzimanja() > 0){
        		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        		Date datum2 = new Date(rezervacija.getDatumPreuzimanja()*1000);            
        		datumPreuzimanja = dateFormat.format(datum2);
        	}
        	Object[] data={rezervacija.getPrimjerak().getKnjigaId().toString(), datumPreuzimanja, "5 dana"};            
            tableModel.addRow(data);
        }
        
        table.setModel(tableModel); 
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);            
	}

}
