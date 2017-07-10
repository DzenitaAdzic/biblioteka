package view;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Korisnik;

public class StudentPanel extends JFrame {
	private Korisnik korisnik;
	public StudentPanel(Korisnik k) {
		korisnik = k;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Student panel");
		getContentPane().setLayout(null);
		
		JLabel lblDobrodoli = new JLabel("Prijavljeni ste kao:");
		lblDobrodoli.setBounds(24, 24, 145, 16);
		getContentPane().add(lblDobrodoli);
		
		JLabel lblUsername = new JLabel(k.getKorisnickoIme() + " (" + k.getIme() + " " + k.getPrezime() + ")");
		lblUsername.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblUsername.setForeground(new Color(30, 144, 255));
		lblUsername.setBounds(149, 24, 263, 16);
		getContentPane().add(lblUsername);
		
		JButton btnOdjava = new JButton("Odjava");
		btnOdjava.setBounds(350, 19, 117, 29);
		getContentPane().add(btnOdjava);
		
		JButton btnRezervacije = new JButton("Rezervacije");
		btnRezervacije.setBounds(45, 66, 107, 59);
		getContentPane().add(btnRezervacije);
		
		JButton btnPosudbe = new JButton("Posudbe");
		btnPosudbe.setBounds(164, 66, 107, 59);
		getContentPane().add(btnPosudbe);
		
		JButton btnPromjenaSifre = new JButton("Promjena šifre");
		btnPromjenaSifre.setBounds(283, 66, 107, 59);
		getContentPane().add(btnPromjenaSifre);
		
		btnOdjava.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Prijava prijavaView=new Prijava();
				prijavaView.setResizable(false);
		        prijavaView.setSize(new Dimension(400, 190));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        prijavaView.setLocation(dim.width/2-prijavaView.getSize().width/2, dim.height/2-prijavaView.getSize().height/2);
		        prijavaView.getContentPane().setBackground(new Color(245, 245, 245));
		        prijavaView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
		        prijavaView.setVisible(true);		        
				dispose();
			}
			
		});
		
		btnRezervacije.addActionListener(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent e) {
				StudentRezervacije rezervacijeView = new StudentRezervacije(korisnik);
				rezervacijeView.setResizable(true);
				rezervacijeView.setSize(new Dimension(450, 300));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        rezervacijeView.setLocation(dim.width/2-rezervacijeView.getSize().width/2, dim.height/2-rezervacijeView.getSize().height/2);
		        rezervacijeView.getContentPane().setBackground(new Color(245, 245, 245));
		        rezervacijeView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
		        rezervacijeView.setVisible(true);		        
				dispose();
			}			
		});
		
		btnPosudbe.addActionListener(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent e) {
				StudentPosudbe posudbeView = new StudentPosudbe(korisnik);
				posudbeView.setResizable(true);
				posudbeView.setSize(new Dimension(550, 390));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        posudbeView.setLocation(dim.width/2-posudbeView.getSize().width/2, dim.height/2-posudbeView.getSize().height/2);
		        posudbeView.getContentPane().setBackground(new Color(245, 245, 245));
		        posudbeView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
		        posudbeView.setVisible(true);		        
				dispose();
			}			
		});
		
		btnPromjenaSifre.addActionListener(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent e) {
				PromjenaSifre promjena = new PromjenaSifre(korisnik);
				promjena.setResizable(true);
				promjena.setSize(new Dimension(363, 150));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        promjena.setLocation(dim.width/2-promjena.getSize().width/2, dim.height/2-promjena.getSize().height/2);
		        promjena.getContentPane().setBackground(new Color(245, 245, 245));
		        promjena.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
		        promjena.setVisible(true);		        
				dispose();
			}			
		});

	}

}
