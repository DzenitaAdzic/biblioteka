package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import model.Posudba;
import model.Primjerak;
import model.Rezervacija;
import model.Knjiga;
import service.KnjigaService;
import service.NastavnikService;
import service.PosudbaService;
import service.RezervacijaService;


public class NastavnikPosudbe extends JFrame{
	private Korisnik korisnik;
	private PosudbaService service = new PosudbaService();
	DefaultTableModel tableModel = new DefaultTableModel(
    		new Object[][] {
    		},
    		new String[] {
    			"Invetarni broj", "Knjiga", "Datum posudbe", "Datum vracanja"
    		}
    	);
    
    private JTable table;
    
    private void showForm(JFrame view){    
		view.setSize(610, 530);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        view.setLocation(dim.width/2 - view.getSize().width/2, dim.height/2 - view.getSize().height/2);
        view.getContentPane().setBackground(new Color(245, 245, 245));        
        view.setResizable(true);
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
        view.setIconImage( (new ImageIcon(NastavnikPanel.class.getResource("/ikone/library.png"))).getImage() );
        dispose();
    }
    
    public NastavnikPosudbe(Korisnik k){
    	korisnik = k;
    	setTitle("Posudbe");
    	getContentPane().setLayout(new FlowLayout());
    	
    	JTable tabela = new JTable();		
    	tabela.setModel(tableModel);
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setBounds(17, 16, 515, 268);
		getContentPane().add(scrollPane);
		
		JButton btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(435, 331, 97, 29);
		getContentPane().add(btnZatvoriti);
		btnZatvoriti.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				NastavnikPanel panel=new NastavnikPanel(korisnik);
				showForm(panel);        			
			}
			
		});
		
      reloadTable();

    }
    
    
    private void reloadTable(){		
    	
  	   PosudbaService service = new PosudbaService();
  	   
  	     List<Posudba> lista = (List<Posudba>)korisnik.getPosudbaCollection();
        
         for (Posudba p : korisnik.getPosudbaCollection()) {               
             Object[] data = {p.getPrimjerak().getInvBroj(), p.getPrimjerak().getKnjigaId().toString(), p.getDatumPosudbe() + "", p.getDatumVracanja()};
             tableModel.addRow(data);
         }
	}
}

