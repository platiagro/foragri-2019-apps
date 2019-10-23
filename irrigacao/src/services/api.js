import axios from 'axios';

const api = axios.create({
  baseURL: 'http://10.50.11.147:8000',
});

export { api };
