package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import model.Korisnik;
import model.Nastavnik;
import model.Rezervacija;
import service.NastavnikService;
import service.RezervacijaService;


public class RezervacijaView extends JFrame{
	Korisnik korisnik;
	List<Rezervacija> lista;
	Rezervacija odabraniObjekat;
	 private enum Mod {
          IZMJENA, DODAVANJE
	 }
	 private Mod trenutniMod = Mod.DODAVANJE;
	
	JTable table = new JTable();
	JTextField txtKorisnik = new JTextField();
	JTextField primjerak = new JTextField();
	JTextField rokPreuzimanja = new JTextField();
	JTextField datumPreuzimanja = new JTextField();
	JTextField txtFilter = new JTextField();
	JComboBox comboFilter = new JComboBox();
	
	JButton btnDodavanje = new JButton();
	
	String columnNames[] = {"KORISNIK", "PRIMJERAK", "ROK PREUZIMANJA", "DATUM PREUZIMANJA"};
	
	public RezervacijaView(Korisnik k, List<Rezervacija> l){
		korisnik = k;
		lista = l;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Rezervacija view");
		
		 DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		 
		 table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        this.addWindowListener(new java.awt.event.WindowAdapter() {
	            @Override
	            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	                JFrame view = null;
	                    view = new NastavnikPanel(korisnik);
	                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	                view.setLocation(dim.width / 2 - view.getSize().width / 2, dim.height / 2 - view.getSize().height / 2);
	                view.getContentPane().setBackground(new Color(245, 245, 245));
	                view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                view.setResizable(false);
	                view.setVisible(true);
	                dispose();
	            }
	        });
		 
	        txtFilter.getDocument().addDocumentListener(new DocumentListener() {

	            @Override
	            public void insertUpdate(DocumentEvent e) {
	                reloadTable();
	            }

	            @Override
	            public void removeUpdate(DocumentEvent e) {
	                reloadTable();
	            }

	            @Override
	            public void changedUpdate(DocumentEvent e) {                
	            }
	    
	        });
   
	        
		 txtKorisnik.setText("Unesite korisnika: ");
		 primjerak.setText("Unesite inventarni broj primjerka: ");
		 rokPreuzimanja.setText("Unesite rok preuzimanja: ");
		 datumPreuzimanja.setText("Unesite datum preuzimanja: ");
		 
		 getContentPane().add(txtKorisnik);
		 getContentPane().add(primjerak);
		 getContentPane().add(rokPreuzimanja);
		 getContentPane().add(datumPreuzimanja);
		 

	        btnDodavanje.setText("Dodaj rezervaciju");
	        btnDodavanje.setHorizontalAlignment(SwingConstants.TRAILING);
	        btnDodavanje.setVerticalAlignment(SwingConstants.BOTTOM);
	        btnDodavanje.setVerticalTextPosition(SwingConstants.BOTTOM);
	        btnDodavanje.setFont(new Font("Lucida Grande", Font.BOLD, 14));
	        btnDodavanje.addActionListener(new ActionListener(){
	        	@Override
	        	public void actionPerformed(ActionEvent e){
	                    if(txtKorisnik.getText().length()==0){
	                    	JOptionPane.showMessageDialog(null, "Polje Korisnik je obavezno.", "Greska", JOptionPane.ERROR_MESSAGE);
	                	    txtKorisnik.requestFocus();
	                    } else if(primjerak.getText().length()==0){
	                    	JOptionPane.showMessageDialog(null, "Polje Primjerak je obavezno.", "Greska", JOptionPane.ERROR_MESSAGE);
	                    	primjerak.requestFocus();
	                    } else if (rokPreuzimanja.getText().length() == 0) {
	                        JOptionPane.showMessageDialog(null, "Polje Rok preuzimanja je obavezno.", "Greska", JOptionPane.ERROR_MESSAGE);
	                        rokPreuzimanja.requestFocus();
	                    } else if (datumPreuzimanja.getText().length() == 0) {
	                        JOptionPane.showMessageDialog(null, "Polje Datum preuzimanja je obavezno.", "Greska", JOptionPane.ERROR_MESSAGE);
	                        datumPreuzimanja.requestFocus();
	                    } else{
	                    	Rezervacija r = new Rezervacija();
	                        r.getKorisnik().setIme(txtKorisnik.getText());
	                        NastavnikService n = new NastavnikService();
	                        r.getPrimjerak().setInvBroj(primjerak.getText());                  
	                        r.setRokPreuzimanja(0);
	                        r.setDatumPreuzimanja(0);
	                       }
	                            resetFields();
	                            reloadTable();
	        	}});
	        getContentPane().add(btnDodavanje);
	        
	        
	        table.setModel(model);
	        JScrollPane tPane = new JScrollPane(table);
	        getContentPane().add(tPane);
	        
	        txtFilter.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            }
	        });
	        getContentPane().add(txtFilter);
	        
	        comboFilter.setFont(new java.awt.Font("Lucida Grande", 0, 14));
	        comboFilter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "opis", "datum (dd.MM.yyyy)", "ime nastavnika", "prezime nastavnika" }));
	        comboFilter.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	               reloadTable();
	            }
	        });
	        getContentPane().add(comboFilter);
	        
	        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	
}
	
	private void resetFields(){
	    txtKorisnik.setText("Unesite korisnik id: ");
	    primjerak.setText("Unesite inventarni broj primjerka: ");
		rokPreuzimanja.setText("Unesite rok preuzimanja: ");
		datumPreuzimanja.setText("Unesite datum preuzimanja: ");
        primjerak.requestFocus();
     }

private void reloadTable() {
	 DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
	   RezervacijaService service = new RezervacijaService();
	   String field=(String)comboFilter.getSelectedItem();
       String filter=txtFilter.getText();
       if(txtFilter.getText().length()==0){
           lista=service.findAll();
       } else if(field.equals("ime nastavnika")){
           field="ime";
           NastavnikService ns=new NastavnikService();
           lista=new ArrayList<Rezervacija>();
           for(Nastavnik n : ns.findLikeBy("%" + filter + "%", field)){
               lista.addAll(n.getRezervacijaCollection());
           }
       } else if(field.equals("prezime nastavnika")){
           field="prezime";
           NastavnikService ns=new NastavnikService();
           lista=new ArrayList<Rezervacija>();
           for(Nastavnik n : ns.findLikeBy("%" + filter + "%", field)){
               lista.addAll(n.getRezervacijaCollection());
           }
       } else if(field.equals("datum (dd.MM.yyyy)")){
           field="vrijeme";
           DateFormat df=new SimpleDateFormat("dd.MM.yyyy");
           try {
               Date datum=df.parse(filter);
               DateFormat df2=new SimpleDateFormat("yyyy-MM-dd");
               filter=df2.format(datum);                
           } catch (ParseException ex) {
               
           }            
           lista=service.findLikeBy("%" + filter + "%", field);
       } else {
           lista=service.findLikeBy("%" + filter + "%", field);
       }
       for (Rezervacija r : lista) {        
           DateFormat dfDate = new SimpleDateFormat("dd.M.yyyy HH:mm");        
           Object[] data = {r.getKorisnik(), r.getPrimjerak(), r.getRokPreuzimanja(), r.getDatumPreuzimanja()};
           tableModel.addRow(data);
       }
       odabraniObjekat = null;
       table.setModel(tableModel);
   }
}