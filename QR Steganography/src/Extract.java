import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

public class Extract extends JFrame {

	private JPanel contentPane;
	
	private JTextField textField;
	JLabel lblNewLabel = new JLabel("");
	String path;
	BufferedImage img;
	String extractedMsg;
	
	
	static {
		Security.addProvider(new BouncyCastleProvider()); 
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Extract frame = new Extract();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public static String decrypt(byte[] encryptedMsg, byte[] keytext) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		
		final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
		
		
		/*SecureRandom random = new SecureRandom();
		byte[] iv = new byte[cipher.getBlockSize()];
		random.nextBytes(iv);*/

		final byte[] iv = Hex.decode("9f741fdb5d8845bdb48a94394e84f8a3");
		final Key key = new SecretKeySpec(keytext, "AES");
		
		
		String extracted = new String(decrypt(cipher, key, iv, encryptedMsg), StandardCharsets.UTF_8);
		
		return extracted;
		
	}
	
	private static String extract(BufferedImage image, String keytext) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		
		String extractedMsg;

	
		byte binImage[] =  ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
		
		byte[] secret;
		

		secret = getSecertBits(binImage);
		
		if(keytext.isEmpty())
		{
			extractedMsg = new String(secret);
		}
		else
		{
			
			if (keytext.length() < 16)
			{
				while (keytext.length() < 16 )
				{
					keytext = keytext + 'x';
				}
				
			}
			
			byte[] k = keytext.getBytes();
			
			extractedMsg = decrypt(secret, k);
		}
		
		
		
		return extractedMsg;
	
	}
	
	
	
	private static byte[] getLength(byte[] binImage)
	{
	
		
		int msgLength = 0;
		
		
		for(int i=0; i < 32; i++) {
			
			 msgLength = (msgLength << 1) | (binImage[i] & 1);
				
		}
		
	    byte[] length = new byte[msgLength];
	    

		return length;
	}
	
	
	private static byte[] getSecertBits(byte[] binImage)
	{
		int position = 32;
		
		int extractLength = getLength(binImage).length; //Get the length of the message 
		byte[] length = getLength(binImage); //get the length byte array
	  
	
		for (int i = 0; i < extractLength; i++)
		{
			for(int j = 0; j < 8; j++) {  
				
				length[i] =  (byte) ((length[i] << 1) | (binImage[position] & 1));
				position++;
				
			}
		}
		
		return length;
		
	}
	
	
	private static BufferedImage readImage(String path) throws FileNotFoundException, IOException
	{
	        	       
		BufferedImage QR = ImageIO.read(new FileInputStream(path));
          
        BufferedImage image = new BufferedImage(QR.getWidth(), QR.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
  		Graphics2D graphics = (Graphics2D) image.createGraphics();
  		graphics.drawRenderedImage(QR, null);
  		graphics.dispose();
		
  		
		return image;
	}
	
	
	
	public ImageIcon ResizeImage(String ImagePath) {
		
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance( lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		
		return image;
	}

	/**
	 * Create the frame.
	 */
	public Extract() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Extract.class.getResource("/img/icons8-binary-42.png")));
		setTitle("QRHide | Extract");
		
		 try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 827, 449);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Upload");
		mntmNewMenuItem.setIcon(new ImageIcon(Extract.class.getResource("/img/icons8-upload-file-20.png")));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File(System.getProperty("user.home")));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "png", "jpg");
			
				file.addChoosableFileFilter(filter);
				int result = file.showOpenDialog(null);
				
				if(result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = file.getSelectedFile();
					path = selectedFile.getAbsolutePath();
					
					if(path.toLowerCase().endsWith(".png") || path.toLowerCase().endsWith(".jpg"))
					{
						
						lblNewLabel.setIcon(ResizeImage(path));
						 
						 try {
							 
							img = readImage(path);
							
							
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
					}
					else
					{
						 
						JOptionPane.showMessageDialog(mntmNewMenuItem, "You must upload a .png or .jpg image");
					}
					
						
				}
				else if (result == JFileChooser.CANCEL_OPTION) {
					
					
				}
				
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
	
		
		textField = new JTextField();
		
		JMenu mnNewMenu_1 = new JMenu("Help");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Page instructions");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(textField,  "1. Upload a .png or .jpg image"  + "\n" + "2. Enter a key (Optional - Max 16 charachters)"  + "\n" + "3. Extract message");
			}
		});
		mntmNewMenuItem_2.setIcon(new ImageIcon(Extract.class.getResource("/img/icons8-help-20.png")));
		mnNewMenu_1.add(mntmNewMenuItem_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			
				String getK = textField.getText();
				
				int klength = getK.length();
				
				if(klength >= 16)
				{
					textField.setEditable(false);
				}
				
				if(e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode() == KeyEvent.VK_DELETE)
				{
					textField.setEditable(true);
				}
			}
		});
		textField.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		
		JButton btnNewButton = new JButton("Extract");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String keytext = textField.getText();
				
				if(img == null)
				{
					 JOptionPane.showMessageDialog(btnNewButton, "Please upload an image first");
				}
				else
				{
					try {
						
						
						extractedMsg = extract(img, keytext);
						JOptionPane.showMessageDialog(btnNewButton, "Message extracted");												
						textArea.setText(extractedMsg);
						
					} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException
							| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
							| BadPaddingException e1) {
						// TODO Auto-generated catch block
						 e1.printStackTrace();
					}
					
;				}
					
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Save");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
			    fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
			    
			    if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			        File file = fileChooser.getSelectedFile();
			        
			       if(extractedMsg != null)
			       {
				        try {
				        	 
				           String paths = file.getAbsolutePath();
				           Paths.get(paths);
				           Files.writeString(Paths.get(paths + ".txt") , extractedMsg);
				           
				           JOptionPane.showMessageDialog(mntmNewMenuItem_1, "Message saved");
				             
				        } catch (IOException ex) {
				        	
				        	JOptionPane.showMessageDialog(mntmNewMenuItem_1, "Failed to save message");
				           
				        }
				   } 
			       else
			       {
			    	   JOptionPane.showMessageDialog(mntmNewMenuItem_1, "You must upload a file first");
			       }
			
			    }
			}
		});
		mntmNewMenuItem_1.setIcon(new ImageIcon(Extract.class.getResource("/img/icons8-save-20.png")));
		mnNewMenu.add(mntmNewMenuItem_1);
		
	   Border border = BorderFactory.createLineBorder( null, 2);
	   lblNewLabel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
	   
		
	
		JLabel lblNewLabel_1 = new JLabel("Key");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		
	
		
		JLabel lblNewLabel_3 = new JLabel("Extract a message");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("DialogInput", Font.BOLD | Font.ITALIC, 18));
		
		JLabel lblNewLabel_2 = new JLabel("Image");
		lblNewLabel_2.setFont(new Font("DialogInput", Font.BOLD | Font.ITALIC, 13));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JButton btnNewButton_1_3 = new JButton("Welcome");
		btnNewButton_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				dispose();
				Welcome welcome = new Welcome();
				welcome.setVisible(true);
			}
		});
		btnNewButton_1_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JButton btnNewButton_1_3_1 = new JButton("Embed");
		btnNewButton_1_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				QRSteganography embed = new QRSteganography();
				embed.setVisible(true);
			}
		});
		btnNewButton_1_3_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JButton btnNewButton_1_3_2 = new JButton("Generate QR");
		btnNewButton_1_3_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GenerateQR generate = new GenerateQR();
				generate.setVisible(true);
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
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(27)
					.addComponent(lblNewLabel_1)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(19)
							.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(textField, Alignment.LEADING)
							.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)))
					.addGap(18)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE)
					.addGap(39))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(628, Short.MAX_VALUE)
					.addComponent(lblNewLabel_2)
					.addGap(147))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addGap(33)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1))
							.addGap(18)
							.addComponent(btnNewButton)
							.addGap(18)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 62, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_2)
							.addGap(52))))
		);
		
		
		scrollPane.setViewportView(textArea);
		contentPane.setLayout(gl_contentPane);
	}
	
	private static byte[] decrypt(final Cipher cipher, final Key key, final byte[] inventoryVector, final byte[] data)
			throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(inventoryVector));
		return cipher.doFinal(data);
	}

}
