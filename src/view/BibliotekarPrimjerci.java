package view;

import helper.GlobalHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import service.AutorService;
import service.IzdavacService;
import service.KnjigaService;
import service.PrimjerakService;
import model.Autor;
import model.Izdavac;
import model.Knjiga;
import model.KnjigaAutor;
import model.Korisnik;
import model.Predmet;
import model.Primjerak;


public class BibliotekarPrimjerci extends JFrame {
	private JTextField txtInventarniBroj;
	private JTextField txtStanje;
	private JTextField txtDatum;
	private JTable tabela;
	private JTextField txtFilter;
	private enum Mod {
        IZMJENA, DODAVANJE
    }

    private PrimjerakService service = new PrimjerakService();
    private Mod trenutniMod = Mod.DODAVANJE;
    private Primjerak odabraniObjekat = null;
    private String tabelaKolone[] = {"Inv. broj", "Naslov", "Autor"};
    private DefaultTableModel tableModel=new DefaultTableModel(tabelaKolone, 0);
    
    private JButton btnDodati;
    private JButton btnIzmijeniti;
    private JButton btnObrisati;
    private JButton btnFilter;
    private JComboBox cmbFilter;
    private JComboBox cmbKnjiga;
	
	private Korisnik korisnik;
	
	public BibliotekarPrimjerci(Korisnik k) {		
		setTitle("Primjerci");
		
		korisnik = k;
		getContentPane().setLayout(null);
		
		JLabel lblInventarniBroj = new JLabel("Inventarni broj:");
		lblInventarniBroj.setBounds(17, 17, 115, 16);
		getContentPane().add(lblInventarniBroj);
		
		JLabel lblKnjiga = new JLabel("Knjiga:");
		lblKnjiga.setBounds(17, 45, 115, 16);
		getContentPane().add(lblKnjiga);
		
		JLabel lblStanje = new JLabel("Stanje:");
		lblStanje.setBounds(17, 73, 115, 16);
		getContentPane().add(lblStanje);
		
		JLabel lblDatumNabavke = new JLabel("Datum nabavke:");
		lblDatumNabavke.setBounds(17, 101, 115, 16);
		getContentPane().add(lblDatumNabavke);
		
		txtInventarniBroj = new JTextField();
		txtInventarniBroj.setBounds(144, 12, 130, 26);
		getContentPane().add(txtInventarniBroj);
		txtInventarniBroj.setColumns(10);
		
		txtStanje = new JTextField();
		txtStanje.setColumns(10);
		txtStanje.setBounds(144, 68, 411, 26);
		getContentPane().add(txtStanje);
		
		cmbKnjiga = new JComboBox();
		cmbKnjiga.setBounds(144, 41, 411, 27);
		getContentPane().add(cmbKnjiga);
		
		JLabel lblDdmmyyyy = new JLabel("dd.mm.gggg");
		lblDdmmyyyy.setForeground(Color.GRAY);
		lblDdmmyyyy.setBounds(17, 116, 115, 16);
		getContentPane().add(lblDdmmyyyy);
		
		txtDatum = new JTextField();
		txtDatum.setColumns(10);
		txtDatum.setBounds(144, 96, 130, 26);
		getContentPane().add(txtDatum);
		
		tabela = new JTable();
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setBounds(17, 143, 532, 192);
		getContentPane().add(scrollPane);
		
		JLabel lblFilterPoPolju = new JLabel("Filter po polju:");
		lblFilterPoPolju.setBounds(17, 347, 99, 16);
		getContentPane().add(lblFilterPoPolju);
		
		cmbFilter = new JComboBox();
		cmbFilter.setModel(new DefaultComboBoxModel(new String[] {"inventarni broj", "naziv knjige", "ime autora", "prezime autora"}));
		cmbFilter.setBounds(112, 343, 106, 27);
		getContentPane().add(cmbFilter);
		
		txtFilter = new JTextField();
		txtFilter.setBounds(219, 342, 264, 26);
		getContentPane().add(txtFilter);
		txtFilter.setColumns(10);
		
		btnFilter = new JButton("Filter");
		btnFilter.setBounds(479, 342, 76, 29);
		getContentPane().add(btnFilter);
		
		btnObrisati = new JButton("Obrisati");
		btnObrisati.setBounds(17, 380, 94, 29);
		getContentPane().add(btnObrisati);
		
		JButton btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(461, 380, 94, 29);
		getContentPane().add(btnZatvoriti);
		
		btnDodati = new JButton("Dodati");
		btnDodati.setBounds(367, 380, 94, 29);
		getContentPane().add(btnDodati);
		
		btnIzmijeniti = new JButton("Izmijeniti");
		btnIzmijeniti.setBounds(273, 380, 94, 29);
		getContentPane().add(btnIzmijeniti);
		
		osvjeziTabelu();
		
		KnjigaService knjigaService = new KnjigaService();
		List<Knjiga> knjige = knjigaService.findAllSortedAsc("naslov");
		for(Knjiga k1 : knjige){
			cmbKnjiga.addItem(k1);
		}
		
		btnDodati.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(txtInventarniBroj.getText().length()==0){
					JOptionPane.showMessageDialog(null, "Polje Inventarni broj je obavezno.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtInventarniBroj.requestFocus();
					return;
				}
				if(cmbKnjiga.getSelectedIndex()==-1){
					JOptionPane.showMessageDialog(null, "Polje Knjiga je obavezno.", "Greška", JOptionPane.ERROR_MESSAGE);					
					return;
				}
				if(txtDatum.getText().length()==0){
					JOptionPane.showMessageDialog(null, "Polje Datum nabavke je obavezno.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtDatum.requestFocus();
					return;
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Date datumNabavke;
				int datum;
				try {
					datumNabavke = dateFormat.parse(txtDatum.getText());
					datum = (int) datumNabavke.getTime();
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "Pogrešan format datuma. Unesite datum u obliku dd.mm.gggg", "Greška", JOptionPane.ERROR_MESSAGE);
					txtDatum.requestFocus();
					return;
				}
				
				switch(trenutniMod){
					case DODAVANJE:{						
						Primjerak p = new Primjerak();            
			            p.setInvBroj(txtInventarniBroj.getText());
			            p.setKnjigaId((Knjiga)cmbKnjiga.getSelectedItem());
			            p.setStanje(txtStanje.getText());
			            p.setDatumNabavke(datum);			            
			            try{
			            	service.create(p);
			            	txtInventarniBroj.setText("");
			            	txtStanje.setText("");
			            	txtDatum.setText("");
			            	cmbKnjiga.setSelectedIndex(-1);
			            	txtInventarniBroj.requestFocus();
			                osvjeziTabelu();			            
			            } catch(Exception ex){
			            	JOptionPane.showMessageDialog(null, "Pojavila se greška prilikom spremanja u bazu podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
			            }	
					}
					break;
					case IZMJENA:{
						String sifra = (String) tabela.getModel().getValueAt(tabela.getSelectedRow(), 0);
			            odabraniObjekat=service.find(sifra);
			            Primjerak p = odabraniObjekat;
			            p.setInvBroj(txtInventarniBroj.getText());
			            p.setKnjigaId((Knjiga)cmbKnjiga.getSelectedItem());
			            p.setStanje(txtStanje.getText());
			            p.setDatumNabavke(datum);
			            try{
			            	service.edit(p);
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
	                	String sifra = (String) tabela.getModel().getValueAt(tabela.getSelectedRow(), 0);
			            odabraniObjekat=service.find(sifra);
	                    btnDodati.setText("Spremiti");
	                    btnIzmijeniti.setText("Odbaciti");
	                    btnObrisati.setVisible(false);
	                    btnFilter.setEnabled(false);
	                    tabela.setEnabled(false);	                    
	                    txtInventarniBroj.setText(odabraniObjekat.getInvBroj());
	                    txtStanje.setText(odabraniObjekat.getStanje());
	                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	                    Date datum = new Date((long)odabraniObjekat.getDatumNabavke());
	                    txtDatum.setText(dateFormat.format(datum));
		            	cmbKnjiga.setSelectedItem(odabraniObjekat.getKnjigaId());
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
		        	String sifra = (String) tabela.getModel().getValueAt(tabela.getSelectedRow(), 0);
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
	}
	
	private void osvjeziTabelu(){		
		tableModel=new DefaultTableModel(tabelaKolone, 0){
	    	@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };
        
		List<Primjerak> lista = new ArrayList<Primjerak>();
		if(txtFilter.getText().length()==0){
            lista=service.findAll();
        } else{
        	String field = "";
        	if(cmbFilter.getSelectedIndex()==1){
                field="naslov";
                KnjigaService ks = new KnjigaService();
                lista=new ArrayList<Primjerak>();
                for(Knjiga k : ks.findLikeBy("%" + txtFilter.getText() + "%", field)){
                    lista.addAll(k.getPrimjerakCollection());
                }
            } else if(cmbFilter.getSelectedIndex()==0){
        		field="invBroj";
        		lista=service.findLikeBy("%" + txtFilter.getText() + "%", field);
        	} else if(cmbFilter.getSelectedIndex()==2){
        		field="ime";
                AutorService as = new AutorService();
                lista=new ArrayList<Primjerak>();
                for(Autor a : as.findLikeBy("%" + txtFilter.getText() + "%", field)){
                    for(KnjigaAutor k : a.getKnjigaAutorCollection()){
                    	lista.addAll(k.getKnjiga().getPrimjerakCollection());
                    }
                }
        	} else if(cmbFilter.getSelectedIndex()==3){
        		field="prezime";
                AutorService as = new AutorService();
                lista=new ArrayList<Primjerak>();
                for(Autor a : as.findLikeBy("%" + txtFilter.getText() + "%", field)){
                    for(KnjigaAutor k : a.getKnjigaAutorCollection()){
                    	lista.addAll(k.getKnjiga().getPrimjerakCollection());
                    }
                }
        	}                       
        }        
        for(Primjerak primjerak : lista){
        	String autor = "";
        	if(primjerak.getKnjigaId().getKnjigaAutorCollection().size()>0){
        		autor = primjerak.getKnjigaId().getKnjigaAutorCollection().get(0).getAutor().toString();
        	}
            Object[] data={primjerak.getInvBroj(), primjerak.getKnjigaId().toString(), autor};            
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
        txtInventarniBroj.setText("");
    	txtStanje.setText("");
    	txtDatum.setText("");
    	cmbKnjiga.setSelectedIndex(-1);
    	txtInventarniBroj.requestFocus();    	
    }

}
