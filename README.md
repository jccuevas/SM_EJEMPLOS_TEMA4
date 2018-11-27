# SM_EJEMPLOS_TEMA4
Ejemplos Tema 4 de Servicios Móviles

Ejemplo el protocolo dle Ejericio 2


PROTOCOLO = PETICION / RESPUESTA

  PETICION = USER SP date-time SP 1*DATOS
  
  PETICION = USER SP DATE SP DATOS
  
    USER = 6*20ALPHA; nombre
    
    DATE = YYYY “-“ MM “-“ DD “-“ HH “-“ m “-“ SS
    
    YYYY = 4DIGIT; Año expresado en cuatro dígitos
    
    MM = 2DIGT; mes del año con 0. Ejemplo Enero el 01.
    
    ;...

    DATOS = REF SP FAB SP UDS; Estos son los datos de protocolo 
			; que define una pieza por su referencia <REF>
			; fabricante <FAB> y cantidad solicitada <UDS>
      REF = 10*20DIGIT
      FAB = 10*20DIGIT
      UDS = 1*5DIGIT
