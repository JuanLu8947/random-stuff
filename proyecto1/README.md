## Proyecto UCO Reviews

Esta aplicacion usa Java Swing. En entornos sin escritorio grafico (como un contenedor), la interfaz se visualiza con Xvfb + noVNC.

## Requisitos

Instala estas dependencias en Ubuntu/Debian:

```bash
sudo apt-get update
sudo apt-get install -y xvfb x11vnc novnc websockify
```

Si aparece un error de firma GPG por el repo de Yarn, desactivalo y repite:

```bash
if [ -f /etc/apt/sources.list.d/yarn.list ]; then
  sudo sed -i 's/^deb /# deb /' /etc/apt/sources.list.d/yarn.list
fi
sudo apt-get update
```

## Compilar

Desde la raiz del workspace:

```bash
cd /workspaces/Sistemas_Interactivos && javac -target 11 -source 11 -d bin $(find proyecto/src -name '*.java') 2>&1 | sed -n '1,200p'
```

## Ejecutar interfaz grafica en contenedor

Desde la raiz del workspace:

```bash
cd /workspaces/Sistemas_Interactivos/proyecto
./run-gui.sh
```

Luego abre en el navegador:

```text
http://127.0.0.1:6080/vnc.html?autoconnect=true&resize=scale
```

## Detener servicios graficos

```bash
cd /workspaces/Sistemas_Interactivos/proyecto
./stop-gui.sh
```

## Estructura principal

- proyecto/src: codigo fuente Java
- bin: clases compiladas
- run-gui.sh: levanta Xvfb + x11vnc + noVNC + MainFrame
- stop-gui.sh: detiene los procesos de entorno grafico

