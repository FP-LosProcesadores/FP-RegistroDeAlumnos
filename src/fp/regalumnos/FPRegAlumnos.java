/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.regalumnos;
import java.util.*;
import java.io.*;
/**
 *
 * @author Enano
 */
public class FPRegAlumnos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Menu(); //Se ejecuta el menú, de ahí se conecta a todos los demás métodos.
    }
    public static void Menu() //Despliega el menú de opciones.
    {
        int option;
        System.out.println("Bienvenido al registro de alumnos");
        System.out.println("Por favor introduzca una opción del menú: ");
        System.out.println("------------------------------------------");
        System.out.println("1. Registrar Alumnos");
        System.out.println("2. Mostrar Registro");
        System.out.println("3. Borrar Registro");
        System.out.println("4. Salir");
        Scanner in = new Scanner(System.in);
        option = in.nextInt();
        in.nextLine();
        switch (option) //Evalua con un switch las opciones posibles.
        {
            case 1:
                nAlumn();
                break;
            case 2:
                showRecords();
                break;
            case 3:
                eraseRecord();
                break;
            case 4:
                System.out.println("Gracias por usar el programa!");
                System.exit(0);
                break;
            default:    //En caso de no elegir ninguna de las opciones disponibles, termina el programa.
                System.out.println("Opción fuera del rango, se saldrá del programa");
                System.exit(0);
                break;
        }
    }
    public static void nAlumn () //Se encarga de pedir cuantos alumnos se van a registrar
    {
        System.out.println("¿Cuántos alumnos desea registrar?");
        Scanner in = new Scanner(System.in);
        getAlData(in.nextInt()); //La cantidad de alumnos se envía a getAlData
    }
    public static void getAlData(int nAlumns) //Se encarga de pedir la información de los alumnos, los nombres y matrículas.
    {
        Scanner in=new Scanner(System.in);
        String [][] Alumns = new String [nAlumns][2];
        String Name, Number;
        for(int i=0;i<Alumns.length;i++)
        {
            System.out.println("Registro N°"+(i+1));
            System.out.println("Ingrese el nombre completo del alumno");
            Name=in.nextLine();
            for(int j=0;j<i;j++) //Se recorre el arreglo, del primero al actual.
            {
                while (Alumns[j][0].contains(Name)) //Mientras que el nombre sea igual a alguno ya ingresado, se pedirá otro nombre.
                {
                    System.out.println("Ese nombre ya ha sido ingresado, vuelva a ingresar otro");
                    Name=in.nextLine();
                } 
            }
            System.out.println("Ingrese la matrícula del alumno");
            Number=in.nextLine();
            for(int k=0;k<i;k++) //Se recorre el arreglo, del primero al actual.
            {
                while (Alumns[k][1].contains(Number)) //Mientras que la matrícula sea igual a alguna ya ingresada, se pedirá otra matrícula.
                {
                    System.out.println("Esa matrícula ya fue ingresada, vuelva a intentarlo");
                    Number=in.nextLine();
                }
            }
            Alumns[i][0]=" "+Name+" ";      //Se agrega el nombre y la matrícula al arreglo después de comprobar que no estan repetidos
            Alumns[i][1]=" "+Number+" ";
        }
        createFile(Alumns, getFileName()); //El arreglo con los alumnos y el return de getFileName (el nombre del archivo) se mandan a createFile
    }
    public static String getFileName()  //Se encarga de pedir el nombre del archivo.
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Ingrese el nombre que tendrá el archivo: ");
        String fileName=in.nextLine()+".txt";  
        return fileName;    //Se regresa el nombre
    }
    public static void createFile(String[][] alList, String fileName) //Se encarga de crear el archivo, utilizando la información del arreglo
    {                                                                 //y el nombre de archivo deseado
        File file = new File (fileName); //Se crea un archivo utilizando el nombre
        if (!file.exists()) //Se comprueba que se haya creado
        {   
            try //Lo intenta, en caso de errores.
            {  
                file.createNewFile();   //Utiliza el método createNewFile() para crear el archivo vacío.
            } 
            catch (IOException ex) 
            {
                System.out.println("El nombre del archivo ya esta en uso o es inválido, inténtelo de nuevo"); //Despliega un mensaje de error
                ex.printStackTrace();   //Aparte del de sistema.
                createFile(alList, fileName);   //Se reinicia el método.
            }
        }
        try //Lo intenta, en caso de errores
        {
            PrintWriter write = new PrintWriter (file, "utf-8");//Instancia un objeto tipo PrintWriter, el cual escribe caracter por caracter.
            for(int i=0;i<alList.length;i++)    //Recorre el arreglo.
            {
                write.println(Arrays.toString(alList[i]));  //Arrays.toString toma como parámetro el arreglo, y lo escribe en el documento.
            }
            write.close();  //Se cierra el archivo.
            System.out.println("La creación del archivo fue exitosa!");
            restart();      //Se manda a un menú de reseteo
        }
        catch (FileNotFoundException | UnsupportedEncodingException e) //En caso de error, imprime el log del error.
        {
            e.printStackTrace();
        }
    }
    public static void showRecords() //Se encarga de leer e imprimir el texto de los archivos.
    {
        Scanner in=new Scanner (System.in);
        System.out.println("Ingrese el nombre del archivo que quiera revisar:");
        File file = new File (in.nextLine()+".txt"); //Pide el nombre del archivo, y le agrega la extensión.
        String cadena="";
        try (FileReader lectura = new FileReader(file)) //Intenta instanciar un objeto FileReader
        {
            BufferedReader bufferL = new BufferedReader(lectura);
            while (cadena!=null) //Mientras la cadena no sea nula
            {
                cadena = bufferL.readLine(); //Lee línea por línea 
                if(cadena!=null)//Si no se encuentra null dentro del archivo
                {
                    System.out.println(cadena); //Imprime la línea.
                }
            }
            bufferL.close();//Se cierra la instancia del BufferedReader y FileReader
            lectura.close();
            System.out.println("La lectura del archivo fue exitosa!");
        } 
        catch (Exception e) //En caso de no poder, manda un mensaje de error
        {
            System.out.println("No se pudo encontrar el archivo, se le regresará al menú principal");
            System.out.println("------------------------------------------------------------------");
            Menu(); //Y regresa al usuario al menú principal.
        } 
        restart();  //En caso de ser exitoso, manda al usuario a las opciones de restart()
    }
    public static void eraseRecord() //Se encarga de borrar archivos .txt
    {
        Scanner in=new Scanner (System.in);
        System.out.println("Ingrese el nombre del archivo que desea eliminar"); //Se pide el nombre del archivo.
        String fileName=in.nextLine();  
        System.out.println("Se borrará el archivo "+fileName+", vuelva a ingresar el nombre del archivo para confirmar esta acción");
        String Confirmation=in.nextLine(); //Se pide una confirmación
        if(fileName.equals(Confirmation)) //Si se confirma, borra el archivo.
        {
            fileName+=".txt";   //Se le agrega la extensión .txt al nombre del archivo para poder ser eliminado.
            File f = new File (fileName);   //Se instancia el objeto con ese nombre
            try //Se intenta eliminar
            {
                f.delete(); //Se utiliza delete() para eliminar el fichero
                System.out.println("El archivo "+fileName+" se ha borrado exitosamente.");  //Mensaje de confirmación
                restart(); //En caso de ser exitoso, manda al usuario a las opciones de restart()
            }
            catch (Exception e) //En caso de no ser exitoso
            {
                System.out.println("No se encontro el archivo"); //Se despliega el mensaje de error
            }
        }
        //En caso de que no se confirme, o haya fallado, el programa ofrece opciones.
        System.out.println("Se ha cancelado la operación");
        System.out.println("Desea:");
        System.out.println("1. Volver a intentarlo");
        System.out.println("2. Regresar al menú");
        System.out.println("3. Salir");
        switch (in.nextInt())
        {
            case 1:
                eraseRecord(); //Reinicia el método
                break;
            case 2:
                Menu();       //Regresa al menú
                break;
            case 3:
                System.out.println("Gracias por usar el programa!");    //Agradece, y sale del programa
                System.exit(0);
                break;
            default:
                System.out.println("Opción fuera del rango, se saldrá del programa");//En caso de no usar una opción predefinida, se sale del programa
                System.exit(0);
                break;
        } 
    }
    public static void restart  () //Se encarga de regresar al menú, utilizando S,s o N,n. Se hizo un método para ahorrar código.
    {
        String opt;
        Scanner in = new Scanner(System.in);
        System.out.println("¿Desea regresar al menú?[S/N]"); //Se recibe la elección del usuario
        opt =in.nextLine();
        switch (opt)
        {
            case "S":   //En caso de decir si, se regresa al Menú
                System.out.println("------------------------------------------------------------------");
                Menu();
                break;
            case "s":
                System.out.println("------------------------------------------------------------------");
                Menu();
                break;
            case "N":  //En caso de decir no, se sale del programa
                System.out.println("Gracias por usar el programa!");
                System.exit(0);
                break;
            case "n":
                System.out.println("Gracias por usar el programa!");
                System.exit(0);
                break;
            default: //En caso de no elegir ninguna opción valida, reinicia el método para volver a pedir la elección
                System.out.println("Opción no valida, ingrese [S] para continuar, o [N] para salir ");
                restart(); 
                break;
        }
    }
}
