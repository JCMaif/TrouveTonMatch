import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  root: '.', // La racine du projet Vite (dossier racine de votre projet)
  base: '/', // Chemin de base pour servir les fichiers
  resolve: {
    alias: {
      '@': '/src/js', // Alias pour simplifier les imports
    },
  },
  build: {
    outDir: 'src/main/resources/static', // Output du build pour Spring Boot
    emptyOutDir: true,
  },
});
