package Base.Task_5;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Encode {
    public static String encode(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        try{
            KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
            SecretKey myDesKey = keygenerator.generateKey();



            Cipher desCipher;
            desCipher = Cipher.getInstance("DES");


            byte[] text = string.getBytes("UTF8");


            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            byte[] textEncrypted = desCipher.doFinal(text);

            String s = new String(textEncrypted);
            stringBuilder.append(s);

            desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
            byte[] textDecrypted = desCipher.doFinal(textEncrypted);

            s = new String(textDecrypted);
            stringBuilder.append(" : "+s);

        }catch(Exception e)
        {
            System.out.println("Exception");
        }
        return stringBuilder.toString();
    }
}
