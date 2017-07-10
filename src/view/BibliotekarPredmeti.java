package view;

import helper.GlobalHelper;

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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import service.PredmetService;
import model.Korisnik;
import model.Predmet;


public class BibliotekarPredmeti extends JFrame {
	private JTextField txtNaslov;
	private JTextField txtSifra;
	private JTextField txtSkraceniNaziv;
	
	private enum Mod {
        IZMJENA, DODAVANJE
    }

    private PredmetService service = new PredmetService();
    private Mod trenutniMod = Mod.DODAVANJE;
    private Predmet odabraniObjekat = null;
    private String tabelaKolone[] = {"Sifra", "Naziv", "Semestar"};
    private DefaultTableModel tableModel=new DefaultTableModel(tabelaKolone, 0);
    private JTable tabela;    
    private JTextField txtFilter;
    private JButton btnDodati;
    private JButton btnIzmijeniti;
    private JButton btnObrisati;
    private JButton btnFilter;
    private JComboBox cmbFilter; 
    private JComboBox cmbSemestar;
   
    
	private Korisnik korisnik;
	
	public BibliotekarPredmeti(Korisnik k) {
		korisnik = k;
		setTitle("Predmeti");
		getContentPane().setLayout(null);
		
		JLabel lblNaslov= new JLabel("Naziv:");
		lblNaslov.setBounds(17, 47, 123, 16);
		getContentPane().add(lblNaslov);
		
		JLabel lblSifra = new JLabel("Sifra predmeta:");
		lblSifra.setBounds(17, 20, 123, 16);
		getContentPane().add(lblSifra);
		
		JLabel lblSkraceniNaziv = new JLabel("Skraceni naziv:");
		lblSkraceniNaziv.setBounds(17, 75, 123, 16);
		getContentPane().add(lblSkraceniNaziv);
		
		JLabel lblSemestar = new JLabel("Semestar:");
		lblSemestar.setBounds(17, 103, 123, 16);
		getContentPane().add(lblSemestar);
		
		txtNaslov = new JTextField();
		txtNaslov.setBounds(152, 42, 200, 26);
		getContentPane().add(txtNaslov);
		txtNaslov.setColumns(10);
		
		txtSifra = new JTextField();
		txtSifra.setColumns(10);
		txtSifra.setBounds(152, 15, 100, 26);
		getContentPane().add(txtSifra);
		
		txtSkraceniNaziv = new JTextField();
		txtSkraceniNaziv.setColumns(10);
		txtSkraceniNaziv.setBounds(152, 70, 100, 26);
		getContentPane().add(txtSkraceniNaziv);
		
		tabela = new JTable();
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setBounds(17, 131, 510, 205);
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
		
		//btnAutori = new JButton("Autori");
		//btnAutori.setBounds(160, 382, 94, 29);
		//getContentPane().add(btnAutori);
		
		btnDodati = new JButton("Dodati");
		btnDodati.setBounds(254, 382, 94, 29);		
		getContentPane().add(btnDodati);
		
		JLabel lblFilterPoPolju = new JLabel("Filter po polju:");
		lblFilterPoPolju.setBounds(27, 348, 113, 16);
		getContentPane().add(lblFilterPoPolju);
		
		cmbFilter = new JComboBox();
		cmbFilter.setModel(new DefaultComboBoxModel(new String[] {"sifra predmeta", "naziv predmeta"}));
		cmbFilter.setBounds(123, 344, 131, 27);
		getContentPane().add(cmbFilter);
		
		
		txtFilter = new JTextField();
		txtFilter.setBounds(254, 343, 190, 26);
		getContentPane().add(txtFilter);
		txtFilter.setColumns(10);
		

		btnFilter = new JButton("Filter");
		btnFilter.setBounds(468, 343, 64, 29);
		getContentPane().add(btnFilter);
		
		cmbSemestar = new JComboBox();
		cmbSemestar.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8"}));
		cmbSemestar.setBounds(152, 99, 100, 27);
		getContentPane().add(cmbSemestar);
		
		osvjeziTabelu();
		
	
		
		btnDodati.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(txtNaslov.getText().length()==0){
					JOptionPane.showMessageDialog(null, "Polje Naslov je obavezno.", "Greska", JOptionPane.ERROR_MESSAGE);
					txtNaslov.requestFocus();
					return;
				}				
				
				
				if(txtSifra.getText().length()==0){
					JOptionPane.showMessageDialog(null, "Polje Sifra je obavezno.", "Greska", JOptionPane.ERROR_MESSAGE);
					txtSifra.requestFocus();
					return;
				}				
				if(txtSkraceniNaziv.getText().length()==0){
					JOptionPane.showMessageDialog(null, "Polje Skraceni naziv je obavezno.", "Greska", JOptionPane.ERROR_MESSAGE);
					txtSkraceniNaziv.requestFocus();
					return;
				}
				
				switch(trenutniMod){
					case DODAVANJE:{						
						Predmet k = new Predmet();            
			            k.setNaziv(txtNaslov.getText());
			            k.setSifra(txtSifra.getText());
			            k.setSkraceniNaziv(txtSkraceniNaziv.getText());
			            k.setSemestar(Short.parseShort((String) cmbSemestar.getSelectedItem()));		            
			            try{
			            	service.create(k);
			            	txtNaslov.setText("");
			            	txtSifra.setText("");
			            	txtSkraceniNaziv.setText("");
			            	cmbSemestar.setSelectedIndex(0);
			                osvjeziTabelu();			            
			            } catch(Exception ex){
			            	JOptionPane.showMessageDialog(null, "Pojavila se greska prilikom spremanja u bazu podataka.", "Greka", JOptionPane.ERROR_MESSAGE);
			            }				            
					}
					break;
					case IZMJENA:{						
						String id = (String) tabela.getModel().getValueAt(tabela.getSelectedRow(), 0);
			            odabraniObjekat=service.find(id);
			            Predmet k = odabraniObjekat;
			            k.setNaziv(txtNaslov.getText());
			            k.setSifra(txtSifra.getText());
			            k.setSkraceniNaziv(txtSkraceniNaziv.getText());
			            k.setSemestar(Short.parseShort((String) cmbSemestar.getSelectedItem()));			       	
			            try{
			            	service.edit(k);
			            	setDefaultMod();
			            	osvjeziTabelu();
			            	JOptionPane.showMessageDialog(null, "Podaci su uspjesno izmijenjeni.", "Izmjena", JOptionPane.INFORMATION_MESSAGE);
			            } catch (Exception ex){
			            	JOptionPane.showMessageDialog(null, "Pojavila se greska prilikom spremanja u bazu podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
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
	            JOptionPane.showMessageDialog(null, "Niste odabrali nijedan unos.", "Greska", JOptionPane.WARNING_MESSAGE);
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
	                    String id = (String) tabela.getModel().getValueAt(tabela.getSelectedRow(), 0);
	                    odabraniObjekat=service.find(id);
	                    btnDodati.setText("Spremiti");
	                    btnIzmijeniti.setText("Odbaciti");
	                    btnObrisati.setVisible(false);
	                    btnFilter.setEnabled(false);
	                    tabela.setEnabled(false);	                    
	                    txtNaslov.setText(odabraniObjekat.getNaziv());
		            	txtSifra.setText("" + odabraniObjekat.getSifra());
		            	txtSkraceniNaziv.setText("" + odabraniObjekat.getSkraceniNaziv());
		            	cmbSemestar.setSelectedItem(odabraniObjekat.getSemestar());		            	
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
			            String id = (String) tabela.getModel().getValueAt(tabela.getSelectedRow(), 0);
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
		
		/*btnAutori.addActionListener(new ActionListener(){
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
			
		}); */
	}
	
	
	private void osvjeziTabelu(){
		tableModel=new DefaultTableModel(tabelaKolone, 0){
	    	@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };
        
		List<Predmet> lista = null;
		if(txtFilter.getText().length()==0){
            lista=service.findAll();
        } else{
        	String field = "";
        	if(cmbFilter.getSelectedIndex()==0){
        		field="sifra";
        		lista=service.findLikeBy("%" + txtFilter.getText() + "%", field);
        	} else if(cmbFilter.getSelectedIndex()==1){
        		field="naziv";
        		lista=service.findLikeBy("%" + txtFilter.getText() + "%", field);
        	} else if(cmbFilter.getSelectedIndex()==2){
        		field="semestar";
        		lista=service.findBy(Short.parseShort((String) cmbSemestar.getSelectedItem()), field);
        	}            
        }    
		if(lista!=null){
			for(Predmet predmet : lista){
            	Object[] data={predmet.getSifra(), predmet.getNaziv(), predmet.getSemestar()};            
            	tableModel.addRow(data);
        	}
        	odabraniObjekat=null;
    
        	tabela.setModel(tableModel); 
        	tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
	}
	
	private void setDefaultMod() {		
        trenutniMod=Mod.DODAVANJE;
        btnDodati.setText("Dodati");
        btnFilter.setEnabled(true);
        btnIzmijeniti.setText("Izmijeniti");
        btnObrisati.setVisible(true);
        tabela.setEnabled(true);
        txtNaslov.setText("");
    	txtSifra.setText("");
    	txtSkraceniNaziv.setText("");
    	cmbSemestar.setSelectedIndex(0);
    }
}

