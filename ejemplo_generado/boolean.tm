*      Compilacion TINY para el codigo objeto TM
*      Archivo: NOMBRE_ARREGLAR
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*			Secuencia
*      -> asignacion
*      -> Operacion: mod
*      -> constante
2:       LDC       0,10(0)      cargar constante: 10
*      <- constante
3:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
4:       LDC       0,4(0)      cargar constante: 4
*      <- constante
5:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
6:       DIV       2,1,0      op: /
7:       MUL       2,0,2      op: *
8:       SUB       0,1,2      op: %
*      <- Operacion: mod
9:       ST       0,0(5)      asignacion: almaceno el valor para el id varb
*      <- asignacion
*      -> escribir
*      -> identificador
10:       LD       0,0(5)      cargar valor de identificador: varb
*      -> identificador
11:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      Fin de la ejecucion.
12:       HALT       0,0,0