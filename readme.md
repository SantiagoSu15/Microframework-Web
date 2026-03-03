# Mini Framework Web en Java

Un micro-framework web construido desde cero en Java puro, sin dependencias externas. Permite definir rutas HTTP y servir archivos estáticos con una API simple.

---

## Arquitectura

**Estructura del proyecto**

```
src/main/java/org/example/rest/
├── HttpServer.java         → Núcleo del framework: servidor TCP, enrutamiento y archivos estáticos
├── HttpRequest.java        → Representa la petición HTTP entrante 
├── HttpResponse.java       → Representa la respuesta HTTP saliente
├── StaticFileConfi.java    → Configuración de la carpeta de archivos estáticos
└── webMethod.java          → Interfaz funcional para definir los handlers de cada ruta
└── MathService.java        → Ejemplo de aplicación construida sobre el framework

src/main/resources/
└── webroot/public/         → Carpeta de archivos estáticos (HTML, CSS, imágenes)
```

### Flujo 

```
Cliente HTTP
    │
    ▼
HttpServer (puerto 35000)
    │  Lee la primera línea → método, path, query string
    ▼
¿Existe el path en el map de endPoints?
    ├── SÍ  → Ejecuta el webMethod registrado → responde con HTML
    └── NO  → Busca el archivo en staticfiles()
                  ├── Encontrado → responde con el archivo (HTML/CSS/PNG...)
                  └── No encontrado → 404 
```

---

## Requisitos 

- **Java 21** 
- **Maven 3.6** o superior

Verificar instalación:

```bash
java -version
mvn -version
```

---

## Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/SantiagoSu15/Microframework-Web.git
```

### 2. Compilar 

```bash
mvn compile
```

### 3. Ejecutar la aplicación de ejemplo

```bash
mvn exec:java -Dexec.mainClass=org.example.rest.MathService
```

El servidor quedara en `http://localhost:35000`.

### 4. Agregar archivos estáticos 

Colocar los archivos en `src/main/resources/webroot/public/`. Maven los copiara a `target/classes/webroot/public/` al compilar.

```
src/main/resources/webroot/public/
├── index.html
├── styles.css
└── imagen_prueba.png
```

---

## Ejemplo 

El archivo `MathService.java` muestra cómo un dev puede construir la aplicacion sobre el framework:

```java

public class MathService {
    public static void main(String[] args) throws IOException, URISyntaxException {

        staticfiles("/webroot/public");         // Define la carpeta de archivos estaticos

        // Define rutas GET con funciones lambda
        get("/pi", (req, res) -> req.getPath().substring(1) + " " + Math.PI);

        get("/eulers", (req, res) -> "Euler: " + Math.E);

        get("/hellow", (req, res) -> req.getPath().substring(1) + "Hola mundo");

        get("/saludo", (req, res) -> req.getPath().substring(1) + " " + req.getQueryParams("name"));

        // Inicia el servidor
        HttpServer.main(args);
    }
}
```



## Pruebas realizadas

### Prueba 1 — Ruta `/pi`

```bash
curl http://localhost:35000/pi
```

Respuesta esperada:
```html
<h1>pi 3.141592653589793</h1>
```
Respuesta:

![imagen](/public/piCurl.png)

---

### Prueba 2 — Ruta `/saludo` con query param

```bash
curl "http://localhost:35000/saludo?name=Carlos"
```

Respuesta esperada:
```html
<h1>saludo Carlos</h1>
```
Respuesta:

![imagen](/public/saludoCurl.png)

---

### Prueba 3 — Archivo estático HTML

```bash
curl http://localhost:35000/index.html
```

Respuesta esperada: contenido del archivo `index.html`.

Respuesta:

![imagen](/public/indexCurl.png)

---

### Prueba 4 — Archivo estático CSS

```bash
curl http://localhost:35000/style.css
```

Respuesta esperada: contenido del archivo CSS.

Respuesta:

![imagen](/public/styleCurl.png)

---

### Prueba 5 — Ruta no encontrada (404)

```bash
curl http://localhost:35000/no-existe
```

Respuesta esperada:
```html
<h1>404 - Not Found</h1>
```

Respuesta:

![imagen](/public/404Curl.png)
---

### Pruebas extra (Navegador)


Ruta:

![imagen](/public/url.png)

Respuesta:

![imagen](/public/salud.png)

Logs en consola:

![imagen](/public/log.png)

Solicitud index.html:

![imagen](/public/index.png)



---

## Construido con

- **Java** — Lenguaje principal
- **Maven** — Gestión de dependencias y build
- **Java Sockets** (`java.net.ServerSocket`) — Servidor HTTP desde cero, sin frameworks externos

---

## Autor

* **Santiago Suarez** — [SantiagoSu15](https://github.com/SantiagoSu15)