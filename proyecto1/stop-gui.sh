#!/usr/bin/env bash
set -euo pipefail

echo "[Stop] Deteniendo servicios GUI..."

# Matar en orden inverso con escalado de señales
echo "  - Deteniendo MainFrame..."
pkill -TERM -f "main.MainFrame" || true
sleep 1
pkill -9 -f "main.MainFrame" || true

echo "  - Deteniendo noVNC/websockify..."
pkill -TERM -f "websockify" || true
sleep 1
pkill -9 -f "websockify" || true

echo "  - Deteniendo x11vnc..."
pkill -TERM -f "x11vnc" || true
sleep 1
pkill -9 -f "x11vnc" || true

echo "  - Deteniendo Xvfb..."
pkill -TERM -f "Xvfb :99" || true
sleep 1
pkill -9 -f "Xvfb" || true

echo ""
echo "═════════════════════════════════════════════════════════════"
echo "✓ GUI detenida correctamente"
echo "═════════════════════════════════════════════════════════════"
