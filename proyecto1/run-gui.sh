#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="/workspaces/Sistemas_Interactivos"
DISPLAY_NUM=":99"
NOVNC_PORT="6080"
VNC_PORT="5901"
RUNTIME_DIR="/tmp/sisint-gui"
MAX_RETRIES=3
RETRY_DELAY=2

mkdir -p "$RUNTIME_DIR"

# Copiar recursos al classpath antes de arrancar Java.
sync_resources() {
  mkdir -p "$ROOT_DIR/bin/resources" "$ROOT_DIR/bin/bundle"
  cp -R "$ROOT_DIR/proyecto/src/resources/." "$ROOT_DIR/bin/resources/" 2>/dev/null || true
  cp -R "$ROOT_DIR/proyecto/src/bundle/." "$ROOT_DIR/bin/bundle/" 2>/dev/null || true
}

# Función: limpiar todos los procesos pendientes
cleanup_processes() {
  echo "[Cleanup] Deteniendo procesos previos..."
  pkill -9 -f "Xvfb $DISPLAY_NUM" || true
  pkill -9 -f "x11vnc .*${VNC_PORT}" || true
  pkill -9 -f "websockify .*${NOVNC_PORT}" || true
  pkill -9 -f "main.MainFrame" || true
  sleep 2
}

# Función: esperar a que un puerto esté libre
wait_port_free() {
  local port=$1
  local timeout=10
  local elapsed=0
  while lsof -i ":$port" >/dev/null 2>&1; do
    if [ $elapsed -ge $timeout ]; then
      echo "[ERROR] Puerto $port sigue ocupado tras ${timeout}s. Fuerza kill más agresivo."
      return 1
    fi
    echo "[Espera] Puerto $port aún ocupado, esperando..."
    sleep 1
    elapsed=$((elapsed + 1))
  done
  echo "[OK] Puerto $port libre"
  return 0
}

# Limpiar e inicializar
cleanup_processes
sync_resources
wait_port_free "$VNC_PORT" || true
wait_port_free "$NOVNC_PORT" || true
sleep 1

# Iniciar servicios
echo "[Init] Levantando Xvfb en $DISPLAY_NUM..."
nohup Xvfb "$DISPLAY_NUM" -screen 0 1280x800x24 -ac -nolisten tcp > "$RUNTIME_DIR/xvfb.log" 2>&1 &
XVFB_PID=$!
sleep 2

echo "[Init] Levantando x11vnc en puerto $VNC_PORT..."
nohup x11vnc -display "$DISPLAY_NUM" -forever -shared -nopw -rfbport "$VNC_PORT" -localhost > "$RUNTIME_DIR/x11vnc.log" 2>&1 &
VNC_PID=$!
sleep 1

echo "[Init] Levantando websockify/noVNC en puerto $NOVNC_PORT..."
nohup websockify --web=/usr/share/novnc "$NOVNC_PORT" "127.0.0.1:${VNC_PORT}" > "$RUNTIME_DIR/novnc.log" 2>&1 &
NOVNC_PID=$!
sleep 1

echo "[Init] Levantando MainFrame en display $DISPLAY_NUM..."
nohup env DISPLAY="$DISPLAY_NUM" java -cp "$ROOT_DIR/bin" main.MainFrame > "$RUNTIME_DIR/mainframe.log" 2>&1 &
JAVA_PID=$!
sleep 2

# Validar que todos los procesos estén vivos
echo "[Validación] Comprobando procesos..."
for pid in $XVFB_PID $VNC_PID $NOVNC_PID $JAVA_PID; do
  if kill -0 "$pid" 2>/dev/null; then
    echo "  ✓ PID $pid está activo"
  else
    echo "  ✗ PID $pid NO está activo (posible fallo de inicio)"
  fi
done

echo ""
echo "═════════════════════════════════════════════════════════════"
LOCAL_NOVNC_URL="http://127.0.0.1:${NOVNC_PORT}/vnc.html?autoconnect=true&resize=scale"
if [[ "${CODESPACES:-}" == "true" && -n "${GITHUB_CODESPACES_PORT_FORWARDING_DOMAIN:-}" ]]; then
  PUBLIC_NOVNC_URL="https://${CODESPACE_NAME}-${NOVNC_PORT}.${GITHUB_CODESPACES_PORT_FORWARDING_DOMAIN}/vnc.html?autoconnect=true&resize=scale"
  echo "✓ GUI levantada en noVNC:"
  echo "  → ${PUBLIC_NOVNC_URL}"
  echo "  → Local: ${LOCAL_NOVNC_URL}"
else
  echo "✓ GUI levantada en noVNC:"
  echo "  → ${LOCAL_NOVNC_URL}"
fi
echo ""
echo "Logs en: $RUNTIME_DIR"
echo "  - Xvfb:      $RUNTIME_DIR/xvfb.log"
echo "  - x11vnc:    $RUNTIME_DIR/x11vnc.log"
echo "  - noVNC:     $RUNTIME_DIR/novnc.log"
echo "  - MainFrame: $RUNTIME_DIR/mainframe.log"
echo ""
echo "Para parar:  cd proyecto && ./stop-gui.sh"
echo "═════════════════════════════════════════════════════════════"
