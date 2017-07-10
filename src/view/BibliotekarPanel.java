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

public class BibliotekarPanel extends JFrame {
	private Korisnik korisnik;
	public BibliotekarPanel(Korisnik k) {
		korisnik = k;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Bibliotekar");
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
		btnOdjava.setBounds(474, 19, 117, 29);
		getContentPane().add(btnOdjava);
		
		JButton btnKnjige = new JButton("Knjige");
		btnKnjige.setBounds(284, 57, 117, 49);
		getContentPane().add(btnKnjige);
		
		JButton btnAutori = new JButton("Autori");
		btnAutori.setBounds(24, 57, 117, 49);
		getContentPane().add(btnAutori);
		
		JButton btnIzdavaci = new JButton("Izdavaci");
		btnIzdavaci.setBounds(155, 57, 117, 49);
		getContentPane().add(btnIzdavaci);
		
		JButton btnKorisnici = new JButton("Studenti");
		btnKorisnici.setBounds(415, 57, 117, 49);
		getContentPane().add(btnKorisnici);
		
		JButton btnNastavnici = new JButton("Nastavnici");
		btnNastavnici.setBounds(155, 110, 117, 49);
		getContentPane().add(btnNastavnici);
		
		JButton btnPredmeti = new JButton("Predmeti");
		btnPredmeti.setBounds(284, 110, 117, 49);
		getContentPane().add(btnPredmeti);
		
		JButton btnRezervacije = new JButton("Rezervacije");
		btnRezervacije.setBounds(415, 110, 117, 49);
		getContentPane().add(btnRezervacije);
		
		JButton btnPrimjerci = new JButton("Primjerci");
		btnPrimjerci.setBounds(24, 110, 117, 49);
		getContentPane().add(btnPrimjerci);
		
		JButton btnPosudbe = new JButton("Posudbe");
		btnPosudbe.setBounds(24, 163, 117, 49);
		getContentPane().add(btnPosudbe);
		
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
		
		btnAutori.addActionListener(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent e) {
				BibliotekarAutori autoriView = new BibliotekarAutori(korisnik);
				autoriView.setResizable(true);
				autoriView.setSize(new Dimension(440, 380));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        autoriView.setLocation(dim.width/2-autoriView.getSize().width/2, dim.height/2-autoriView.getSize().height/2);
		        autoriView.getContentPane().setBackground(new Color(245, 245, 245));
		        autoriView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
		        autoriView.setVisible(true);		        
				dispose();
			}			
		});
		
		btnIzdavaci.addActionListener(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent e) {
				BibliotekarIzdavaci izdavaciView = new BibliotekarIzdavaci(korisnik);
				izdavaciView.setResizable(false);
				izdavaciView.setSize(new Dimension(440, 380));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        izdavaciView.setLocation(dim.width/2-izdavaciView.getSize().width/2, dim.height/2-izdavaciView.getSize().height/2);
		        izdavaciView.getContentPane().setBackground(new Color(245, 245, 245));
		        izdavaciView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
		        izdavaciView.setVisible(true);		        
				dispose();
			}			
		});
		
		btnKnjige.addActionListener(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent e) {
				BibliotekarKnjige knjigeView = new BibliotekarKnjige(korisnik);
				knjigeView.setResizable(false);
				knjigeView.setSize(new Dimension(550, 440));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        knjigeView.setLocation(dim.width/2-knjigeView.getSize().width/2, dim.height/2-knjigeView.getSize().height/2);
		        knjigeView.getContentPane().setBackground(new Color(245, 245, 245));
		        knjigeView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
		        knjigeView.setVisible(true);		        
				dispose();
			}			
		});
		
		btnKorisnici.addActionListener(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent e) {
				BibliotekarStudenti korisniciView = new BibliotekarStudenti(korisnik);
				korisniciView.setResizable(false);
				korisniciView.setSize(new Dimension(525, 460));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        korisniciView.setLocation(dim.width/2-korisniciView.getSize().width/2, dim.height/2-korisniciView.getSize().height/2);
		        korisniciView.getContentPane().setBackground(new Color(245, 245, 245));
		        korisniciView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
		        korisniciView.setVisible(true);		        
				dispose();
			}			
		});
		
		btnNastavnici.addActionListener(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent e) {
				BibliotekarNastavnici izdavaciView = new BibliotekarNastavnici(korisnik);
				izdavaciView.setResizable(false);
				izdavaciView.setSize(new Dimension(520, 450));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        izdavaciView.setLocation(dim.width/2-izdavaciView.getSize().width/2, dim.height/2-izdavaciView.getSize().height/2);
		        izdavaciView.getContentPane().setBackground(new Color(245, 245, 245));
		        izdavaciView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
		        izdavaciView.setVisible(true);		        
				dispose();
			}
		});
		
		btnPredmeti.addActionListener(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent e) {
				BibliotekarPredmeti izdavaciView = new BibliotekarPredmeti(korisnik);
				izdavaciView.setResizable(false);
				izdavaciView.setSize(new Dimension(540, 440));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        izdavaciView.setLocation(dim.width/2-izdavaciView.getSize().width/2, dim.height/2-izdavaciView.getSize().height/2);
		        izdavaciView.getContentPane().setBackground(new Color(245, 245, 245));
		        izdavaciView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
		        izdavaciView.setVisible(true);		        
				dispose();
			}			
		});
		
		btnRezervacije.addActionListener(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent e) {
				BibliotekarRezervacije izdavaciView = new BibliotekarRezervacije(korisnik);
				izdavaciView.setResizable(false);
				izdavaciView.setSize(new Dimension(520, 405));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        izdavaciView.setLocation(dim.width/2-izdavaciView.getSize().width/2, dim.height/2-izdavaciView.getSize().height/2);
		        izdavaciView.getContentPane().setBackground(new Color(245, 245, 245));
		        izdavaciView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
		        izdavaciView.setVisible(true);		        
				dispose();
			}			
		});
		
		btnPrimjerci.addActionListener(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent e) {
				BibliotekarPrimjerci izdavaciView = new BibliotekarPrimjerci(korisnik);
				izdavaciView.setResizable(false);
				izdavaciView.setSize(new Dimension(570, 450));
		        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();	        
		        izdavaciView.setLocation(dim.width/2-izdavaciView.getSize().width/2, dim.height/2-izdavaciView.getSize().height/2);
		        izdavaciView.getContentPane().setBackground(new Color(245, 245, 245));
		        izdavaciView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
		        izdavaciView.setVisible(true);		        
				dispose();
			}			
		});

		btnPosudbe.addActionListener(new ActionListener(){			
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
			}			
		});


	}

}
