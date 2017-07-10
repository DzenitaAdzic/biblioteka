package view;

import helper.GlobalHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import model.Izdavac;
import model.Knjiga;
import model.Korisnik;
import model.Nastavnik;
import model.Posudba;
import model.Predmet;
import model.Primjerak;
import model.Rezervacija;
import model.Student;
import service.IzdavacService;
import service.KnjigaService;
import service.PosudbaService;
import service.KorisnikService;
import service.NastavnikService;
import service.RezervacijaService;
import service.ServiceException;
import service.StudentService;

public class BibliotekarRezervacije extends JFrame {
	private JTextField txtSifra;
	private JTextField txtIme;
	private JTextField txtPrezime;
	private JTextField txtNegativniBodovi;
	private JTextField txtBrojIndeksa;
	private JPasswordField txtPassword;
	private List<Rezervacija> lista;
	
	private enum Mod {
        IZMJENA, DODAVANJE
    }

    private RezervacijaService service = new RezervacijaService();
    private Mod trenutniMod = Mod.DODAVANJE;
    private Student odabraniObjekat = null;
    private String tabelaKolone[] = {"Inv. broj", "Sifra korisnika", "Korisnik", "Knjiga", "Datum preuzimanja", "Rok preuzimanja"};
    private DefaultTableModel tableModel=new DefaultTableModel(tabelaKolone, 0);
	private JTable table;
	private JTextField txtFilter;
	private JButton btnDodati;
    private JButton btnIzmijeniti;
    private JButton btnObrisati;
    private JButton btnFilter;
    private JButton btnPosudba;
    private JButton btnVratiKnjigu;
    private JComboBox cmbFilter;
	private Korisnik korisnik;
    
	public BibliotekarRezervacije(Korisnik k) {
		this.korisnik = k;
		setTitle("Rezervacije");
		getContentPane().setLayout(null);
		
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(18, 14, 483, 270);
		getContentPane().add(scrollPane);
		
		JLabel lblFilterPoPolju = new JLabel("Filter po polju:");
		lblFilterPoPolju.setBounds(18, 302, 92, 16);
		getContentPane().add(lblFilterPoPolju);
		
		cmbFilter = new JComboBox();
		cmbFilter.setModel(new DefaultComboBoxModel(new String[] {"sifra korisnika", "inventarni broj", "naziv knjige"}));
		cmbFilter.setBounds(116, 302, 108, 27);
		getContentPane().add(cmbFilter);
		
		txtFilter = new JTextField();
		txtFilter.setBounds(225, 301, 215, 26);
		getContentPane().add(txtFilter);
		txtFilter.setColumns(10);
		
		btnFilter = new JButton("Filter");
		btnFilter.setBounds(435, 301, 66, 29);
		getContentPane().add(btnFilter);
		
		btnObrisati = new JButton("Obrisati");
		btnObrisati.setBounds(18, 393, 92, 29);
		getContentPane().add(btnObrisati);
		btnObrisati.setVisible(false);
		
		btnVratiKnjigu = new JButton("Vrati knjigu");
		btnVratiKnjigu.setBounds(135, 440, 110, 29);
		getContentPane().add(btnVratiKnjigu);
		
		JButton btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(409, 340, 92, 29);
		getContentPane().add(btnZatvoriti);
		
		btnIzmijeniti = new JButton("Izmijeniti");
		btnIzmijeniti.setBounds(324, 393, 92, 29);
		getContentPane().add(btnIzmijeniti);
		btnIzmijeniti.setVisible(false);
		
		btnDodati = new JButton("Dodati");
		btnDodati.setBounds(235, 393, 92, 29);
		getContentPane().add(btnDodati);
		btnDodati.setVisible(false);
		
		btnPosudba = new JButton("Posudi");
		btnPosudba.setBounds(18, 340, 110, 29);
		getContentPane().add(btnPosudba);
		btnPosudba.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRowCount()==0) {
		            JOptionPane.showMessageDialog(null, "Niste odabrali nijednu rezervaciju.", "Greška", JOptionPane.WARNING_MESSAGE);
		        } else {
		        	Rezervacija odabranaR = lista.get(table.getSelectedRow());
		        	Posudba p = new Posudba();
				    p.setDatumPosudbe((new Date()).getTime()/1000);
				    p.setRokVracanja(30);
				    p.setKorisnik(odabranaR.getKorisnik());
				    p.setPrimjerak(odabranaR.getPrimjerak());
					try{
		            	(new PosudbaService()).create(p);
		            	service.remove(odabranaR);
		            	osvjeziTabelu();
		            	JOptionPane.showMessageDialog(null, "Posudba je uspješno kreirana.", "Posudba", JOptionPane.INFORMATION_MESSAGE);
		            } catch(Exception ex){
		            	JOptionPane.showMessageDialog(null, "Pojavila se greška prilikom spremanja u bazu podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
		            }
		        }
			}			
		});	
		
		
		JButton btnOsvjeziti = new JButton("Ocistiti listu");
		btnOsvjeziti.setBounds(120, 340, 150, 29);
		getContentPane().add(btnOsvjeziti);
		
		btnOsvjeziti.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Rezervacija r : service.findAll()){
					if(r.getDatumPreuzimanja()==0){
						try {
							service.remove(r);
						} catch (ServiceException e1) {
							e1.printStackTrace();
						}
					}
					long rokPreuzimanja = r.getDatumPreuzimanja() + 5*24*60*60;
					if((new Date()).getTime()/1000>rokPreuzimanja){
						int brojDana = (int) (((new Date()).getTime()/1000 - rokPreuzimanja)/(60*60*24));
						r.getKorisnik().setNegativniBodovi(r.getKorisnik().getNegativniBodovi() + brojDana * r.getPrimjerak().getKnjigaId().getNegativniBodovi());
						try {
							(new KorisnikService()).editWithoutRefresh(r.getKorisnik());
							service.remove(r);
						} catch (ServiceException e1) {
							e1.printStackTrace();
						}						
					}
				}
				JOptionPane.showMessageDialog(null, "Rezervacije su osvježene.", "Greška", JOptionPane.INFORMATION_MESSAGE);
				osvjeziTabelu();
			}			
		});	
		
		
		btnVratiKnjigu.setVisible(false);
		
		osvjeziTabelu();
		
		btnFilter.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				osvjeziTabelu();				
			}			
		});
		
		btnZatvoriti.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				BibliotekarPanel panel=new BibliotekarPanel(korisnik);
				panel.setResizable(false);
				panel.setSize(new Dimension(610, 250));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        panel.setLocation(dim.width/2-panel.getSize().width/2, dim.height/2-panel.getSize().height/2);
		        panel.getContentPane().setBackground(new Color(245, 245, 245));
		        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        dispose();
		        panel.setVisible(true);		        			
			}			
		});			
	}
	
	private void osvjeziTabelu(){
		tableModel=new DefaultTableModel(tabelaKolone, 0){
	    	@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };        
		lista = new ArrayList<Rezervacija>();
		if(txtFilter.getText().length()==0){
            lista=service.findAll();
        } else{
        	String field = "";
        	if(cmbFilter.getSelectedIndex()==0){
        		field="korisnikSifra";
        		lista=service.findLikeBy("%" + txtFilter.getText() + "%", field);
        	} else if(cmbFilter.getSelectedIndex()==1){
        		field="primjerakInvBroj";
        		lista=service.findLikeBy("%" + txtFilter.getText() + "%", field);
        	} else if(cmbFilter.getSelectedIndex()==2){
        		field = "naslov";
        		KnjigaService ks = new KnjigaService();
                lista=new ArrayList<Rezervacija>();
                for(Knjiga k : ks.findLikeBy("%" + txtFilter.getText() + "%", field)){
                	for(Primjerak p : k.getPrimjerakCollection()){
                		lista.addAll(p.getRezervacijaCollection());
                	}                    
                }                
        	}                    
        }        
        for(Rezervacija rezervacija : lista){
        	String datumPreuzimanja = "";
        	if(rezervacija.getDatumPreuzimanja() > 0){
        		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        		Date datum2 = new Date(rezervacija.getDatumPreuzimanja()*1000);            
        		datumPreuzimanja = dateFormat.format(datum2);
        	}        	
            Object[] data={rezervacija.getPrimjerak().getInvBroj(), rezervacija.getKorisnikSifra(), rezervacija.getKorisnik().toString(), rezervacija.getPrimjerak().getKnjigaId().toString(), datumPreuzimanja, "5 dana"};            
            tableModel.addRow(data);
        }
        odabraniObjekat=null;
    
        table.setModel(tableModel); 
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	private void setDefaultMod() {
        trenutniMod=Mod.DODAVANJE;
        btnDodati.setText("Dodati");
        btnFilter.setEnabled(true);
        btnIzmijeniti.setText("Izmijeniti");
        btnObrisati.setVisible(true);
        table.setEnabled(true);
        txtSifra.setText("");
    	txtIme.setText("");
    	txtPrezime.setText("");
    	txtNegativniBodovi.setText("0");
    	txtPassword.setText("");
    	txtBrojIndeksa.setText("");
    	txtSifra.requestFocus();    	              
    }
}
