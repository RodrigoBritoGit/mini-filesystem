import { createApp } from 'vue'
import App from './App.vue'
const app = createApp(App);
app.config.compilerOptions.isCustomElement = (tag) => {
    return tag === 'your-custom-element'; // replace with your custom element name
  };

createApp(App).mount('#app')
