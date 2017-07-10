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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import service.IzdavacService;
import service.KnjigaService;
import model.Izdavac;
import model.Knjiga;
import model.Korisnik;


public class BibliotekarKnjige extends JFrame {
	private JTextField txtNaslov;
	private JTextField txtBrojStrana;
	private JTextField txtGodinaIzdanja;
	private JTextField txtNegativniBodovi;
	
	private enum Mod {
        IZMJENA, DODAVANJE
    }

    private KnjigaService service = new KnjigaService();
    private Mod trenutniMod = Mod.DODAVANJE;
    private Knjiga odabraniObjekat = null;
    private String tabelaKolone[] = {"ID", "Naslov", "Godina", "Izdavac"};
    private DefaultTableModel tableModel=new DefaultTableModel(tabelaKolone, 0);
    private JTable tabela;    
    private JTextField txtFilter;
    private JButton btnDodati;
    private JButton btnIzmijeniti;
    private JButton btnObrisati;
    private JButton btnFilter;
    private JComboBox cmbFilter; 
    private JComboBox cmbIzdavac;
    private JComboBox cmbTip;
    private JButton btnAutori;
    private JComboBox cmbFilterTip;
    
	private Korisnik korisnik;
	
	public BibliotekarKnjige(Korisnik k) {
		korisnik = k;
		setTitle("Knjige");
		getContentPane().setLayout(null);
		
		JLabel lblNaziv = new JLabel("Naslov:");
		lblNaziv.setBounds(17, 14, 123, 16);
		getContentPane().add(lblNaziv);
		
		JLabel lblBrojStrana = new JLabel("Broj strana:");
		lblBrojStrana.setBounds(17, 42, 123, 16);
		getContentPane().add(lblBrojStrana);
		
		JLabel lblGodinaIzdanja = new JLabel("Godina izdanja:");
		lblGodinaIzdanja.setBounds(241, 42, 123, 16);
		getContentPane().add(lblGodinaIzdanja);
		
		JLabel lblIzdava = new JLabel("Izdavač:");
		lblIzdava.setBounds(17, 70, 123, 16);
		getContentPane().add(lblIzdava);
		
		JLabel lblTip = new JLabel("Tip:");
		lblTip.setBounds(17, 98, 123, 16);
		getContentPane().add(lblTip);
		
		JLabel lblNegativniBodovi = new JLabel("Negativni bodovi:");
		lblNegativniBodovi.setBounds(17, 126, 123, 16);
		getContentPane().add(lblNegativniBodovi);
		
		txtNaslov = new JTextField();
		txtNaslov.setBounds(152, 9, 381, 26);
		getContentPane().add(txtNaslov);
		txtNaslov.setColumns(10);
		
		txtBrojStrana = new JTextField();
		txtBrojStrana.setColumns(10);
		txtBrojStrana.setBounds(152, 37, 53, 26);
		getContentPane().add(txtBrojStrana);
		
		txtGodinaIzdanja = new JTextField();
		txtGodinaIzdanja.setColumns(10);
		txtGodinaIzdanja.setBounds(368, 37, 53, 26);
		getContentPane().add(txtGodinaIzdanja);
		
		txtNegativniBodovi = new JTextField();
		txtNegativniBodovi.setColumns(10);
		txtNegativniBodovi.setBounds(152, 121, 53, 26);
		getContentPane().add(txtNegativniBodovi);
		
		cmbIzdavac = new JComboBox();
		cmbIzdavac.setBounds(152, 66, 381, 27);
		getContentPane().add(cmbIzdavac);
		
		cmbTip = new JComboBox();
		cmbTip.setModel(new DefaultComboBoxModel(new String[] {"knjiga", "časopis", "diplomski rad", "magistarski rad", "doktorski rad", "ostalo"}));
		cmbTip.setBounds(152, 94, 381, 27);
		getContentPane().add(cmbTip);
		
		tabela = new JTable();
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setBounds(17, 162, 516, 174);
		getContentPane().add(scrollPane);
		
		btnObrisati = new JButton("Obrisati");
		btnObrisati.setBounds(17, 382, 94, 29);
		getContentPane().add(btnObrisati);
		
		JButton btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(439, 382, 94, 29);
		getContentPane().add(btnZatvoriti);
		
		btnIzmijeniti = new JButton("Izmijeniti");
		btnIzmijeniti.setBounds(347, 382, 94, 29);
		getContentPane().add(btnIzmijeniti);
		
		btnAutori = new JButton("Autori");
		btnAutori.setBounds(160, 382, 94, 29);
		getContentPane().add(btnAutori);
		
		btnDodati = new JButton("Dodati");
		btnDodati.setBounds(254, 382, 94, 29);		
		getContentPane().add(btnDodati);
		
		JLabel lblFilterPoPolju = new JLabel("Filter po polju:");
		lblFilterPoPolju.setBounds(27, 348, 113, 16);
		getContentPane().add(lblFilterPoPolju);
		
		cmbFilter = new JComboBox();
		cmbFilter.setModel(new DefaultComboBoxModel(new String[] {"naslov", "naziv izdavaca", "tip", "ime autora", "prezime autora"}));
		cmbFilter.setBounds(123, 344, 131, 27);
		getContentPane().add(cmbFilter);
		
		cmbFilter.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        if(cmbFilter.getSelectedIndex()==2){
		        	txtFilter.setVisible(false);
		        	cmbFilterTip.setVisible(true);
		        } else{
		        	txtFilter.setVisible(true);
		        	cmbFilterTip.setVisible(false);
		        }
		    }
		});
		
		txtFilter = new JTextField();
		txtFilter.setBounds(254, 343, 216, 26);
		getContentPane().add(txtFilter);
		txtFilter.setColumns(10);
		
		cmbFilterTip = new JComboBox();
		cmbFilterTip.setModel(new DefaultComboBoxModel(new String[] {"knjiga", "časopis", "diplomski rad", "magistarski rad", "doktorski rad", "ostalo"}));
		cmbFilterTip.setBounds(254, 343, 216, 26);
		getContentPane().add(cmbFilterTip);
		cmbFilterTip.setVisible(false);
		
		btnFilter = new JButton("Filter");
		btnFilter.setBounds(468, 343, 64, 29);
		getContentPane().add(btnFilter);
		
		osvjeziTabelu();
		
		IzdavacService izdavacService = new IzdavacService();
		List<Izdavac> izdavaci = izdavacService.findAll();
		for(Izdavac i : izdavaci){
			cmbIzdavac.addItem(i);
		}
		
		btnDodati.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(txtNaslov.getText().length()==0){
					JOptionPane.showMessageDialog(null, "Polje Naslov je obavezno.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtNaslov.requestFocus();
					return;
				}				
				if(cmbIzdavac.getSelectedIndex()==-1){
					JOptionPane.showMessageDialog(null, "Polje Izdavač je obavezno.", "Greška", JOptionPane.ERROR_MESSAGE);					
					return;
				}
				if(GlobalHelper.isNumber(txtBrojStrana.getText())==false){
					JOptionPane.showMessageDialog(null, "Polje Broj strana mora biti broj.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtBrojStrana.requestFocus();
					return;
				}				
				if(GlobalHelper.isNumber(txtGodinaIzdanja.getText())==false){
					JOptionPane.showMessageDialog(null, "Polje Godina izdanja mora biti broj.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtGodinaIzdanja.requestFocus();
					return;
				}
				if(GlobalHelper.isNumber(txtNegativniBodovi.getText())==false){
					JOptionPane.showMessageDialog(null, "Polje Negativni bodovi mora biti broj.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtNegativniBodovi.requestFocus();
					return;
				}
				
				switch(trenutniMod){
					case DODAVANJE:{						
						Knjiga k = new Knjiga();            
			            k.setNaslov(txtNaslov.getText());
			            k.setBrojStranica(Integer.parseInt(txtBrojStrana.getText()));
			            k.setGodinaIzdanja(Integer.parseInt(txtGodinaIzdanja.getText()));
			            k.setIzdavacId((Izdavac)cmbIzdavac.getSelectedItem());
			            k.setNegativniBodovi(Integer.parseInt(txtNegativniBodovi.getText()));
			            k.setTip((short)cmbTip.getSelectedIndex());			            
			            try{
			            	service.create(k);
			            	txtNaslov.setText("");
			            	txtBrojStrana.setText("");
			            	txtGodinaIzdanja.setText("");
			            	cmbIzdavac.setSelectedIndex(-1);
			            	cmbTip.setSelectedIndex(0);
			            	txtNegativniBodovi.setText("");
			                osvjeziTabelu();			            
			            } catch(Exception ex){
			            	JOptionPane.showMessageDialog(null, "Pojavila se greška prilikom spremanja u bazu podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
			            }				            
					}
					break;
					case IZMJENA:{						
						Integer id = (Integer) tabela.getModel().getValueAt(tabela.getSelectedRow(), 0);
			            odabraniObjekat=service.find(id);
			            Knjiga k = odabraniObjekat;
			            k.setNaslov(txtNaslov.getText());
			            k.setBrojStranica(Integer.parseInt(txtBrojStrana.getText()));
			            k.setGodinaIzdanja(Integer.parseInt(txtGodinaIzdanja.getText()));
			            k.setIzdavacId((Izdavac)cmbIzdavac.getSelectedItem());
			            k.setNegativniBodovi(Integer.parseInt(txtNegativniBodovi.getText()));
			            k.setTip((short)cmbTip.getSelectedIndex());		
			            try{
			            	service.edit(k);
			            	setDefaultMod();
			            	osvjeziTabelu();
			            	JOptionPane.showMessageDialog(null, "Podaci su uspješno izmijenjeni.", "Izmjena", JOptionPane.INFORMATION_MESSAGE);
			            } catch (Exception ex){
			            	JOptionPane.showMessageDialog(null, "Pojavila se greška prilikom spremanja u bazu podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
			            }			            
					}
					break;
				}
				
			}
			
		});
		
		btnFilter.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				osvjeziTabelu();				
			}			
		});
		
		btnIzmijeniti.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
			if (tabela.getSelectedRowCount()==0) {
	            JOptionPane.showMessageDialog(null, "Niste odabrali nijedan unos.", "Greška", JOptionPane.WARNING_MESSAGE);
	        } else {
	            if (trenutniMod == Mod.DODAVANJE) {
	                trenutniMod = Mod.IZMJENA;
	            } else {
	                trenutniMod = Mod.DODAVANJE;
	            }
	            switch (trenutniMod) {
	                case DODAVANJE:
	                    setDefaultMod();
	                    break;
	                case IZMJENA:	                	
	                    Integer id = (Integer) tabela.getModel().getValueAt(tabela.getSelectedRow(), 0);
	                    odabraniObjekat=service.find(id);
	                    btnDodati.setText("Spremiti");
	                    btnIzmijeniti.setText("Odbaciti");
	                    btnObrisati.setVisible(false);
	                    btnFilter.setEnabled(false);
	                    tabela.setEnabled(false);	                    
	                    txtNaslov.setText(odabraniObjekat.getNaslov());
		            	txtBrojStrana.setText("" + odabraniObjekat.getBrojStranica());
		            	txtGodinaIzdanja.setText("" + odabraniObjekat.getGodinaIzdanja());
		            	cmbIzdavac.setSelectedItem(odabraniObjekat.getIzdavacId());
		            	cmbTip.setSelectedIndex(odabraniObjekat.getTip());
		            	txtNegativniBodovi.setText("" + odabraniObjekat.getNegativniBodovi());
		            	txtNaslov.requestFocus();
	                    break;	                    
	            }
	        }			
			}
		});
		
		btnObrisati.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRowCount()==0) {
		            JOptionPane.showMessageDialog(null, "Niste odabrali nijedan unos.", "Greška", JOptionPane.WARNING_MESSAGE);
		        } else{
		            Integer id = (Integer) tabela.getModel().getValueAt(tabela.getSelectedRow(), 0);
		            odabraniObjekat=service.find(id);
		             int odgovor = JOptionPane.showConfirmDialog(null,
		                "Jeste li sigurni da želite obrisati unos " + odabraniObjekat.toString() + "?",
		                "Brisanje",
		                JOptionPane.YES_NO_OPTION);
		            if(odgovor==JOptionPane.YES_OPTION){
		            	try{
		            		service.remove(odabraniObjekat);
		            		osvjeziTabelu();
		                    JOptionPane.showMessageDialog(null, "Unos uspješno obrisan iz baze podataka.", "Brisanje", JOptionPane.INFORMATION_MESSAGE);
		            	} catch(Exception ex){
		            		JOptionPane.showMessageDialog(null, "Dogodila se greška prilikom brisanja.", "Greška", JOptionPane.ERROR_MESSAGE);
		            	}		                    		                
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
		
		btnAutori.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRowCount()==0) {
		            JOptionPane.showMessageDialog(null, "Niste odabrali nijedan unos.", "Greška", JOptionPane.WARNING_MESSAGE);
		        } else{
		        	Integer id = (Integer) tabela.getModel().getValueAt(tabela.getSelectedRow(), 0);
		            odabraniObjekat=service.find(id);
		        	KnjigaAutori panel=new KnjigaAutori(korisnik, odabraniObjekat);
		        	panel.setResizable(false);
		        	panel.setSize(new Dimension(455, 310));
		        	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        	panel.setLocation(dim.width/2-panel.getSize().width/2, dim.height/2-panel.getSize().height/2);
		        	panel.getContentPane().setBackground(new Color(245, 245, 245));
		        	panel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        	dispose();
		        	panel.setVisible(true);	
		        }
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
        
		List<Knjiga> lista = new ArrayList<Knjiga>();
		String field = "";
		if(txtFilter.getText().length()==0 && cmbFilter.getSelectedIndex()!=2){
            lista=service.findAll();
        } else if(cmbFilter.getSelectedIndex()==1){
            field="naziv";
            IzdavacService is = new IzdavacService();
            lista=new ArrayList<Knjiga>();
            for(Izdavac i : is.findLikeBy("%" + txtFilter.getText() + "%", field)){
                lista.addAll(i.getKnjigaCollection());
            }
        } else{        	
        	String filterText = "";
        	if(cmbFilter.getSelectedIndex()==0){
        		field="naslov";
        		filterText = txtFilter.getText();
        		lista=service.findLikeBy("%" + filterText + "%", field);
        	} else if(cmbFilter.getSelectedIndex()==2){
        		field="tip";        		
        		lista=service.findBy(cmbFilterTip.getSelectedIndex(), field);        		
        	}                       
        }        
        for(Knjiga knjiga : lista){
            Object[] data={knjiga.getKnjigaId(), knjiga.getNaslov(), knjiga.getGodinaIzdanja(), knjiga.getIzdavacId().getNaziv()};            
            tableModel.addRow(data);
        }
        odabraniObjekat=null;
    
        tabela.setModel(tableModel); 
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	private void setDefaultMod() {		
        trenutniMod=Mod.DODAVANJE;
        btnDodati.setText("Dodati");
        btnFilter.setEnabled(true);
        btnIzmijeniti.setText("Izmijeniti");
        btnObrisati.setVisible(true);
        tabela.setEnabled(true);
        txtNaslov.setText("");
    	txtBrojStrana.setText("");
    	txtGodinaIzdanja.setText("");
    	cmbIzdavac.setSelectedIndex(-1);
    	cmbTip.setSelectedIndex(0);
    	txtNegativniBodovi.setText("");
    }
	
}
