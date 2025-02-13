package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Utilidades {
	static Scanner input = new Scanner(System.in);

	public static void muestraMenu() {

		System.out.println("""
				1.Mostrar todos los mensajes
				2.Mostrar mensajes con nº me gusta > 3
				3.Incrementar en 1 me gustas de los mensajes escritos por pepe@gmail.com
				4.Borrar mensajes de dolores@gmail.com
				5.Insertar mensajes
				6.salir
				""");

	}
	public static int solicitaInt() {
	    int id = 0;
	    while (true) {
	        System.out.println("Introduce un número:");
	        try {
	            id = input.nextInt();
	            input.nextLine(); 
	            return id; 
	        } catch (InputMismatchException e) {
	            System.out.println("Error: No has introducido un número válido.");
	            input.nextLine();
	        }
	    }
	}

	public static float solicitaFloat() {
	    float precio=0;
	    boolean entradaValida = false;
	    do {
	        try {
	            precio = Float.parseFloat(input.nextLine());
	            entradaValida = true;
	        } catch (NumberFormatException e) {
	            System.out.println("Error: Ingresa un número válido.");
	        }
	    } while (!entradaValida);
	    return precio;
	}


	public static String solicitaString() {
		String string = input.nextLine();
		return string;
	}

	  public static String solicitaMail() {
	        String mail;
	        boolean formatoCorrecto = false;
	        do {
	            System.out.println("Introduce tu correo electrónico:");
	            mail = input.nextLine();
	            if (validarEmail(mail)) {
	                formatoCorrecto = true;
	            } else {
	                System.out.println("Formato de correo electrónico incorrecto. Inténtalo de nuevo.");
	            }
	        } while (!formatoCorrecto);
	        return mail;
	    }

	  public static boolean validarEmail(String email) {
	        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(email);
	        return matcher.matches();
	    }
	  
	public static Date solicitarFecha() {

		boolean fechaValida = false;
		Date fecha = null;
		do {
			System.out.println("Introduce la fecha (YYYY-MM-DD):");
			String fechaStr = input.nextLine();
			try {
				fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
				fechaValida = true;
			} catch (ParseException e) {
				System.out.println(
						"Error al parsear la fecha. Asegúrate de que esté en el formato correcto (YYYY-MM-DD).");
			}
		} while (!fechaValida);
		return fecha;
	}

}
