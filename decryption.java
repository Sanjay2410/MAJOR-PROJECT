package pack;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;

public class decryption
{
	public String decrypt(String txt,String skey)
	{
		String decryptedtext = null;
		try
		{	
			byte[] bs=Base64.decode(skey);
			SecretKey sec=new SecretKeySpec(bs, "AES");
			
			System.out.println("converted string to seretkey:"+sec);

			System.out.println("secret key:"+sec);

			Cipher aesCipher = Cipher.getInstance("AES");//getting AES instance
			aesCipher.init(Cipher.ENCRYPT_MODE,sec);//initiating ciper encryption using secretkey

			byte[] byteCipherText =new BASE64Decoder().decodeBuffer(txt); //encrypting data 

			aesCipher.init(Cipher.DECRYPT_MODE,sec,aesCipher.getParameters());//initiating ciper decryption

			byte[] byteDecryptedText = aesCipher.doFinal(byteCipherText);
			decryptedtext = new String(byteDecryptedText);

			System.out.println("Decrypted Text:"+decryptedtext);
		}
		catch(Exception e)
		{
			System.out.println(e);	
		}
		return decryptedtext;
	}
}



