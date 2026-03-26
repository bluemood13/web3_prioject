import { createPinia, type Pinia } from "pinia";
import persist from "pinia-plugin-persistedstate";

export const p: Pinia = createPinia();
p.use(persist);
