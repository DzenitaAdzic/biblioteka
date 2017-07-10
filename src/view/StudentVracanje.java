package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.List;
//import java.util.Calendar;
//import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import model.Knjiga;
import model.Korisnik;
import model.Posudba;
import model.PosudbaPK;
import model.Primjerak;
import model.Student;

import service.KnjigaService;
import service.PosudbaService;
import service.PrimjerakService;

public class StudentVracanje extends JFrame{

	private JTable tabela;
	private PosudbaService service = new PosudbaService();
    private String tabelaKolone[] = {"Inv. broj", "Knjiga", "Datum posudbe", "Datum vraćanja"};
    private DefaultTableModel tableModel=new DefaultTableModel(tabelaKolone, 0);
	
	private Korisnik korisnik;
	private JTextField textField;
	
	public StudentVracanje(Korisnik k) {		
		korisnik = k;
		setTitle("Vracanje");
		getContentPane().setLayout(null);
		
		tabela = new JTable();		
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setBounds(17, 16, 515, 268);
		getContentPane().add(scrollPane);
		
		JButton btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(435, 331, 97, 29);
		getContentPane().add(btnZatvoriti);
		
		JLabel lableFilterPoPolju = new JLabel("Filter po polju:");
		lableFilterPoPolju.setBounds(17, 298, 97, 16);
		getContentPane().add(lableFilterPoPolju);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"knjiga (naslov)", "datum posudbe", "datum vraćanja"}));
		comboBox.setBounds(112, 294, 113, 27);
		getContentPane().add(comboBox);
		
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
	}
	
	private void osvjeziTabelu(){		
		tableModel=new DefaultTableModel(tabelaKolone, 0){
	    	@Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };
        
		List<Posudba> lista = new ArrayList<Posudba>();
		lista=service.findAll();
            
        for(Posudba posudba : lista){
        	Object[] data={posudba.getPrimjerak().getInvBroj(), posudba.getPrimjerak().getKnjigaId().toString(), posudba.getDatumPosudbe() + "", posudba.getDatumVracanja()};            
            tableModel.addRow(data);
        }
        
        tabela.setModel(tableModel); 
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
	}
	
}
