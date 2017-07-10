package view;

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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import service.KnjigaService;
import service.KorisnikService;
import service.PosudbaService;
import service.PrimjerakService;
import service.ServiceException;
import model.Knjiga;
import model.Korisnik;
import model.Nastavnik;
import model.Posudba;
import model.Primjerak;

public class BibliotekarSvePosudbe extends JFrame {
	private JTable tabela;
	private JTextField txtFilter;
	private JButton btnFilter;
	private JButton btnVratitiKnjigu;
	private JButton btnZatvoriti;
	
	private JComboBox cmbFilter;
	
	private Posudba odabraniObjekat;
	
	private String tabelaKolone[] = {"Sifra korisnika", "Inv. broj", "Prezime", "Ime", "Naslov", "Datum posudbe", "Rok vracanja", "Datum vracanja"};
    private DefaultTableModel tableModel=new DefaultTableModel(tabelaKolone, 0);
	
    private PosudbaService service = new PosudbaService();
	
	private Korisnik korisnik; 
	
	private List<Posudba> lista = new ArrayList<Posudba>();
	
	private void osvjeziTabelu(){
		tableModel=new DefaultTableModel(tabelaKolone, 0){
	    	@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };        
		if(txtFilter.getText().length()==0){
            lista=service.findAllSortedDesc("datumVracanja");
        } else{
        	//{"inventarni broj", "naslov", "sifra korisnika", "ime", "prezime"}
        	String field = "";
        	if(cmbFilter.getSelectedIndex()==0){
        		field="invBroj";
                PrimjerakService ps = new PrimjerakService();
                lista=new ArrayList<Posudba>();
                for(Primjerak p : ps.findLikeBy("%" + txtFilter.getText() + "%", field)){
                    lista.addAll(p.getPosudbaCollection());
                }
        	} else if(cmbFilter.getSelectedIndex()==1){
        		field="naslov";
                KnjigaService ks = new KnjigaService();
                lista=new ArrayList<Posudba>();
                for(Knjiga k : ks.findLikeBy("%" + txtFilter.getText() + "%", field)){
                    for(Primjerak p : k.getPrimjerakCollection()){
                    	lista.addAll(p.getPosudbaCollection());
                    }
                }
        	} else if(cmbFilter.getSelectedIndex()==2){
        		field="sifra";
                KorisnikService ks = new KorisnikService();
                lista=new ArrayList<Posudba>();
                for(Korisnik k : ks.findLikeBy("%" + txtFilter.getText() + "%", field)){
                    lista.addAll(k.getPosudbaCollection());
                }
        	} else if(cmbFilter.getSelectedIndex()==3){
        		field="ime";
                KorisnikService ks = new KorisnikService();
                lista=new ArrayList<Posudba>();
                for(Korisnik k : ks.findLikeBy("%" + txtFilter.getText() + "%", field)){
                    lista.addAll(k.getPosudbaCollection());
                }
        	} else if(cmbFilter.getSelectedIndex()==3){
        		field="prezime";
                KorisnikService ks = new KorisnikService();
                lista=new ArrayList<Posudba>();
                for(Korisnik k : ks.findLikeBy("%" + txtFilter.getText() + "%", field)){
                    lista.addAll(k.getPosudbaCollection());
                }
        	}                       
        }        
        for(Posudba posudba : lista){
        	String datumPosudbe = "";
        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date datum1 = new Date(posudba.getDatumPosudbe()*1000);
            datumPosudbe = dateFormat.format(datum1);
            
            String datumVracanja = "";
        	if(posudba.getDatumVracanja()>0){
        		Date datum2 = new Date(posudba.getDatumVracanja()*1000);            
        		datumVracanja = dateFormat.format(datum2);
        	}
            Object[] data={posudba.getKorisnik().getSifra(), posudba.getPrimjerak().getInvBroj(), posudba.getKorisnik().getPrezime(), posudba.getKorisnik().getIme(), posudba.getPrimjerak().getKnjigaId().getNaslov(), datumPosudbe, posudba.getRokVracanja() + " dana", datumVracanja};            
            tableModel.addRow(data);
        }
        odabraniObjekat=null;
    
        tabela.setModel(tableModel); 
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public BibliotekarSvePosudbe(Korisnik k) {
		korisnik = k;
		setTitle("Sve posudbe");
		getContentPane().setLayout(null);
		
		tabela = new JTable();
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setBounds(10, 10, 484, 259);
		getContentPane().add(scrollPane);
		
		JLabel lblFilterPoPolju = new JLabel("Filter po polju:");
		lblFilterPoPolju.setBounds(10, 281, 103, 16);
		getContentPane().add(lblFilterPoPolju);
		
		cmbFilter = new JComboBox();
		cmbFilter.setModel(new DefaultComboBoxModel(new String[] {"inventarni broj", "naslov", "sifra korisnika", "ime", "prezime"}));
		cmbFilter.setBounds(107, 277, 114, 27);
		getContentPane().add(cmbFilter);
		
		txtFilter = new JTextField();
		txtFilter.setBounds(219, 276, 215, 27);
		getContentPane().add(txtFilter);
		txtFilter.setColumns(10);
		
		btnFilter = new JButton("Filter");
		btnFilter.setBounds(433, 276, 67, 29);
		getContentPane().add(btnFilter);
		
		btnVratitiKnjigu = new JButton("Vratiti knjigu");
		btnVratitiKnjigu.setBounds(6, 318, 117, 29);
		getContentPane().add(btnVratitiKnjigu);
		
		btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(397, 318, 103, 29);
		getContentPane().add(btnZatvoriti);
		
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
				BibliotekarNovaPosudba izdavaciView = new BibliotekarNovaPosudba(korisnik);
				izdavaciView.setResizable(false);
				izdavaciView.setSize(new Dimension(550, 380));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        izdavaciView.setLocation(dim.width/2-izdavaciView.getSize().width/2, dim.height/2-izdavaciView.getSize().height/2);
		        izdavaciView.getContentPane().setBackground(new Color(245, 245, 245));
		        izdavaciView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
		        izdavaciView.setVisible(true);		        
				dispose();		        	
				service.closeConnection();
			}
			
		});
		
		btnVratitiKnjigu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRowCount()==0) {
		            JOptionPane.showMessageDialog(null, "Niste odabrali nijednu posudbu.", "Greška", JOptionPane.WARNING_MESSAGE);
		        } else {
		        	Posudba odabranaPosudba = lista.get(tabela.getSelectedRow());
		        	if(odabranaPosudba.getDatumVracanja()==0){
		        		odabranaPosudba.setDatumVracanja((new Date()).getTime()/1000);
		        		try {
							long danaKasnjenja = (odabranaPosudba.getDatumVracanja() - odabranaPosudba.getDatumPosudbe())/(60*60*24);
							if(1>0){
								long negativniBodovi = odabranaPosudba.getKorisnik().getNegativniBodovi() + danaKasnjenja * odabranaPosudba.getPrimjerak().getKnjigaId().getNegativniBodovi();
								KorisnikService ks = new KorisnikService();
								odabranaPosudba.getKorisnik().setNegativniBodovi((int)negativniBodovi);
								ks.editWithoutRefresh(odabranaPosudba.getKorisnik());
							}
							service.remove(odabranaPosudba);
							osvjeziTabelu();
			        		JOptionPane.showMessageDialog(null, "Knjiga uspješno vraćena.", "Greška", JOptionPane.INFORMATION_MESSAGE);
						} catch (ServiceException e1) {
							JOptionPane.showMessageDialog(null, "Dogodila se greška prilikom snimanja u bazu podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}		        		
		        	} else{
		        		JOptionPane.showMessageDialog(null, "Korisnik je već vratio odabranu knjigu.", "Greška", JOptionPane.WARNING_MESSAGE);
		        	}
		        }
			}
		});
	}

}
