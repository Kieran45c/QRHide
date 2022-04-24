

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Hashtable;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import javax.swing.border.MatteBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class GenerateQR extends JFrame {

	
	private JPanel contentPane;
	private JTextField sField;
	JLabel ImgDisplay = new JLabel();
	BufferedImage QRimg;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					GenerateQR frame = new GenerateQR();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ImageIcon ResizeImage(BufferedImage QRImg) {
		
		ImageIcon MyImage = new ImageIcon(QRImg);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance( ImgDisplay.getWidth(), ImgDisplay.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		
		return image;
	}
	
	
	public ErrorCorrectionLevel getLevel(String msg) {
		
		ErrorCorrectionLevel error = null;
		
		if(msg == "L")
		{
			 error = ErrorCorrectionLevel.L;
			
		}
		else if(msg == "M")
		{
			 error = ErrorCorrectionLevel.M;
			
		}
		else if(msg == "Q")
		{
			 error = ErrorCorrectionLevel.Q;
			
		}
		else if(msg == "H")
		{
			 error = ErrorCorrectionLevel.H;
		}
		
		
		return error;
			
			
		}
	
	
	
	private static BufferedImage genQR(String qrText, int size, ErrorCorrectionLevel errorType)
			throws WriterException, IOException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, errorType);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, size, size, hintMap);

	
		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_3BYTE_BGR);
		image.createGraphics();


		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		graphics.setColor(Color.BLACK);

		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		
		return image;
		
	}
	

	/**
	 * Create the frame.
	 */
	public GenerateQR() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(GenerateQR.class.getResource("/img/icons8-binary-42.png")));
		setTitle("QRHide | Generate ");
		
	
		 try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		 
		JFileChooser fileChooser = new JFileChooser();
		 
		Border border = BorderFactory.createLineBorder( null, 2);
		ImgDisplay.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		   
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 827, 449);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileOptions = new JMenu("File");
		menuBar.add(fileOptions);
		
		JMenuItem saveFile = new JMenuItem("Save");
		saveFile.setIcon(new ImageIcon(GenerateQR.class.getResource("/img/icons8-save-20.png")));
		
		fileOptions.add(saveFile);
		
		JMenu mnNewMenu_1 = new JMenu("Help");
		menuBar.add(mnNewMenu_1);
		
		JLabel qrText = new JLabel("Message");
		qrText.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel ecLevel = new JLabel("Error correction level");
		ecLevel.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JTextArea mField = new JTextArea();
		mField.setLineWrap(true);
		mField.setWrapStyleWord(true);
		
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Page instructions");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				JOptionPane.showMessageDialog(mField,  "1. Enter a message (Max. 4,296 characters)"  + "\n" + "2. Enter a size (Max 6000)"  + "\n" + "3. Select error correction level"  + "\n" + "4. Generate QR" + "\n" + "5. Save image");
						
			}
		});
		mntmNewMenuItem_2.setIcon(new ImageIcon(GenerateQR.class.getResource("/img/icons8-help-20.png")));
		mnNewMenu_1.add(mntmNewMenuItem_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
	
		sField = new JTextField();
		sField.setText("300");
		sField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				
				char c = e.getKeyChar();
				
				if(!Character.isDigit(c))
				{
					e.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				
				
				String getK = sField.getText();
				
				int klength = getK.length();
				
				if(klength >= 4)
				{
					sField.setEditable(false);
				}
				
				if(e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_DELETE)
				{
					sField.setEditable(true);
				}
			}
		});
		sField.setColumns(10);
		
		JLabel imageSize = new JLabel("Image size");
		imageSize.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		String[] ecLvl = {"L", "M", "Q", "H"};
		JComboBox ecDropdown = new JComboBox(ecLvl);
		
		JButton genQR = new JButton("Generate QR");
		genQR.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		genQR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String message = mField.getText();
				int imgSize;
				
				
				if(sField.getText().equals("") || message.isEmpty())
				{
					JOptionPane.showMessageDialog(genQR, "Please enter a message, and a size");
				}
				else
				{
				
					
					imgSize = Integer.parseInt(sField.getText());
				
					String msg = (String) ecDropdown.getSelectedItem();
				    
				    
			   
					try {
							if(imgSize <= 6000)
							{
								QRimg = genQR(message, imgSize, getLevel(msg));
								ImgDisplay.setIcon(ResizeImage(QRimg));
							}
							else
							{
								JOptionPane.showMessageDialog(genQR, "Size must be 6000 or less");
							}
							
						
						} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException
								| NoSuchPaddingException | InvalidAlgorithmParameterException
								| IllegalBlockSizeException | BadPaddingException | WriterException
								| IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
			    }
						
			}
		});
		
		
		saveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				    fileChooser.setFileFilter(new FileNameExtensionFilter("*.png", "png"));
				    
				    
				    if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				        File file = fileChooser.getSelectedFile();
				        
				        if(QRimg != null)
				        {
					        try {
					           ImageIO.write((BufferedImage) QRimg, "png", new File(file.getAbsolutePath()+ ".png"));
					           
					           JOptionPane.showMessageDialog(saveFile, "Image saved!");
					             
					        } catch (IOException ex) {
					        	
					        	JOptionPane.showMessageDialog(saveFile, "Failed to save image!!");
					           
					        }
				        
				        }
				        else
				        {
				        	JOptionPane.showMessageDialog(saveFile, "You must create a QR code first");
				        }
				    } 
				    
				    
				}
			
			});
		
		JLabel lblNewLabel_3 = new JLabel("Generate a QR Code");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("DialogInput", Font.BOLD | Font.ITALIC, 18));
		
		JLabel lblNewLabel_2 = new JLabel("Image");
		lblNewLabel_2.setFont(new Font("DialogInput", Font.BOLD | Font.ITALIC, 13));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JButton btnNewButton_1_3_1 = new JButton("Embed");
		btnNewButton_1_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				QRSteganography embed = new QRSteganography();
				embed.setVisible(true);
				
			}
		});
		btnNewButton_1_3_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JButton btnNewButton_1_3_2 = new JButton("Extract");
		btnNewButton_1_3_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				dispose();
				Extract extraction = new Extract();
				extraction.setVisible(true);
			}
		});
		btnNewButton_1_3_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JButton btnNewButton_1_3_2_1 = new JButton("Scan");
		btnNewButton_1_3_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				dispose();
				Scan scan = new Scan();
				scan.setVisible(true);
			}
		});
		btnNewButton_1_3_2_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JButton btnNewButton_1_3 = new JButton("Welcome");
		btnNewButton_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				dispose();
				Welcome welcome = new Welcome();
				welcome.setVisible(true);
			}
		});
		btnNewButton_1_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 805, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(55)
					.addComponent(btnNewButton_1_3, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(57)
					.addComponent(btnNewButton_1_3_1, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(44)
					.addComponent(btnNewButton_1_3_2, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(41)
					.addComponent(btnNewButton_1_3_2_1, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(64))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 34, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_1_3)
						.addComponent(btnNewButton_1_3_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1_3_2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1_3_2_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
		);
		panel.setLayout(gl_panel);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(22)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(qrText)
								.addComponent(imageSize)
								.addComponent(ecLevel))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(genQR, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
									.addComponent(sField)
									.addComponent(scrollPane)
									.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE))
								.addComponent(ecDropdown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(50)
							.addComponent(ImgDisplay, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(638)
							.addComponent(lblNewLabel_2))
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(11)
							.addComponent(ImgDisplay, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addComponent(lblNewLabel_2))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
								.addComponent(qrText))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(sField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(imageSize))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(ecDropdown, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(ecLevel))
							.addGap(18)
							.addComponent(genQR)))
					.addPreferredGap(ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
		);
		
	
		scrollPane.setViewportView(mField);
		contentPane.setLayout(gl_contentPane);
	}
}
