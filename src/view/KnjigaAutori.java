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
import model.Autor;
import model.Izdavac;
import model.Knjiga;
import model.KnjigaAutor;
import model.Korisnik;

public class KnjigaAutori extends JFrame {
	private JTextField txtRedniBroj;
	private enum Mod {
        IZMJENA, DODAVANJE
    }

    private KnjigaService knjigaService = new KnjigaService();
    private KnjigaAutorService knjigaAutorService = new KnjigaAutorService();
    private Mod trenutniMod = Mod.DODAVANJE;
    private KnjigaAutor odabraniObjekat = null;
    private String tabelaKolone[] = {"Autor", "Redni broj"};
    private DefaultTableModel tableModel=new DefaultTableModel(tabelaKolone, 0);

	private JTable tabela;
	private JButton btnDodati;
    private JButton btnObrisati;
    
    private JComboBox cmbAutor; 
    
	private Korisnik korisnik;
	private Knjiga knjiga;
	
	public KnjigaAutori(Korisnik korisnik, Knjiga k) {
		this.knjiga = k;
		this.korisnik = korisnik;
		setTitle("Autori - " + k.getNaslov());
		getContentPane().setLayout(null);
		
		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setBounds(21, 19, 71, 16);
		getContentPane().add(lblAutor);
		
		cmbAutor = new JComboBox();
		cmbAutor.setBounds(105, 15, 325, 27);
		getContentPane().add(cmbAutor);
		
		JLabel lblRedniBroj = new JLabel("Redni broj:");
		lblRedniBroj.setBounds(21, 47, 71, 16);
		getContentPane().add(lblRedniBroj);
		
		txtRedniBroj = new JTextField();
		txtRedniBroj.setBounds(105, 42, 71, 26);
		getContentPane().add(txtRedniBroj);
		txtRedniBroj.setColumns(10);
		
		tabela = new JTable();		
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setBounds(16, 81, 428, 155);
		getContentPane().add(scrollPane);		
		
		btnObrisati = new JButton("Obrisati");
		btnObrisati.setBounds(16, 248, 99, 29);
		getContentPane().add(btnObrisati);
		
		JButton btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(345, 248, 99, 29);
		getContentPane().add(btnZatvoriti);
		
		btnDodati = new JButton("Dodati");
		btnDodati.setBounds(250, 248, 99, 29);
		getContentPane().add(btnDodati);
		
		final Korisnik k1 = this.korisnik;
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	BibliotekarKnjige knjigeView = new BibliotekarKnjige(k1);
				knjigeView.setResizable(false);
				knjigeView.setSize(new Dimension(550, 440));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        knjigeView.setLocation(dim.width/2-knjigeView.getSize().width/2, dim.height/2-knjigeView.getSize().height/2);
		        knjigeView.getContentPane().setBackground(new Color(245, 245, 245));
		        knjigeView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
		        knjigeView.setVisible(true);		        
				dispose();
		    }
		});
		
		osvjeziTabelu();
		AutorService autorService = new AutorService();
		List<Autor> autori = autorService.findAll();
		for(Autor a : autori){
			cmbAutor.addItem(a);
		}
		
		btnDodati.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(cmbAutor.getSelectedIndex()==-1){
					JOptionPane.showMessageDialog(null, "Polje Autor je obavezno.", "Greška", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(GlobalHelper.isNumber(txtRedniBroj.getText())==false){
					JOptionPane.showMessageDialog(null, "Polje Redni broj mora biti broj.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtRedniBroj.requestFocus();
					return;
				}
				switch(trenutniMod){
					case DODAVANJE:{		
						Autor selectedAutor=(Autor)cmbAutor.getSelectedItem();
						knjiga = knjigaService.find(knjiga.getKnjigaId());
						knjiga.addAutor(selectedAutor, Integer.parseInt(txtRedniBroj.getText()));
						try{
							knjigaService.edit(knjiga);
							txtRedniBroj.setText("");
			            	cmbAutor.setSelectedIndex(-1);
			            	txtRedniBroj.requestFocus();
			                osvjeziTabelu();			            
			            } catch(Exception ex){
			            	ex.printStackTrace();
			            	JOptionPane.showMessageDialog(null, "Pojavila se greška prilikom spremanja u bazu podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
			            }	
			            
					}
					break;										
				}
				
			}
			
		});
		
		btnObrisati.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRowCount()==0) {
		            JOptionPane.showMessageDialog(null, "Niste odabrali nijedan unos.", "Greška", JOptionPane.WARNING_MESSAGE);
		        } else{		        	
		            Integer index = tabela.getSelectedRow();
		            odabraniObjekat = knjiga.getKnjigaAutorCollection().get(index);
		             int odgovor = JOptionPane.showConfirmDialog(null,
		                "Jeste li sigurni da želite obrisati unos " + odabraniObjekat.toString() + "?",
		                "Brisanje",
		                JOptionPane.YES_NO_OPTION);
		            if(odgovor==JOptionPane.YES_OPTION){
		            	knjiga.removeAutor(odabraniObjekat);
		            	try{
		            		knjigaService.edit(knjiga);
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
				BibliotekarKnjige knjigeView = new BibliotekarKnjige(k1);
				knjigeView.setResizable(false);
				knjigeView.setSize(new Dimension(550, 440));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        knjigeView.setLocation(dim.width/2-knjigeView.getSize().width/2, dim.height/2-knjigeView.getSize().height/2);
		        knjigeView.getContentPane().setBackground(new Color(245, 245, 245));
		        knjigeView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
		        knjigeView.setVisible(true);		        
				dispose();        			
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
        
		List<KnjigaAutor> lista;
		knjiga = knjigaService.find(knjiga.getKnjigaId());
		lista=(List<KnjigaAutor>) knjiga.getKnjigaAutorCollection();
		for(KnjigaAutor autor : lista){   
			Object[] data={autor.getAutor().getIme() + " " + autor.getAutor().getPrezime(), autor.getRedniBrojAutora()};            
            tableModel.addRow(data);
        }
        odabraniObjekat=null;
    
        tabela.setModel(tableModel); 
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	private void setDefaultMod() {
        trenutniMod=Mod.DODAVANJE;
        btnDodati.setText("Dodati");
        btnObrisati.setVisible(true);
        tabela.setEnabled(true);
        txtRedniBroj.setText("");                      
    }
}
