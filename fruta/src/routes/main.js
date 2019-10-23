/* istanbul ignore file */
import Fruit from '../pages/Fruit';
import Machine from '../pages/Machine';
import E404 from '../pages/E404'; // 404 error

const mainRoutes = [
  {
    path: '*',
    exact: true,
    icon: 'dollar',
    title: 'Cotações de Frutas',
    component: Fruit,
  },
];

export default mainRoutes;
