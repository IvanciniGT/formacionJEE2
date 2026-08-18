// En JAVA 1.8 se añade el paquete java.util.function 
// que contiene interfaces funcionales, es decir, 
// interfaces que permiten apuntar a funciones.
// Son tipos de datos para apuntar a funciones.
// Cada uno de esos interfaces define UNA FUNCION ESPECIAL
// para invocar a la función apuntada.
// Hay 4 principales:
// - Supplier<T>        Función que no recibe argumenbtos y devuelve un valor de tipo T
//                      Cualquier getter es un Supplier<T>
//                      La función get() es la función especial de Supplier<T>
// - Consumer<T>        Función que recibe un argumento de tipo T y no devuelve nada
//                      Cualquier setter es un Consumer<T>
//                      La función accept() es la función especial de Consumer<T>
// - Function<T,R>      Función que recibe un argumento de tipo T y devuelve un valor de tipo R
//                      La función apply() es la función especial de Function<T,R>
// - Predicate<T>       Función que recibe un argumento de tipo T y devuelve un valor booleano
//                      Las funciones de tipo isXXX, hasXXX son Predicate<T>
//                      La función test() es la función especial de Predicate<T>
// Luego hay combinaciones de ellos.
// BiFunction<T,U,R>    Función que recibe dos argumentos de tipo T y U y devuelve un valor de tipo R
// BiPredicate<T,U>     Función que recibe dos argumentos de tipo T y U y devuelve un valor booleano

import java.util.function.Consumer;
import java.util.function.Function;

public class ProgramacionFuncional {

    public static String generarSaludoFormal(String nombre) {
        return "Buenos días " + nombre;
    }
    public static String generarSaludoInformal(String nombre) {
        return "Hola " + nombre;
    }

    public static void saluda(String nombre) {
        System.out.println("Hola " + nombre);
    }

    public static void main(String[] args) {
        saluda("Menchu");
        String variable = "Federico";
        Consumer<String> variable2 = ProgramacionFuncional::saluda;
        // En java 1.8 sale el operador :: que permite apuntar a funciones
        variable2.accept("Federico");

        imprimirResultadoDeOperacion(5, ProgramacionFuncional::duplicar);
        imprimirResultadoDeOperacion(5, ProgramacionFuncional::triplicar);
        imprimirResultadoDeOperacion(5, ProgramacionFuncional::mitar);
        imprimirSaludo("Menchu", ProgramacionFuncional::generarSaludoFormal);
        imprimirSaludo("Menchu", ProgramacionFuncional::generarSaludoInformal);
        // Cuando no quiero reusar una función, y el tener la función definida de forma tradicional NO MEJORA LA LEGIBILIDAD DEL CODIGO
        // Tenemos a nuestra disposición otra herramienta: EXPRESIONES LAMBDA
        // Qué es una expresión lambda? Ante todo una expresión.
        // Qué es una expresión en programación? Es un trozo de código que devuelve un valor. Por ejemplo, 5+6 es una expresión que devuelve 11.
        int numero = 17; // Statement (Sentencia = Frase/Oración)
        int numero2 = 5+6; // Statement (Sentencia = Frase/Oración)
                      // 5+6 es una expresión
        // Por tanto una expresión lambda es un trozo de código que devuelve un valor, qué valor devuelve?
        // Una función ANONIMA (sin nombre) definida dentro de la propia expresión.

        Function<String,String> miFuncion = (String nombre) -> {
                                                                    return "Buenos días " + nombre;
                                                                };
        miFuncion = (String nombre) -> "Buenos días " + nombre;
        miFuncion = (nombre) -> "Buenos días " + nombre;
        miFuncion = nombre -> "Buenos días " + nombre;

        imprimirSaludo("Gertru", miFuncion);
        
        imprimirSaludo("Gertru", nombre -> "Buenos días " + nombre);



    }

    public static double duplicar(double x) {
        return x * 2;
    }
    public static double triplicar(double x) {
        return x * 3;
    }
    public static double mitar(double x) {
        return x / 2;
    }
    public static void imprimirResultadoDeOperacion(double x, Function<Double,Double> operacion) {
        double resultado = operacion.apply(x);
        System.out.println("El resultado es: " + resultado);
    }

    public static void imprimirSaludo(String nombre, Function<String,String> funcionGeneradoraDeSaludos) {
        String saludo = funcionGeneradoraDeSaludos.apply(nombre);
        System.out.println(saludo);
    }

}
