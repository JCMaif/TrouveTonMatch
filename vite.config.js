import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  root: '.', // La racine du projet Vite
  base: '/', // Chemin de base pour servir les fichiers
  resolve: {
    alias: {
      '@': '/src/js', // Alias pour simplifier les imports
    },
  },
  build: {
    outDir: 'dist', // Output du build pour Spring Boot
    emptyOutDir: true,
  },
});
