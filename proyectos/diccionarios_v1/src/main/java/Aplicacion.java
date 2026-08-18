
public class Aplicacion {

    public static void main(String[] args) {
        SuministradorDeDiccionarios suministradorDeDiccionarios = new SuministradorDeDiccionariosEnFicheros("./diccionarios");
        ComunicadorConUsuario comunicadorConUsuario = new ComunicadorConUsuarioDesdeTerminal(args);
        ProcesadorDePeticiones procesadorDePeticiones = new ProcesadorDePeticiones(suministradorDeDiccionarios, comunicadorConUsuario);
        procesadorDePeticiones.procesarPeticion();
    }

}