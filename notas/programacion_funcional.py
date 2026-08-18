
def saluda(nombre):
    print("Hola " + nombre + "!")

saluda("Menchu")

variable = "Federico" # La variable apunta al dato "Federico" que tengo en RAM

variable = saluda     # Ahora mi variable apunta a la función saluda, que está en RAM, igual que en RAM tengo el dato "Federico"
variable("Federico") # Aquí ejecuto la función saluda a través de la variable

def imprimir_doble(numero):
    resultado = numero * 2
    print("El doble de " + str(numero) + " es " + str(resultado))

def imprimir_resultado_de_multiplicar(numero1, numero2):
    resultado = numero1 * numero2
    print("El resultado de multiplicar " + str(numero1) + " por " + str(numero2) + " es " + str(resultado))

# Y si quiero INYECTAR LOGICA a una función? Pasar como argumento LOGICA? No datos... LOGICA!
# Aquí entra la programación funcional.

def imprimir_resultado_de_operacion(numero1, operacion):
    resultado = operacion(numero1)
    print("El resultado de la operación es " + str(resultado))

def duplicar(numero):
    return numero * 2

def triplicar(numero):
    return numero * 3

def mitad(numero):
    return numero / 2

imprimir_doble(5)
imprimir_resultado_de_multiplicar(5, 10)

imprimir_resultado_de_operacion(5, duplicar)
imprimir_resultado_de_operacion(5, triplicar)
imprimir_resultado_de_operacion(5, mitad)

# hay veces que cuando creo una función, parte de la lógica es desconocida o puede ser variable...
# En estos casos, la programación funcional me resuelve el cotarro.
# Me permite inyectar LOGICA en tiempo de ejecución a una función.