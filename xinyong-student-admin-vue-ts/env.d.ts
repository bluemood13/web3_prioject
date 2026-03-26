/// <reference types="vite/client" />

interface ImportMetaEnv {
  VITE_PORT: number;
  VITE_API: string;
  VITE_TITLE: string;
  VITE_KEY: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
