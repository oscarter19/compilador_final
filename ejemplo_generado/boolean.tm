*      Compilacion TINY para el codigo objeto TM
*      Archivo: NOMBRE_ARREGLAR
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
*			Secuencia
*      -> asignacion
*      -> constante
2:       LDC       0,0(0)      cargar constante: 0
*      <- constante
3:       ST       0,0(5)      asignacion: almaceno el valor para el id varib
*      <- asignacion
*      -> if
*      -> identificador
4:       LD       0,0(5)      cargar valor de identificador: varib
*      -> identificador
*      If: el salto hacia el else debe estar aqui
*      -> escribir
*      -> constante
6:       LDC       0,1(0)      cargar constante: 1
*      <- constante
7:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      If: el salto hacia el final debe estar aqui
5:       JEQ       0,3(7)      if: jmp hacia else
*      -> escribir
*      -> constante
9:       LDC       0,0(0)      cargar constante: 0
*      <- constante
10:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
8:       LDA       7,2(7)      if: jmp hacia el final
*      <- if
*      Fin de la ejecucion.
11:       HALT       0,0,0