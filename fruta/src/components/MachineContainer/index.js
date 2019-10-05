import React from 'react';
import PropTypes from 'prop-types';

import './style.scss';
import { Layout } from 'antd';

import MachineHeader from '../MachineHeader';
import MachineContent from '../MachineContent';

const { Header, Content } = Layout;

const MachineContainer = (props) => {
  const { data } = props;
  return (
    <>
      <Header>
        <MachineHeader />
      </Header>
      {/* <div style={{ margin: '40px' }}> */}
      <Layout className='machine-container'>
        {/* <Layout className='machine-content'> */}
        <Content className='machine-wrapper'>
          <MachineContent data={data} />
        </Content>
        {/* </Layout> */}
      </Layout>
      {/* </div> */}
    </>
  );
};

MachineContainer.propTypes = {
  data: PropTypes.array
};

export default MachineContainer;
