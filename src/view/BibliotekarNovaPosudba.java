package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import service.KnjigaService;
import service.KorisnikService;
import service.NastavnikService;
import service.PosudbaService;
import service.PrimjerakService;
import model.Knjiga;
import model.Korisnik;
import model.Nastavnik;
import model.Posudba;
import model.Primjerak;
import model.Rezervacija;

public class BibliotekarNovaPosudba extends JFrame {
	private JTable tabelaKorisnik;
	private JTextField txtFilterKorisnik;
	private JTable tabelaKnjiga;	
	private JTextField txtFilterKnjiga;
	private JComboBox cmbRok;
	private JComboBox cmbFilterKorisnik;
	private JComboBox cmbFilterKnjiga;
	
	private String tabelaKnjigaKolone[] = {"Inv. broj", "Naziv", "Stanje"};
    private DefaultTableModel tableKnjigaModel=new DefaultTableModel(tabelaKnjigaKolone, 0);
	
    private String tabelaKorisnikaKolone[] = {"Sifra", "Prezime", "Ime", "Negativni bodovi"};
    private DefaultTableModel tableKorisnikaModel=new DefaultTableModel(tabelaKorisnikaKolone, 0);
	
	private Korisnik korisnik; 
	
	private PrimjerakService primjerakService = new PrimjerakService();
	private KorisnikService korisnikService = new KorisnikService();
	private PosudbaService service = new PosudbaService();
	
	private Primjerak odabraniPrimjerak;
	private Korisnik odabraniKorisnik;
	
	
	private void osvjeziTabeluKnjiga(){
		tableKnjigaModel=new DefaultTableModel(tabelaKnjigaKolone, 0){
	    	@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };        
		List<Primjerak> lista = new ArrayList<Primjerak>();
		if(txtFilterKnjiga.getText().length()==0){
            for(Primjerak p : primjerakService.findAll()){
            	primjerakService.refresh(p);
            	if(p.getRezervacijaCollection()!=null){
            		for(Rezervacija r : p.getRezervacijaCollection()){
            			int prosloDana = (int) ((new Date()).getTime()/1000 - r.getDatumPreuzimanja());
            			prosloDana = prosloDana / (60*60*24);
            			if(prosloDana>5){
            				lista.add(p);            			
            			}            			
            		}
            	}
            	if(p.getPosudbaCollection()==null || p.getPosudbaCollection().size()==0){
            		lista.add(p);
            		continue;
            	}
            	for(Posudba posudba : p.getPosudbaCollection()){
            		if(posudba.getDatumVracanja()>0){
            			lista.add(p);
            			break;
            		}
            	}
            }
            
        } else{
        	String field = "";
        	if(cmbFilterKnjiga.getSelectedIndex()==0){
        		field="invBroj";
        		lista=primjerakService.findLikeBy("%" + txtFilterKnjiga.getText() + "%", field);
        	} else if(cmbFilterKnjiga.getSelectedIndex()==1){
        		field="naslov";        		
                KnjigaService ks = new KnjigaService();
                lista=new ArrayList<Primjerak>();
                for(Knjiga k : ks.findLikeBy("%" + txtFilterKnjiga.getText() + "%", field)){
                    lista.addAll(k.getPrimjerakCollection());
                }
        	}                     
        }        
        for(Primjerak primjerak : lista){
            Object[] data={primjerak.getInvBroj(), primjerak.getKnjigaId().getNaslov(), primjerak.getStanje()};            
            tableKnjigaModel.addRow(data);
        }
        odabraniPrimjerak=null;
    
        tabelaKnjiga.setModel(tableKnjigaModel); 
        tabelaKnjiga.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	private void osvjeziTabeluKorisnika(){
		tableKorisnikaModel=new DefaultTableModel(tabelaKorisnikaKolone, 0){
	    	@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };        
		List<Korisnik> lista = new ArrayList<Korisnik>();
		if(txtFilterKorisnik.getText().length()==0){
            lista=korisnikService.findAll();
        } else{
        	String field = "";
        	if(cmbFilterKorisnik.getSelectedIndex()==0){
        		field="sifra";
        		lista=korisnikService.findLikeBy("%" + txtFilterKorisnik.getText() + "%", field);
        	} else if(cmbFilterKorisnik.getSelectedIndex()==1){
        		field="ime";        		
        		lista=korisnikService.findLikeBy("%" + txtFilterKorisnik.getText() + "%", field);
        	} else if(cmbFilterKorisnik.getSelectedIndex()==2){
        		field="prezime";        		
        		lista=korisnikService.findLikeBy("%" + txtFilterKorisnik.getText() + "%", field);
        	}                     
        }        
        for(Korisnik k : lista){
            Object[] data={k.getSifra(), k.getPrezime(), k.getIme(), k.getNegativniBodovi()};            
            tableKorisnikaModel.addRow(data);
        }
        odabraniKorisnik=null;
    
        tabelaKorisnik.setModel(tableKorisnikaModel); 
        tabelaKorisnik.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public BibliotekarNovaPosudba(Korisnik k) {
		setTitle("Kreiranje nove posudbe");
		korisnik = k;
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Rok za vracanje (broj dana):");
		lblNewLabel.setBounds(16, 18, 184, 16);
		getContentPane().add(lblNewLabel);
		
		cmbRok = new JComboBox();
		cmbRok.setModel(new DefaultComboBoxModel(new String[] {"7", "15", "30", "45"}));
		cmbRok.setBounds(198, 14, 124, 27);
		getContentPane().add(cmbRok);
		
		JLabel lblKorisnik = new JLabel("Korisnik:");
		lblKorisnik.setBounds(16, 46, 61, 16);
		getContentPane().add(lblKorisnik);
		
		tabelaKorisnik = new JTable();
		JScrollPane scrollPane = new JScrollPane(tabelaKorisnik);
		scrollPane.setBounds(89, 46, 444, 85);
		getContentPane().add(scrollPane);
		
		JLabel lblFilterPo = new JLabel("Filter po:");
		lblFilterPo.setBounds(89, 143, 61, 16);
		getContentPane().add(lblFilterPo);
		
		cmbFilterKorisnik = new JComboBox();
		cmbFilterKorisnik.setModel(new DefaultComboBoxModel(new String[] {"sifra", "ime", "prezime"}));
		cmbFilterKorisnik.setBounds(147, 139, 97, 27);
		getContentPane().add(cmbFilterKorisnik);
		
		txtFilterKorisnik = new JTextField();
		txtFilterKorisnik.setBounds(242, 141, 216, 23);
		getContentPane().add(txtFilterKorisnik);
		txtFilterKorisnik.setColumns(10);
		
		JButton btnFilterKorisnik = new JButton("Filter");
		btnFilterKorisnik.setBounds(455, 140, 76, 29);
		getContentPane().add(btnFilterKorisnik);
		
		JLabel lblPrimjerak = new JLabel("Knjiga:");
		lblPrimjerak.setBounds(16, 180, 61, 16);
		getContentPane().add(lblPrimjerak);
		
		tabelaKnjiga = new JTable();
		JScrollPane scrollPaneKnjiga = new JScrollPane(tabelaKnjiga);
		scrollPaneKnjiga.setBounds(89, 180, 444, 85);
		getContentPane().add(scrollPaneKnjiga);
		
		JLabel label_1 = new JLabel("Filter po:");
		label_1.setBounds(89, 277, 61, 16);
		getContentPane().add(label_1);
		
		cmbFilterKnjiga = new JComboBox();
		cmbFilterKnjiga.setModel(new DefaultComboBoxModel(new String[] {"inv. broj", "naziv"}));
		cmbFilterKnjiga.setBounds(147, 273, 97, 27);
		getContentPane().add(cmbFilterKnjiga);
		
		txtFilterKnjiga = new JTextField();
		txtFilterKnjiga.setColumns(10);
		txtFilterKnjiga.setBounds(242, 275, 216, 23);
		getContentPane().add(txtFilterKnjiga);
		
		JButton btnFilterKnjiga = new JButton("Filter");
		btnFilterKnjiga.setBounds(455, 274, 76, 29);
		getContentPane().add(btnFilterKnjiga);
		
		JButton btnDodati = new JButton("Dodati");
		btnDodati.setBounds(335, 323, 97, 29);
		getContentPane().add(btnDodati);
		
		JButton btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(436, 323, 97, 29);
		getContentPane().add(btnZatvoriti);
		
		JButton btnSvePosudbe = new JButton("Sve posudbe");
		btnSvePosudbe.setBounds(12, 323, 110, 29);
		getContentPane().add(btnSvePosudbe);
		
		osvjeziTabeluKnjiga();
		osvjeziTabeluKorisnika();
		
		btnFilterKnjiga.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				osvjeziTabeluKnjiga();				
			}			
		});
		
		btnFilterKorisnik.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				osvjeziTabeluKorisnika();				
			}			
		});
		
		btnDodati.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
			if (tabelaKorisnik.getSelectedRowCount()==0) {
	            JOptionPane.showMessageDialog(null, "Niste odabrali nijednog korisnika.", "Greška", JOptionPane.WARNING_MESSAGE);
	        } else if (tabelaKnjiga.getSelectedRowCount()==0) {
	            JOptionPane.showMessageDialog(null, "Niste odabrali nijednu knjigu.", "Greška", JOptionPane.WARNING_MESSAGE);
	        } else {
	        	String invBroj = (String) tabelaKnjiga.getModel().getValueAt(tabelaKnjiga.getSelectedRow(), 0);
	        	String sifraKorisnika = (String) tabelaKorisnik.getModel().getValueAt(tabelaKorisnik.getSelectedRow(), 0);
			    odabraniKorisnik=korisnikService.find(sifraKorisnika);
			    odabraniPrimjerak=primjerakService.find(invBroj);
			    Posudba p = new Posudba();
			    p.setDatumPosudbe((new Date()).getTime()/1000);
			    p.setRokVracanja(Integer.parseInt((String)cmbRok.getSelectedItem()));
			    p.setKorisnik(odabraniKorisnik);
			    p.setPrimjerak(odabraniPrimjerak);
				try{
	            	service.create(p);
	            	txtFilterKnjiga.setText("");
	            	txtFilterKorisnik.setText("");
	            	cmbRok.setSelectedIndex(0);
	            	osvjeziTabeluKnjiga();
	            	osvjeziTabeluKorisnika();
	            	JOptionPane.showMessageDialog(null, "Posudba je uspješno kreirana.", "Posudba", JOptionPane.INFORMATION_MESSAGE);
	            } catch(Exception ex){
	            	JOptionPane.showMessageDialog(null, "Pojavila se greška prilikom spremanja u bazu podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
	            }				    	        
			}					
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
		
		btnSvePosudbe.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				BibliotekarSvePosudbe panel=new BibliotekarSvePosudbe(korisnik);
				panel.setResizable(false);
				panel.setSize(new Dimension(505, 375));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        panel.setLocation(dim.width/2-panel.getSize().width/2, dim.height/2-panel.getSize().height/2);
		        panel.getContentPane().setBackground(new Color(245, 245, 245));
		        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        dispose();
		        panel.setVisible(true);		        			
			}
			
		});
	}
}
