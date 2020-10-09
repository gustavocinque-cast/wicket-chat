package br.com.chat;


public class Teste {

	public static void main(String[] args) {
		
		/*String t = "";
		String regex = "";
		
		for (int i = 0; i < t.length()-1; i++) {
			regex+="\\w";
		}
		
		if(!Pattern.matches("[a-zA-Z][_a-zA-Z0-9*][_a-zA-Z0-9*][_a-zA-Z0-9*][_a-zA-Z0-9*]+", u.getLogin())){
			error("O nome do usuario só deve conter caracteres alfanuméricos e _");
		}*/
		/*String texto="aaa";
		
		try {
			MessageDigest msg = MessageDigest.getInstance("MD5");
			msg.reset();
			msg.update(texto.getBytes(Charset.forName("UTF8")));
			byte[] resultByte = msg.digest();
			String result = new String(HexBin.encode(resultByte));
			System.out.println("senha: "+result);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("ERRO no getinstance criptografia");
		}*/
		
		String texto="asadsAa2222=)33333ds=ada)sad";
		texto=texto.replaceAll("[=][)]","<h1>teste</h1>");
		System.out.println(texto);
	}

}
