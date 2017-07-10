package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import service.KnjigaService;
import service.KorisnikService;
import service.PosudbaService;
import service.PrimjerakService;
import service.RezervacijaService;
import model.Knjiga;
import model.Korisnik;
import model.Posudba;
import model.Primjerak;
import model.Rezervacija;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;

public class StudentPosudbe extends JFrame {
	private JTable tabela;
	private PosudbaService service = new PosudbaService();
    private String tabelaKolone[] = {"Inv. broj", "Knjiga", "Datum posudbe", "Rok vraćanja"};
    private DefaultTableModel tableModel=new DefaultTableModel(tabelaKolone, 0);
	
	private Korisnik korisnik;
	private JTextField textField;
	private JComboBox cmbFilter;
	
	public StudentPosudbe(Korisnik k) {		
		korisnik = k;
		setTitle("Posudbe");
		getContentPane().setLayout(null);
		
		tabela = new JTable();		
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setBounds(17, 16, 515, 268);
		getContentPane().add(scrollPane);
		
		JButton btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(435, 331, 97, 29);
		getContentPane().add(btnZatvoriti);
		
		JLabel lblFilterPoPolju = new JLabel("Filter po polju:");
		lblFilterPoPolju.setBounds(17, 298, 97, 16);
		getContentPane().add(lblFilterPoPolju);
		
		cmbFilter = new JComboBox();
		cmbFilter.setModel(new DefaultComboBoxModel(new String[] {"knjiga (naslov)"}));
		cmbFilter.setBounds(112, 294, 113, 27);
		getContentPane().add(cmbFilter);
		
		textField = new JTextField();
		textField.setBounds(226, 293, 242, 26);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnFilter = new JButton("Filter");
		btnFilter.setBounds(467, 293, 65, 29);
		getContentPane().add(btnFilter);
		
		
		
		btnZatvoriti.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				StudentPanel panel=new StudentPanel(korisnik);
				panel.setResizable(false);
				panel.setSize(new Dimension(470, 175));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        panel.setLocation(dim.width/2-panel.getSize().width/2, dim.height/2-panel.getSize().height/2);
		        panel.getContentPane().setBackground(new Color(245, 245, 245));
		        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        dispose();
		        panel.setVisible(true);		        			
			}
			
		});
		
		osvjeziTabelu();
		
		btnFilter.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				osvjeziTabelu();		        			
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
        
		Collection<Posudba> lista = korisnik.getPosudbaCollection();
        
		if(textField.getText().length()==0){
            lista=korisnik.getPosudbaCollection();
        } else{
        	//{"Inv. broj", "Knjiga", "Datum posudbe", "Datum vraćanja"};
        	String field = "";
        	if(cmbFilter.getSelectedIndex()==0){
        		field="naslov";
                lista=new ArrayList<Posudba>();
                for(Posudba p : korisnik.getPosudbaCollection()){
                	if(p.getPrimjerak().getKnjigaId().getNaslov().contains(textField.getText())){
                		lista.add(p);
                	}
                }
        	}                    
        }        
		
        for(Posudba posudba : lista){
        	String datumPosudbe = "";        	
        	if(posudba.getDatumPosudbe() > 0){
        		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        		Date datum1 = new Date(posudba.getDatumPosudbe()*1000);            
        		datumPosudbe = dateFormat.format(datum1);
        	}
        	Object[] data={posudba.getPrimjerak().getInvBroj(), posudba.getPrimjerak().getKnjigaId().toString(), datumPosudbe, posudba.getRokVracanja() + " dana"};            
            tableModel.addRow(data);
        }
        
        tabela.setModel(tableModel); 
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
	}
}
