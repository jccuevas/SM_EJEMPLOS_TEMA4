# SM_EJEMPLOS_TEMA4

Ejemplos del Tema 4 Servicios y comunicaciones multimedia para terminales móviles
- Asignatura: Servicios Móviles
- Grado en Ingeniería Telemática
- Universidad de Jaén.

##Objetivos del tema
- Conocer las bases del manejo de gráficos en 2-D de Android.
- Conocer las bases de la animación de gráficos.
- Conocer el manejo del audio básico.
- Conocer las bases del streaming de contenido multimedia.
##Contenido=
- Gráficos 2-D:
  - Canvas
  - Drawable
  - Animación de Drawables
  - Frame Animation
  - Animación de Vistas
  - Tween animation
- Multimedia
  - Media player
  - PlayBack de audio y video
  - Captura de audio

###Ejemplo el protocolo del Ejericio 2

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
