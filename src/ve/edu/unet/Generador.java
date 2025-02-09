package ve.edu.unet;

import ve.edu.unet.nodosAST.*;

public class Generador {
	/* Ilustracion de la disposicion de la memoria en
	 * este ambiente de ejecucion para el lenguaje Tiny
	 *
	 * |t1	|<- mp (Maxima posicion de memoria de la TM
	 * |t1	|<- desplazamientoTmp (tope actual)
	 * |free|
	 * |free|
	 * |...	|
	 * |x	|
	 * |y	|<- gp
	 *
	 * */

	/* desplazamientoTmp es una variable inicializada en 0
	 * y empleada como el desplazamiento de la siguiente localidad
	 * temporal disponible desde la parte superior o tope de la memoria
	 * (la que apunta el registro MP).
	 *
	 * - Se decrementa (desplazamientoTmp--) despues de cada almacenamiento y
	 *
	 * - Se incrementa (desplazamientoTmp++) despues de cada eliminacion/carga en
	 *   otra variable de un valor de la pila.
	 *
	 * Pudiendose ver como el apuntador hacia el tope de la pila temporal
	 * y las llamadas a la funcion emitirRM corresponden a una inserccion
	 * y extraccion de esta pila
	 */
	private static int desplazamientoTmp = 0;
	private static TablaSimbolos tablaSimbolos = null;

	public static void setTablaSimbolos(TablaSimbolos tabla){
		tablaSimbolos = tabla;
	}

	public static void generarCodigoObjeto(NodoBase raiz){
		System.out.println();
		System.out.println();
		System.out.println("------ CODIGO OBJETO DEL LENGUAJE TINY GENERADO PARA LA TM ------");
		System.out.println();
		System.out.println();
		generarPreludioEstandar();
		generar(raiz);
		/*Genero el codigo de finalizacion de ejecucion del codigo*/
		UtGen.emitirComentario("Fin de la ejecucion.");
		UtGen.emitirRO("HALT", 0, 0, 0, "");
		System.out.println();
		System.out.println();
		System.out.println("------ FIN DEL CODIGO OBJETO DEL LENGUAJE TINY GENERADO PARA LA TM ------");
	}

	//Funcion principal de generacion de codigo
	//prerequisito: Fijar la tabla de simbolos antes de generar el codigo objeto
	private static void generar(NodoBase nodo){
		if(tablaSimbolos!=null) {
			if (nodo instanceof NodoBloque)
				generarBlock(nodo);
			if (nodo instanceof NodoIf) {
				generarIf(nodo);
			} else if (nodo instanceof NodoRepeat) {
				generarRepeat(nodo);
			} else if (nodo instanceof NodoAsignacion) {
				generarAsignacion(nodo);
			} else if (nodo instanceof NodoLeer) {
				generarLeer(nodo);
			} else if (nodo instanceof NodoEscribir) {
				generarEscribir(nodo);
			} else if (nodo instanceof NodoValor) {
				generarValor(nodo);
			} else if (nodo instanceof NodoIdentificador) {
				generarIdentificador(nodo);
			} else if (nodo instanceof NodoOperacion) {
				generarOperacion(nodo);
			} else if (nodo instanceof NodoFor) {
				generarFor(nodo);
			} else if (nodo instanceof NodoValorBooleano){
				generarVariableBooleana(nodo);
			} else if (nodo instanceof NodoVariableEntera) {
				generarVariableEntera(nodo);
			} else if (nodo instanceof NodoDeclaracion) {
				generarDeclaraciones(nodo);
			} else if (nodo instanceof NodoVector) {
				generarVector(nodo);
			}
			else{
				System.out.println("BUG: Tipo de nodo a generar desconocido");
			}
			/*Si el hijo de extrema izquierda tiene hermano a la derecha lo genero tambien*/
			if(nodo.TieneHermano())
				generar(nodo.getHermanoDerecha());
		}else
			System.out.println("???ERROR: por favor fije la tabla de simbolos a usar antes de generar codigo objeto!!!");
	}

	private static void generarIf(NodoBase nodo){
		NodoIf n = (NodoIf)nodo;
		int localidadSaltoElse,localidadSaltoEnd,localidadActual;
		if(UtGen.debug)	UtGen.emitirComentario("-> if");
		/*Genero el codigo para la parte de prueba del IF*/
		generar(n.getPrueba());
		localidadSaltoElse = UtGen.emitirSalto(1);
		UtGen.emitirComentario("If: el salto hacia el else debe estar aqui");
		/*Genero la parte THEN*/
		generar(n.getParteThen());
		localidadSaltoEnd = UtGen.emitirSalto(1);
		UtGen.emitirComentario("If: el salto hacia el final debe estar aqui");
		localidadActual = UtGen.emitirSalto(0);
		UtGen.cargarRespaldo(localidadSaltoElse);
		UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadActual, "if: jmp hacia else");
		UtGen.restaurarRespaldo();
		/*Genero la parte ELSE*/
		if(n.getParteElse()!=null){
			generar(n.getParteElse());
			localidadActual = UtGen.emitirSalto(0);
			UtGen.cargarRespaldo(localidadSaltoEnd);
			UtGen.emitirRM_Abs("LDA", UtGen.PC, localidadActual, "if: jmp hacia el final");
			UtGen.restaurarRespaldo();
		}

		if(UtGen.debug)	UtGen.emitirComentario("<- if");
	}

	private static void generarRepeat(NodoBase nodo){
		NodoRepeat n = (NodoRepeat)nodo;
		int localidadSaltoInicio;
		if(UtGen.debug)	UtGen.emitirComentario("-> repeat");
		localidadSaltoInicio = UtGen.emitirSalto(0);
		UtGen.emitirComentario("repeat: el salto hacia el final (luego del cuerpo) del repeat debe estar aqui");
		/* Genero el cuerpo del repeat */
		generar(n.getCuerpo());
		/* Genero el codigo de la prueba del repeat */
		generar(n.getPrueba());
		UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadSaltoInicio, "repeat: jmp hacia el inicio del cuerpo");
		if(UtGen.debug)	UtGen.emitirComentario("<- repeat");
	}
	private static void generarBlock(NodoBase nodo){
		NodoBloque n = (NodoBloque)nodo;
		if(n.getDlcs() != null)
			generar(n.getDlcs());
		generar(n.getSt());

	}

	private static void generarDeclaraciones(NodoBase nodo){
		NodoSecuencia n = (NodoSecuencia)nodo;

		generar(n.getDecl());
		generar(n.getDcs());

	}

	private static void generarAsignacion(NodoBase nodo){
		NodoAsignacion n = (NodoAsignacion)nodo;
		int direccion;
		if(UtGen.debug)	UtGen.emitirComentario("-> asignacion");
		/* Genero el codigo para la expresion a la derecha de la asignacion */
		generar(n.getExpresion());
		/* Ahora almaceno el valor resultante */
		direccion = tablaSimbolos.getDireccion(n.getIdentificador());
		UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "asignacion: almaceno el valor para el id "+n.getIdentificador());
		if(UtGen.debug)	UtGen.emitirComentario("<- asignacion");
	}

	private static void generarLeer(NodoBase nodo){
		NodoLeer n = (NodoLeer)nodo;
		int direccion;
		if(UtGen.debug)	UtGen.emitirComentario("-> leer");
		UtGen.emitirRO("IN", UtGen.AC, 0, 0, "leer: lee un valor entero ");
		direccion = tablaSimbolos.getDireccion(n.getIdentificador());
		UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "leer: almaceno el valor entero leido en el id "+n.getIdentificador());
		if(UtGen.debug)	UtGen.emitirComentario("<- leer");
	}

	private static void generarEscribir(NodoBase nodo){
		NodoEscribir n = (NodoEscribir)nodo;
		if(UtGen.debug)	UtGen.emitirComentario("-> escribir");
		/* Genero el codigo de la expresion que va a ser escrita en pantalla */
		generar(n.getExpresion());
		/* Ahora genero la salida */
		UtGen.emitirRO("OUT", UtGen.AC, 0, 0, "escribir: genero la salida de la expresion");
		if(UtGen.debug)	UtGen.emitirComentario("<- escribir");
	}

	private static void generarValor(NodoBase nodo){
		NodoValor n = (NodoValor)nodo;
		if(UtGen.debug)	UtGen.emitirComentario("-> constante");
		UtGen.emitirRM("LDC", UtGen.AC, n.getValor(), 0, "cargar constante: "+n.getValor());
		if(UtGen.debug)	UtGen.emitirComentario("<- constante");
	}

	private static void generarIdentificador(NodoBase nodo){
		NodoIdentificador n = (NodoIdentificador)nodo;
		int direccion;
		if(UtGen.debug)	UtGen.emitirComentario("-> identificador");
		direccion = tablaSimbolos.getDireccion(n.getNombre());
		UtGen.emitirRM("LD", UtGen.AC, direccion, UtGen.GP, "cargar valor de identificador: "+n.getNombre());
		if(UtGen.debug)	UtGen.emitirComentario("-> identificador");
	}

	private static void generarOperacion(NodoBase nodo){
		NodoOperacion n = (NodoOperacion) nodo;
		if(UtGen.debug)	UtGen.emitirComentario("-> Operacion: " + n.getOperacion());
		/* Genero la expresion izquierda de la operacion */
		generar(n.getOpIzquierdo());
		/* Almaceno en la pseudo pila de valor temporales el valor de la operacion izquierda */
		UtGen.emitirRM("ST", UtGen.AC, desplazamientoTmp--, UtGen.MP, "op: push en la pila tmp el resultado expresion izquierda");
		/* Genero la expresion derecha de la operacion */
		generar(n.getOpDerecho());
		/* Ahora cargo/saco de la pila el valor izquierdo */
		UtGen.emitirRM("LD", UtGen.AC1, ++desplazamientoTmp, UtGen.MP, "op: pop o cargo de la pila el valor izquierdo en AC1");
		switch(n.getOperacion()){
			case	mas:	UtGen.emitirRO("ADD", UtGen.AC, UtGen.AC1, UtGen.AC, "op: +");
				break;
			case	menos:	UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: -");
				break;
			case	por:	UtGen.emitirRO("MUL", UtGen.AC, UtGen.AC1, UtGen.AC, "op: *");
				break;
			case	div:	UtGen.emitirRO("DIV", UtGen.AC, UtGen.AC1, UtGen.AC, "op: /");
				break;
			case	menor:	UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: <");
				UtGen.emitirRM("JLT", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<0)");
				UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
				UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
				UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
				break;
			case	igual:	UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: ==");
				UtGen.emitirRM("JEQ", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC==0)");
				UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
				UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
				UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
				break;
			case menorIgual:
				UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: <=");
				UtGen.emitirRM("JLE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<=0)");
				UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
				UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incondicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
				UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
				break;
			case mayor:
				UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: >");
				UtGen.emitirRM("JGT", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC>0)");
				UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
				UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incondicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
				UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
				break;
			case mayorIgual:
				UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: >=");
				UtGen.emitirRM("JGE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC>=0)");
				UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
				UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incondicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
				UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
				break;
			case diferente:
				UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: !=");
				UtGen.emitirRM("JNE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC!=0)");
				UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
				UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incondicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
				UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
				break;
			case and:
				UtGen.emitirRO("AND", UtGen.AC, UtGen.AC1, UtGen.AC, "op: AND");
				break;
			case or:
				UtGen.emitirRO("OR", UtGen.AC, UtGen.AC1, UtGen.AC, "op: OR");
				break;
			case not:
				UtGen.emitirRM("LDC", UtGen.AC1, 1, UtGen.AC1, "Load constant 1");
				UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: NOT");
				break;
			case mod:    UtGen.emitirRO("MOD", UtGen.AC, UtGen.AC1, UtGen.AC, "op: %");
				break;
			default:
				UtGen.emitirComentario("BUG: tipo de operacion desconocida");
		}
		if(UtGen.debug)	UtGen.emitirComentario("<- Operacion: " + n.getOperacion());
	}

	private static void generarVariableBooleana(NodoBase nodo){
		NodoIdentificador n = (NodoIdentificador) nodo;
		int direccion = tablaSimbolos.getDireccion(n.getNombre());

		if(UtGen.debug) UtGen.emitirComentario("-> variable booleana");

		UtGen.emitirRM("LD", UtGen.AC, direccion, UtGen.GP, "cargar valor de la variable booleana: " + n.getNombre());

		if(UtGen.debug) UtGen.emitirComentario("<- variable booleana");
	}

	private static void generarVariableEntera(NodoBase nodo){
		NodoIdentificador n = (NodoIdentificador) nodo;
		int direccion = tablaSimbolos.getDireccion(n.getNombre());

		if(UtGen.debug) UtGen.emitirComentario("-> variable entera");

		UtGen.emitirRM("LD", UtGen.AC, direccion, UtGen.GP, "cargar valor de la variable entera: " + n.getNombre());

		if(UtGen.debug) UtGen.emitirComentario("<- variable entera");
	}

	private static void generarVector(NodoBase nodo){
		NodoVector n = (NodoVector) nodo;
		int direccionBase = tablaSimbolos.getDireccion(n.getNombre());
		if (direccionBase == -1) {
			System.out.println("Error: Vector no declarado");
			return;
		}

		if(UtGen.debug) UtGen.emitirComentario("-> vector: " + n.getNombre());

		// Generar el c�digo para calcular el �ndice del vector
		generar(n.getIndice());

		// Calcular la direcci�n efectiva del elemento del vector
		UtGen.emitirRO("ADD", UtGen.AC, UtGen.AC, direccionBase, "Calcular direcci�n del vector " + n.getNombre());
		UtGen.emitirRM("LD", UtGen.AC, 0, UtGen.AC, "Cargar valor del vector " + n.getNombre());

		if(UtGen.debug) UtGen.emitirComentario("<- vector: " + n.getNombre());
	}


	private static void generarFor(NodoBase nodo){
		NodoFor n = (NodoFor)nodo;
		int localidadInicio, localidadCondicion, localidadIncremento, localidadFin;

		if(UtGen.debug) UtGen.emitirComentario("-> for");

		// Generar c?digo para la inicializaci?n


		localidadCondicion = UtGen.emitirSalto(0);

		localidadFin = UtGen.emitirSalto(1);

		// Generar c?digo para el cuerpo del ciclo
		localidadInicio = UtGen.emitirSalto(0);


		UtGen.emitirRM_Abs("LDA", UtGen.PC, localidadCondicion, "for: regresar a la condici?n");
		localidadFin = UtGen.emitirSalto(0);
		UtGen.cargarRespaldo(localidadFin);
		UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadFin, "for: fin del ciclo");
		UtGen.restaurarRespaldo();

		if(UtGen.debug) UtGen.emitirComentario("<- for");
	}

	private static void generarOperacionVector(NodoBase nodo){
		NodoOperacionVector n = (NodoOperacionVector) nodo;
		int direccionBaseIzq = tablaSimbolos.getDireccion(n.getVectorIzquierdo().getNombre());
		int direccionBaseDer = tablaSimbolos.getDireccion(n.getVectorDerecho().getNombre());

		if (direccionBaseIzq == -1 || direccionBaseDer == -1) {
			System.out.println("Error: Vector no declarado");
			return;
		}

		int tamanio = tablaSimbolos.getTamanioVector(n.getVectorIzquierdo().getNombre());
		if (tamanio != tablaSimbolos.getTamanioVector(n.getVectorDerecho().getNombre())) {
			System.out.println("Error: Vectores de diferentes tama�os");
			return;
		}

		if(UtGen.debug) UtGen.emitirComentario("-> operacion entre vectores: " + n.getVectorIzquierdo().getNombre() + " y " + n.getVectorDerecho().getNombre());

		for (int i = 0; i < tamanio; i++) {
			// Calcular direcci�n de cada elemento del vector izquierdo
			UtGen.emitirRM("LDA", UtGen.AC, direccionBaseIzq + i, UtGen.GP, "Cargar direcci�n del vector izquierdo");
			UtGen.emitirRM("LD", UtGen.AC1, 0, UtGen.AC, "Cargar valor del vector izquierdo");

			// Calcular direcci�n de cada elemento del vector derecho
			UtGen.emitirRM("LDA", UtGen.AC, direccionBaseDer + i, UtGen.GP, "Cargar direcci�n del vector derecho");
			UtGen.emitirRM("LD", UtGen.AC2, 0, UtGen.AC, "Cargar valor del vector derecho");

			// Realizar la operaci�n
			switch (n.getOperacion()) {
				case SUMA:
					UtGen.emitirRO("ADD", UtGen.AC, UtGen.AC1, UtGen.AC2, "Suma de elementos de vectores");
					break;
				case RESTA:
					UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC2, "Resta de elementos de vectores");
					break;
				case MULTIPLICACION:
					UtGen.emitirRO("MUL", UtGen.AC, UtGen.AC1, UtGen.AC2, "Multiplicaci�n de elementos de vectores");
					break;
				case DIVISION:
					UtGen.emitirRO("DIV", UtGen.AC, UtGen.AC1, UtGen.AC2, "Divisi�n de elementos de vectores");
					break;
			}

			// Guardar el resultado de vuelta en el vector izquierdo
			UtGen.emitirRM("ST", UtGen.AC, direccionBaseIzq + i, UtGen.GP, "Guardar resultado en el vector izquierdo");
		}

		if(UtGen.debug) UtGen.emitirComentario("<- operacion entre vectores: " + n.getVectorIzquierdo().getNombre() + " y " + n.getVectorDerecho().getNombre());
	}



	//TODO: enviar preludio a archivo de salida, obtener antes su nombre
	private static void generarPreludioEstandar(){
		UtGen.emitirComentario("Compilacion TINY para el codigo objeto TM");
		UtGen.emitirComentario("Archivo: "+ "NOMBRE_ARREGLAR");
		/*Genero inicializaciones del preludio estandar*/
		/*Todos los registros en tiny comienzan en cero*/
		UtGen.emitirComentario("Preludio estandar:");
		UtGen.emitirRM("LD", UtGen.MP, 0, UtGen.AC, "cargar la maxima direccion desde la localidad 0");
		UtGen.emitirRM("ST", UtGen.AC, 0, UtGen.AC, "limpio el registro de la localidad 0");
	}

}
