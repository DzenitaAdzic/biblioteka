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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import service.KnjigaService;
import service.LiteraturaService;
import service.NastavnikService;
import service.PredmetService;
import model.Knjiga;
import model.Korisnik;
import model.Literatura;
import model.Nastavnik;
import model.Predmet;

public class LiteraturaPanel extends JFrame {
	private JTextField txtVaznost;
	private enum Mod {
        IZMJENA, DODAVANJE
    }

    private KnjigaService knjigaService = new KnjigaService();
    private LiteraturaService literaturaService = new LiteraturaService();
    private Mod trenutniMod = Mod.DODAVANJE;
    private Literatura odabraniObjekat = null;
    private String tabelaKolone[] = {"Knjiga", "Predmet", "Vaznost"};
    private DefaultTableModel tableModel=new DefaultTableModel(tabelaKolone, 0);

	private JTable tabela;
	private JButton btnDodati;
    private JButton btnObrisati;
    
    private JComboBox cmbPredmet; 
    
	private Korisnik korisnik;
	private Literatura literatura;
	private Knjiga knjiga;
	
	public LiteraturaPanel(Korisnik korisnik, Knjiga k) {
		this.knjiga = k;
		this.korisnik = korisnik;
		setTitle("Literatura - " + k.getNaslov());
		getContentPane().setLayout(null);
		
		JLabel lblPredmet = new JLabel("Naziv predmeta:");
		lblPredmet.setBounds(21, 19, 110, 16);
		getContentPane().add(lblPredmet);
		
		cmbPredmet = new JComboBox();
		cmbPredmet.setBounds(125, 15, 310, 27);
		getContentPane().add(cmbPredmet);
		
		JLabel lblVaznost = new JLabel("Vaznost knjige:");
		lblVaznost.setBounds(21, 47, 110, 16);
		getContentPane().add(lblVaznost);
		
		txtVaznost = new JTextField();
		txtVaznost.setBounds(125, 42, 150, 26);
		getContentPane().add(txtVaznost);
		txtVaznost.setColumns(10);
		
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
		    	NastavnikKnjige knjigeView = new NastavnikKnjige(k1);
				knjigeView.setResizable(true);
				knjigeView.setSize(new Dimension(610, 530));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        knjigeView.setLocation(dim.width/2-knjigeView.getSize().width/2, dim.height/2-knjigeView.getSize().height/2);
		        knjigeView.getContentPane().setBackground(new Color(245, 245, 245));
		        knjigeView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	  
		        knjigeView.setIconImage( (new ImageIcon(NastavnikPanel.class.getResource("/ikone/library.png"))).getImage() );
		        knjigeView.setVisible(true);		        
				dispose();
		    }
		});
		
		osvjeziTabelu();
		PredmetService predmetService = new PredmetService();
		List<Predmet> predmet = predmetService.findAll();
		for(Predmet p : predmet){
			cmbPredmet.addItem(p);
		}
		
		btnDodati.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(cmbPredmet.getSelectedIndex()==-1){
					JOptionPane.showMessageDialog(null, "Polje Predmet je obavezno.", "Greška", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(txtVaznost.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Polje Vaznost je obavezno.", "Greska", JOptionPane.ERROR_MESSAGE);
				    return;
				}
				switch(trenutniMod){
					case DODAVANJE:{		
						Predmet selectedPredmet=(Predmet)cmbPredmet.getSelectedItem();
	    				knjiga = knjigaService.find(knjiga.getKnjigaId());
	    				NastavnikService nastavnikService = new NastavnikService();
	    				Nastavnik nastavnik = nastavnikService.find(k1.getSifra());
	    				literatura = new Literatura(selectedPredmet.getSifra(), knjiga.getKnjigaId());
						literatura.setVaznost(txtVaznost.getText());
						literatura.setKnjiga(knjiga);
						literatura.setPredmet(selectedPredmet);
						if(nastavnik.getPredmetCollection().contains(selectedPredmet)){
						knjiga.getLiteraturaCollection().add(literatura);
						try{
							knjigaService.edit(knjiga);
							txtVaznost.setText("");
			            	cmbPredmet.setSelectedIndex(-1);
			            	txtVaznost.requestFocus();
			                osvjeziTabelu();			            
			            } catch(Exception ex){
			            	ex.printStackTrace();
			            	JOptionPane.showMessageDialog(null, "Pojavila se greška prilikom spremanja u bazu podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
			            }
					}
						else
							JOptionPane.showMessageDialog(null, "Ne možete dodavati literaturu na predmete na kojima niste nastavnik.", "Greška", JOptionPane.ERROR_MESSAGE);
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
		            Integer index =(Integer) tabela.getSelectedRow();
		            List<Literatura> listaL =(List<Literatura>) knjiga.getLiteraturaCollection();
		            Literatura objekat = listaL.get(index);
		            if(knjiga.getLiteraturaCollection().contains(objekat)){
		               odabraniObjekat = objekat;
		            }
		             int odgovor = JOptionPane.showConfirmDialog(null,
		                "Jeste li sigurni da želite obrisati unos " + odabraniObjekat.getPredmet().getNaziv() + "?",
		                "Brisanje",
		                JOptionPane.YES_NO_OPTION);
		            if(odgovor==JOptionPane.YES_OPTION){
		            	knjiga.getLiteraturaCollection().remove(odabraniObjekat);
		            	try{
		            		literaturaService.remove(odabraniObjekat);
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
				NastavnikKnjige panel=new NastavnikKnjige(k1);
				panel.setResizable(true);
				panel.setSize(new Dimension(610, 530));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        panel.setLocation(dim.width/2-panel.getSize().width/2, dim.height/2-panel.getSize().height/2);
		        panel.getContentPane().setBackground(new Color(245, 245, 245));
		        panel.setIconImage( (new ImageIcon(NastavnikPanel.class.getResource("/ikone/library.png"))).getImage() );
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
        
		List<Literatura> lista;
		lista=(List<Literatura>) knjiga.getLiteraturaCollection();
		for(Literatura l : lista){   
			Object[] data={l.getKnjiga().getNaslov(), l.getPredmet().getNaziv(), l.getVaznost()};            
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
        txtVaznost.setText("");                      
    }
}

