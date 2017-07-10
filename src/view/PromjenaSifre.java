package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import service.KorisnikService;
import service.StudentService;
import model.Korisnik;

public class PromjenaSifre extends JFrame {
	private JPasswordField txtStariPassword;
	private JPasswordField txtNoviPassword;
	private Korisnik korisnik;
	public PromjenaSifre(Korisnik k) {
		korisnik = k;
		setTitle("Promjena passworda");
		getContentPane().setLayout(null);
		
		JLabel lblStariPassword = new JLabel("Stari password:");
		lblStariPassword.setBounds(17, 19, 106, 16);
		getContentPane().add(lblStariPassword);
		
		JLabel lblNoviPassword = new JLabel("Novi password:");
		lblNoviPassword.setBounds(17, 52, 106, 16);
		getContentPane().add(lblNoviPassword);
		
		txtStariPassword = new JPasswordField();
		txtStariPassword.setBounds(135, 14, 212, 25);
		getContentPane().add(txtStariPassword);
		
		txtNoviPassword = new JPasswordField();
		txtNoviPassword.setBounds(135, 47, 212, 25);
		getContentPane().add(txtNoviPassword);
		
		JButton btnSpremiti = new JButton("Spremiti");
		btnSpremiti.setBounds(82, 84, 96, 29);
		getContentPane().add(btnSpremiti);
		
		JButton btnZatvoriti = new JButton("Zatvoriti");
		btnZatvoriti.setBounds(178, 84, 96, 29);
		getContentPane().add(btnZatvoriti);
		
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
		
		btnSpremiti.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(txtNoviPassword.getText().length()<6){
					JOptionPane.showMessageDialog(null, "Novi password mora imati najmanje 6 karaktera.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtNoviPassword.requestFocus();
					return;
				}
				
				if(!txtStariPassword.getText().equals(korisnik.getPassword())){
					JOptionPane.showMessageDialog(null, "Unijeli ste pogrešan stari password.", "Greška", JOptionPane.ERROR_MESSAGE);
					txtNoviPassword.requestFocus();
					return;
				}
				
				korisnik.setPassword(txtNoviPassword.getText());
				KorisnikService service = new KorisnikService();
				try{
					service.editWithoutRefresh(korisnik);
					StudentPanel panel=new StudentPanel(korisnik);
					panel.setResizable(false);
					panel.setSize(new Dimension(470, 175));
			        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
			        panel.setLocation(dim.width/2-panel.getSize().width/2, dim.height/2-panel.getSize().height/2);
			        panel.getContentPane().setBackground(new Color(245, 245, 245));
			        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        dispose();
			        panel.setVisible(true);		
				} catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
			
		});
	}
}
