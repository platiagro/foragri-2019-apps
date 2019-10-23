import React from 'react';
import PropTypes from 'prop-types';

import './style.scss';
import { Layout } from 'antd';

import IrrigationHeader from '../IrrigationHeader';
import IrrigationContent from '../IrrigationContent';

const { Header, Content } = Layout;

const IrrigationContainer = (props) => {
  const { data } = props;
  return (
    <>
      <Header>
        <IrrigationHeader />
      </Header>
      {/* <div style={{ margin: '40px' }}> */}
      <Layout className='machine-container'>
        {/* <Layout className='machine-content'> */}
        <Content className='machine-wrapper'>
          <IrrigationContent data={data} />
        </Content>
        {/* </Layout> */}
      </Layout>
      {/* </div> */}
    </>
  );
};

IrrigationContainer.propTypes = {
  data: PropTypes.array
};

export default IrrigationContainer;
