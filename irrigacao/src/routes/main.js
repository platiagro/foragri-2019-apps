/* istanbul ignore file */
import Irrigation from '../pages/Irrigation';

const mainRoutes = [
  {
    path: '*',
    exact: true,
    icon: 'dollar',
    title: 'Irrigação',
    component: Irrigation,
  },
];

export default mainRoutes;
