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
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import service.IzdavacService;
import service.KnjigaService;
import model.Izdavac;
import model.Knjiga;
import model.Korisnik;


public class BibliotekaView extends JFrame {

    private KnjigaService service = new KnjigaService();
    private String tabelaKolone[] = {"ID", "Naslov", "Godina", "Izdava�"};
    private DefaultTableModel tableModel=new DefaultTableModel(tabelaKolone, 0);
    private JTable tabela;    
    private JTextField txtFilter;
    private JButton btnFilter;
    private JComboBox cmbFilter;
    private JComboBox cmbFilterTip;
    
	private Korisnik korisnik;
	
	public BibliotekaView(Korisnik k) {
		korisnik = k;
		setTitle("Knjige");
		getContentPane().setLayout(null);
		
		tabela = new JTable();
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setBounds(17, 20, 516, 174);
		getContentPane().add(scrollPane);
		
		JButton btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(439, 250, 94, 29);
		getContentPane().add(btnZatvoriti);
		
		JLabel lblFilterPoPolju = new JLabel("Filter po polju:");
		lblFilterPoPolju.setBounds(27, 200, 113, 16);
		getContentPane().add(lblFilterPoPolju);
		
		cmbFilter = new JComboBox();
		cmbFilter.setModel(new DefaultComboBoxModel(new String[] {"naslov", "naziv izdava�a", "tip"}));
		cmbFilter.setBounds(123, 205, 131, 27);
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
		txtFilter.setBounds(254, 205, 216, 26);
		getContentPane().add(txtFilter);
		txtFilter.setColumns(10);
		
		cmbFilterTip = new JComboBox();
		cmbFilterTip.setModel(new DefaultComboBoxModel(new String[] {"knjiga", "�asopis", "diplomski rad", "magistarski rad", "doktorski rad", "ostalo"}));
		cmbFilterTip.setBounds(254, 205, 216, 26);
		getContentPane().add(cmbFilterTip);
		cmbFilterTip.setVisible(false);
		
		btnFilter = new JButton("Filter");
		btnFilter.setBounds(468, 205, 64, 29);
		getContentPane().add(btnFilter);
		
		btnFilter.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				osvjeziTabelu();				
			}			
		});
		
		osvjeziTabelu();
		
		btnZatvoriti.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				NastavnikPanel nastavniciView = new NastavnikPanel(korisnik);
				nastavniciView.setResizable(false);
				nastavniciView.setSize(new Dimension(610, 530));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        nastavniciView.setLocation(dim.width/2-nastavniciView.getSize().width/2, dim.height/2-nastavniciView.getSize().height/2);
		        nastavniciView.getContentPane().setBackground(new Color(245, 245, 245));
		        nastavniciView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		        nastavniciView.setIconImage( (new ImageIcon(NastavnikPanel.class.getResource("/ikone/library.png"))).getImage() );
		        nastavniciView.setVisible(true);		        
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
        
		List<Knjiga> lista = new ArrayList<Knjiga>();
		String field = "";
		if(txtFilter.getText().length()==0){
			lista = service.findAll();
        } else if(cmbFilter.getSelectedIndex()==1){
            field="naziv izdavaca";
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
            Object[] data={knjiga.getKnjigaId(), knjiga.getNaslov(), knjiga.getGodinaIzdanja(), knjiga.getIzdavacId().getNaziv(), knjiga.getLiteraturaCollection().toString()};            
            tableModel.addRow(data);
        }
    
        tabela.setModel(tableModel); 
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	private void setDefaultMod() {		
        btnFilter.setEnabled(true);
        tabela.setEnabled(true);
    }
	
}
