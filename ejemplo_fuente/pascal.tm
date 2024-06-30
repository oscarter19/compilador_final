
*      Compilacion TINY para el codigo objeto TM
*      Archivo: NOMBRE_ARREGLAR
*      Preludio estandar:
0:       LD       6,0(0)      cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      limpio el registro de la localidad 0
BUG: Tipo de nodo a generar desconocido
*      -> if
*      -> Operacion: menor
*      -> constante
2:       LDC       0,0(0)      cargar constante: 0
*      <- constante
3:       ST       0,0(6)      op: push en la pila tmp el resultado expresion izquierda
*      -> constante
4:       LDC       0,9(0)      cargar constante: 9
*      <- constante
5:       LD       1,0(6)      op: pop o cargo de la pila el valor izquierdo en AC1
6:       SUB       0,1,0      op: <
7:       JLT       0,2(7)      voy dos instrucciones mas alla if verdadero (AC<0)
8:       LDC       0,0(0)      caso de falso (AC=0)
9:       LDA       7,1(7)      Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)
10:       LDC       0,1(0)      caso de verdadero (AC=1)
*      <- Operacion: menor
*      If: el salto hacia el else debe estar aqui
*      -> escribir
*      -> constante
12:       LDC       0,0(0)      cargar constante: 0
*      <- constante
13:       OUT       0,0,0      escribir: genero la salida de la expresion
*      <- escribir
*      If: el salto hacia el final debe estar aqui
11:       JEQ       0,3(7)      if: jmp hacia else
*      <- if
BUG: Tipo de nodo a generar desconocido
*      Fin de la ejecucion.
15:       HALT       0,0,0

