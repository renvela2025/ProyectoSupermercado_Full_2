Proyecto: Sistema de Supermercado (Formato 1)
Fecha: 2025-11-26 04:34:09

Estructura:
ProyectoSupermercado_Full/src/
  sistema/
    Menu.java
    Supermercado.java
  clientes/
    Cliente.java
    ColaPrioridad.java
  inventario/
    Producto.java
    NodoAVL.java
    ArbolAVL.java

Compilar (desde la carpeta ProyectoSupermercado_Full/src):
  javac sistema/*.java clientes/*.java inventario/*.java

Ejecutar:
  java sistema.Menu

Descripción:
  - Árbol AVL: inserción, búsqueda, eliminación, recorrido in-orden. Mensajes claros.
  - Cola de prioridad: heap (min-heap) por prioridad y hora de llegada.
  - Menú interactivo con validación de entrada.
  - Simulación interactiva: tras atender cada cliente, puedes agregar nuevos clientes, continuar o detener la simulación.

Entrega:
  - Código ya documentado con Javadoc y comentarios.
  - Diagrama de clases en formato PlantUML disponible en 'diagrama_clases.puml'.

Notas:
  - La simulación no bloquea con delays largos; el tiempo de atención se muestra como valor simulado (segundos).
  - Si quieres que agregue delays reales (Thread.sleep), dime y lo incluyo (pero puede hacer la ejecución lenta).
