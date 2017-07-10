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
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import model.Korisnik;
import model.Nastavnik;
import model.Predmet;
import service.NastavnikService;

public class BibliotekarNastavnici extends JFrame {
	private JTextField txtSifra;
	private JTextField txtIme;
	private JTextField txtPrezime;
	private JTextField txtNegativniBodovi;
	private JTextField txtAkademskoZvanje;
	private JPasswordField txtPassword;
	
	private enum Mod {
        IZMJENA, DODAVANJE
    }

    private NastavnikService service = new NastavnikService();
    private Mod trenutniMod = Mod.DODAVANJE;
    private Nastavnik odabraniObjekat = null;
    private String tabelaKolone[] = {"Sifra", "Ime", "Prezime", "Negativni bodovi"};
    private DefaultTableModel tableModel=new DefaultTableModel(tabelaKolone, 0);
	private JTable table;
	private JTextField txtFilter;
	private JButton btnDodati;
    private JButton btnIzmijeniti;
    private JButton btnObrisati;
    private JButton btnFilter;
    private JButton btnPredmeti;
    private JComboBox cmbFilter;
	private Korisnik korisnik;
    
	public BibliotekarNastavnici(Korisnik k) {
		this.korisnik = k;
		setTitle("Nastavnici");
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Šifra:");
		lblNewLabel.setBounds(18, 19, 127, 16);
		getContentPane().add(lblNewLabel);
		
		JLabel lblIme = new JLabel("Ime:");
		lblIme.setBounds(18, 47, 127, 16);
		getContentPane().add(lblIme);
		
		JLabel lblPrezime = new JLabel("Prezime:");
		lblPrezime.setBounds(18, 73, 127, 16);
		getContentPane().add(lblPrezime);
		
		JLabel lblAkademskoZvanje = new JLabel("Akademsko zvanje:");
		lblAkademskoZvanje.setBounds(18, 101, 127, 16);
		getContentPane().add(lblAkademskoZvanje);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(18, 129, 92, 16);
		getContentPane().add(lblPassword);
		
		JLabel lblNegativniBodovi = new JLabel("Negativni bodovi:");
		lblNegativniBodovi.setBounds(18, 156, 120, 16);
		getContentPane().add(lblNegativniBodovi);
		
		txtSifra = new JTextField();
		txtSifra.setBounds(157, 14, 82, 26);
		getContentPane().add(txtSifra);
		txtSifra.setColumns(10);
		
		txtIme = new JTextField();
		txtIme.setColumns(10);
		txtIme.setBounds(157, 42, 344, 26);
		getContentPane().add(txtIme);
		
		txtPrezime = new JTextField();
		txtPrezime.setColumns(10);
		txtPrezime.setBounds(157, 68, 344, 26);
		getContentPane().add(txtPrezime);
		
		txtAkademskoZvanje = new JTextField();
		txtAkademskoZvanje.setColumns(10);
		txtAkademskoZvanje.setBounds(157, 96, 344, 26);
		getContentPane().add(txtAkademskoZvanje);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(157, 124, 153, 26);
		getContentPane().add(txtPassword);
		
		txtNegativniBodovi = new JTextField();
		txtNegativniBodovi.setColumns(10);
		txtNegativniBodovi.setBounds(157, 152, 60, 26);
		getContentPane().add(txtNegativniBodovi);
		txtNegativniBodovi.setText("0");
		
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(18, 194, 483, 150);
		getContentPane().add(scrollPane);
		
		JLabel lblFilterPoPolju = new JLabel("Filter po polju:");
		lblFilterPoPolju.setBounds(18, 356, 92, 16);
		getContentPane().add(lblFilterPoPolju);
		
		cmbFilter = new JComboBox();
		cmbFilter.setModel(new DefaultComboBoxModel(new String[] {"sifra", "ime", "prezime"}));
		cmbFilter.setBounds(116, 352, 108, 27);
		getContentPane().add(cmbFilter);
		
		txtFilter = new JTextField();
		txtFilter.setBounds(225, 351, 215, 26);
		getContentPane().add(txtFilter);
		txtFilter.setColumns(10);
		
		btnFilter = new JButton("Filter");
		btnFilter.setBounds(435, 351, 66, 29);
		getContentPane().add(btnFilter);
		
		btnObrisati = new JButton("Obrisati");
		btnObrisati.setBounds(18, 393, 92, 29);
		getContentPane().add(btnObrisati);
		
		JButton btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(409, 393, 92, 29);
		getContentPane().add(btnZatvoriti);
		
		btnIzmijeniti = new JButton("Izmijeniti");
		btnIzmijeniti.setBounds(324, 393, 92, 29);
		getContentPane().add(btnIzmijeniti);
		
		btnDodati = new JButton("Dodati");
		btnDodati.setBounds(235, 393, 92, 29);
		getContentPane().add(btnDodati);
		
		btnPredmeti = new JButton("Predmeti");
		btnPredmeti.setBounds(147, 393, 92, 29);
		getContentPane().add(btnPredmeti);
		
		osvjeziTabelu();
		
		btnDodati.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(txtSifra.getText().length()==0){
					JOptionPane.showMessageDialog(null, "Polje Šifra je obavezno.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtSifra.requestFocus();
					return;
				}
				if(txtIme.getText().length()==0){
					JOptionPane.showMessageDialog(null, "Polje Ime je obavezno.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtIme.requestFocus();
					return;
				}
				if(txtPrezime.getText().length()==0){
					JOptionPane.showMessageDialog(null, "Polje Prezime je obavezno.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtPrezime.requestFocus();
					return;
				}
				if(txtPassword.getText().length()<6){
					JOptionPane.showMessageDialog(null, "Polje Password mora imati minimalno 6 karaktera.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtPassword.requestFocus();
					return;
				}				
				if(txtSifra.getText().length()>10){
					JOptionPane.showMessageDialog(null, "Maksimalna dužina polja Šifra je 10 karaktera.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtSifra.requestFocus();
					return;
				}		
				if(GlobalHelper.isNumber(txtNegativniBodovi.getText())==false){
					JOptionPane.showMessageDialog(null, "Polje Negativni bodovi mora biti broj.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtNegativniBodovi.requestFocus();
					return;
				}
				
				switch(trenutniMod){
					case DODAVANJE:{						
						Nastavnik n = new Nastavnik();
						n.setSifra(txtSifra.getText());
						n.setIme(txtIme.getText());
						n.setPrezime(txtPrezime.getText());
			            n.setKorisnickoIme(txtIme.getText().toLowerCase() + "." + txtPrezime.getText().toLowerCase());			            
			            n.setNegativniBodovi(Integer.parseInt(txtNegativniBodovi.getText()));
			            n.setPassword(txtPassword.getText());
			            n.setAkademskoZvanje(txtAkademskoZvanje.getText());
			            n.setUloga(1);
			            try{
			            	service.create(n);
			            	txtSifra.setText("");
			            	txtIme.setText("");
			            	txtPrezime.setText("");
			            	txtNegativniBodovi.setText("0");
			            	txtPassword.setText("");
			            	txtAkademskoZvanje.setText("");
			            	txtSifra.requestFocus();
			                osvjeziTabelu();			            
			            } catch(Exception ex){
			            	JOptionPane.showMessageDialog(null, "Pojavila se greška prilikom spremanja u bazu podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
			            }	
					}
					break;
					case IZMJENA:{
						String sifra = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
			            odabraniObjekat=service.find(sifra);
			            Nastavnik n = odabraniObjekat;
			            n.setSifra(txtSifra.getText());
						n.setIme(txtIme.getText());
						n.setPrezime(txtPrezime.getText());
						n.setKorisnickoIme(txtIme.getText().toLowerCase() + "." + txtPrezime.getText().toLowerCase());			            
			            n.setNegativniBodovi(Integer.parseInt(txtNegativniBodovi.getText()));
			            n.setPassword(txtPassword.getText());
			            n.setAkademskoZvanje(txtAkademskoZvanje.getText());
			            n.setUloga(1);
			            try{
			            	service.edit(n);
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
			if (table.getSelectedRowCount()==0) {
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
	                	String sifra = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
			            odabraniObjekat=service.find(sifra);
	                    btnDodati.setText("Spremiti");
	                    btnIzmijeniti.setText("Odbaciti");
	                    btnObrisati.setVisible(false);
	                    btnFilter.setEnabled(false);
	                    table.setEnabled(false);	                    
	                    txtSifra.setText(odabraniObjekat.getSifra());
	                    txtIme.setText(odabraniObjekat.getIme());
		            	txtPrezime.setText(odabraniObjekat.getPrezime());
		            	txtNegativniBodovi.setText(odabraniObjekat.getNegativniBodovi() + "");
		            	txtPassword.setText(odabraniObjekat.getPassword());
		            	txtAkademskoZvanje.setText(odabraniObjekat.getAkademskoZvanje());
		            	txtSifra.requestFocus();
	                    break;
	            }
	        }			
			}
		});
		
		btnObrisati.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRowCount()==0) {
		            JOptionPane.showMessageDialog(null, "Niste odabrali nijedan unos.", "Greška", JOptionPane.WARNING_MESSAGE);
		        } else{
		        	String sifra = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
		            odabraniObjekat=service.find(sifra);
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
		
		btnPredmeti.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRowCount()==0) {
		            JOptionPane.showMessageDialog(null, "Niste odabrali nijedan unos.", "Greška", JOptionPane.WARNING_MESSAGE);
		        } else{
		        	String sifra = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
		            odabraniObjekat=service.find(sifra);
		        	NastavnikPredmeti panel=new NastavnikPredmeti(korisnik, odabraniObjekat);
		        	panel.setResizable(false);
		        	panel.setSize(new Dimension(620, 290));
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
		List<Nastavnik> lista = new ArrayList<Nastavnik>();
		if(txtFilter.getText().length()==0){
            lista=service.findAll();
        } else{
        	String field = "";
        	if(cmbFilter.getSelectedIndex()==0){
        		field="sifra";
        		lista=service.findLikeBy("%" + txtFilter.getText() + "%", field);
        	} else if(cmbFilter.getSelectedIndex()==1){
        		field="ime";
        		lista=service.findLikeBy("%" + txtFilter.getText() + "%", field);
        	} else if(cmbFilter.getSelectedIndex()==2){
        		field="prezime";
        		lista=service.findLikeBy("%" + txtFilter.getText() + "%", field);
        	}                       
        }        
        for(Nastavnik nastavnik : lista){
            Object[] data={nastavnik.getSifra(), nastavnik.getIme(), nastavnik.getPrezime(), nastavnik.getNegativniBodovi()};            
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
    	txtAkademskoZvanje.setText("");
    	txtSifra.requestFocus();    	              
    }
}
