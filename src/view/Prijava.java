package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import model.Korisnik;
import model.Nastavnik;
import service.KorisnikService;
import service.NastavnikService;
import service.ServiceException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Prijava extends JFrame {
	private JTextField txtKorisnickoIme;
	private JPasswordField txtPassword;
	
	public Prijava() {
		/*
		Korisnik k = new Korisnik();
		k.setSifra("S1");
		k.setIme("Bibliotekar");
		k.setPrezime("Prvi");
		k.setKorisnickoIme("bibliotekar");
		k.setPassword("123");
		k.setUloga(0);
		k.setNegativniBodovi(0);
		KorisnikService s = new KorisnikService();
		try {
			s.create(k);
		} catch (ServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Prijava");
		getContentPane().setLayout(null);
		
		JLabel lblKorisnikoIme = new JLabel("Korisničko ime:");
		lblKorisnikoIme.setBounds(26, 37, 110, 16);
		getContentPane().add(lblKorisnikoIme);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(26, 67, 110, 16);
		getContentPane().add(lblPassword);
		
		txtKorisnickoIme = new JTextField();
		txtKorisnickoIme.setBounds(148, 32, 219, 26);
		getContentPane().add(txtKorisnickoIme);
		txtKorisnickoIme.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(148, 62, 219, 26);
		getContentPane().add(txtPassword);
		
		JButton btnPrijava = new JButton("Prijava");
		btnPrijava.setBounds(134, 110, 117, 34);
		getContentPane().add(btnPrijava);
		
		btnPrijava.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(txtKorisnickoIme.getText().length()==0){
					JOptionPane.showMessageDialog(null, "Niste unijeli korisničko ime.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtKorisnickoIme.requestFocus();
					return;
				}
				if(txtPassword.getText().length()==0){
					JOptionPane.showMessageDialog(null, "Niste unijeli password.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtPassword.requestFocus();
					return;
				}
				KorisnikService service = new KorisnikService();
				List<Korisnik> korisnici = service.findBy("korisnickoIme", txtKorisnickoIme.getText());
				if(korisnici.size()==0){
					JOptionPane.showMessageDialog(null, "Korisnik sa korisničkim imenom '" + txtKorisnickoIme.getText() + "' ne postoji.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtKorisnickoIme.requestFocus();
					return;
				} else{
					Korisnik k = korisnici.get(0);
					if(k.getPassword().equals(txtPassword.getText())){
						switch (k.getUloga()){
						case 0:
							pokreniBibliotekarPanel(k);
							break;
						case 1:
							pokreniNastavnikPanel(k);
							break;
						case 2: 
							pokreniStudentPanel(k);
							break;
						}
					} else{
						JOptionPane.showMessageDialog(null, "Pogrešan password.", "Greška", JOptionPane.ERROR_MESSAGE);
						txtPassword.requestFocus();
						return;
					}
				}
			}
		});
	}
	
	private void pokreniBibliotekarPanel(Korisnik korisnik){
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
	
	private void pokreniNastavnikPanel(Korisnik korisnik){
		NastavnikPanel panel=new NastavnikPanel(korisnik);
		panel.setResizable(true);
		panel.setSize(new Dimension(610, 530));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
        panel.setLocation(dim.width/2-panel.getSize().width/2, dim.height/2-panel.getSize().height/2);
        panel.getContentPane().setBackground(new Color(245, 245, 245));
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
        panel.setVisible(true);
        dispose();
	}
	
	private void pokreniStudentPanel(Korisnik korisnik){
		StudentPanel panel=new StudentPanel(korisnik);
		panel.setResizable(true);
		panel.setSize(new Dimension(470, 175));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
        panel.setLocation(dim.width/2-panel.getSize().width/2, dim.height/2-panel.getSize().height/2);
        panel.getContentPane().setBackground(new Color(245, 245, 245));
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
        panel.setVisible(true);
        this.dispose();
	}
		
}
