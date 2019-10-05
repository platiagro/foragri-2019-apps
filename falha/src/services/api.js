import axios from 'axios';
// import { connect } from 'mqtt';

// const client  = connect('mqtt://10.50.11.147');

const post = (url, data) => {
  // client.publish('/admin/b2460e/attrs', JSON.stringify(data));
  const pathArray = url.split('/');
  const protocol = pathArray[0];
  const host = pathArray[2];
  const baseURL = protocol + '//' + host;
  const path = pathArray.slice(3).join('/');
  const api = axios.create({
    baseURL: baseURL,
  });
  return api.post(path, data);
};

export { post };
