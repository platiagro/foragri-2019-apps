/*
  Main application header.

  This component is responsible for displaying the logo and main menu of
  the application.

  This component is also responsible for changing the main routes of the application according to menu clicks.
*/

import React from 'react';

import PropTypes from 'prop-types';

import { Menu } from 'antd';

import logo from '../../assets/logo.png';
import logoIrrigacao from '../../assets/logoIrrigacao.png';
import logoDemo from '../../assets/logoDemo.png';

import './style.scss';

import getCurrentRoute from '../../utils';

const MainHeader = ({ location, mainRoutes }) => (
  <div>
    {/* left logo PlatIAgro */}
    <div className='logo'>
      <img src={logoIrrigacao} alt='Irrigação' />
      <span className='machine-header-title'>Irrigação</span>
    </div>
    {/* right logo Demonstração ForAgri */}
    <div className='logoDemo'>
      <img src={logo} alt='PlatIAgro logo' />
      <div className='machine-header-divider'></div>
      <img src={logoDemo} alt='Demonstração ForAGRI' />
    </div>
    <Menu
      className='machine-header-menu'
      theme='dark'
      mode='horizontal'
      selectedKeys={[
        location && mainRoutes
          ? getCurrentRoute(location, mainRoutes).path
          : null,
      ]}
    >
    </Menu>
  </div>
);

MainHeader.propTypes = {
  location: PropTypes.objectOf(PropTypes.any),
  mainRoutes: PropTypes.arrayOf(PropTypes.any),
};

MainHeader.defaultProps = {
  location: null,
  mainRoutes: null,
};

export default MainHeader;
