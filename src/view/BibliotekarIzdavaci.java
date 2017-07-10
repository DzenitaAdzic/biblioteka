package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import model.Autor;
import model.Izdavac;
import model.Korisnik;
import service.IzdavacService;

import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class BibliotekarIzdavaci extends JFrame {
	private JTextField txtNaziv;
	private JTextField txtSjediste;
	private enum Mod {
        IZMJENA, DODAVANJE
    }

    private IzdavacService service = new IzdavacService();
    private Mod trenutniMod = Mod.DODAVANJE;
    private Izdavac odabraniObjekat = null;
    private String tabelaKolone[] = {"ID", "Naziv", "Sjedište"};
    private DefaultTableModel tableModel=new DefaultTableModel(tabelaKolone, 0);
    private JTable tabela;    
    private JTextField txtFilter;
    private JButton btnDodati;
    private JButton btnIzmijeniti;
    private JButton btnObrisati;
    private JButton btnFilter;
    private JComboBox cmbFilter;    
    private Korisnik korisnik;
    
	public BibliotekarIzdavaci(Korisnik k) {
		this.korisnik = k;
		setTitle("Izdavaci");		
		getContentPane().setLayout(null);
		
		JLabel lblIme = new JLabel("Naziv:");
		lblIme.setBounds(20, 22, 76, 16);
		getContentPane().add(lblIme);
		
		JLabel lblPrezime = new JLabel("Sjedište:");
		lblPrezime.setBounds(20, 50, 76, 16);
		getContentPane().add(lblPrezime);
		
		txtNaziv = new JTextField();
		txtNaziv.setBounds(108, 17, 309, 26);
		getContentPane().add(txtNaziv);
		txtNaziv.setColumns(10);
		
		txtSjediste = new JTextField();
		txtSjediste.setColumns(10);
		txtSjediste.setBounds(108, 45, 309, 26);
		getContentPane().add(txtSjediste);
		
		btnObrisati = new JButton("Obrisati");
		btnObrisati.setBounds(10, 313, 90, 29);
		getContentPane().add(btnObrisati);
		
		btnIzmijeniti = new JButton("Izmijeniti");
		btnIzmijeniti.setBounds(161, 313, 90, 29);
		getContentPane().add(btnIzmijeniti);
		
		JButton btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(334, 313, 90, 29);
		getContentPane().add(btnZatvoriti);
		
		btnDodati = new JButton("Dodati");
		btnDodati.setBounds(248, 313, 90, 29);
		getContentPane().add(btnDodati);
		
		tabela = new JTable();
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setBounds(10, 94, 414, 173);
		getContentPane().add(scrollPane);
		
		JLabel lblFilterPoPolju = new JLabel("Filter po polju:");
		lblFilterPoPolju.setBounds(20, 285, 101, 16);
		getContentPane().add(lblFilterPoPolju);
		
		cmbFilter = new JComboBox();
		cmbFilter.setModel(new DefaultComboBoxModel(new String[] {"naziv", "sjediste"}));
		cmbFilter.setBounds(119, 281, 90, 27);
		getContentPane().add(cmbFilter);
		
		txtFilter = new JTextField();
		txtFilter.setBounds(212, 280, 147, 26);
		getContentPane().add(txtFilter);
		txtFilter.setColumns(10);
		
		btnFilter = new JButton("Filter");
		btnFilter.setBounds(362, 280, 67, 29);
		getContentPane().add(btnFilter);
		
		osvjeziTabelu();
		
		btnDodati.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(txtNaziv.getText().length()==0){
					JOptionPane.showMessageDialog(null, "Polje Naziv je obavezno.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtNaziv.requestFocus();
					return;
				}
				if(txtSjediste.getText().length()==0){
					JOptionPane.showMessageDialog(null, "Polje Sjedište je obavezno.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtSjediste.requestFocus();
					return;
				}
				switch(trenutniMod){
					case DODAVANJE:{						
						Izdavac i = new Izdavac();            
			            i.setNaziv(txtNaziv.getText());
			            i.setSjediste(txtSjediste.getText());
			            try{
			            	service.create(i);
			            	txtNaziv.setText("");
			                txtSjediste.setText("");
			                txtNaziv.requestFocus();
			                osvjeziTabelu();			            
			            } catch(Exception ex){
			            	JOptionPane.showMessageDialog(null, "Pojavila se greška prilikom spremanja u bazu podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
			            }	
					}
					break;
					case IZMJENA:{
						Integer id = (Integer) tabela.getModel().getValueAt(tabela.getSelectedRow(), 0);
			            odabraniObjekat=service.find(id);
			            Izdavac i = odabraniObjekat;
			            i.setNaziv(txtNaziv.getText());
			            i.setSjediste(txtSjediste.getText());
			            try{
			            	service.edit(i);
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
	                    txtNaziv.setText(odabraniObjekat.getNaziv());                    
	                    txtSjediste.setText(odabraniObjekat.getSjediste());
	                    txtNaziv.requestFocus();
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
	}
	
	private void osvjeziTabelu(){
		tableModel=new DefaultTableModel(tabelaKolone, 0){
	    	@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };
        
		List<Izdavac> lista;
		if(txtFilter.getText().length()==0){
            lista=service.findAll();
        } else{
        	String field = "";
        	if(cmbFilter.getSelectedIndex()==0){
        		field="naziv";
        	} else if(cmbFilter.getSelectedIndex()==1){
        		field="sjediste";
        	}           
            lista=service.findLikeBy("%" + txtFilter.getText() + "%", field);
        }        
        for(Izdavac izdavac : lista){
            Object[] data={izdavac.getIzdavacId(), izdavac.getNaziv(), izdavac.getSjediste()};            
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
        txtNaziv.setText("");
        txtSjediste.setText("");               
    }
}
