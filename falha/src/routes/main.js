/* istanbul ignore file */
import Fruit from '../pages/Fruit';
import Machine from '../pages/Machine';
import E404 from '../pages/E404'; // 404 error

const mainRoutes = [
  {
    path: '*',
    exact: true,
    icon: 'dollar',
    title: 'Falha em Máquina',
    component: Machine,
  },
];

export default mainRoutes;
