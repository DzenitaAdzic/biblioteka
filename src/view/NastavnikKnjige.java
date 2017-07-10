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
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import service.IzdavacService;
import service.KnjigaService;
import model.Izdavac;
import model.Knjiga;
import model.Korisnik;


public class NastavnikKnjige extends JFrame {

    private KnjigaService service = new KnjigaService();
    private Knjiga odabraniObjekat = null;
    private String tabelaKolone[] = {"ID", "Naslov", "Godina", "Izdava�"};
    private DefaultTableModel tableModel=new DefaultTableModel(tabelaKolone, 0);
    private JTable tabela;    
    private JTextField txtFilter;
    private JButton btnDodatiLiteraturu;
    private JButton btnFilter;
    private JComboBox cmbFilter;
    
	private Korisnik korisnik;
	
	public NastavnikKnjige(Korisnik k) {
		korisnik = k;
		setTitle("Dodavanje literature");
		getContentPane().setLayout(null);
		
		JLabel txtField = new JLabel();
		txtField.setText("Izaberite knjigu koju zelite postaviti kao literaturu:");
		txtField.setBounds(20, 20, 400, 20);
		getContentPane().add(txtField);
		
		tabela = new JTable();
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setBounds(20, 50, 516, 174);
		getContentPane().add(scrollPane);
		
		JButton btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(439, 280, 94, 29);
		getContentPane().add(btnZatvoriti);
		
		btnDodatiLiteraturu = new JButton("Dodati literaturu");
		btnDodatiLiteraturu.setBounds(90, 280, 150, 29);		
		getContentPane().add(btnDodatiLiteraturu);
		
		JLabel lblFilterPoPolju = new JLabel("Filter po polju:");
		lblFilterPoPolju.setBounds(27, 240, 113, 16);
		getContentPane().add(lblFilterPoPolju);
		
		cmbFilter = new JComboBox();
		cmbFilter.setModel(new DefaultComboBoxModel(new String[] {"naslov", "naziv izdava�a"}));
		cmbFilter.setBounds(123, 239, 131, 27);
		getContentPane().add(cmbFilter);
		
		cmbFilter.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        if(cmbFilter.getSelectedIndex()==2){
		        	txtFilter.setVisible(false);
		        } else{
		        	txtFilter.setVisible(true);
		        }
		    }
		});
		
		txtFilter = new JTextField();
		txtFilter.setBounds(254, 240, 216, 26);
		getContentPane().add(txtFilter);
		txtFilter.setColumns(10);
		
		btnFilter = new JButton("Filter");
		btnFilter.setBounds(468, 239, 64, 29);
		getContentPane().add(btnFilter);
		
		
		btnDodatiLiteraturu.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tabela.getSelectedRowCount()==0) {
		            JOptionPane.showMessageDialog(null, "Niste odabrali nijedan unos.", "Gre�ka", JOptionPane.WARNING_MESSAGE);
		        } else{
		        	Integer id = (Integer) tabela.getModel().getValueAt(tabela.getSelectedRow(), 0);
		            odabraniObjekat=service.find(id);
		        	LiteraturaPanel panel=new LiteraturaPanel(korisnik, odabraniObjekat);
		        	panel.setResizable(false);
		        	panel.setSize(new Dimension(610, 530));
		        	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        	panel.setLocation(dim.width/2-panel.getSize().width/2, dim.height/2-panel.getSize().height/2);
		        	panel.getContentPane().setBackground(new Color(245, 245, 245));
		        	panel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        	panel.setIconImage( (new ImageIcon(NastavnikPanel.class.getResource("/ikone/library.png"))).getImage() );
		        	dispose();
		        	panel.setVisible(true);	
		        }
			}
			
		});
		
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
		if(txtFilter.getText().length()==0 && cmbFilter.getSelectedIndex()==0){
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
        	}                  
        }        
        for(Knjiga knjiga : lista){
            Object[] data={knjiga.getKnjigaId(), knjiga.getNaslov(), knjiga.getGodinaIzdanja(), knjiga.getIzdavacId().getNaziv(), knjiga.getLiteraturaCollection().toString()};            
            tableModel.addRow(data);
        }
        odabraniObjekat=null;
    
        tabela.setModel(tableModel); 
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
}
